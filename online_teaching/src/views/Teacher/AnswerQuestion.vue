<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/TeacherContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/TeacherContainer/TeacherQuestion' }">选择课程</el-breadcrumb-item>
            <el-breadcrumb-item><a @click="prev">学生问题</a></el-breadcrumb-item>
            <el-breadcrumb-item>问题详情</el-breadcrumb-item>
        </el-breadcrumb>
        </br>

        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="问题标题" prop="qTitle">
                <el-input v-model="ruleForm.qTitle" style="width:500px" disabled></el-input>
            </el-form-item>
            <el-form-item label="问题内容" prop="qInfo">
                <el-input type="textarea" v-model="ruleForm.qInfo" style="width:500px" disabled
                    :autosize="{ minRows: 4, maxRows: 4}"></el-input>
            </el-form-item>
            <el-form-item label="提问学生">
                {{ruleForm.sName}}
            </el-form-item>
            <el-form-item label="回答内容" prop="qAnswer">
                <el-input type="textarea" v-model="ruleForm.qAnswer" style="width:500px" :disabled="diasabledInput"
                    :autosize="{ minRows: 4, maxRows: 4}"></el-input>
            </el-form-item>
            <el-form-item label="回答时间">
                <span v-if="ruleForm.qTime==null">
                   请回答学生问题~！
                </span>
                <span v-else>{{ruleForm.qTime| dateFmt('YYYY-MM-DD HH:mm')}}</span>
            </el-form-item>
            <el-form-item>
                <span>
                    <el-button type="primary" @click="diasabledInput=false">编辑
                    </el-button>
                </span>
                <span v-if="diasabledInput==false">
                    <el-button type="primary" @click="submitForm('ruleForm');diasabledInput=true">提交
                    </el-button>
                </span>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                ruleForm: {
                    qTitle: '',
                    qInfo: '',
                    qAnswer: '',
                },
                diasabledInput: true,
                rules: {
                    qAnswer: [
                        { required: true, message: '请填写问题内容', trigger: 'blur' },
                        { min: 10, message: '问题最少10个字符', trigger: 'blur' }
                    ]
                }
            };
        }, mounted() {
            this.selectQuestion();
        },
        methods: {
            prev() {
                this.$router.go(-1)
            },
            selectQuestion() {
                let params = {
                    qId: this.$route.query.qId,
                }
                let that = this
                this.$axios.post("/api/selectQuestionByQid", this.$qs.stringify(params))
                    .then(function (res) {
                        console.log(res.data)
                        that.ruleForm = res.data
                    }).catch(function (error) {
                        console.log(error);
                    });
            },
            submitForm(formName) {
                var that = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let params = {
                            qId: that.$route.query.qId,
                            qTitle: this.ruleForm.qTitle,
                            qInfo: this.ruleForm.qInfo,
                            qAnswer: this.ruleForm.qAnswer,
                        }
                        this.$axios.post("/api/updateQuestion", this.$qs.stringify(params))
                            .then(function (res) {
                                that.reload()
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