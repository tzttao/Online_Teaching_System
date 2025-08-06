package com.dao;

import com.model.StudentHomework;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentHomeworkMapper {
    int deleteByPrimaryKey(Integer shId);

    int insert(StudentHomework record);

    int addHomework(StudentHomework studentHomework);

    StudentHomework selectByPrimaryKey(Integer shId);

    List<StudentHomework> selectSHomeworkByHid(Integer hId);

    StudentHomework selectMyHomeworkByHidAndSId(@Param("hId")Integer hId, @Param("sId")Integer sId);

    int updateBySHid(StudentHomework studentHomework);
}