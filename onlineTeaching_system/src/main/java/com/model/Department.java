package com.model;

import java.io.Serializable;

public class Department implements Serializable {
    //专业
    private Integer dId;

    private String dName;

    private static final long serialVersionUID = 1L;

    public Department(Integer dId, String dName) {
        this.dId = dId;
        this.dName = dName;
    }

    public Department() {
        super();
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
}