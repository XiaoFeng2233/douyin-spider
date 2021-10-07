package com.github.xiaofeng2233.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Video implements Serializable {
    /**
     * 视频作者名称
     */
    private String authorNickName;
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
}
