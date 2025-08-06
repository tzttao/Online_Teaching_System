package com.service.impl;

import com.dao.CurriculumMapper;
import com.service.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurriculumServiceImpl implements CurriculumService {
    @Autowired
    CurriculumMapper curriculumMapper;

}
