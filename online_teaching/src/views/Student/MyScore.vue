<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/StudentContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a @click="prev">课程成绩</a></el-breadcrumb-item>
            <el-breadcrumb-item>我的成绩</el-breadcrumb-item>
          </el-breadcrumb>
          </br>
        <el-form ref="form" :model="form" label-width="120px">
            <el-form-item label="学生姓名：">
                <span v-model="form.hName">{{form.sName}}</span>
            </el-form-item>
            <el-form-item label="专业：">
                <span v-model="form.hInfo">{{form.sMajor}}</span>
            </el-form-item>
            <el-form-item label="班级：">
                <span v-model="form.hInfo">{{form.sClass}}</span>
            </el-form-item>
            <el-form-item label="作业成绩：">
                <span v-if="form.shScore==0" style="color:#cccccc">您还没提交作业或老师还未批改</span>
                <span v-else> {{form.shScore}}</span>
            </el-form-item>
            <el-form-item label="期末成绩：">
                <span v-if="form.shScore==0" style="color:#cccccc">请耐心等待！</span>
                <span v-else> {{form.examScore}}</span>
            </el-form-item>
            <el-form-item label="最终成绩：">
                <span v-if="form.shScore==0" style="color:#cccccc">请耐心等待！</span>
               <span v-else>{{form.finalScore}}</span> 
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                form: {
                    sName: '',
                    sMajor: '',
                    sClass: '',
                    shScore: 0,
                    examScore: 0,
                    finalScore:0,
                },
            }
        },
        mounted: function () {
            this.selectMyScoreByCidAndSid()
        },
        methods: {
            prev() {
                this.$router.go(-1)
            },
            selectMyScoreByCidAndSid() {
                let params = {
                    cId: this.$route.query.cId,
                    sId: sessionStorage.getItem('sId')
                }
                let that = this
                this.$axios.post("/api/selectMyScoreByCidAndSid", this.$qs.stringify(params))
                    .then(function (res) {
                        if (res.data == null) {
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