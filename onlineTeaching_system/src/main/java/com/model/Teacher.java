package com.model;

import java.io.Serializable;

public class Teacher implements Serializable {
    //老师
    private Integer tId;

    private Integer dId;

    private String tName;

    private Integer tAge;

    private String tSex;

    private String tUsername;

    private String tPassword;

    private static final long serialVersionUID = 1L;

    public Teacher(Integer tId, Integer dId, String tName, Integer tAge, String tSex, String tUsername, String tPassword) {
        this.tId = tId;
        this.dId = dId;
        this.tName = tName;
        this.tAge = tAge;
        this.tSex = tSex;
        this.tUsername = tUsername;
        this.tPassword = tPassword;
    }

    public Teacher() {
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettUsername() {
        return tUsername;
    }

    public void settUsername(String tUsername) {
        this.tUsername = tUsername;
    }

    public String gettPassword() {
        return tPassword;
    }

    public void settPassword(String tPassword) {
        this.tPassword = tPassword;
    }

    public String gettSex() {
        return tSex;
    }

    public void settSex(String tSex) {
        this.tSex = tSex;
    }

    public Integer gettAge() {
        return tAge;
    }

    public void settAge(Integer tAge) {
        this.tAge = tAge;
    }

}