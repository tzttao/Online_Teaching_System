package com.controller;

import com.dao.CourseMapper;
import com.dao.CourseWareMapper;
import com.dao.CurriculumMapper;
import com.dao.StudentSelectionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.Course;
import com.model.CourseWare;
import com.model.Curriculum;
import com.model.StudentSelection;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
public class TeacherCourseController {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CurriculumMapper curriculumMapper;

    @Autowired
    private CourseWareMapper courseWareMapper;


    @RequestMapping("/selectCourseByTid")
    @ResponseBody
    public List selectCourseByTid(@RequestParam(value = "tId", required = false) Integer tId) {
        List<Course> courseList = courseMapper.selectCourseByTid(tId);
        if (courseList != null && !courseList.isEmpty()) {
            return courseList;
        } else {
            return null;
        }
    }

    @RequestMapping("/selectCurriculumByCid")
    @ResponseBody
    public PageInfo<Curriculum> selectCurriculumByCid(@RequestParam(value = "cId", required = false) Integer cId,
                                                      @RequestParam(required = false, value = "pageNum") Integer pageNum,
                                                      @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Curriculum> curriculumList = curriculumMapper.selectCurriculumByCid(cId);
        PageInfo pageInfo = new PageInfo<>(curriculumList);
        if (curriculumList != null && !curriculumList.isEmpty()) {
            return pageInfo;
        } else {
            return null;
        }
    }

    @RequestMapping("/updateCurriculumByCRid")
    @ResponseBody
    public String updateCurriculumByCRid(@RequestParam(value = "crId", required = false) Integer crId,
                                         @RequestParam(value = "crName", required = false) String crName) {
        System.out.println("xiugai ");
        Curriculum curriculum = new Curriculum();
        curriculum.setCrId(crId);
        curriculum.setCrName(crName);
        curriculumMapper.updateCurriculumByCRid(curriculum);
        return "update";
    }

    @RequestMapping("/addCurriculum")
    @ResponseBody
    public String addCurriculum(@RequestParam(value = "cId", required = false) Integer cId,
                                @RequestParam(value = "crName", required = false) String crName) {
        Curriculum curriculum = new Curriculum();
        curriculum.setcId(cId);
        curriculum.setCrName(crName);
        curriculumMapper.addCurriculum(curriculum);
        return "add";
    }

    @RequestMapping("/deleteCurriculum")
    @ResponseBody
    public String deleteCurriculum(@RequestParam(value = "crId", required = false) Integer crId) {
        List<CourseWare> courseWareList = courseWareMapper.selectCourseWareByCRid(crId);
        if (courseWareList != null && !courseWareList.isEmpty()) {
            return null;
        } else {
            curriculumMapper.deleteCurriculumByCRid(crId);
            return "delete";
        }
    }

    @RequestMapping("/selectWareByCRid")
    @ResponseBody
    public List selectWareByCRid(@RequestParam(value = "crId", required = false) Integer crId) {
        List<CourseWare> courseWareList = courseWareMapper.selectCourseWareByCRid(crId);
        if (courseWareList != null && !courseWareList.isEmpty()) {
            return courseWareList;
        } else {
            return null;
        }
    }

    @PostMapping(value = "/fileupload", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String fileupload(@RequestParam("fileList") MultipartFile[] files,
                             @RequestParam(value = "crId", required = false) Integer crId,
                             @RequestParam(value = "cwName", required = false) String cwName,
                             @RequestParam(value = "cwInfo", required = false) String cwInfo) throws Exception {
        System.out.println("文件上传...");
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String dir = "E://IDEA//IDEA-workspace//onlineTeaching_system//doc//CourseWare";
                File saveFile = new File(dir, fileName);
                try {
                    file.transferTo(saveFile);//文件保存到本地
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CourseWare cw = new CourseWare();
                cw.setCrId(crId);
                cw.setCwName(cwName);
                cw.setCwInfo(cwInfo);
                cw.setCwDoc(fileName);
                courseWareMapper.addCourseWare(cw);
            }
            return "upload success";
        } else {
            return "null";
        }
    }

    @PostMapping(value = "/updateWare", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String updateWare(@RequestParam("fileList") MultipartFile[] files,
                             @RequestParam(value = "cwId", required = false) Integer cwId,
                             @RequestParam(value = "cwName", required = false) String cwName,
                             @RequestParam(value = "cwInfo", required = false) String cwInfo) {
        System.out.println("update!");
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String dir = "E://IDEA//IDEA-workspace//onlineTeaching_system//doc//CourseWare";
                File saveFile = new File(dir, fileName);
                try {
                    file.transferTo(saveFile); //文件保存到本地
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CourseWare cw = new CourseWare();
                cw.setCwId(cwId);
                cw.setCwName(cwName);
                cw.setCwInfo(cwInfo);
                cw.setCwDoc(fileName);
                courseWareMapper.updateCourseWareByCWid(cw);
            }
            return "upload success";
        } else {
            return "null";
        }
    }

    @PostMapping(value = "/deleteWare", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String deleteWare(@RequestParam(value = "cwId", required = false) Integer cwId,
                             @RequestParam(value = "cwDoc", required = false) String cwDoc) {
        String dir = "E://IDEA//IDEA-workspace//onlineTeaching_system//doc//CourseWare";
        File file = new File(dir, cwDoc);
        file.delete();
        courseWareMapper.deleteCourseWareByCWid(cwId);
        return "delete";
    }
}
