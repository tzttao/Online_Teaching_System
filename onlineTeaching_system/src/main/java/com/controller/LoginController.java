package com.controller;

import com.dao.DepartmentMapper;
import com.dao.StudentMapper;
import com.dao.TeacherMapper;
import com.model.CourseWare;
import com.model.Department;
import com.model.Student;
import com.model.Teacher;
import com.service.TeacherService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @RequestMapping("/teacherLogin")
    @ResponseBody
    @CrossOrigin
    public int teacherLogin(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {
        System.out.println(username + "+" + password);
        Teacher teacher = teacherService.TeacherLogin(username, password);
        if (teacher != null) {
            //session.setAttribute("teacher_session", teacher);
            System.out.println("登录成功");
            return teacher.gettId();
        } else {
            System.out.println("登录失败");
            return 0;
        }
    }

    @RequestMapping("/studentLogin")
    @ResponseBody
    @CrossOrigin
    public int studentLogin(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {
        System.out.println(username + "+" + password);
        Student student = studentMapper.studentLogin(username, password);
        if (student != null) {
            System.out.println("登录成功");
            return student.getsId();
        } else {
            System.out.println("登录失败");
            return 0;
        }
    }

    @RequestMapping("/teacherRegister")
    @ResponseBody
    @CrossOrigin
    public Object teacherRegister(@RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "password", required = false) String password,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "dId", required = false) Integer dId,
                                  @RequestParam(value = "sex", required = false) String sex,
                                  @RequestParam(value = "age", required = false) Integer age) {
        Map<String, Object> map = new HashMap<>();
        Teacher isExist = teacherMapper.selectTeacherByUsername(username);
        System.out.println("1111" + isExist);
        if (isExist == null) {
            Teacher teacher = new Teacher();
            teacher.settUsername(username);
            teacher.settPassword(password);
            teacher.settName(name);
            teacher.setdId(dId);
            teacher.settSex(sex);
            teacher.settAge(age);
            teacherMapper.addTeacher(teacher);
            map.put("code", 200);
            map.put("message", "注册成功");
        } else {
            map.put("code", 500);
            map.put("message", "注册失败 已存在同名用户");
        }
        return map;
    }

    @RequestMapping("/studentRegister")
    @ResponseBody
    @CrossOrigin
    public Object studentRegister(@RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "password", required = false) String password,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "dId", required = false) Integer dId,
                                  @RequestParam(value = "sex", required = false) String sex,
                                  @RequestParam(value = "age", required = false) Integer age,
                                  @RequestParam(value = "major", required = false) String major,
                                  @RequestParam(value = "sClass", required = false) String sClass) {
        Map<String, Object> map = new HashMap<>();
        Student student = new Student();
        student.setsUsername(username);
        student.setsPassword(password);
        student.setsName(name);
        student.setdId(dId);
        student.setsSex(sex);
        student.setsAge(age);
        student.setsMajor(major);
        student.setsClass(sClass);
        studentMapper.addStudent(student);
        map.put("code", 200);
        map.put("message", "注册成功");
        return map;
    }

    @RequestMapping("/checkTeacherUsername")
    @ResponseBody
    @CrossOrigin
    public Object checkTeacherUsername(@RequestParam(value = "username", required = false) String username) {
        Map<String, Object> map = new HashMap<>();
        Teacher isExist = teacherMapper.selectTeacherByUsername(username);
        System.out.println(isExist + "  1111  " + username);
        if (isExist == null) {
            map.put("code", 200);
            map.put("message", "用户名可用");
        } else {
            map.put("code", 500);
            map.put("message", "已存在同名用户");
        }
        return map;
    }

    @RequestMapping("/checkStudentUsername")
    @ResponseBody
    @CrossOrigin
    public Object checkStudentUsername(@RequestParam(value = "username", required = false) String username) {
        Map<String, Object> map = new HashMap<>();
        Student isExist = studentMapper.selectStudentByUsername(username);
        if (isExist == null) {
            map.put("code", 200);
            map.put("message", "用户名可用");
        } else {
            map.put("code", 500);
            map.put("message", "已存在同名用户");
        }
        return map;
    }

    @RequestMapping("/TeacherUpdatePassword")
    @ResponseBody
    public void TeacherUpdatePassword(@RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "tId", required = false) Integer tId) {
            Teacher teacher = new Teacher();
            teacher.settId(tId);
            teacher.settPassword(password);
            teacherMapper.updateTeacherByTid(teacher);
    }
    @RequestMapping("/StudentUpdatePassword")
    @ResponseBody
    public void StudentUpdatePassword(@RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "sId", required = false) Integer sId) {
        Student student = new Student();
        student.setsId(sId);
        student.setsPassword(password);
        studentMapper.updateStudentBySid(student);
    }

    @RequestMapping("/selectStudentBySid")
    @ResponseBody
    public Student selectStudentBySid(@RequestParam(value = "sId", required = false) Integer sId) {
        studentMapper.selectStudentBySid(sId);
        return studentMapper.selectStudentBySid(sId);
    }

    @RequestMapping("/updateStudentBySid")
    @ResponseBody
    public void updateStudentBySid(@RequestParam(value = "sId", required = false) Integer sId,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "sex", required = false) String sex,
                                      @RequestParam(value = "age", required = false) Integer age) {
        System.out.println(sId+name+sex+age);
        Student student = new Student();
        student.setsId(sId);
        student.setsName(name);
        student.setsSex(sex);
        student.setsAge(age);
        studentMapper.updateStudentBySid(student);
    }
    @RequestMapping("/updateTeacherByTid")
    @ResponseBody
    public void updateTeacherByTid(@RequestParam(value = "tId", required = false) Integer tId,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "sex", required = false) String sex,
                                   @RequestParam(value = "age", required = false) Integer age) {
        Teacher teacher = new Teacher();
        teacher.settId(tId);
        teacher.settName(name);
        teacher.settAge(age);
        teacher.settSex(sex);
        teacherMapper.updateTeacherByTid(teacher);
    }
    @RequestMapping("/selectTeacherByTid")
    @ResponseBody
    public Teacher selectTeacherByTid(@RequestParam(value = "tId", required = false) Integer tId) {
        teacherMapper.selectTeacherByTid(tId);
        return teacherMapper.selectTeacherByTid(tId);
    }
    @RequestMapping("/selectDepartment")
    @ResponseBody
    public List selectDepartment() {
        List<Department> departmentList = departmentMapper.selectDepartment();
        if (departmentList != null && !departmentList.isEmpty()) {
            return departmentList;
        } else {
            return null;
        }
    }
}
