<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/TeacherContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a @click="prev">授课课程</a></el-breadcrumb-item>
            <el-breadcrumb-item>选课学生</el-breadcrumb-item>
        </el-breadcrumb>
        </br>
        <el-table :data="tableData" style="width: 100%" stripe>
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
            <el-table-column label="性别" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.row.sSex }}</span>
                </template>
            </el-table-column>
            <el-table-column label="年龄" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.row.sAge }}</span>
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
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
            :page-size="pageSize" :page-sizes="[5, 10, 15, 20]" background
            layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                total: 0, //数据总数
                pageSize: 5, //每页的数据条数
                currentPage: 1, //默认开始页面
            }
        },
        mounted: function () {
            this.seletStudent()
        },
        methods: {
            prev() {
                this.$router.go(-1)
            },
            seletStudent() {
                let that = this
                let cId = this.$route.query.cId;
                let params = {
                    cId: cId,
                    pageSize: this.pageSize,
                    pageNum: this.currentPage
                }
                this.$axios.post("/api/selectStudentByCid", this.$qs.stringify(params))
                    .then(function (res) {
                        if (res.data.list == '') {
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
                            console.log(res.data);
                        }
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