package com.dao;

import com.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {
    int deleteQuestionByQid(Integer qId);

    int addQuestion(Question question);

    Question selectQuestionByQid(Integer qId);

    List<Question> selectQuestionBySidAndCid(@Param("cId")Integer cId, @Param("sId")Integer sId);

    List<Question> selectQuestionByCid(Integer cId);

    int updateQuestionByQid(Question question);

}