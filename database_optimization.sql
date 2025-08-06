-- Database Optimization Script for Online Teaching System
-- This script adds indexes and constraints to improve performance and data integrity

USE online_teaching;

-- Add indexes for student table
ALTER TABLE student 
ADD INDEX idx_student_username (s_username),
ADD INDEX idx_student_email (s_email),
ADD INDEX idx_student_department (s_department);

-- Add indexes for teacher table
ALTER TABLE teacher 
ADD INDEX idx_teacher_username (t_username),
ADD INDEX idx_teacher_email (t_email),
ADD INDEX idx_teacher_department (t_department);

-- Add indexes for course table
ALTER TABLE course
ADD INDEX idx_course_teacher (t_id),
ADD INDEX idx_course_name (c_name);

-- Add indexes for student_selection table (course enrollments)
ALTER TABLE student_selection
ADD INDEX idx_selection_student (s_id),
ADD INDEX idx_selection_course (c_id),
ADD INDEX idx_selection_composite (s_id, c_id);

-- Add indexes for homework table
ALTER TABLE homework
ADD INDEX idx_homework_course (c_id),
ADD INDEX idx_homework_deadline (h_deadline),
ADD INDEX idx_homework_course_deadline (c_id, h_deadline);

-- Add indexes for student_homework table (submissions)
ALTER TABLE student_homework
ADD INDEX idx_student_homework_student (s_id),
ADD INDEX idx_student_homework_homework (h_id),
ADD INDEX idx_student_homework_composite (s_id, h_id),
ADD INDEX idx_student_homework_submit_time (sh_submit_time);

-- Add indexes for question table (Q&A system)
ALTER TABLE question
ADD INDEX idx_question_student (s_id),
ADD INDEX idx_question_course (c_id),
ADD INDEX idx_question_status (q_status),
ADD INDEX idx_question_composite (s_id, c_id),
ADD INDEX idx_question_time (q_time);

-- Add indexes for score table
ALTER TABLE score
ADD INDEX idx_score_student (s_id),
ADD INDEX idx_score_homework (h_id),
ADD INDEX idx_score_composite (s_id, h_id);

-- Add indexes for course_ware table
ALTER TABLE course_ware
ADD INDEX idx_courseware_course (cr_id),
ADD INDEX idx_courseware_name (cw_name);

-- Add indexes for curriculum table
ALTER TABLE curriculum
ADD INDEX idx_curriculum_course (c_id),
ADD INDEX idx_curriculum_time (cu_time);

-- Add NOT NULL constraints for critical fields
ALTER TABLE student
MODIFY COLUMN s_username VARCHAR(255) NOT NULL,
MODIFY COLUMN s_password VARCHAR(255) NOT NULL,
MODIFY COLUMN s_name VARCHAR(255) NOT NULL;

ALTER TABLE teacher
MODIFY COLUMN t_username VARCHAR(255) NOT NULL,
MODIFY COLUMN t_password VARCHAR(255) NOT NULL,
MODIFY COLUMN t_name VARCHAR(255) NOT NULL;

ALTER TABLE course
MODIFY COLUMN c_name VARCHAR(255) NOT NULL;

-- Add unique constraints
ALTER TABLE student 
ADD CONSTRAINT uk_student_username UNIQUE (s_username);

ALTER TABLE teacher 
ADD CONSTRAINT uk_teacher_username UNIQUE (t_username);

-- Add check constraints
ALTER TABLE student
ADD CONSTRAINT chk_student_age CHECK (s_age > 0 AND s_age < 120);

-- Optimize table data types
ALTER TABLE student 
MODIFY COLUMN s_age INT(3);  -- Fixed from INT(255)

-- Add audit columns for better tracking
ALTER TABLE student
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE teacher
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE course
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE homework
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Optimize MyISAM tables to InnoDB for better performance and ACID compliance
ALTER TABLE student ENGINE=InnoDB;
ALTER TABLE teacher ENGINE=InnoDB;
ALTER TABLE course ENGINE=InnoDB;
ALTER TABLE student_selection ENGINE=InnoDB;
ALTER TABLE homework ENGINE=InnoDB;
ALTER TABLE student_homework ENGINE=InnoDB;
ALTER TABLE question ENGINE=InnoDB;
ALTER TABLE score ENGINE=InnoDB;
ALTER TABLE course_ware ENGINE=InnoDB;
ALTER TABLE curriculum ENGINE=InnoDB;
ALTER TABLE department ENGINE=InnoDB;

-- Create views for common queries
CREATE VIEW student_course_view AS
SELECT 
    s.s_id, s.s_name, s.s_username,
    c.c_id, c.c_name, c.c_info,
    t.t_name as teacher_name,
    ss.selection_time
FROM student s
JOIN student_selection ss ON s.s_id = ss.s_id
JOIN course c ON ss.c_id = c.c_id
JOIN teacher t ON c.t_id = t.t_id;

CREATE VIEW homework_submission_view AS
SELECT 
    h.h_id, h.h_name, h.h_content, h.h_deadline,
    c.c_name as course_name,
    s.s_name as student_name,
    sh.sh_content as submission_content,
    sh.sh_submit_time,
    sc.score_value
FROM homework h
JOIN course c ON h.c_id = c.c_id
LEFT JOIN student_homework sh ON h.h_id = sh.h_id
LEFT JOIN student s ON sh.s_id = s.s_id
LEFT JOIN score sc ON (sh.s_id = sc.s_id AND sh.h_id = sc.h_id);

-- Performance optimization settings
-- Note: These should be set in MySQL configuration file (my.cnf) for persistence

-- Analyze tables to update statistics
ANALYZE TABLE student, teacher, course, student_selection, 
             homework, student_homework, question, score, 
             course_ware, curriculum, department;

-- Enable query cache (if not already enabled)
-- SET GLOBAL query_cache_type = ON;
-- SET GLOBAL query_cache_size = 67108864; -- 64MB

SHOW WARNINGS;
SELECT 'Database optimization completed successfully!' as message;