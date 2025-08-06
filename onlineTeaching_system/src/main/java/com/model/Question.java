package com.model;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
    //答疑
    private Integer qId;

    private String qTitle;

    private String qInfo;

    private Date qTime;

    private Integer sId;

    private Integer tId;

    private String qAnswer;

    private Integer cId;

    private String sName;

    private String tName;

    private static final long serialVersionUID = 1L;

    public Question(Integer qId, String qTitle, String qInfo, Date qTime, Integer sId, Integer tId,String qAnswer,Integer cId) {
        this.qId = qId;
        this.qTitle = qTitle;
        this.qInfo = qInfo;
        this.qTime = qTime;
        this.sId = sId;
        this.tId = tId;
        this.qAnswer=qAnswer;
        this.cId=cId;
    }

    public Question(Integer qId, String qTitle, String qInfo, Date qTime, Integer sId, Integer tId, String qAnswer, Integer cId, String sName, String tName) {
        this.qId = qId;
        this.qTitle = qTitle;
        this.qInfo = qInfo;
        this.qTime = qTime;
        this.sId = sId;
        this.tId = tId;
        this.qAnswer = qAnswer;
        this.cId = cId;
        this.sName = sName;
        this.tName = tName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Question() {
        super();
    }

    public String getqAnswer() {
        return qAnswer;
    }

    public void setqAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getqId() {
        return qId;
    }

    public void setqId(Integer qId) {
        this.qId = qId;
    }

    public String getqTitle() {
        return qTitle;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public String getqInfo() {
        return qInfo;
    }

    public void setqInfo(String qInfo) {
        this.qInfo = qInfo;
    }

    public Date getqTime() {
        return qTime;
    }

    public void setqTime(Date qTime) {
        this.qTime = qTime;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }
}