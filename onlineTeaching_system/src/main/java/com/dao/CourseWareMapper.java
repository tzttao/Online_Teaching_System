package com.dao;

import com.model.CourseWare;

import java.util.List;

public interface CourseWareMapper {
    int deleteCourseWareByCWid(Integer cwId);

    int addCourseWare(CourseWare courseWare);

    List<CourseWare> selectCourseWareByCRid(Integer crId);

    List<CourseWare> selectCourseWareByCWdoc(String cwDoc);

    CourseWare selectCourseWareByCWid(Integer cwId);

    int updateCourseWareByCWid(CourseWare courseWare);
}