<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/StudentContainer' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a @click="prev">课程问题</a></el-breadcrumb-item>
            <el-breadcrumb-item>我的问题</el-breadcrumb-item>
          </el-breadcrumb>
          </br>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column label="编号" width="100">
                <template slot-scope="scope">
                    {{ scope.$index+1 }}
                </template>
            </el-table-column>
            <el-table-column label="问题标题" width="180">
                <template slot-scope="scope">
                    {{scope.row.qTitle}}
                </template>
            </el-table-column>
            <el-table-column label="问题内容" width="180" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    {{scope.row.qInfo}}
                </template>
            </el-table-column>
            <el-table-column label="答疑老师" width="100">
                <template slot-scope="scope">
                    {{scope.row.tName}}
                </template>
            </el-table-column>
            <el-table-column label="状态" width="180">
                <template slot-scope="scope">
                    <span v-if="scope.row.qAnswer!=null">
                        已回答,点击按钮查看详情
                    </span>
                    <span v-else style="color:#cccccc">
                        老师还未回答
                    </span>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">查看</el-button>
                    <span v-if="scope.row.qAnswer==null">
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </span>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-size="pageSize" :page-sizes="[5, 10, 15, 20]" background
        layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
        <el-button type="primary" @click="addQuestion()">添加问题</el-button>
    </div>
</template>

<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                tableData: [],
                total: 0, //数据总数
                pageSize: 5, //每页的数据条数
                currentPage: 1, //默认开始页面
            }
        },
        mounted() {
            this.selectAllQuestion()
        },
        methods: {
            prev() {
                this.$router.go(-1)
            },
            addQuestion() {
                this.$router.push({ path: 'AddQuestion', query: { cId: this.$route.query.cId, tId: this.$route.query.tId } })
            },
            handleEdit(index, row) {
                this.$router.push({ path: 'StudentQuestionByQid', query: { qId: row.qId } })
                console.log(index, row);
            },
            handleDelete(index, row) {
                this.$confirm('此操作将永久删除该提问, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let that = this
                    let params = {
                        qId: row.qId,
                    }
                    this.$axios.post("/api/deleteQuestion", this.$qs.stringify(params))
                        .then(function (res) {
                            that.reload();
                        }).catch(function (error) {
                            console.log(error);
                        });
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
                console.log(index, row);
            },
            selectAllQuestion() {
                let params = {
                    cId: this.$route.query.cId,
                    sId: sessionStorage.getItem('sId'),
                    pageSize: this.pageSize,
                    pageNum: this.currentPage
                }
                let that = this
                this.$axios.post("/api/selectQuestionBySidAndCid", this.$qs.stringify(params))
                    .then(function (res) {
                        if (res.data.list == null) {
                            that.$message({
                                title: '系统提示',
                                message: '当前课程您还没有提问！',
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