package com.model;

import java.io.Serializable;
import java.util.Date;

public class StudentHomework implements Serializable {
    private Integer shId;

    private Integer sId;

    private Integer tId;

    private Integer hId;

    private String shDoc;

    private Double shScore;

    private String sName;

    private String tName;

    private Integer cId;

    private Integer crId;

    private String hName;

    private String hInfo;

    private Date hTime;

    private static final long serialVersionUID = 1L;

    public StudentHomework(Integer cId,Integer crId,Integer shId, Integer sId, Integer tId, Integer hId, String shDoc, Double shScore,String sName,String tName,String hName,String hInfo,Date hTime) {
        this.shId = shId;
        this.sId = sId;
        this.tId = tId;
        this.hId = hId;
        this.shDoc = shDoc;
        this.shScore = shScore;
        this.sName=sName;
        this.tName=tName;
        this.cId = cId;
        this.crId = crId;
        this.hName=hName;
        this.hInfo=hInfo;
        this.hTime=hTime;
    }

    public Date gethTime() {
        return hTime;
    }

    public void sethTime(Date hTime) {
        this.hTime = hTime;
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

    public String getsName() {
        return sName;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
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

    public StudentHomework() {
        super();
    }

    public Integer getShId() {
        return shId;
    }

    public void setShId(Integer shId) {
        this.shId = shId;
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

    public Integer gethId() {
        return hId;
    }

    public void sethId(Integer hId) {
        this.hId = hId;
    }

    public String getShDoc() {
        return shDoc;
    }

    public void setShDoc(String shDoc) {
        this.shDoc = shDoc;
    }

    public Double getShScore() {
        return shScore;
    }

    public void setShScore(Double shScore) {
        this.shScore = shScore;
    }
}