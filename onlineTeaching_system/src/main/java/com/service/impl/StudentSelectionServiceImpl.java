package com.service.impl;

import com.dao.StudentMapper;
import com.dao.StudentSelectionMapper;
import com.model.StudentHomework;
import com.model.StudentSelection;
import com.service.StudentSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
public class StudentSelectionServiceImpl implements StudentSelectionService {
    @Autowired
    StudentSelectionMapper studentSelectionMapper;

    @Override
    public double selectAllScore(Integer cId, Integer sId) {
        double scoreAll = 0.0;
        for (StudentHomework ss : studentSelectionMapper.selectAllScore(cId, sId)) {
            if(ss==null){
                break;
            }
            scoreAll = scoreAll+ss.getShScore();
        }
        return scoreAll;
    }

}
