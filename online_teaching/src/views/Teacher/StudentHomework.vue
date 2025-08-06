<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/TeacherContainer' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/TeacherContainer/TeacherCourse' }">授课课程</el-breadcrumb-item>
      <el-breadcrumb-item><a @click="prev1">课程详情</a></el-breadcrumb-item>
      <el-breadcrumb-item><a @click="prev">课程作业</a></el-breadcrumb-item>
      <el-breadcrumb-item>学生作业</el-breadcrumb-item>
    </el-breadcrumb>
    </br>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="学号" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.sId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" width="180">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>学号: {{ scope.row.sId }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.sName }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="作业文件" width="250">
        <template slot-scope="scope">
          <el-link @click='download(scope.$index, scope.row)'>{{ scope.row.shDoc}}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="分数" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.shScore!==0||null" @mouseover="update=false" @mouseleave="update=true">
            <el-rate v-model="scope.row.shScore" :disabled="update" allow-half show-score text-color="#ff9900"
              @change="addScore(scope.$index, scope.row)" score-template="{value}">
            </el-rate>
          </span>

          <span v-else>
            <el-rate v-model="scope.row.shScore" allow-half show-score text-color="#ff9900" score-template="{value}"
              @change="addScore(scope.$index, scope.row)">
            </el-rate>
          </span>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
      :page-size="pageSize" :page-sizes="[5, 10, 15, 20]" background layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <el-dialog title="打分" :visible.sync="ScoreFormVisible" @close="resetForm('scoreForm')">
      <el-form :model="scoreForm" ref="scoreForm">
        <el-form-item label="分数" :label-width="formLabelWidth" prop="crName">
          <el-input v-model="scoreForm.shScore" autocomplete="off" maxlength="3" type='number'></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="ScoreFormVisible = false;resetForm('scoreForm')">取 消</el-button>
        <el-button type="primary" @click="ScoreFormVisible = false;addCurriculum();resetForm('scoreForm');">确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    inject: ['reload'],
    data() {
      return {
        tableData: [],
        ScoreFormVisible: false,
        update: true,
        scoreForm: { shScore: '' },
        formLabelWidth: '50px',
        total: 0, //数据总数
        pageSize: 5, //每页的数据条数
        currentPage: 1, //默认开始页面
      }
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
      updateScore(index, row) {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      addScore(index, row) {
        console.log(row.shId)
        let params = {
          shId: row.shId,
          shScore: row.shScore
        }
        let that = this
        this.$axios.post("/api/addScore", this.$qs.stringify(params))
          .then(function (res) {
            that.reload();
          }).catch(function (error) {
            console.log(error);
          });
      },
      resetForm(formName) {
        if (this.$refs[formName] !== undefined) {
          this.$refs[formName].resetFields();
        }
      },
      download(index, row) {
        let params = { filename: row.shDoc }
        this.$axios.post('/api/downloadStudentHomework', this.$qs.stringify(params), { responseType: 'blob' })
          .then(function (res) {
            let url = window.URL.createObjectURL(new Blob([res.data]))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', row.shDoc)
            document.body.appendChild(link)
            link.click()
          }).catch(function (error) {
            console.log(error);
          });
      },
      selectStudentHomework() {
        let params = {
          hId: this.$route.query.hId,
          pageSize: this.pageSize,
          pageNum: this.currentPage
        }
        let that = this
        this.$axios.post("/api/selectStudentHomework", this.$qs.stringify(params))
          .then(function (res) {
            if (res.data.list == '') {
              that.tableData = [];
              that.$message({
                title: '系统提示',
                message: '当前课程没有学生提交作业！',
                showClose: true,
                center: true,
                type: 'success'
              })
            } else {
              that.tableData = res.data.list;
              that.total = res.data.total;
            }
          }).catch(function (error) {
            console.log(error);
          });
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val;
        this.selectStudentHomework();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage = val;
        this.selectStudentHomework();
      },
    }
  }
</script>