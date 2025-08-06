package com.service.impl;

import com.dao.CourseWareMapper;
import com.service.CourseWareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseWareServiceImpl implements CourseWareService {
    @Autowired
    CourseWareMapper courseWareMapper;
}
