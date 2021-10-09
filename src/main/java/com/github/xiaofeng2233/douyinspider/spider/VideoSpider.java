package com.github.xiaofeng2233.douyinspider.spider;

import com.github.xiaofeng2233.douyinspider.bean.Video;
import com.github.xiaofeng2233.douyinspider.bean.VideoInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class VideoSpider {
    private String videoUrl;
    private final String API_URL = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=%s";

    public VideoSpider(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Video getVideo() throws IOException {
        String videoId = getVideoId(this.videoUrl);
        String api = String.format(this.API_URL, videoId);
        OkHttpClient client = getHttpClient();
        Request request = getRequest(api);
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray itemList = jsonObject.getJSONArray("item_list");
        JSONObject item = itemList.getJSONObject(0);
        JSONObject statistics = item.getJSONObject("statistics");
        String Description = String.valueOf(item.get("desc"));
        JSONObject author = item.getJSONObject("author");
        JSONObject video = item.getJSONObject("video");
        Video v = new Video();
        v.setVideoDescription(Description);
        v.setLikeCount((Integer) statistics.get("digg_count"));
        v.setShareCount((Integer) statistics.get("share_count"));
        v.setCommentCount((Integer) statistics.get("comment_count"));
        v.setAuthorNickName(String.valueOf(author.get("nickname")));
        v.setAuthorSignature(String.valueOf(author.get("signature")));
        v.setAuthorAvatar(String.valueOf(author.getJSONObject("avatar_larger").getJSONArray("url_list").get(0)));

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setDuration((Integer) video.get("duration"));
        videoInfo.setWidth((Integer) video.get("width"));
        videoInfo.setHeight((Integer) video.get("height"));
        videoInfo.setVideoUrl(String.valueOf(video.getJSONObject("play_addr").getJSONArray("url_list").get(0)).replace("playwm","play"));
        videoInfo.setCoverUrl(String.valueOf(video.getJSONObject("cover").getJSONArray("url_list").get(0)));
        videoInfo.setDynamicCoverUrl(String.valueOf(video.getJSONObject("dynamic_cover").getJSONArray("url_list").get(0)));
        videoInfo.setRatio(String.valueOf(video.get("ratio")));

        v.setVideoInfo(videoInfo);
        return v;
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }

    private Request getRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .get()
                .build();
        return request;
    }

    private String getVideoId(String url) throws IOException {
        OkHttpClient client = getHttpClient();
        Request request = getRequest(url);
        Response response = client.newCall(request).execute();
        return response.request().url().pathSegments().get(1);
    }
}
