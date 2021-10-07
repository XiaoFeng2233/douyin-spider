package com.github.xiaofeng2233.douyinspider.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoInfo implements Serializable {
    /**
     * 视频链接
     */
    private String videoUrl;
    /**
     * 封面链接
     */
    private String coverUrl;
    /**
     * 动态封面链接
     */
    private String dynamicCoverUrl;
    /**
     * 视频时长 单位 毫秒
     */
    private int duration;
    /**
     * 视频宽度
     */
    private int width;
    /**
     * 视频高度
     */
    private int height;
    /**
     * 视频分辨率
     */
    private String ratio;
}
