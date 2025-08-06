package com.model;

import java.io.Serializable;

public class StudentSelection implements Serializable {
    private Integer ssId;
    private Integer sId;
    private Integer cId;
    private Integer dId;
    private String dName;
    private String sName;
    private Integer sAge;
    private String sSex;
    private String sMajor;
    private String sClass;
    private String shScore;
    private Integer hId;
    private Integer crId;
    private Double examScore;
    private Double finalScore;

    private Integer tId;

    private String cName;
    private String tName;
    private String cInfo;
    private Integer cCapacity;
    private static final long serialVersionUID = 1L;

    public StudentSelection(Integer ssId, Integer sId, Integer cId, Integer tId, String cName, String tName, String cInfo, Integer cCapacity) {
        this.ssId = ssId;
        this.sId = sId;
        this.cId = cId;
        this.tId = tId;
        this.cName = cName;
        this.tName = tName;
        this.cInfo = cInfo;
        this.cCapacity = cCapacity;
    }

    public StudentSelection(Integer ssId, Integer sId, Integer cId, Integer dId, String dName, String sName, Integer sAge, String sSex, String sMajor, String sClass, String shScore, Integer hId, Integer crId, Double examScore, Double finalScore) {
        this.ssId = ssId;
        this.sId = sId;
        this.cId = cId;
        this.dId = dId;
        this.dName = dName;
        this.sName = sName;
        this.sAge = sAge;
        this.sSex = sSex;
        this.sMajor = sMajor;
        this.sClass = sClass;
        this.shScore=shScore;
        this.hId=hId;
        this.crId=crId;
        this.examScore=examScore;
        this.finalScore=finalScore;
    }

    public StudentSelection() {
    }

    public Integer getSsId() {
        return ssId;
    }

    public void setSsId(Integer ssId) {
        this.ssId = ssId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Integer getsAge() {
        return sAge;
    }

    public void setsAge(Integer sAge) {
        this.sAge = sAge;
    }

    public String getsSex() {
        return sSex;
    }

    public void setsSex(String sSex) {
        this.sSex = sSex;
    }

    public String getsMajor() {
        return sMajor;
    }

    public void setsMajor(String sMajor) {
        this.sMajor = sMajor;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getShScore() {
        return shScore;
    }

    public void setShScore(String shScore) {
        this.shScore = shScore;
    }

    public Integer gethId() {
        return hId;
    }

    public void sethId(Integer hId) {
        this.hId = hId;
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getcInfo() {
        return cInfo;
    }

    public void setcInfo(String cInfo) {
        this.cInfo = cInfo;
    }

    public Integer getcCapacity() {
        return cCapacity;
    }

    public void setcCapacity(Integer cCapacity) {
        this.cCapacity = cCapacity;
    }
}