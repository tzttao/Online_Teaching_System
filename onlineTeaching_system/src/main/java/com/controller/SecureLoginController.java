package com.controller;

import com.dao.StudentMapper;
import com.dao.TeacherMapper;
import com.model.Student;
import com.model.Teacher;
import com.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Secure Login Controller with proper security measures
 * - Password hashing with BCrypt
 * - Input validation and sanitization
 * - Rate limiting protection
 * - Session management
 * - Proper error handling without information disclosure
 */
@Controller
@RequestMapping("/secure")
public class SecureLoginController {
    
    private static final Logger logger = LoggerFactory.getLogger(SecureLoginController.class);
    
    // Regex patterns for input validation
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,50}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{8,100}$");
    
    // Rate limiting - simple in-memory implementation (use Redis in production)
    private final Map<String, LoginAttempt> loginAttempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_TIME = 15 * 60 * 1000; // 15 minutes
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    @Autowired
    private StudentMapper studentMapper;
    
    /**
     * Secure teacher login endpoint
     */
    @PostMapping("/teacherLogin")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> teacherLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        String clientIP = getClientIP(request);
        
        try {
            // Input validation
            if (!isValidInput(username, password)) {
                logger.warn("Invalid input format for teacher login from IP: {}", clientIP);
                response.put("success", false);
                response.put("message", "Invalid input format");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Rate limiting check
            if (isBlocked(clientIP)) {
                logger.warn("Blocked login attempt from IP: {}", clientIP);
                response.put("success", false);
                response.put("message", "Too many failed attempts. Try again later.");
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
            }
            
            // Find teacher by username
            Teacher teacher = teacherMapper.selectTeacherByUsername(username);
            
            if (teacher != null && PasswordUtil.verifyPassword(password, teacher.gettPassword())) {
                // Successful login
                resetLoginAttempts(clientIP);
                
                // Create secure session
                session.setAttribute("user_id", teacher.gettId());
                session.setAttribute("user_type", "teacher");
                session.setAttribute("username", teacher.gettUsername());
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
                
                logger.info("Successful teacher login for username: {} from IP: {}", username, clientIP);
                
                response.put("success", true);
                response.put("userId", teacher.gettId());
                response.put("username", teacher.gettUsername());
                response.put("name", teacher.gettName());
                
                return ResponseEntity.ok(response);
            } else {
                // Failed login
                recordFailedAttempt(clientIP);
                logger.warn("Failed teacher login attempt for username: {} from IP: {}", username, clientIP);
                
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            logger.error("Error during teacher login for username: {} from IP: {}", username, clientIP, e);
            response.put("success", false);
            response.put("message", "Login service temporarily unavailable");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Secure student login endpoint
     */
    @PostMapping("/studentLogin")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> studentLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        String clientIP = getClientIP(request);
        
        try {
            // Input validation
            if (!isValidInput(username, password)) {
                logger.warn("Invalid input format for student login from IP: {}", clientIP);
                response.put("success", false);
                response.put("message", "Invalid input format");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Rate limiting check
            if (isBlocked(clientIP)) {
                logger.warn("Blocked login attempt from IP: {}", clientIP);
                response.put("success", false);
                response.put("message", "Too many failed attempts. Try again later.");
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
            }
            
            // Find student by username
            Student student = studentMapper.selectStudentByUsername(username);
            
            if (student != null && PasswordUtil.verifyPassword(password, student.getsPassword())) {
                // Successful login
                resetLoginAttempts(clientIP);
                
                // Create secure session
                session.setAttribute("user_id", student.getsId());
                session.setAttribute("user_type", "student");
                session.setAttribute("username", student.getsUsername());
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
                
                logger.info("Successful student login for username: {} from IP: {}", username, clientIP);
                
                response.put("success", true);
                response.put("userId", student.getsId());
                response.put("username", student.getsUsername());
                response.put("name", student.getsName());
                
                return ResponseEntity.ok(response);
            } else {
                // Failed login
                recordFailedAttempt(clientIP);
                logger.warn("Failed student login attempt for username: {} from IP: {}", username, clientIP);
                
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            logger.error("Error during student login for username: {} from IP: {}", username, clientIP, e);
            response.put("success", false);
            response.put("message", "Login service temporarily unavailable");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Logout endpoint
     */
    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (session != null) {
                String username = (String) session.getAttribute("username");
                session.invalidate();
                logger.info("User logged out: {}", username);
            }
            
            response.put("success", true);
            response.put("message", "Logged out successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error during logout", e);
            response.put("success", false);
            response.put("message", "Logout failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Check session status
     */
    @GetMapping("/checkSession")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        if (session != null && session.getAttribute("user_id") != null) {
            response.put("authenticated", true);
            response.put("userId", session.getAttribute("user_id"));
            response.put("userType", session.getAttribute("user_type"));
            response.put("username", session.getAttribute("username"));
        } else {
            response.put("authenticated", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    // Helper methods
    
    private boolean isValidInput(String username, String password) {
        return username != null && password != null &&
               USERNAME_PATTERN.matcher(username.trim()).matches() &&
               PASSWORD_PATTERN.matcher(password).matches();
    }
    
    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIP = request.getHeader("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) {
            return xRealIP;
        }
        
        return request.getRemoteAddr();
    }
    
    private boolean isBlocked(String clientIP) {
        LoginAttempt attempt = loginAttempts.get(clientIP);
        if (attempt == null) return false;
        
        if (attempt.getAttempts() >= MAX_ATTEMPTS) {
            return System.currentTimeMillis() - attempt.getLastAttempt() < LOCKOUT_TIME;
        }
        
        return false;
    }
    
    private void recordFailedAttempt(String clientIP) {
        LoginAttempt attempt = loginAttempts.get(clientIP);
        if (attempt == null) {
            attempt = new LoginAttempt();
            loginAttempts.put(clientIP, attempt);
        }
        
        attempt.setAttempts(attempt.getAttempts() + 1);
        attempt.setLastAttempt(System.currentTimeMillis());
    }
    
    private void resetLoginAttempts(String clientIP) {
        loginAttempts.remove(clientIP);
    }
    
    // Inner class for tracking login attempts
    private static class LoginAttempt {
        private int attempts = 0;
        private long lastAttempt = 0;
        
        public int getAttempts() { return attempts; }
        public void setAttempts(int attempts) { this.attempts = attempts; }
        public long getLastAttempt() { return lastAttempt; }
        public void setLastAttempt(long lastAttempt) { this.lastAttempt = lastAttempt; }
    }
}