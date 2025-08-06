package com.controller;

import com.dao.HomeworkMapper;
import com.dao.StudentHomeworkMapper;
import com.dao.StudentSelectionMapper;
import com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class StudentCourseController {

    @Autowired
    private StudentSelectionMapper studentSelectionMapper;

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;

    @RequestMapping("/selectStudentCourseBySid")
    @ResponseBody
    public List selectStudentCourseBySid(@RequestParam(value = "sId", required = false) Integer sId) {
        System.out.println(sId);
        List<StudentSelection> ssList = studentSelectionMapper.selectStudentCourseBySid(sId);
        if (ssList != null && !ssList.isEmpty()) {
            return ssList;
        } else {
            return null;
        }
    }

    @RequestMapping("/downloadCourseWare")
    public String download(String filename,
                           HttpServletResponse response) throws IOException {
        //模拟文件，myfile.txt为需要下载的文件
        System.out.println(filename);
        String path = "E:\\IDEA\\IDEA-workspace\\onlineTeaching_system\\doc\\CourseWare\\" + filename;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename, "UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while ((len = bis.read()) != -1) {
            out.write(len);
            out.flush();
        }
        out.close();
        return filename;
    }

    @RequestMapping("/selectCourseByCRid")
    @ResponseBody
    public List selectCourseByCRid(@RequestParam(value = "crId", required = false) Integer crId) {
        List<Homework> homeworkList = homeworkMapper.selectCourseByCRid(crId);
        if (homeworkList != null && !homeworkList.isEmpty()) {
            return homeworkList;
        } else {
            return null;
        }
    }

    @RequestMapping("/downloadTeacherHomework")
    public String downloadTeacherHomework(String filename,
                                          HttpServletResponse response) throws IOException {
        //模拟文件，myfile.txt为需要下载的文件
        System.out.println(filename);
        String path = "E:\\IDEA\\IDEA-workspace\\onlineTeaching_system\\doc\\TeacherHomework\\" + filename;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename, "UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while ((len = bis.read()) != -1) {
            out.write(len);
            out.flush();
        }
        out.close();
        return filename;
    }

    @RequestMapping("/selectMyHomework")
    @ResponseBody
    public StudentHomework selectStudentHomework(@RequestParam(value = "hId", required = false) Integer hId,
                                                 @RequestParam(value = "sId", required = false) Integer sId) {
        StudentHomework studentHomework = studentHomeworkMapper.selectMyHomeworkByHidAndSId(hId, sId);
        if (studentHomework != null) {
            return studentHomework;
        } else {
            return null;
        }
    }

    @RequestMapping("/addMyHomework")
    @ResponseBody
    public void addHomework(@RequestParam(value = "hId", required = false) Integer hId,
                            @RequestParam(value = "tId", required = false) Integer tId,
                            @RequestParam(value = "sId", required = false) Integer sId) {
        StudentHomework studentHomework = studentHomeworkMapper.selectMyHomeworkByHidAndSId(hId, sId);
        if (studentHomework == null) {
            StudentHomework sh=new StudentHomework();
            sh.sethId(hId);
            sh.settId(tId);
            sh.setsId(sId);
            studentHomeworkMapper.addHomework(sh);
        }
    }

    @RequestMapping("/uploadHomework")
    @ResponseBody
    public String uploadHomework(@RequestParam("fileList") MultipartFile[] files,
                                 @RequestParam(value = "shId", required = false) Integer shId) {
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String dir = "E://IDEA//IDEA-workspace//onlineTeaching_system//doc//StudentHomework";
                File saveFile = new File(dir, fileName);
                try {
                    //文件保存到本地
                    file.transferTo(saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                StudentHomework studentHomework=new StudentHomework();
                studentHomework.setShDoc(fileName);
                studentHomework.setShId(shId);
                studentHomeworkMapper.updateBySHid(studentHomework);
            }
            return "add success";
        } else {
            return "null";
        }
    }
    @RequestMapping("/selectMyScoreByCidAndSid")
    @ResponseBody
    public StudentSelection selectMyScoreByCidAndSid(@RequestParam(value = "cId", required = false) Integer cId,
                                         @RequestParam(value = "sId", required = false) Integer sId) {
        StudentSelection ss = studentSelectionMapper.selectMyScoreByCidAndSid(cId,sId);
        if (ss != null) {
            return ss;
        } else {
            return null;
        }
    }

}
