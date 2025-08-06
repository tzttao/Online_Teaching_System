package com.dao;

import com.model.Curriculum;
import com.model.Homework;
import com.model.StudentSelection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeworkMapper {
    int deleteHomeworkByHid(Integer hId);

    int addHomework(Homework homework);

    Homework selectHomeworkByHid(Integer hId);

    List<Homework> selectCourseByCRidAndTid(@Param("crId") Integer crId, @Param("tId") Integer tId);

    List<Homework> selectCourseByCRid(@Param("crId") Integer crId);

    //List<Homework> selectStudentHomeworkBySidAndCRid(@Param("crId")Integer crId, @Param("sId")Integer sId);
    int updateHomeworkByHid(Homework homework);

}