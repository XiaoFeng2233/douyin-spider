package com.github.xiaofeng2233.douyinspider.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Video implements Serializable {
    /**
     * 视频作者名称
     */
    private String authorNickName;
    /**
     * 视频作者抖音号
     */
    private String authorAccountId;
    /**
     * 视频作者签名
     */
    private String authorSignature;
    /**
     * 视频作者的头像URL
     */
    private String authorAvatar;
    /**
     * 点赞数量
     */
    private int likeCount;
    /**
     * 评论数量
     */
    private int commentCount;
    /**
     * 分享数量
     */
    private int shareCount;
    /**
     * 视频介绍
     */
    private String videoDescription;
    /**
     * 视频链接详细信息
     */
    private VideoInfo videoInfo;
    /**
     * 视频上传时间
     */
    private Date createdTime;
    /**
     * 视频分享链接
     */
    private String shareUrl;
    /**
     * 纯音频文件地址
     */
    private String audioUrl;
    /**
     * 视频唯一Id
     */
    private String videoId;
}
