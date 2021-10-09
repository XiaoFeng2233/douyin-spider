package com.github.xiaofeng2233.douyinspider;

import cn.hutool.core.util.RandomUtil;
import com.github.xiaofeng2233.douyinspider.bean.Video;
import com.github.xiaofeng2233.douyinspider.spider.MultiVideoSpider;
import com.github.xiaofeng2233.douyinspider.spider.VideoSpider;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Application {
    public static void main(String[] args) {
        System.out.println("请在输入1或者2选择要抓取的模式");
        System.out.println("1. 通过用户主页抓取多个视频");
        System.out.println("2. 仅通过视频链接抓取单个视频");
        System.out.print("请选择:");
        try {
            Scanner i = new Scanner(System.in);
            Integer type = i.nextInt();
            if (type != 1 && type != 2) {
                throw new Exception();
            }
            if (type == 1) {
                new Application().getMultiVideo();
            } else {
                new Application().getVideo();
            }
        } catch (Exception e) {
            System.out.println("输入错误,已退出");
            System.exit(0);
        }
    }

    private void getMultiVideo() {
        Integer count = 0;
        Integer threadCount = 0;
        String url = "";
        System.out.print("请输入需要抓取的个数 (0表示抓取所有视频) :");
        try {
            Scanner s = new Scanner(System.in);
            count = s.nextInt();
        } catch (Exception e) {
            System.out.println("输入错误,已退出");
            System.exit(0);
        }

        System.out.print("请输入下载的线程数 : ");
        try {
            Scanner s = new Scanner(System.in);
            threadCount = s.nextInt();
        } catch (Exception e) {
            System.out.println("输入错误,已退出");
            System.exit(0);
        }

        System.out.print("请输入要抓取的用户链接 :");
        try {
            Scanner s = new Scanner(System.in);
            url = s.next();
            List<Video> videoList = new MultiVideoSpider(url, count).getVideoList();
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            for (Video v : videoList){
                Future future = executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        Downloader downloader = new Downloader();
                        try {
                            downloader.download(v,"./" + v.getVideoDescription() + RandomUtil.randomString(5) + ".mp4");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("输入错误,已退出");
            System.exit(0);
        }

        MultiVideoSpider spider = new MultiVideoSpider(url, count);
        try {
            List<Video> list = spider.getVideoList();
            for (Video v : list) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private int getVideo() {
        System.out.print("请输入分享的视频链接 :");
        try {
            Scanner s = new Scanner(System.in);
            String url  = s.next();
            Video video = new VideoSpider(url).getVideo();
            Downloader downloader = new Downloader();
            downloader.download(video,"./" + video.getVideoDescription() + RandomUtil.randomString(5) + ".mp4");
        } catch (Exception e) {
            System.out.println("输入错误,已退出");
            System.exit(0);
        }


        return 1;
    }
}
