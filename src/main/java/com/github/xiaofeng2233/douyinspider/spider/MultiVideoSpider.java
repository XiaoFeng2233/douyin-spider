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
import java.util.ArrayList;
import java.util.List;

public class MultiVideoSpider {

    /**
     * 抖音获取用户视频API
     */


    private final String API_URL = "https://www.iesdouyin.com/web/api/v2/aweme/%s/?sec_uid=%s&count=%s&max_cursor=%s&aid=1128&_signature=PDHVOQAAXMfFyj02QEpGaDwx1S&dytk=";

    private String userUrl;

    private int maxCount;

    /**
     * userUrl为通过抖音分享的用户主页链接
     * maxCount 为抓取用户视频数量的最大个数   注:传递0或负数,表示不限制个数,抓取用户所有视频
     *
     * @param userUrl
     * @param maxCount
     */
    public MultiVideoSpider(String userUrl, int maxCount) {
        userUrl.contains("");
        this.userUrl = userUrl;
        this.maxCount = maxCount;
    }

    public List<Video> getVideoList() throws IOException {
        ArrayList<Video> videoArrayList;
        if (this.maxCount <= 0) {
            return getVideoUnlimit();
        } else {
            return getVideoLimit(maxCount);
        }

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

    private String getUserId() throws IOException {
        OkHttpClient client = getHttpClient();
        Request request = getRequest(this.userUrl);
        Response response = client.newCall(request).execute();
        return response.request().url().pathSegments().get(1);
    }

    private List<Video> getVideoUnlimit() throws IOException {
        ArrayList<Video> videoArrayList = new ArrayList<>();
        String maxCursor = "0";
        String userId = getUserId();
        while (true) {
            String api = String.format(this.API_URL, "post", userId, "9999", maxCursor);
            Request request = getRequest(api);
            Response response = getHttpClient().newCall(request).execute();
            String jsonStr = response.body().string();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            maxCursor = String.valueOf(jsonObject.get("max_cursor"));
            JSONArray AWEME_LIST = (JSONArray) jsonObject.get("aweme_list");
            for (int i = 0; i < AWEME_LIST.size(); i++) {
                JSONObject o = (JSONObject) AWEME_LIST.get(i);
                JSONObject statistics = (JSONObject) o.get("statistics");
                JSONObject author = (JSONObject) o.get("author");
                String Description = String.valueOf(o.get("desc"));
                Video video = new Video();
                video.setCommentCount((Integer) statistics.get("comment_count"));
                video.setShareCount((Integer) statistics.get("share_count"));
                video.setLikeCount((Integer) statistics.get("digg_count"));
                video.setAuthorAvatar(String.valueOf(author.getJSONObject("avatar_larger").getJSONArray("url_list").get(0)));
                video.setAuthorNickName(String.valueOf(author.get("nickname")));
                video.setAuthorSignature(String.valueOf(author.get("signature")));
                video.setVideoDescription(Description);
                JSONObject videoObject = o.getJSONObject("video");
                VideoInfo videoInfo = new VideoInfo();
                videoInfo.setDuration((Integer) videoObject.get("duration"));
                videoInfo.setHeight((Integer) videoObject.get("height"));
                videoInfo.setWidth((Integer) videoObject.get("width"));
                videoInfo.setRatio(String.valueOf(videoObject.get("ratio")));
                videoInfo.setVideoUrl(String.valueOf(videoObject.getJSONObject("play_addr").getJSONArray("url_list").get(0)));
                videoInfo.setCoverUrl(String.valueOf(videoObject.getJSONObject("cover").getJSONArray("url_list").get(0)));
                videoInfo.setDynamicCoverUrl(String.valueOf(videoObject.getJSONObject("dynamic_cover").getJSONArray("url_list").get(0)));
                video.setVideoInfo(videoInfo);
                videoArrayList.add(video);
            }

            if (maxCursor.equalsIgnoreCase("0")) {
                break;
            }
        }
        return videoArrayList;
    }

    private List<Video> getVideoLimit(int count) throws IOException {
        ArrayList<Video> videoArrayList = new ArrayList<>();
        String maxCursor = "0";
        String userId = getUserId();
        while (videoArrayList.size() < count) {
            String api = String.format(this.API_URL, "post", userId, "9999", maxCursor);
            Request request = getRequest(api);
            Response response = getHttpClient().newCall(request).execute();
            String jsonStr = response.body().string();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            maxCursor = String.valueOf(jsonObject.get("max_cursor"));
            JSONArray AWEME_LIST = (JSONArray) jsonObject.get("aweme_list");
            for (int i = 0; i < AWEME_LIST.size(); i++) {
                if (videoArrayList.size() >= count) break;
                JSONObject o = (JSONObject) AWEME_LIST.get(i);
                JSONObject statistics = (JSONObject) o.get("statistics");
                JSONObject author = (JSONObject) o.get("author");
                String Description = String.valueOf(o.get("desc"));
                Video video = new Video();
                video.setCommentCount((Integer) statistics.get("comment_count"));
                video.setShareCount((Integer) statistics.get("share_count"));
                video.setLikeCount((Integer) statistics.get("digg_count"));
                video.setAuthorAvatar(String.valueOf(author.getJSONObject("avatar_larger").getJSONArray("url_list").get(0)));
                video.setAuthorNickName(String.valueOf(author.get("nickname")));
                video.setAuthorSignature(String.valueOf(author.get("signature")));
                video.setVideoDescription(Description);
                JSONObject videoObject = o.getJSONObject("video");
                VideoInfo videoInfo = new VideoInfo();
                videoInfo.setDuration((Integer) videoObject.get("duration"));
                videoInfo.setHeight((Integer) videoObject.get("height"));
                videoInfo.setWidth((Integer) videoObject.get("width"));
                videoInfo.setRatio(String.valueOf(videoObject.get("ratio")));
                videoInfo.setVideoUrl(String.valueOf(videoObject.getJSONObject("play_addr").getJSONArray("url_list").get(0)));
                videoInfo.setCoverUrl(String.valueOf(videoObject.getJSONObject("cover").getJSONArray("url_list").get(0)));
                videoInfo.setDynamicCoverUrl(String.valueOf(videoObject.getJSONObject("dynamic_cover").getJSONArray("url_list").get(0)));
                video.setVideoInfo(videoInfo);
                videoArrayList.add(video);
            }

            if (maxCursor.equalsIgnoreCase("0")) {
                break;
            }
        }
        return videoArrayList;
    }
}
