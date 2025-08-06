<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/StudentContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/StudentContainer/StudentCourse' }">已选课程</el-breadcrumb-item>
            <el-breadcrumb-item>课程详情</el-breadcrumb-item>
        </el-breadcrumb>
        </br>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column label="课程编号" width="180" prop="crId">
                <template slot-scope="scope">
                    <i class="el-icon-reading"></i>
                    <span style="margin-left: 10px">第{{ scope.$index+1 }}节</span>
                </template>
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
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
            :page-size="pageSize" :page-sizes="[5, 10, 15, 20]" background
            layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                tableData: [],
                crId: '',
                total: 0, //数据总数
                pageSize: 5, //每页的数据条数
                currentPage: 1, //默认开始页面 
            }
        },
        mounted: function () {
            this.selectAllCurriculum();

        },
        methods: {
            selectHomework(index, row) {
                this.$router.push({ path: 'StudentHomeworkFromS', query: { crId: row.crId, tId: this.$route.query.tId } })
            },
            selectCourseWare(index, row) {
                this.$router.push({ path: 'StudentCourseWare', query: { crId: row.crId } })
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