package com.service.impl;

import com.dao.TeacherMapper;
import com.model.Teacher;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
//教师登录
    @Override
    public Teacher TeacherLogin(String tUsername, String tPassword) {
        return teacherMapper.teacherLogin(tUsername,tPassword);
    }

    @Override
    public Teacher selectTeacherByTid(Integer tId) {
        return teacherMapper.selectTeacherByTid(tId);
    }
}
