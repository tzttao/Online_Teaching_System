package com.model;

import java.io.Serializable;

public class Curriculum implements Serializable {
    //课时
    private Integer crId;

    private Integer cId;

    private String crName;




    private static final long serialVersionUID = 1L;

    public Curriculum(Integer crId, Integer cId, String crName) {
        this.crId = crId;
        this.cId = cId;
        this.crName = crName;
    }

    public Curriculum() {
        super();
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getCrName() {
        return crName;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }


}