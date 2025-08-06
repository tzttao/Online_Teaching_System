import request from '@/utils/request'

/**
 * Secure Authentication API
 */

// Login API
export function secureLogin({ type, username, password }) {
  return request({
    url: `/secure/${type}Login`,
    method: 'post',
    data: {
      username: username.trim(),
      password
    }
  })
}

// Logout API
export function logout() {
  return request({
    url: '/secure/logout',
    method: 'post'
  })
}

// Check session status
export function checkSession() {
  return request({
    url: '/secure/checkSession',
    method: 'get'
  })
}

// Password strength validation
export function validatePasswordStrength(password) {
  const requirements = {
    length: password.length >= 8,
    uppercase: /[A-Z]/.test(password),
    lowercase: /[a-z]/.test(password),
    digit: /\d/.test(password),
    special: /[!@#$%^&*(),.?":{}|<>]/.test(password)
  }
  
  const score = Object.values(requirements).filter(Boolean).length
  
  return {
    requirements,
    score,
    strength: score <= 2 ? 'weak' : score <= 4 ? 'medium' : 'strong',
    isValid: score >= 4
  }
}

// Register APIs (if needed)
export function registerStudent(userData) {
  return request({
    url: '/secure/studentRegister',
    method: 'post',
    data: userData
  })
}

export function registerTeacher(userData) {
  return request({
    url: '/secure/teacherRegister',
    method: 'post',
    data: userData
  })
}