package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dao.StudentSelectionMapper;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.Curriculum;
import com.model.StudentSelection;
import com.service.impl.StudentSelectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherScoreController {
    @Autowired
    private StudentSelectionMapper studentSelectionMapper;

    @Autowired
    private StudentSelectionServiceImpl studentSelectionService;

    @RequestMapping("/addExamScore")
    @ResponseBody
    public void addExamScore(@RequestParam(value = "sId", required = false) Integer sId,
                             @RequestParam(value = "examScore", required = false) double examScore) {
        StudentSelection ss = new StudentSelection();
        ss.setsId(sId);
        ss.setExamScore(examScore);
        studentSelectionMapper.updateExamScoreBySid(ss);
    }

    @RequestMapping("/selectStudentByCid")
    @ResponseBody
    public PageInfo<StudentSelection> selectStudentByCid(@RequestParam(value = "cId", required = false) Integer cId,
                                                         @RequestParam(required = false, value = "pageNum") Integer pageNum,
                                                         @RequestParam(required = false, value = "pageSize") Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<StudentSelection> studentSelectionList = studentSelectionMapper.selectStudentByCid(cId);
        PageInfo pageInfo = new PageInfo<>(studentSelectionList);
        if (studentSelectionList != null && !studentSelectionList.isEmpty()) {
            return pageInfo;
        } else {
            return null;
        }
    }

    @RequestMapping("/selectScoreByCid")
    @ResponseBody
    public PageInfo<StudentSelection> selectScoreByCid(@RequestParam(value = "cId", required = false) Integer cId,
                                                 @RequestParam(required = false, value = "pageNum") Integer pageNum,
                                                 @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentSelection> studentSelectionList = studentSelectionMapper.selectScoreByCid(cId);
        PageInfo pageInfo = new PageInfo<>(studentSelectionList);
        if (studentSelectionList != null && !studentSelectionList.isEmpty()) {
            return pageInfo;
        } else {
            return null;
        }
    }
    @RequestMapping("/selectScoreByCid1")
    @ResponseBody
    public List selectScoreByCid1(@RequestParam(value = "cId", required = false) Integer cId) {
        List<StudentSelection> studentSelectionList = studentSelectionMapper.selectScoreByCid(cId);
        if (studentSelectionList != null && !studentSelectionList.isEmpty()) {
            return studentSelectionList;
        } else {
            return null;
        }
    }
    @RequestMapping("/addFinalScore")
    @ResponseBody
    public List addFinalScore(@RequestBody String tableData) {
        //解析json数据
        JSONObject json = JSON.parseObject(tableData);
        Integer cId = Integer.valueOf(json.getString("cId"));
        Double dailyRate = Double.parseDouble(json.getString("dailyRate"));
        Double examRate = Double.parseDouble(json.getString("examRate"));
        List<StudentSelection> ssList = JSON.parseArray(JSON.parseObject(tableData).getString("tableData"), StudentSelection.class);
        for (StudentSelection ss : ssList) {
            double dailyScore = studentSelectionService.selectAllScore(cId, ss.getsId());
            int count = studentSelectionMapper.selectHomeworkCount(cId);
            Double finalScore = (dailyScore * 20 * dailyRate )/count+ss.getExamScore() * examRate;
            BigDecimal b=new BigDecimal(finalScore);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("finalScore:"+finalScore);
            System.out.println("f1:"+f1);
            StudentSelection sss = new StudentSelection();
            sss.setsId(ss.getsId());
            sss.setFinalScore(f1);
            studentSelectionMapper.updateExamScoreBySid(sss);
        }
        return ssList;
    }

}

