package com.example.administrator.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21.
 */

public class UserProblemData  implements Serializable {
    private String questiontitle;
    private String questiondata;
    private int userid;
    private int questionid;

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getQuestiontitle() {
        return questiontitle;
    }

    public void setQuestiontitle(String questiontitle) {
        this.questiontitle = questiontitle;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public String getQuestiondata() {
        return questiondata;
    }

    public void setQuestiondata(String questiondata) {
        this.questiondata = questiondata;
    }





}

