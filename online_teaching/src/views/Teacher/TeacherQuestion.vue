<template>
    <div class="teacher-course">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/TeacherContainer' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>选择课程</el-breadcrumb-item>
      </el-breadcrumb>
    </br>
      <el-table :data="msg" border style="width: 880px">
        <el-table-column prop="cId" label="课程编号" width="180">
          <template slot-scope="scope">
           {{scope.row.cId}}
          </template>
        </el-table-column>
        <el-table-column label="课程名称" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.cName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="cInfo" label="课程信息" width="220"></el-table-column>
        <el-table-column label="查看学生提问" width="120">
          <template slot-scope="scope">
            <el-button size="small" @click="selectStudent(scope.$index, scope.row)">查 看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="cCapacity" label="课程剩余容量"></el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
    export default {
      name: 'teacher-course',
      data() {
        return {
          msg: [],
        }
      },
      created:function () {
        this.selectAllCourse();
      },
      mounted: function () {
        let user = sessionStorage.getItem('user');
        let tId = sessionStorage.getItem('tId');
        if (user) {
          this.username = user;
          this.tId = tId;
        }
  
      },
  
      methods: {
        selectStudent(index, row) {
          this.$router.push({ path: 'SelectStudentQuestion', query: { cId: row.cId} })
        },
        selectAllCourse() {
          let that = this
          let tId = sessionStorage.getItem('tId');
          let params = { tId: tId }
          this.$axios.post("/api/selectCourseByTid", this.$qs.stringify(params))
            .then(function (res) {
              if (res.data == '') {
                that.$message({
                  title: '系统提示',
                  message: '您当前没有授课课程！',
                  showClose: true,
                  center: true,
                  type: 'success'
                })
                that.msg = []
              } else { that.msg = res.data; }
            }).catch(function (error) {
              console.log(error);
            });
        }
      },
    }
  
  </script>