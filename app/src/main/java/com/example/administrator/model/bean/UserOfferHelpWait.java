package com.example.administrator.model.bean;

import java.sql.Timestamp;

public class UserOfferHelpWait {

    private int userOfferHelpWaitId;
    private int needHelpId;

    private int status;
    private String startWaitDateTime;
    private int willingToWaitTime;
    private String endDateTime;

    private int willingToWaitFlag;


    public int getUserOfferHelpWaitId() {
        return userOfferHelpWaitId;
    }

    public void setUserOfferHelpWaitId(int userOfferHelpWaitId) {
        this.userOfferHelpWaitId = userOfferHelpWaitId;
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

    public String getStartWaitDateTime() {
        return startWaitDateTime;
    }

    public void setStartWaitDateTime(String startWaitDateTime) {
        this.startWaitDateTime = startWaitDateTime;
    }

    public int getWillingToWaitFlag() {
        return willingToWaitFlag;
    }

    public void setWillingToWaitFlag(int willingToWaitFlag) {
        this.willingToWaitFlag = willingToWaitFlag;
    }

    public int getWillingToWaitTime() {
        return willingToWaitTime;
    }

    public void setWillingToWaitTime(int willingToWaitTime) {
        this.willingToWaitTime = willingToWaitTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
