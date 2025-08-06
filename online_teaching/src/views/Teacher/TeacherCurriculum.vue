<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/TeacherContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/TeacherContainer/TeacherCourse' }">授课课程</el-breadcrumb-item>
            <el-breadcrumb-item>课程详情</el-breadcrumb-item>
        </el-breadcrumb>
        </br>
        <el-button type="primary" size="small" @click="addFormVisible = true">添加课程</el-button>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column label="课程编号" width="180" type="index" :index="indexMethod">
                <!-- <template slot-scope="scope">
                    <i class="el-icon-reading"></i>
                    <span style="margin-left: 10px">第{{ scope.$index+1 }}节</span>
                </template> -->
            </el-table-column>
            <el-table-column label="课程名称" width="180">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                        <p>课程名称: {{ scope.row.crName }}</p>
                        <p>所属课程: {{ $route.query.cName }}</p>
                        <div slot="reference" class="name-wrapper">
                            <el-tag size="medium">{{ scope.row.crName }}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column label="课件" width="180">
                <template slot-scope="scope">
                    <el-button size="mini" @click="selectCourseWare(scope.$index, scope.row)">查看课件
                    </el-button>
                </template>
            </el-table-column>
            <el-table-column label="作业" width="180">
                <template slot-scope="scope">
                    <el-button size="mini" @click="selectHomework(scope.$index, scope.row)">查看作业
                    </el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row),dialogFormVisible = true">编辑
                    </el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
            :page-size="pageSize" :page-sizes="[3,5,10,20]" background layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>

        <el-dialog title="修改信息" :visible.sync="dialogFormVisible" @close="resetForm('crForm')">
            <el-form :model="crForm" ref="crForm">
                <el-form-item label="课程名称" :label-width="formLabelWidth" prop="crName">
                    <el-input v-model="crForm.crName" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false;resetForm('crForm')">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false;updateCurriculum();resetForm('crForm');">确 定
                </el-button>
            </div>
        </el-dialog>
        <el-dialog title="添加课程" :visible.sync="addFormVisible" @close="resetForm('addForm')">
            <el-form :model="addForm" ref="addForm">
                <el-form-item label="课程名称" :label-width="formLabelWidth" prop="crName">
                    <el-input v-model="addForm.crName" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible = false;resetForm('addForm')">取 消</el-button>
                <el-button type="primary" @click="addFormVisible = false;addCurriculum();resetForm('addForm');">确 定
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
                addFormVisible: false,
                dialogFormVisible: false,
                crId: '',
                crForm: {
                    crName: '',
                },
                addForm: {
                    crName: '',
                },
                formLabelWidth: '100px',
                total: 0, //数据总数
                pageSize: 5, //每页的数据条数
                currentPage: 1, //默认开始页面
            }
        },
        mounted: function () {
            this.selectAllCurriculum();

        },
        methods: {
            indexMethod(index) {
                return "第" + ((this.currentPage - 1) * this.pageSize + index + 1) + "节";
            },
            selectHomework(index, row) {
                this.$router.push({ path: 'TeacherHomework', query: { crId: row.crId, tId: this.$route.query.tId } })
            },
            selectCourseWare(index, row) {
                this.$router.push({ path: 'CourseWare', query: { crId: row.crId } })
            },
            addCurriculum() {
                let that = this
                let cId = this.$route.query.cId;
                let params = {
                    cId: cId,
                    crName: this.addForm.crName
                }
                this.$axios.post("/api/addCurriculum", this.$qs.stringify(params))
                    .then(function (res) {
                        console.log(res.data);
                        that.reload();
                    }).catch(function (error) {
                        console.log(error);
                    });
            },
            updateCurriculum() {
                let that = this
                let params = {
                    crId: this.crId,
                    crName: this.crForm.crName
                }
                this.$axios.post("/api/updateCurriculumByCRid", this.$qs.stringify(params))
                    .then(function (res) {
                        console.log(res.data);
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
            handleEdit(index, row) {
                this.crId = row.crId
                this.crForm.crName = row.crName
                console.log(this.crName)
            },
            handleDelete(index, row) {
                this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let that = this
                    let params = {
                        crId: row.crId,
                    }
                    this.$axios.post("/api/deleteCurriculum", this.$qs.stringify(params))
                        .then(function (res) {
                            console.log(res.data);
                            if (res.data == '') {
                                that.$message({
                                    title: '删除提示',
                                    message: '该课程下存在课件，请删除先删除课件！',
                                    center: true,
                                    type: 'error'
                                })
                            } else {
                                that.$message({
                                    type: 'success',
                                    message: '删除成功!'
                                });
                                that.reload();

                            }
                        }).catch(function (error) {
                            console.log(error);
                        });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            selectAllCurriculum() {
                let that = this
                let cId = this.$route.query.cId;
                let params = {
                    cId: cId,
                    pageSize: this.pageSize,
                    pageNum: this.currentPage
                }
                this.$axios.post("/api/selectCurriculumByCid", this.$qs.stringify(params))
                    .then(function (res) {
                        if (res.data.list == '') {
                            that.$message({
                                title: '系统提示',
                                message: '当前课程没有课程！',
                                showClose: true,
                                center: true,
                                type: 'success'
                            })
                            that.tableData = []
                        } else {
                            that.tableData = res.data.list;
                            that.total = res.data.total;
                            console.log(res.data);
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });

            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.selectAllCurriculum();
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.selectAllCurriculum();
            },
            // beforeRemove(file, fileList) {
            //     return this.$confirm(`确定移除 ${file.name}？`);
            // },
            // handleChange(file, fileList) {
            //     let existFile = fileList.slice(0, fileList.length - 1).find(f => f.name === file.name)
            //     if (existFile) {
            //         this.$message.error('当前文件已经存在!');
            //         //fileList.pop()
            //     }
            //     this.fileList = fileList
            // },

        }
    }
</script>