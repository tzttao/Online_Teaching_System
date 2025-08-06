package com.controller;

import com.dao.QuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.Question;
import com.model.StudentSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionMapper questionMapper;

    @RequestMapping("/selectQuestionBySidAndCid")
    @ResponseBody
    public PageInfo<Question> selectQuestionBySidAndCid(@RequestParam(value = "cId", required = false) Integer cId,
                                                    @RequestParam(value = "sId", required = false) Integer sId,
                                                    @RequestParam(required = false, value = "pageNum") Integer pageNum,
                                                    @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questionList = questionMapper.selectQuestionBySidAndCid(cId,sId);
        PageInfo pageInfo = new PageInfo<>(questionList);
        if (questionList != null && !questionList.isEmpty()) {
            return pageInfo;
        } else {
            return null;
        }
    }

    @RequestMapping("/selectQuestionByCid")
    @ResponseBody
    public PageInfo<StudentSelection> selectQuestionByCid(@RequestParam(value = "cId", required = false) Integer cId,
                                                          @RequestParam(required = false, value = "pageNum") Integer pageNum,
                                                          @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questionList = questionMapper.selectQuestionByCid(cId);
        PageInfo pageInfo = new PageInfo<>(questionList);
        if (questionList != null && !questionList.isEmpty()) {
            return pageInfo;
        } else {
            return null;
        }
    }

    @RequestMapping("/addQuestion")
    @ResponseBody
    public void addQuestion(@RequestParam(value = "cId", required = false) Integer cId,
                            @RequestParam(value = "tId", required = false) Integer tId,
                              @RequestParam(value = "sId", required = false) Integer sId,
                              @RequestParam(value = "qTitle", required = false) String qTitle,
                              @RequestParam(value = "qInfo", required = false) String qInfo) {
            Question question=new Question();
            question.setcId(cId);
            question.setsId(sId);
            question.setqTitle(qTitle);
            question.setqInfo(qInfo);
            question.settId(tId);
            questionMapper.addQuestion(question);
    }

    @RequestMapping("/selectQuestionByQid")
    @ResponseBody
    public Question selectQuestionByQid(@RequestParam(value = "qId", required = false) Integer qId) {
        questionMapper.selectQuestionByQid(qId);
        return questionMapper.selectQuestionByQid(qId);
    }

    @RequestMapping("/updateQuestion")
    @ResponseBody
    public void updateQuestion(@RequestParam(value = "qId", required = false) Integer qId,
                               @RequestParam(value = "qTitle", required = false) String qTitle,
                               @RequestParam(value = "qInfo", required = false) String qInfo,
                               @RequestParam(value = "qAnswer", required = false) String qAnswer) {
        Question q=new Question();
        q.setqId(qId);
        q.setqTitle(qTitle);
        q.setqInfo(qInfo);
        q.setqAnswer(qAnswer);
        if(qAnswer!=null){
            q.setqTime(new Date());
        }
        questionMapper.updateQuestionByQid(q);
    }

    @RequestMapping("/deleteQuestion")
    @ResponseBody
    public void deleteQuestion(@RequestParam(value = "qId", required = false) Integer qId) {
        questionMapper.deleteQuestionByQid(qId);
    }
}
