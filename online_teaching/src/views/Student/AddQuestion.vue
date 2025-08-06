<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/StudentContainer' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a @click="prev1">课程问题</a></el-breadcrumb-item>
      <el-breadcrumb-item><a @click="prev">我的问题</a></el-breadcrumb-item>
      <el-breadcrumb-item>添加问题</el-breadcrumb-item>
    </el-breadcrumb>
    </br>
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" >
    <el-form-item label="问题标题" prop="qTitle">
      <el-input v-model="ruleForm.qTitle"  style="width:500px"></el-input>
    </el-form-item>
    <el-form-item label="问题内容" prop="qInfo">
      <el-input type="textarea" v-model="ruleForm.qInfo" style="width:500px" :autosize="{ minRows: 4, maxRows: 4}"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form></div>
</template>
  <script>
    export default {
      data() {
        return {
          ruleForm: {
            qTitle: '',
            qInfo: ''
          },
          rules: {
            qTitle: [
              { required: true, message: '请输入问题标题', trigger: 'blur' },
              { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
            ],
            qInfo: [
              { required: true, message: '请填写问题内容', trigger: 'blur' },
              { min: 10, message: '问题最少10个字符', trigger: 'blur' }
            ]
          }
        };
      },
      methods: {
        prev1() {
                this.$router.go(-2)
            },
            prev() {
                this.$router.go(-1)
            },
        submitForm(formName) {
          var that = this;
          this.$refs[formName].validate((valid) => {
            if (valid) {
              let params = {
                    cId: that.$route.query.cId,
                    sId: sessionStorage.getItem('sId'),
                    qTitle:this.ruleForm.qTitle,
                    qInfo:this.ruleForm.qInfo,
                    tId:that.$route.query.tId,
                }
                this.$axios.post("/api/addQuestion", this.$qs.stringify(params))
                    .then(function (res) {
                      that.$router.push({ path: 'MyQuestion', query: { cId: that.$route.query.cId } });
                    }).catch(function (error) {
                        console.log(error);
                    });
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        },
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }
      }
    }
  </script>