package com.dao;

import com.model.StudentHomework;
import com.model.StudentSelection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentSelectionMapper {
    int deleteStudentSelectionBySid(Integer sId);

    int addStudentSelection(StudentSelection studentSelection);

    int selectHomeworkCount(Integer cId);


    List<StudentSelection> selectStudentCourseBySid(Integer sId);

    List<StudentSelection> selectStudentByCid(Integer cId);

    List<StudentSelection> selectScoreByCid(Integer cId);

    List<StudentHomework> selectAllScore(@Param("cId")Integer cId, @Param("sId")Integer sId);

    int updateExamScoreBySid(StudentSelection studentSelection);

    StudentSelection selectMyScoreByCidAndSid(@Param("cId")Integer cId, @Param("sId")Integer sId);

}