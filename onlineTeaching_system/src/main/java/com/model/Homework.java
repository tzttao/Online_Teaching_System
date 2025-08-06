package com.model;

import java.io.Serializable;
import java.util.Date;

public class Homework implements Serializable {
    //作业
    private Integer hId;

    private Integer tId;

    private Integer crId;

    private String hName;

    private String hInfo;

    private String hDoc;

    private Date hTime;

    private String shDoc;

    private static final long serialVersionUID = 1L;

    public Homework(Integer hId, Integer tId, Integer crId, String hName, String hInfo, String hDoc, Date hTime) {
        this.hId = hId;
        this.tId = tId;
        this.crId = crId;
        this.hName = hName;
        this.hInfo = hInfo;
        this.hDoc = hDoc;
        this.hTime = hTime;
    }
    public Homework(Integer hId, Integer tId, Integer crId, String hName, String hInfo, String hDoc, Date hTime, String shDoc) {
        this.hId = hId;
        this.tId = tId;
        this.crId = crId;
        this.hName = hName;
        this.hInfo = hInfo;
        this.hDoc = hDoc;
        this.hTime = hTime;
        this.shDoc = shDoc;
    }

    public Homework() {
        super();
    }

    public String getShDoc() {
        return shDoc;
    }

    public void setShDoc(String shDoc) {
        this.shDoc = shDoc;
    }

    public Integer gethId() {
        return hId;
    }

    public void sethId(Integer hId) {
        this.hId = hId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer getCRid() {
        return crId;
    }

    public void setCRid(Integer crId) {
        this.crId = crId;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public String gethInfo() {
        return hInfo;
    }

    public void sethInfo(String hInfo) {
        this.hInfo = hInfo;
    }

    public String gethDoc() {
        return hDoc;
    }

    public void sethDoc(String hDoc) {
        this.hDoc = hDoc;
    }

    public Date gethTime() {
        return hTime;
    }

    public void sethTime(Date hTime) {
        this.hTime = hTime;
    }

}