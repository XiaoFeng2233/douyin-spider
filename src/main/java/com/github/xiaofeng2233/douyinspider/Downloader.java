package com.github.xiaofeng2233.douyinspider;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.github.xiaofeng2233.douyinspider.bean.Video;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Downloader {
    Downloader() {
    }

    void download(Video video, String fileDir) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(video.getVideoInfo().getVideoUrl()).build();
        Response response = client.newCall(request).execute();
        String url = response.request().url().toString();
        HttpUtil.downloadFile(url, FileUtil.file(fileDir), new StreamProgress() {
            @Override
            public void start() {
                System.out.println(video.getVideoDescription() + ".mp4,开始下载");
            }

            @Override
            public void progress(long progressSize) {
            }

            @Override
            public void finish() {
                System.out.println(video.getVideoDescription() + ".mp4,下载完成");
            }
        });
    }


}
