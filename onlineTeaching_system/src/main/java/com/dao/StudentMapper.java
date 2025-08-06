package com.dao;

import com.model.Student;
import com.model.Teacher;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {
    //删除学生信息
    int deleteStudentBySid(Integer sId);

    //学生注册
    int addStudent(Student student);

    //通过学生id查询学生信息
    Student selectStudentBySid(Integer sId);

    //通过学生id修改学生信息
    int updateStudentBySid(Student student);

    Student studentLogin(@Param("sUsername") String sUsername, @Param("sPassword") String sPassword);

    Student selectStudentByUsername(String sUsername);
}