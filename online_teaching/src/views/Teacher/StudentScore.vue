<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/TeacherContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a @click="prev">选择课程</a></el-breadcrumb-item>
            <el-breadcrumb-item>学生成绩</el-breadcrumb-item>
        </el-breadcrumb>
        </br>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column label="学号" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.row.sId }}</span>
                </template>
            </el-table-column>
            <el-table-column label="姓名" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.row.sName }}</span>
                </template>
            </el-table-column>
            <el-table-column label="平时成绩" width="150">
                <template slot-scope="scope">
                    <span>{{ scope.row.shScore }}</span>
                </template>
            </el-table-column>
            <el-table-column label="期末成绩" width="120">
                <template slot-scope="scope">
                    <span v-if="scope.row.examScore!==0">{{ scope.row.examScore }}</span>
                    <span v-else>
                        <el-link style="color:#cccccc"
                            @click="ScoreFormVisible = true;handleEdit(scope.$index, scope.row)">请登记成绩</el-link>
                    </span>
                </template>
            </el-table-column>
            <el-table-column label="最终成绩" width="120">
                <template slot-scope="scope">
                    <span v-if="scope.row.finalScore!==0">{{ scope.row.finalScore }}</span>
                    <span v-else style="color:#cccccc">请登记成绩</span>
                </template>
            </el-table-column>
            <el-table-column label="学院" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.row.dName }}</span>
                </template>
            </el-table-column>
            <el-table-column label="专业" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.row.sMajor }}</span>
                </template>
            </el-table-column>
            <el-table-column label="班级" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.row.sClass }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" @click="ScoreFormVisible = true;handleEdit(scope.$index, scope.row)">编辑期末成绩
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div align="center">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-size="pageSize" :page-sizes="[3,5,10,20]" background layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
        </div>
        
        <el-button @click="FinalFormVisible = true;">生成最终成绩</el-button>
        <el-button @click="exportExcel()">导出excel</el-button>
        <el-dialog title="打分" :visible.sync="ScoreFormVisible" @close="resetForm('scoreForm')">
            <el-form :model="scoreForm" ref="scoreForm">
                <el-form-item label="分数" :label-width="formLabelWidth" prop="examScore">
                    <el-input v-model="scoreForm.examScore" autocomplete="off" maxlength="3" type='number'></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="ScoreFormVisible = false;resetForm('scoreForm')">取 消</el-button>
                <el-button type="primary" @click="ScoreFormVisible = false;addExamScore();resetForm('scoreForm');">确 定
                </el-button>
            </div>
        </el-dialog>

        <el-dialog title="设置分数比例" :visible.sync="FinalFormVisible" @close="resetForm('finalForm')">
            <el-form :model="finalForm" ref="finalForm" :rules="rules" status-icon>
                <el-form-item label="平时作业比例" :label-width="formLabelWidth" prop="dailyRate">
                    <el-input v-model="finalForm.dailyRate" autocomplete="off" maxlength="3" @blur="scoreRule">
                    </el-input>
                </el-form-item>
                <el-form-item label="期末考试比例" :label-width="formLabelWidth" prop="examRate">
                    <el-input v-model="finalForm.examRate" autocomplete="off" maxlength="3" @blur="scoreRule">
                    </el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="FinalFormVisible = false;resetForm('finalForm')">取 消</el-button>
                <el-button type="primary"
                    @click="FinalFormVisible = false;addFinalScore('finalForm');resetForm('finalForm');">确 定
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        inject: ['reload'],
        data() {
            var checkRate = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('数值不能为空'));
                }
                else if (!(/^((([^0][0-9]+|0)\.([0-9]{1,2}))$)|^(([1-9]+)\.([0-9]{1,2})$)/).test(value)) {
                    callback(new Error('请输入比例值在0-1之间的小数！'));
                } else {
                    if (this.finalForm.dailyRate != 0 && this.finalForm.examRate != 0) {
                        if (parseFloat(Number(this.finalForm.dailyRate) + Number(this.finalForm.examRate)) != 1) {
                            callback(new Error('输入值之和必须为1'));
                        }
                        callback();
                    }
                    callback();
                }
            };
            return {
                tableData: [],
                tableData1: [],
                ScoreFormVisible: false,
                FinalFormVisible: false,
                scoreForm: { examScore: '' },
                finalForm: {
                    dailyRate: '',
                    examRate: ''
                },
                formLabelWidth: '150px',
                sId: '',
                cId: '',
                examScore: '',
                total: 0, //数据总数
                pageSize: 5, //每页的数据条数
                currentPage: 1, //默认开始页面
                rules: {
                    dailyRate: [
                        { validator: checkRate, trigger: 'blur' }
                    ],
                    examRate: [
                        { validator: checkRate, trigger: 'blur' }
                    ]
                }
            }
        },
        mounted: function () {
            this.seletStudent()
            this.seletStudent1()
        },
        methods: {
            //导出Excel
            exportExcel: function () {
                require.ensure([], () => {
                    const { export_json_to_excel } = require('../../excel/Export2Excel');
                    //设置Excel表格第一行的标题
                    const tHeader = ['学号', '姓名', '平时成绩', '期末成绩', '最终成绩', '学院', '专业', '班级'];
                    //设置标题对应的字段
                    let filterVal = ['sId', 'sName', 'shScore', 'examScore', 'finalScore', 'dName', 'sMajor', 'sClass'];
                    //把后台传过来的数据存到list
                    const list = this.tableData1;
                    const data = this.formatJson(filterVal, list);
                    export_json_to_excel(tHeader, data, '学生成绩表');
                })
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },

            scoreRule() {
                console.log("1   " + this.finalForm.dailyRate)
                console.log("2  " + parseFloat(Number(this.finalForm.dailyRate) + Number(this.finalForm.examRate)))
            },
            addFinalScore(formName) {
                console.log("dailyRate  " + this.finalForm.dailyRate)
                console.log("examRate   " + this.finalForm.examRate)
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(this.tableData)
                        let that = this
                        let params = {
                            tableData: this.tableData1,
                            cId: this.$route.query.cId,
                            dailyRate: this.finalForm.dailyRate,
                            examRate: this.finalForm.examRate,
                        }
                        this.$axios.post("/api/addFinalScore", JSON.stringify(params), { headers: { 'Content-Type': "application/json;charset=utf-8" } })
                            .then(function (res) {
                                that.reload();
                            }).catch(function (error) {
                                console.log(error);
                            });
                    } else {
                        console.log('error submit!!');
                        this.$message({
                            title: '系统提示',
                            message: '分数比例错误，提交失败！',
                            showClose: true,
                            center: true,
                            type: 'error'
                        })
                        return false;
                    }
                });
            },
            addExamScore() {
                let that = this
                let params = {
                    sId: that.sId,
                    examScore: this.scoreForm.examScore
                }
                this.$axios.post("/api/addExamScore", this.$qs.stringify(params))
                    .then(function (res) {
                        that.reload();
                    }).catch(function (error) {
                        console.log(error);
                    });
            },
            handleEdit(index, row) {
                this.sId = row.sId
                this.cId = row.cId
                this.examScore = row.examScore
            },
            prev() {
                this.$router.go(-1)
            },
            resetForm(formName) {
                if (this.$refs[formName] !== undefined) {
                    this.$refs[formName].resetFields();
                }
            },
            seletStudent() {
                let that = this
                let cId = this.$route.query.cId;
                let params = { cId: cId,
                    pageSize: this.pageSize,
                    pageNum: this.currentPage }
                this.$axios.post("/api/selectScoreByCid", this.$qs.stringify(params))
                    .then(function (res) {
                        if (res.data == '') {
                            that.$message({
                                title: '系统提示',
                                message: '您当前课程没有学生！',
                                showClose: true,
                                center: true,
                                type: 'success'
                            })
                            that.tableData = []
                        } else {
                            that.tableData = res.data.list;
                            that.total = res.data.total;
                            console.log(that.tableData);
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });
            },
            seletStudent1() {
                let that = this
                let cId = this.$route.query.cId;
                let params = { cId: cId}
                this.$axios.post("/api/selectScoreByCid1", this.$qs.stringify(params))
                    .then(function (res) {
                            that.tableData1 = res.data;
                            console.log("11"+that.tableData1);
                    }).catch(function (error) {
                        console.log(error);
                    });
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.seletStudent();
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.seletStudent();
            },
        }
    }
</script>