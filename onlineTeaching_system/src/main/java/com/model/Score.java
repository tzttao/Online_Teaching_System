package com.model;

import java.io.Serializable;

public class Score implements Serializable {
    //成绩
    private Integer scId;

    private Integer cId;

    private Integer sId;

    private Double dailyScore;

    private Double finalExam;

    private Double finalScore;

    private static final long serialVersionUID = 1L;

    public Score(Integer scId, Integer cId, Integer sId, Double dailyScore, Double finalExam, Double finalScore) {
        this.scId = scId;
        this.cId = cId;
        this.sId = sId;
        this.dailyScore = dailyScore;
        this.finalExam = finalExam;
        this.finalScore = finalScore;
    }

    public Score() {
        super();
    }

    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Double getDailyScore() {
        return dailyScore;
    }

    public void setDailyScore(Double dailyScore) {
        this.dailyScore = dailyScore;
    }

    public Double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(Double finalExam) {
        this.finalExam = finalExam;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }
}