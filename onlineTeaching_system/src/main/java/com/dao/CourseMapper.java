package com.dao;

import com.model.Course;

import java.util.List;

public interface CourseMapper {

    //增加课程
    int addCourse(Course course);

    //通过教师id查询所有课程
    List<Course> selectCourseByTid(Integer tId);

    //通过课程id查询课程
    Course selectCourseByCid(Integer cId);

    //修改课程信息
    int updateCourseByCid(Course course);

    // 通过课程id删除课程
    int deleteCourseByCid(Integer cId);


}