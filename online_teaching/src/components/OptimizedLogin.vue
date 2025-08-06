<template>
  <div class="login-container">
    <el-card class="login-card" shadow="hover">
      <div slot="header" class="card-header">
        <span class="title">{{ userType === 'student' ? '学生登录' : '教师登录' }}</span>
      </div>
      
      <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        @submit.native.prevent="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            prefix-icon="el-icon-user"
            placeholder="用户名"
            :maxlength="50"
            clearable
            @keyup.enter.native="handleLogin"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            prefix-icon="el-icon-lock"
            placeholder="密码"
            :maxlength="100"
            show-password
            clearable
            @keyup.enter.native="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            class="login-button"
            @click="handleLogin"
          >
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="footer-links">
        <el-link type="primary" @click="switchUserType">
          {{ userType === 'student' ? '教师登录' : '学生登录' }}
        </el-link>
      </div>
    </el-card>
  </div>
</template>

<script>
import { secureLogin } from '@/api/auth'

export default {
  name: 'OptimizedLogin',
  
  props: {
    initialUserType: {
      type: String,
      default: 'student',
      validator: value => ['student', 'teacher'].includes(value)
    }
  },
  
  data() {
    // Custom validator for username
    const validateUsername = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入用户名'))
      } else if (value.length < 3) {
        callback(new Error('用户名至少3个字符'))
      } else if (!/^[a-zA-Z0-9_]+$/.test(value)) {
        callback(new Error('用户名只能包含字母、数字和下划线'))
      } else {
        callback()
      }
    }
    
    // Custom validator for password
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (value.length < 8) {
        callback(new Error('密码至少8个字符'))
      } else {
        callback()
      }
    }
    
    return {
      userType: this.initialUserType,
      loading: false,
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [
          { validator: validateUsername, trigger: 'blur' }
        ],
        password: [
          { validator: validatePassword, trigger: 'blur' }
        ]
      }
    }
  },
  
  methods: {
    async handleLogin() {
      try {
        // Validate form
        const valid = await this.$refs.loginForm.validate()
        if (!valid) return
        
        this.loading = true
        
        // Call secure login API
        const response = await secureLogin({
          type: this.userType,
          username: this.loginForm.username.trim(),
          password: this.loginForm.password
        })
        
        if (response.success) {
          // Store user info
          this.$store.dispatch('user/setUserInfo', {
            id: response.userId,
            username: response.username,
            name: response.name,
            type: this.userType
          })
          
          // Success message
          this.$message.success('登录成功')
          
          // Navigate to dashboard
          const route = this.userType === 'student' ? '/student/dashboard' : '/teacher/dashboard'
          this.$router.push(route)
        } else {
          this.$message.error(response.message || '登录失败')
        }
        
      } catch (error) {
        console.error('Login error:', error)
        
        if (error.response) {
          const { status, data } = error.response
          
          if (status === 429) {
            this.$message.error('登录尝试过于频繁，请稍后再试')
          } else if (status === 401) {
            this.$message.error('用户名或密码错误')
          } else {
            this.$message.error(data.message || '登录服务暂时不可用')
          }
        } else {
          this.$message.error('网络连接失败，请检查网络')
        }
      } finally {
        this.loading = false
      }
    },
    
    switchUserType() {
      this.userType = this.userType === 'student' ? 'teacher' : 'student'
      this.resetForm()
    },
    
    resetForm() {
      this.$refs.loginForm.resetFields()
      this.loginForm.username = ''
      this.loginForm.password = ''
    }
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 400px;
  
  .card-header {
    text-align: center;
    
    .title {
      font-size: 24px;
      font-weight: 600;
      color: #303133;
    }
  }
}

.login-form {
  padding: 0 20px;
  
  .el-form-item {
    margin-bottom: 24px;
  }
  
  .login-button {
    width: 100%;
    height: 44px;
    font-size: 16px;
  }
}

.footer-links {
  text-align: center;
  margin-top: 20px;
}

// Responsive design
@media (max-width: 768px) {
  .login-container {
    padding: 10px;
  }
  
  .login-card {
    margin: 0;
  }
}
</style>