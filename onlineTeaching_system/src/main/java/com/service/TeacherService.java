package com.service;

import com.model.Teacher;

public interface TeacherService {

    Teacher TeacherLogin(String tUsername,String tPassword);

    Teacher selectTeacherByTid(Integer tId);

}
