<template>
    <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/StudentContainer' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item><a @click="prev1">课程问题</a></el-breadcrumb-item>
        <el-breadcrumb-item><a @click="prev">我的问题</a></el-breadcrumb-item>
        <el-breadcrumb-item>问题详情</el-breadcrumb-item>
      </el-breadcrumb>
      </br>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="问题标题" prop="qTitle">
            <el-input v-model="ruleForm.qTitle" style="width:500px" :disabled="diasabledInput"></el-input>
        </el-form-item>
        <el-form-item label="问题内容" prop="qInfo">
            <el-input type="textarea" v-model="ruleForm.qInfo" style="width:500px" :disabled="diasabledInput"
                :autosize="{ minRows: 4, maxRows: 4}"></el-input>
        </el-form-item>
        <el-form-item label="回答老师">
            {{ruleForm.tName}}
        </el-form-item>
        <el-form-item label="回答内容" >
            <span v-if="ruleForm.qAnswer!==null">
                <el-input type="textarea" v-model="ruleForm.qAnswer" style="width:500px" disabled
                    :autosize="{ minRows: 4, maxRows: 4}"></el-input>
            </span>
            <span v-else style="color:#cccccc">老师还没有回答，请耐心等待！</span>
        </el-form-item>
        <el-form-item label="回答时间">
            <span v-if="ruleForm.qAnswer!==null">{{ruleForm.qTime| dateFmt('YYYY-MM-DD HH:mm')}}</span>
            <span v-else style="color:#cccccc">老师还没有回答，请耐心等待！</span>
        </el-form-item>
        <el-form-item>
            <span v-if="ruleForm.qAnswer==null">
                <el-button type="primary" @click="diasabledInput=false">编辑
                </el-button>
            </span>
            <span v-if="diasabledInput==false">
                <el-button type="primary" @click="submitForm('ruleForm');diasabledInput=true">提交
                </el-button>
            </span>
        </el-form-item>
    </el-form></div>
</template>
<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                ruleForm: {
                    qTitle: '',
                    qInfo: '',
                },
                diasabledInput:true,
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
        }, mounted() {
            this.selectQuestion();
        },
        methods: {
            prev1() {
                this.$router.go(-2)
            },
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