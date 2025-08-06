<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/StudentContainer' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/StudentContainer/StudentCourse' }">已选课程</el-breadcrumb-item>
      <el-breadcrumb-item><a @click="prev1">课程详情</a></el-breadcrumb-item>
      <el-breadcrumb-item><a @click="prev">课程作业</a></el-breadcrumb-item>
      <el-breadcrumb-item>我的作业</el-breadcrumb-item>
    </el-breadcrumb>
    </br>
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="作业名称：">
        <span v-model="form.hName">{{form.hName}}</span>
      </el-form-item>
      <el-form-item label="作业内容：">
        <span v-model="form.hInfo">{{form.hInfo}}</span>
      </el-form-item>
      <el-form-item label="提交截止日期：">
        <span v-model="form.hInfo">{{form.hTime| dateFmt('YYYY-MM-DD HH:mm')}}</span>
      </el-form-item>
      <el-form-item label="我的作业：">
        <span v-if="currentTime>timestampToTime()" style="color:red">
          提交时间已过！请联系授课老师
        </span>
        <span v-else-if="form.shDoc===null" style="color:#cccccc">
          您还没有提交作业，请提交！
          <el-upload ref='upload' :before-remove="beforeRemove" :on-change="handleChange" action :http-request="getFile"
            :file-list="fileList" multiple>
            <el-button size="small" type="primary">浏览</el-button>
            <el-button type="primary" size="small" @click="addHomework()">提交</el-button>
          </el-upload>
        </span>
        <span v-else v-model="form.shDoc">{{form.shDoc}}</span>
      </el-form-item>
      <el-form-item label="我的分数：">
        <span v-if="form.shScore==0||null" style="color:#cccccc">老师还没批改作业，请耐心等待！</span>
        <span v-else>
          <el-rate v-model="form.shScore" disabled show-score text-color="#ff9900">
          </el-rate>
        </span>
      </el-form-item>
    </el-form>
  </div>
  </el-dialog>
  </div>
</template>
<script>
  export default {
    inject: ['reload'],
    data() {
      return {
        form: {
          hName: '',
          hInfo: '',
          shDoc: '',
          shScore: 0,
          shId: '',
          hTime: '',
        },
        fileList: [],
        currentTime: new Date(), // 获取当前时间
      }
    },
    created() {
      var _this = this; //声明一个变量指向Vue实例this，保证作用域一致
      _this.currentTime = //修改数据date
        new Date().getFullYear() +
        "-" +
        _this.appendZero(new Date().getMonth() + 1) +
        "-" +
        _this.appendZero(new Date().getDate()) +
        " " +
        _this.appendZero(new Date().getHours()) +
        ":" +
        _this.appendZero(new Date().getMinutes())
      // ": " +
      // _this.appendZero(new Date().getSeconds());
    },
    mounted: function () {
      this.selectStudentHomework()
    },
    methods: {
      prev() {
        this.$router.go(-1)
      },
      prev1() {
        this.$router.go(-2)
      },
      // 时间戳转换成时间
      // 使用element table组件中的formatter属性，传入一个函数
      timestampToTime() {
        let moment = require("moment");
        return moment(this.form.hTime).format("YYYY-MM-DD HH:mm:ss")
        console.log(moment(this.form.hTime).format("YYYY-MM-DD HH:mm:ss"));
      },
      //过滤加0
      appendZero(obj) {
        if (obj < 10) {
          return "0" + obj;
        } else {
          return obj;
        }
      },
      addHomework() {
        let that = this
        const formData = new FormData()
        this.fileList.forEach(fileList => {
          formData.append('fileList', fileList.raw); //此处一定是append file.raw 上传文件只需维护fileList file.raw.name要加上 
        })
        formData.append('shId', this.form.shId)
        this.$axios.post("/api/uploadHomework", formData,
          {
            headers: { 'Content-Type': 'multipart/form-data' }
          })
          .then(function (res) {
            if (res.data === null) {
              that.$message({
                title: '系统提示',
                message: '请上传作业后再提交！',
                showClose: true,
                center: true,
                type: 'error'
              })
            } else {
              that.reload()
            }
          }).catch(function (error) {
            console.log(error);
          });
        this.$refs.upload.clearFiles()
      },
      resetForm(formName) {
        if (this.$refs[formName] !== undefined) {
          this.$refs[formName].resetFields();
        }
        this.$refs.upload.clearFiles()
      },
      handleChange(file, fileList) {
        let existFile = fileList.slice(0, fileList.length - 1).find(f => f.name === file.name)
        if (existFile) {
          this.$message.error('当前文件已经存在!');
        }
        this.fileList = fileList
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      getFile(item) {
        console.log('name:' + item.file.name)
        this.file = item.file
      },
      selectStudentHomework() {
        let params = {
          hId: this.$route.query.hId,
          sId: sessionStorage.getItem('sId')
        }
        let that = this
        this.$axios.post("/api/selectMyHomework", this.$qs.stringify(params))
          .then(function (res) {
            if (res.data == '') {
              that.$message({
                title: '系统提示',
                message: '当前课程没有学生提交作业！',
                showClose: true,
                center: true,
                type: 'success'
              })
            } else {
              that.form = res.data;
              console.log(res.data);
            }
          }).catch(function (error) {
            console.log(error);
          });
      },
    }
  }
</script>