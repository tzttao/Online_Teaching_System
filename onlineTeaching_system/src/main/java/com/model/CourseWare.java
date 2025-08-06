package com.model;

import java.io.Serializable;

public class CourseWare implements Serializable {
    private Integer cwId;

    private Integer crId;

    private String cwName;

    private String cwInfo;

    private String cwDoc;

    private Integer cwDownloadtimes;

    private static final long serialVersionUID = 1L;

    public CourseWare(Integer cwId, Integer crId,String cwName, String cwInfo, String cwDoc, Integer cwDownloadtimes) {
        this.cwId = cwId;
        this.crId = crId;
        this.cwName = cwName;
        this.cwInfo = cwInfo;
        this.cwDoc = cwDoc;
        this.cwDownloadtimes = cwDownloadtimes;
    }

    public CourseWare() {
        super();
    }

    public Integer getCwId() {
        return cwId;
    }

    public void setCwId(Integer cwId) {
        this.cwId = cwId;
    }

    public String getCwName() {
        return cwName;
    }

    public void setCwName(String cwName) {
        this.cwName = cwName;
    }

    public String getCwInfo() {
        return cwInfo;
    }

    public void setCwInfo(String cwInfo) {
        this.cwInfo = cwInfo;
    }

    public String getCwDoc() {
        return cwDoc;
    }

    public void setCwDoc(String cwDoc) {
        this.cwDoc = cwDoc;
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public Integer getCwDownloadtimes() {
        return cwDownloadtimes;
    }

    public void setCwDownloadtimes(Integer cwDownloadtimes) {
        this.cwDownloadtimes = cwDownloadtimes;
    }
}