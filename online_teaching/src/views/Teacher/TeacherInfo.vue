<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/TeacherContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>个人信息</el-breadcrumb-item>
        </el-breadcrumb>
        </br>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm register-page"
        status-icon>
        <el-form-item label="用户名：" prop="tUsername">
            <el-input v-model="ruleForm.tUsername" style="width:250px" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名：" prop="tName">
            <el-input v-model="ruleForm.tName" style="width:250px"></el-input>
        </el-form-item>
        <el-form-item label="年龄：" prop="tAge">
            <el-input v-model.number="ruleForm.tAge" style="width:100px"></el-input>
        </el-form-item>
        <el-form-item label="性别：" prop="tSex">
            <el-radio-group v-model="ruleForm.tSex">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        </el-form-item>
    </el-form></div>
</template>
<script>
    export default {
        inject: ['reload'],
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
            return {
                ruleForm: {
                    tUsername: '',
                    tName: "",
                    tAge: 0,
                    tSex: '',
                },
                rules: {
                    tName: [
                        { required: true, message: "请输入真实姓名", trigger: "blur" },
                        { min: 2, max: 8, message: "长度在 2 到 8 个字符", trigger: "blur" }
                    ],
                    tAge: [
                        { required: true, validator: checkAge, trigger: 'blur' }
                    ],
                    tSex: [
                        { required: true, message: "请选择性别", trigger: "blur" }
                    ]
                }
            };
        },
        mounted() {
            this.selectStudentBySid()
        },
        methods: {
            selectStudentBySid() {
                let params = {
                    tId: sessionStorage.getItem('tId'),
                }
                this.$axios.post("/api/selectTeacherByTid", this.$qs.stringify(params))
                    .then(res => {
                        this.ruleForm = res.data
                        console.log(this.ruleForm)
                    }).catch(error => {
                        console.log(error);
                    })
            },
            submitForm() {
                this.$refs.ruleForm.validate(valid => {
                    if (valid) {
                        let params = {
                            tId: sessionStorage.getItem('tId'),
                            age: this.ruleForm.tAge,
                            sex: this.ruleForm.tSex,
                            name: this.ruleForm.tName
                        }
                        let that=this
                        this.$axios.post("/api/updateTeacherByTid", this.$qs.stringify(params))
                            .then(res => {
                                console.log(res);
                                that.reload()
                                    this.$message({
                                        title: '系统提示',
                                        message: '修改个人信息成功',
                                        showClose: true,
                                        center: true,
                                        type: 'success'
                                    });
                            }).catch(error => {
                                console.log(error);
                            })
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            },
        }
    };
</script>