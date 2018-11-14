package com.example.administrator.model.bean;

import java.io.Serializable;


public class UserOfferHelpHistory implements Serializable{

    private int userOfferHelpHistoryId;
    private int needHelpId;
    private int status;
    private int ImageId;
    private String nickname;
    private String startWaitDateTime;
    private String startHelpDateTime;
    private String endDateTime;
    private String userComment;
    private String userCommentDateTime;

    public int getUserOfferHelpHistoryId() {
        return userOfferHelpHistoryId;
    }

    public void setUserOfferHelpHistoryId(int userOfferHelpHistoryId) {
        this.userOfferHelpHistoryId = userOfferHelpHistoryId;
    }

    public int getNeedHelpId() {
        return needHelpId;
    }

    public void setNeedHelpId(int needHelpId) {
        this.needHelpId = needHelpId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStartWaitDateTime() {
        return startWaitDateTime;
    }

    public void setStartWaitDateTime(String startWaitDateTime) {
        this.startWaitDateTime = startWaitDateTime;
    }

    public String getStartHelpDateTime() {
        return startHelpDateTime;
    }

    public void setStartHelpDateTime(String startHelpDateTime) {
        this.startHelpDateTime = startHelpDateTime;
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
