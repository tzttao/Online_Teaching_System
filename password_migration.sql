-- Password Migration Script
-- This script should be run AFTER implementing password hashing in the application
-- It will extend password columns to accommodate BCrypt hashes

USE online_teaching;

-- Backup existing data before migration
CREATE TABLE student_backup AS SELECT * FROM student;
CREATE TABLE teacher_backup AS SELECT * FROM teacher;

-- Extend password columns to accommodate BCrypt hashes (60 characters)
ALTER TABLE student 
MODIFY COLUMN s_password VARCHAR(100) NOT NULL;

ALTER TABLE teacher 
MODIFY COLUMN t_password VARCHAR(100) NOT NULL;

-- Note: Existing plain text passwords need to be hashed by the application
-- This cannot be done in SQL as it requires the BCrypt library

-- Create a temporary table to track migration status
CREATE TABLE password_migration_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(50),
    user_id INT,
    username VARCHAR(255),
    migration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'completed', 'failed') DEFAULT 'pending'
);

-- Insert pending migration records for all users
INSERT INTO password_migration_log (table_name, user_id, username)
SELECT 'student', s_id, s_username FROM student;

INSERT INTO password_migration_log (table_name, user_id, username)
SELECT 'teacher', t_id, t_username FROM teacher;

-- Add a flag column to track which passwords have been hashed
ALTER TABLE student ADD COLUMN password_hashed BOOLEAN DEFAULT FALSE;
ALTER TABLE teacher ADD COLUMN password_hashed BOOLEAN DEFAULT FALSE;

SELECT 'Password migration preparation completed. Use Java application to hash existing passwords.' as message;