/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50727
Source Host           : 127.0.0.1:3306
Source Database       : online_teaching

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-06-07 17:07:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `t_id` int(11) DEFAULT NULL,
  `c_name` varchar(255) DEFAULT NULL,
  `c_info` varchar(255) DEFAULT NULL,
  `c_capacity` int(11) DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  KEY `t_id` (`t_id`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100011 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('100001', '1001', '计算机网络', '讲解计算机网络相关知识', '100');
INSERT INTO `course` VALUES ('100002', '1001', 'Java程序设计', 'Java程序设计相关知识', '80');
INSERT INTO `course` VALUES ('100003', '1002', 'C++程序设计', 'C++程序设计相关知识', '60');
INSERT INTO `course` VALUES ('100004', '1003', '数据结构', '数据结构相关知识', '100');
INSERT INTO `course` VALUES ('100005', '1004', '软件工程', '软件工程相关知识', '80');
INSERT INTO `course` VALUES ('100006', '1005', '数据库设计', '讲解数据库相关知识', '60');

-- ----------------------------
-- Table structure for course_ware
-- ----------------------------
DROP TABLE IF EXISTS `course_ware`;
CREATE TABLE `course_ware` (
  `cw_id` int(11) NOT NULL AUTO_INCREMENT,
  `cr_id` int(11) DEFAULT NULL,
  `cw_name` varchar(255) DEFAULT NULL,
  `cw_info` varchar(255) DEFAULT NULL,
  `cw_doc` varchar(255) DEFAULT NULL,
  `cw_downloadtimes` int(11) DEFAULT NULL,
  PRIMARY KEY (`cw_id`),
  KEY `cr_id` (`cr_id`),
  CONSTRAINT `course_ware_ibfk_1` FOREIGN KEY (`cr_id`) REFERENCES `curriculum` (`cr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10020 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_ware
-- ----------------------------
INSERT INTO `course_ware` VALUES ('10001', '1001', '第一课课件', '第一课课件', '第一课课件.docx', null);
INSERT INTO `course_ware` VALUES ('10002', '1001', '第一课讲义', '第一课讲义内容', '第一课讲义.docx', null);
INSERT INTO `course_ware` VALUES ('10003', '1002', '第2课课件', '第2课课件', '课件.docx', null);
INSERT INTO `course_ware` VALUES ('10004', '1003', '第3课课件', '第3课课件', '课件.docx', null);
INSERT INTO `course_ware` VALUES ('10005', '1004', '第4课课件', '第4课课件', '课件.docx', null);
INSERT INTO `course_ware` VALUES ('10007', '1006', '第6课课件', '第6课课件', '课件.docx', null);
INSERT INTO `course_ware` VALUES ('10008', '1007', '第7课课件', '第7课课件', '课件.docx', null);
INSERT INTO `course_ware` VALUES ('10011', '1010', '第1课课件', '第1课课件', '', null);
INSERT INTO `course_ware` VALUES ('10012', '1011', '第2课课件', '第2课课件', '', null);
INSERT INTO `course_ware` VALUES ('10013', '1012', '第3课课件', '第3课课件', '', null);
INSERT INTO `course_ware` VALUES ('10014', '1013', '第4课课件', '第4课课件', '', null);
INSERT INTO `course_ware` VALUES ('10019', '1001', '第一课附件', '课外知识', '课外知识.docx', null);

-- ----------------------------
-- Table structure for curriculum
-- ----------------------------
DROP TABLE IF EXISTS `curriculum`;
CREATE TABLE `curriculum` (
  `cr_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) DEFAULT NULL,
  `cr_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cr_id`),
  KEY `c_id` (`c_id`),
  CONSTRAINT `curriculum_ibfk_1` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1017 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of curriculum
-- ----------------------------
INSERT INTO `curriculum` VALUES ('1001', '100001', '计算机网络第1课');
INSERT INTO `curriculum` VALUES ('1002', '100001', '计算机网络第2课');
INSERT INTO `curriculum` VALUES ('1003', '100001', '计算机网络第3课');
INSERT INTO `curriculum` VALUES ('1004', '100001', '计算机网络第4课');
INSERT INTO `curriculum` VALUES ('1005', '100001', '计算机网络第5课');
INSERT INTO `curriculum` VALUES ('1006', '100001', '计算机网络第6课');
INSERT INTO `curriculum` VALUES ('1007', '100001', '计算机网络第7课');
INSERT INTO `curriculum` VALUES ('1010', '100002', 'java第1课');
INSERT INTO `curriculum` VALUES ('1011', '100002', 'java第2课');
INSERT INTO `curriculum` VALUES ('1012', '100002', 'java第3课');
INSERT INTO `curriculum` VALUES ('1013', '100002', 'java第4课');
INSERT INTO `curriculum` VALUES ('1014', '100003', 'c++第1课');
INSERT INTO `curriculum` VALUES ('1015', '100004', '数据结构第1课');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `d_id` int(11) NOT NULL AUTO_INCREMENT,
  `d_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`d_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3006 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('3001', '计算机学院');
INSERT INTO `department` VALUES ('3002', '材料学院');
INSERT INTO `department` VALUES ('3003', '艺术学院');
INSERT INTO `department` VALUES ('3004', '汉语言学院');
INSERT INTO `department` VALUES ('3005', '管理学院');

-- ----------------------------
-- Table structure for homework
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `h_id` int(11) NOT NULL AUTO_INCREMENT,
  `t_id` int(11) DEFAULT NULL,
  `cr_id` int(11) DEFAULT NULL,
  `h_name` varchar(255) DEFAULT NULL,
  `h_info` varchar(255) DEFAULT NULL,
  `h_doc` varchar(255) DEFAULT NULL,
  `h_time` datetime DEFAULT NULL,
  PRIMARY KEY (`h_id`),
  KEY `t_id` (`t_id`),
  KEY `c_id` (`cr_id`),
  CONSTRAINT `homework_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `homework_ibfk_2` FOREIGN KEY (`cr_id`) REFERENCES `curriculum` (`cr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('1', '1001', '1001', '第一课作业', '第一课作业', '教师作业1.doc', '2020-03-15 21:13:11');
INSERT INTO `homework` VALUES ('2', '1001', '1002', '第二课作业', '第二课作业', '教师作业2.doc', '2020-04-03 15:21:00');
INSERT INTO `homework` VALUES ('3', '1001', '1003', '第三课作业', '第三课作业', '教师作业3.doc', '2020-03-17 13:41:53');
INSERT INTO `homework` VALUES ('4', '1001', '1004', '第四课作业', '第四课作业', '教师作业4.doc', '2020-03-16 16:13:08');
INSERT INTO `homework` VALUES ('9', '1001', '1001', '附加作业', '第一次作业的补充', '第一课附加作业.docx', '2020-06-05 00:00:00');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `q_id` int(11) NOT NULL AUTO_INCREMENT,
  `q_title` varchar(255) DEFAULT NULL,
  `q_info` varchar(255) DEFAULT NULL,
  `q_time` datetime DEFAULT NULL,
  `s_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  `q_answer` varchar(255) DEFAULT NULL,
  `c_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`q_id`),
  KEY `s_id` (`s_id`),
  KEY `t_id` (`t_id`),
  KEY `c_id` (`c_id`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_ibfk_2` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_ibfk_3` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', '问题1', '1+1=？', '2020-03-17 16:01:51', '2001', '1001', '2', '100001');
INSERT INTO `question` VALUES ('2', '问题2', '2+2=？', '2020-04-10 14:38:07', '2001', '1001', '4', '100001');
INSERT INTO `question` VALUES ('3', '问题3', '3+3=？', '2020-06-05 14:14:55', '2002', '1001', '等于6啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '100001');
INSERT INTO `question` VALUES ('4', '问题4', '4+4=？', '2020-06-05 14:24:22', '2002', '1001', '88888888888888888888888888', '100001');
INSERT INTO `question` VALUES ('5', '关于计算机网络的问题', '什么是计算机网络', '2020-06-06 09:59:05', '2001', '1001', '请看计算机网络书上第一章内容打分', '100001');
INSERT INTO `question` VALUES ('6', '问题6', '6+6=？', '2020-04-04 22:22:16', '2003', '1002', '12', '100002');
INSERT INTO `question` VALUES ('7', '问题7', '7+7=？', '2020-03-09 22:22:16', '2004', '1002', '14', '100001');
INSERT INTO `question` VALUES ('8', '问题8', '8+8=？', '2020-02-12 22:22:16', '2004', '1001', '16', '100001');
INSERT INTO `question` VALUES ('9', '关于数据库的问题', '如何对数据库进行操作？', '2020-05-08 18:19:38', '2002', '1001', '请阅读《数据库设计》第一单元的内容', '100001');
INSERT INTO `question` VALUES ('10', '点撒发生的发生的发', '啊时代发生的发生的发生的发', '2020-05-08 20:24:55', '2001', '1001', '撒大客点撒发生的发生的发生的', '100001');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `sc_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) DEFAULT NULL,
  `s_id` int(11) DEFAULT NULL,
  `daily_score` double DEFAULT NULL,
  `final_exam` double DEFAULT NULL,
  `final_score` double DEFAULT NULL,
  PRIMARY KEY (`sc_id`),
  KEY `c_id` (`c_id`),
  KEY `s_id` (`s_id`),
  CONSTRAINT `score_ibfk_1` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `score_ibfk_2` FOREIGN KEY (`s_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `d_id` int(11) DEFAULT NULL,
  `s_username` varchar(255) DEFAULT NULL,
  `s_password` varchar(255) DEFAULT NULL,
  `s_name` varchar(255) DEFAULT NULL,
  `s_age` int(255) DEFAULT NULL,
  `s_sex` varchar(255) DEFAULT NULL,
  `s_major` varchar(255) DEFAULT NULL,
  `s_class` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`s_id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `department` (`d_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2009 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('2001', '3001', 'qwer', '1234', '张三 ', '23', '男', '软件工程', '四班');
INSERT INTO `student` VALUES ('2002', '3001', '222', '222', '李四', '20', '女', '软件工程', '四班');
INSERT INTO `student` VALUES ('2003', '3001', '333', '333', '王五', '21', '男', '软件工程', '一班');
INSERT INTO `student` VALUES ('2004', '3003', '444', '444', '赵六', '22', '女', '服装表演', '二班');
INSERT INTO `student` VALUES ('2005', '3001', '555', '555', '赵七', '19', '男', '软件工程', '三班');
INSERT INTO `student` VALUES ('2006', '3005', '666', '666', '赵八', '22', '女', '人才管理', '五班');
INSERT INTO `student` VALUES ('2007', '3002', '777', '777', '赵九', '21', '男', '化学工程', '二班');
INSERT INTO `student` VALUES ('2008', '3003', '888', '888', '赵十', '22', '女', '艺术雕刻', '六班');

-- ----------------------------
-- Table structure for student_homework
-- ----------------------------
DROP TABLE IF EXISTS `student_homework`;
CREATE TABLE `student_homework` (
  `sh_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  `h_id` int(11) DEFAULT NULL,
  `sh_doc` varchar(255) DEFAULT NULL,
  `sh_score` double DEFAULT NULL,
  PRIMARY KEY (`sh_id`),
  KEY `s_id` (`s_id`),
  KEY `t_id` (`t_id`),
  KEY `h_id` (`h_id`),
  CONSTRAINT `student_homework_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_homework_ibfk_2` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_homework_ibfk_3` FOREIGN KEY (`h_id`) REFERENCES `homework` (`h_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1041 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_homework
-- ----------------------------
INSERT INTO `student_homework` VALUES ('1001', '2001', '1001', '1', '2001张三.doc', '4');
INSERT INTO `student_homework` VALUES ('1002', '2001', '1001', '2', '学生作业2.doc', '4.6');
INSERT INTO `student_homework` VALUES ('1004', '2001', '1001', '3', '学生作业3.doc', '4.5');
INSERT INTO `student_homework` VALUES ('1005', '2001', '1001', '4', '学生作业4.doc', '4.5');
INSERT INTO `student_homework` VALUES ('1011', '2002', '1001', '1', '2002李四.doc', '4');
INSERT INTO `student_homework` VALUES ('1012', '2002', '1001', '2', '学生作业2.doc', '3.5');
INSERT INTO `student_homework` VALUES ('1013', '2002', '1001', '3', '学生作业3.doc', '4');
INSERT INTO `student_homework` VALUES ('1014', '2002', '1001', '4', '学生作业4.doc', '4.5');
INSERT INTO `student_homework` VALUES ('1015', '2003', '1001', '1', '2003王五.doc', '3.9');
INSERT INTO `student_homework` VALUES ('1016', '2003', '1001', '2', '学生作业2.doc', '4.1');
INSERT INTO `student_homework` VALUES ('1017', '2003', '1001', '3', '学生作业3.doc', '4.2');
INSERT INTO `student_homework` VALUES ('1018', '2003', '1001', '4', '学生作业4.doc', '4');
INSERT INTO `student_homework` VALUES ('1019', '2004', '1001', '1', '2004赵六.doc', '4.3');
INSERT INTO `student_homework` VALUES ('1020', '2004', '1001', '2', '学生作业2.doc', '4.8');
INSERT INTO `student_homework` VALUES ('1021', '2004', '1001', '3', '学生作业3.doc', '4.5');
INSERT INTO `student_homework` VALUES ('1022', '2004', '1001', '4', '学生作业4.doc', '4.6');
INSERT INTO `student_homework` VALUES ('1023', '2005', '1001', '1', '2005赵七.doc', '4.1');
INSERT INTO `student_homework` VALUES ('1024', '2005', '1001', '2', '学生作业2.doc', '4.2');
INSERT INTO `student_homework` VALUES ('1025', '2005', '1001', '3', '学生作业3.doc', '4.3');
INSERT INTO `student_homework` VALUES ('1026', '2005', '1001', '4', '学生作业4.doc', '4.4');
INSERT INTO `student_homework` VALUES ('1027', '2006', '1001', '1', '2006赵八.doc', '4.5');
INSERT INTO `student_homework` VALUES ('1028', '2006', '1001', '2', '学生作业2.doc', '4.1');
INSERT INTO `student_homework` VALUES ('1029', '2006', '1001', '3', '学生作业3.doc', '4.2');
INSERT INTO `student_homework` VALUES ('1030', '2006', '1001', '4', '学生作业4.doc', '4.6');
INSERT INTO `student_homework` VALUES ('1031', '2007', '1001', '1', '2007赵九.doc', '4');
INSERT INTO `student_homework` VALUES ('1032', '2007', '1001', '2', '学生作业2.doc', '3.8');
INSERT INTO `student_homework` VALUES ('1033', '2007', '1001', '3', '学生作业3.doc', '4.2');
INSERT INTO `student_homework` VALUES ('1034', '2007', '1001', '4', '学生作业4.doc', '4.6');
INSERT INTO `student_homework` VALUES ('1035', '2008', '1001', '1', '2008赵十.doc', '4');
INSERT INTO `student_homework` VALUES ('1036', '2008', '1001', '2', '学生作业2.doc', '3.9');
INSERT INTO `student_homework` VALUES ('1037', '2008', '1001', '3', '学生作业3.doc', '4.1');
INSERT INTO `student_homework` VALUES ('1038', '2008', '1001', '4', '学生作业4.doc', '4.3');

-- ----------------------------
-- Table structure for student_selection
-- ----------------------------
DROP TABLE IF EXISTS `student_selection`;
CREATE TABLE `student_selection` (
  `ss_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) DEFAULT NULL,
  `c_id` int(11) DEFAULT NULL,
  `exam_score` double DEFAULT NULL,
  `final_score` double DEFAULT NULL,
  PRIMARY KEY (`ss_id`),
  KEY `s_id` (`s_id`) USING BTREE,
  KEY `c_id` (`c_id`) USING BTREE,
  CONSTRAINT `student_selection_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_selection_ibfk_2` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_selection
-- ----------------------------
INSERT INTO `student_selection` VALUES ('10001', '2001', '100001', '95', '85.16');
INSERT INTO `student_selection` VALUES ('10002', '2002', '100001', '94', '82');
INSERT INTO `student_selection` VALUES ('10003', '2003', '100001', '88', '78.72');
INSERT INTO `student_selection` VALUES ('10004', '2005', '100001', '89', '80.6');
INSERT INTO `student_selection` VALUES ('10005', '2004', '100001', '78', '75.92');
INSERT INTO `student_selection` VALUES ('10006', '2006', '100001', '89', '81.24');
INSERT INTO `student_selection` VALUES ('10007', '2007', '100001', '88', '79.36');
INSERT INTO `student_selection` VALUES ('10008', '2008', '100001', '92', '81.28');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `d_id` int(11) DEFAULT NULL,
  `t_name` varchar(255) DEFAULT NULL,
  `t_username` varchar(255) DEFAULT NULL,
  `t_password` varchar(255) DEFAULT NULL,
  `t_sex` varchar(255) DEFAULT NULL,
  `t_age` int(11) DEFAULT NULL,
  PRIMARY KEY (`t_id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `department` (`d_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1001', '3001', '张老师', 'zhangtao', '1234', '男', '22');
INSERT INTO `teacher` VALUES ('1002', '3001', '李老师', '222', '222222', '男', '33');
INSERT INTO `teacher` VALUES ('1003', '3001', '刘老师', '333', '333333', '女', '32');
INSERT INTO `teacher` VALUES ('1004', '3001', '赵老师', '444', '444444', '男', '31');
INSERT INTO `teacher` VALUES ('1005', '3001', '孙老师', '555', '555555', '女', '30');
INSERT INTO `teacher` VALUES ('1007', '3001', '张韬', 'zhangtao1', '123456', '男', '21');
