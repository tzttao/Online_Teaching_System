<template>
  <div class="register-container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm register-page"
      status-icon>
      <div align="center" style="padding-right:30px">
        <div style="padding:10px 0 0 400px">
          <el-link type="primary" class="register" href="http://localhost:8888/#/TeacherLogin">返回</el-link>
        </div>
        <span>
          <h2>教师注册</h2>
        </span>
      </div>
      <br />
      <el-form-item label="用户名：" prop="username">
        <el-input v-model="ruleForm.username" style="width:250px"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input v-model="ruleForm.password" style="width:250px" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码：" prop="checkPassword">
        <el-input type="password" v-model="ruleForm.checkPassword" style="width:250px" show-password></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="name">
        <el-input v-model="ruleForm.name" style="width:250px"></el-input>
      </el-form-item>
      <el-form-item label="年龄：" prop="age">
        <el-input v-model.number="ruleForm.age" style="width:100px"></el-input>
      </el-form-item>
      <el-form-item label="性别：" prop="sex">
        <el-radio-group v-model="ruleForm.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="学院：" prop="dId">
        <el-select v-model="ruleForm.dId" placeholder="请选择学院" style="width:200px">
          <el-option v-for="item in dept" :key="item.dId" :label="item.dName" :value="item.dId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value < 18) {
              callback(new Error('必须年满18岁'));
            } else if (value < 1 || value > 120) {
              callback(new Error('输入年龄不合法'));
            } else {
              callback();
            }
          }
        }, 500);
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPassword !== '') {
            this.$refs.ruleForm.validateField('checkPassword');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      let that = this;
      var user = function (rule, value, callback) {
        if (!value) {
          return callback(new Error('用户名不能为空'));
        } else {
          let params = { username: value }
          that.$axios.post("/api/checkTeacherUsername", that.$qs.stringify(params)).then((res) => {
            if (res.data.code == '500') {
              callback(new Error('用户名已存在'));
            } else {
              callback();
            }    //这里使用了ES6的语法
          }).catch((error) => {
            console.log(error)       //请求失败返回的数据
          })
        }

      };
      return {
        dept:[],
        ruleForm: {
          username: '',
          name: "",
          password: '',
          checkPassword: '',
          dId: '',
          age: '',
          sex: '',
        },
        rules: {
          username: [
            { required: true, validator: user, trigger: "blur" },
            { min: 3, max: 12, message: "长度在 3 到 12 个字符", trigger: "blur" }
          ],
          password: [
            { required: true, validator: validatePass, trigger: 'blur' },
            { min: 6, max: 11, message: "长度在 6 到 11 个字符", trigger: "blur" }
          ],
          checkPassword: [
            { required: true, validator: validatePass2, trigger: 'blur' }
          ],
          name: [
            { required: true, message: "请输入真实姓名", trigger: "blur" },
            { min: 2, max: 8, message: "长度在 2 到 8 个字符", trigger: "blur" }
          ],
          age: [
            { validator: checkAge, trigger: 'blur' }
          ],
          dId: [
            { required: true, message: "请选择学院", trigger: "change" }
          ],
          sex: [
            { required: true, message: "请选择性别", trigger: "change" }
          ]
        }
      };
    },
    mounted:function () {
      this.selectDepartment();
    },
    methods: {
      submitForm() {
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            let params = {
              username: this.ruleForm.username,
              password: this.ruleForm.password,
              dId: this.ruleForm.dId,
              age: this.ruleForm.age,
              sex: this.ruleForm.sex,
              name: this.ruleForm.name
            }
            this.$axios.post("/api/teacherRegister", this.$qs.stringify(params))
              .then(res => {
                console.log(res);
                if (res.data.code == '200' && res.data.message == '注册成功') {
                  this.$router.push('/TeacherLogin');//否则跳转至首页
                  this.$message({
                    title: '注册提示',
                    message: '注册成功',
                    showClose: true,
                    center: true,
                    type: 'success'
                  });
                } else if (res.data.code == '500') {
                  this.$message({
                    title: '注册提示',
                    message: '用户名已存在',
                    center: true,
                    type: 'error'
                  });
                  this.loading = false;
                }
              }).catch(error => {
                this.loading = false;
                console.log(error);
                this.$message({//这里采用element ui的一个错误显示效果模板
                  title: '注册提示',
                  message: error.message,
                  center: true,
                  type: 'warning'
                });
              })
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      resetForm() {
        this.$refs.ruleForm.resetFields();
      },
      selectDepartment() {
        let that=this
        let params = {}
        this.$axios.post("/api/selectDepartment", this.$qs.stringify(params))
          .then(function (res) {
            that.dept = res.data;
            console.log(that.dept);
          }).catch(function (error) {
            console.log(error);
          });
      }
    }
  };
</script>




<style scoped>
  .register-container {
    width: 100%;
    height: 100%;
    padding-top: 50px;
  }

  .register-page {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    margin: 0px auto;
    width: 500px;
    padding: 20px;
    padding-left: 50px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
</style>