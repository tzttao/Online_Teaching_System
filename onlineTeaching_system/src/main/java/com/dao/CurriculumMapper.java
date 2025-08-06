package com.dao;

import com.model.Curriculum;

import java.util.List;

public interface CurriculumMapper {
    int deleteCurriculumByCRid(Integer crId);

    int addCurriculum(Curriculum curriculum);

    Curriculum selectCurriculumByCRid(Integer crId);

    List<Curriculum> selectCurriculumByCid(Integer cId);

    int updateCurriculumByCRid(Curriculum curriculum);

}