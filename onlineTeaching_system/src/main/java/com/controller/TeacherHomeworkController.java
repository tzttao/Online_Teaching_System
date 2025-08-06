package com.controller;

import com.dao.HomeworkMapper;
import com.dao.StudentHomeworkMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.*;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class TeacherHomeworkController {
    @Autowired
    HomeworkMapper homeworkMapper;
    @Autowired
    StudentHomeworkMapper studentHomeworkMapper;


    @RequestMapping("/selectCourseByCRidAndTid")
    @ResponseBody
    public List selectCourseByCRidAndTid(@RequestParam(value = "tId", required = false) Integer tId,
                                         @RequestParam(value = "crId", required = false) Integer crId) {
        List<Homework> homeworkList = homeworkMapper.selectCourseByCRidAndTid(crId, tId);
        if (homeworkList != null && !homeworkList.isEmpty()) {
            System.out.println(tId + "" + crId);
            return homeworkList;
        } else {
            return null;
        }
    }

    @RequestMapping("/addHomework")
    @ResponseBody
    public String addHomework(@RequestParam("fileList") MultipartFile[] files,
                              @RequestParam(value = "tId", required = false) Integer tId,
                              @RequestParam(value = "crId", required = false) Integer crId,
                              @RequestParam(value = "hName", required = false) String hName,
                              @RequestParam(value = "hDate", required = false) String date,
                              @RequestParam(value = "hInfo", required = false) String hInfo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z", Locale.ENGLISH);
        date=date.replace("GMT", "").replaceAll("\\(.*\\)", "");
        Date hDate = sdf.parse(date);
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String dir = "E://IDEA//IDEA-workspace//onlineTeaching_system//doc//TeacherHomework";
                File saveFile = new File(dir, fileName);
                try {
                    file.transferTo(saveFile); //文件保存到本地
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Homework homework = new Homework();
                homework.setCRid(crId);
                homework.sethDoc(fileName);
                homework.settId(tId);
                homework.sethInfo(hInfo);
                homework.sethName(hName);
                homework.sethTime(hDate);
                homeworkMapper.addHomework(homework);
            }
            return "add success";
        } else {
            return "null";
        }
    }

    @RequestMapping("/updateHomework")
    @ResponseBody
    public String updateHomework(@RequestParam("fileList") MultipartFile[] files,
                                 @RequestParam(value = "hId", required = false) Integer hId,
                                 @RequestParam(value = "tId", required = false) Integer tId,
                                 @RequestParam(value = "crId", required = false) Integer crId,
                                 @RequestParam(value = "hName", required = false) String hName,
                                 @RequestParam(value = "hDate", required = false) String date,
                                 @RequestParam(value = "hInfo", required = false) String hInfo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z", Locale.ENGLISH);
        date=date.replace("GMT", "").replaceAll("\\(.*\\)", "");
        Date hDate = sdf.parse(date);
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String dir = "E://IDEA//IDEA-workspace//onlineTeaching_system//doc//TeacherHomework";
                File saveFile = new File(dir, fileName);
                try {
                    file.transferTo(saveFile); //文件保存到本地
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Homework homework = new Homework();
                homework.sethId(hId);
                homework.setCRid(crId);
                homework.sethDoc(fileName);
                homework.settId(tId);
                homework.sethInfo(hInfo);
                homework.sethName(hName);
                homework.sethTime(hDate);
                homeworkMapper.updateHomeworkByHid(homework);
            }
            return "upload success";
        } else {
            return "null";
        }
    }

    @PostMapping(value = "/deleteHomework", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String deleteHomework(@RequestParam(value = "hId", required = false) Integer hId,
                                 @RequestParam(value = "hDoc", required = false) String hDoc) {
        String dir = "E://IDEA//IDEA-workspace//onlineTeaching_system//doc//TeacherHomework";
        File file = new File(dir, hDoc);
        file.delete();
        homeworkMapper.deleteHomeworkByHid(hId);
        return "delete";
    }

    @RequestMapping("/selectStudentHomework")
    @ResponseBody
    public PageInfo<StudentHomework> selectStudentHomework(@RequestParam(value = "hId", required = false) Integer hId,
                                                            @RequestParam(required = false, value = "pageNum") Integer pageNum,
                                                            @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentHomework> studentHomeworkList = studentHomeworkMapper.selectSHomeworkByHid(hId);
        PageInfo pageInfo = new PageInfo<>(studentHomeworkList);
        if (studentHomeworkList != null && !studentHomeworkList.isEmpty()) {
            return pageInfo;
        } else {
            return null;
        }
    }

    @RequestMapping("/downloadStudentHomework")
    public String download(String filename,
                           HttpServletResponse response) throws IOException {
        //模拟文件，myfile.txt为需要下载的文件
        System.out.println(filename);
        String path = "E:\\IDEA\\IDEA-workspace\\onlineTeaching_system\\doc\\StudentHomework\\" + filename;
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

    @RequestMapping("/addScore")
    @ResponseBody
    public void addScore(@RequestParam(value = "shId", required = false) Integer shId,
                         @RequestParam(value = "shScore", required = false) Double shScore) {
        System.out.println("shId" + shId);
        StudentHomework sh = new StudentHomework();
        sh.setShId(shId);
        sh.setShScore(shScore);
        studentHomeworkMapper.updateBySHid(sh);
    }
}
