package com.test;

import com.dao.*;
import com.model.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TeacherTest {
    private static final Logger log = Logger.getLogger(TeacherTest.class);
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    CourseMapper cm = context.getBean(CourseMapper.class);
    TeacherMapper tm = context.getBean(TeacherMapper.class);
    StudentMapper sm = context.getBean(StudentMapper.class);
    CourseWareMapper cwm = context.getBean(CourseWareMapper.class);
    StudentHomeworkMapper shm = context.getBean(StudentHomeworkMapper.class);
    StudentSelectionMapper ssm = context.getBean(StudentSelectionMapper.class);
    HomeworkMapper hm = context.getBean(HomeworkMapper.class);

    @Test
    public void addCourse() {
        Course course = new Course();
        course.setcId(100003);
        course.setcName("计算机网络");
        course.settId(1001);
        course.setcInfo("讲解计算机网络相关知识");
        cm.addCourse(course);
    }

    @Test
    public void selectCourse() {
        List<Course> courses = cm.selectCourseByTid(1001);
        for (Course course : courses) {
            log.info("cId" + course.getcId() + "getName:" + course.getcName());
        }
    }

    @Test
    public void updateCourse() {
        Course course = new Course();
        course.setcId(100001);
        course.setcCapacity(151);
        cm.updateCourseByCid(course);
    }

    @Test
    public void deleteCoures() {
        cm.deleteCourseByCid(100002);
    }

    @Test
    public void login() {
        ssm.selectAllScore(200001,2001);
    }
}
