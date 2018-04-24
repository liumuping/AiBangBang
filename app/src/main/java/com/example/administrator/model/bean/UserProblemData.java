package com.example.administrator.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21.
 */

public class UserProblemData  implements Serializable {
    private String questiontitle;
    private String questiondata;
    private int Id;

    public String getQuestitle() {
        return questiontitle;
    }

    public void setQuestitle(String questitle) {
        this.questiontitle = questitle;
    }

    public String getQuestiondata() {
        return questiondata;
    }

    public void setQuestiondata(String questiondata) {
        this.questiondata = questiondata;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


}

