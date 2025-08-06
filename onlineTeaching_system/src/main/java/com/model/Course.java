package com.model;

import java.io.Serializable;

public class Course implements Serializable {
    //课程
    private Integer cId;

    private Integer tId;

    private String cName;

    private String cInfo;

    private Integer cCapacity;

    private static final long serialVersionUID = 1L;

    public Course(Integer cId, Integer tId, String cName, String cInfo, Integer cCapacity) {
        this.cId = cId;
        this.tId = tId;
        this.cName = cName;
        this.cInfo = cInfo;
        this.cCapacity = cCapacity;
    }

    public Course() {
        super();
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
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