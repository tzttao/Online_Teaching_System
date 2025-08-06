package com.model;

import java.io.Serializable;

public class Student implements Serializable {
    //学生
    private Integer sId;

    private Integer dId;

    private String sName;

    private Integer sAge;

    private String sSex;

    private String sUsername;

    private String sPassword;

    private String sMajor;

    private String sClass;

    private static final long serialVersionUID = 1L;

    public Student(Integer sId, Integer dId, String sName,Integer sAge,String sSex, String sUsername, String sPassword, String sMajor, String sClass) {
        this.sId = sId;
        this.dId = dId;
        this.sName = sName;
        this.sAge = sAge;
        this.sSex = sSex;
        this.sUsername = sUsername;
        this.sPassword = sPassword;
        this.sMajor = sMajor;
        this.sClass = sClass;
    }
    public Student() {
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
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

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }
}