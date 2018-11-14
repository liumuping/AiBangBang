package com.example.administrator.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/14.
 */

public class ReBang implements Serializable{
    private String nickname;
    private int imageId;
    private int needHelpId;
    private int userNeedHelpId;
    private int status;
    private String details;
    private String createDateTime;
    private String willingToWaitTime;
    private String endDateTime;
    private String userComment;
    private String userCommentDateTime;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getNeedHelpId() {
        return needHelpId;
    }

    public void setNeedHelpId(int needHelpId) {
        this.needHelpId = needHelpId;
    }

    public int getUserNeedHelpId() {
        return userNeedHelpId;
    }

    public void setUserNeedHelpId(int userNeedHelpId) {
        this.userNeedHelpId = userNeedHelpId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getWillingToWaitTime() {
        return willingToWaitTime;
    }

    public void setWillingToWaitTime(String willingToWaitTime) {
        this.willingToWaitTime = willingToWaitTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getUserCommentDateTime() {
        return userCommentDateTime;
    }

    public void setUserCommentDateTime(String userCommentDateTime) {
        this.userCommentDateTime = userCommentDateTime;
    }




}
