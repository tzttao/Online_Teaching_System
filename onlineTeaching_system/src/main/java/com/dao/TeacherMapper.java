package com.dao;

import com.model.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {
    //删除教师信息
    int deleteTeacherByTid(Integer tId);

    //教师注册
    int addTeacher(Teacher teacher);

    //通过教师id查询教师信息
    Teacher selectTeacherByTid(Integer tId);

    //通过教师username查询教师信息
    Teacher selectTeacherByUsername(String tUsername);

    //通过教师id修改教师信息
    int updateTeacherByTid(Teacher teacher);

    //教师登录
    Teacher teacherLogin(@Param("tUsername") String tUsername, @Param("tPassword") String tPassword);
}