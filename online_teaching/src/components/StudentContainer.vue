<template>
  <div class="app">
    <el-container>
      <el-aside class="app-side app-side-left" :class="isCollapse ? 'app-side-collapsed' : 'app-side-expanded'">
        <div class="app-side-logo">
          <img src="@/assets/logo.png" :width="isCollapse ? '60' : '60'" height="60" />
        </div>
        <div>
          <!-- 侧边栏 -->
          <el-menu default-active="this.$route.path" @open="handleOpen" :collapse="isCollapse" router>
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-s-home"></i>
                <span slot="title">个人中心</span>
              </template>
              <el-menu-item index="/StudentContainer/StudentInfo"><i class="el-icon-user"></i>个人信息</el-menu-item>
              <el-menu-item index="/StudentContainer/StudentPassword"><i class="el-icon-lock"></i>修改密码</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-s-management"></i>
                <span slot="title">我的课程</span>
              </template>
              <el-menu-item index="/StudentContainer/StudentCourse"><i class="el-icon-document"></i>已选课程</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title">
                <i class="el-icon-s-data"></i>
                <span slot="title">课程成绩</span>
              </template>
              <el-menu-item index="/StudentContainer/StudentCourseScore"><i class="el-icon-medal-1"></i>我的成绩
              </el-menu-item>
            </el-submenu>
            <el-submenu index="4">
              <template slot="title">
                <i class="el-icon-question"></i>
                <span slot="title">课程答疑</span>
              </template>
              <el-menu-item index="/StudentContainer/StudentQuestion"><i class="el-icon-collection-tag"></i>我的问题
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </div>
      </el-aside>
      <el-container>
        <!-- header -->
        <el-header class="app-header">
          <div style="width: 60px; cursor: pointer;" @click.prevent="toggleSideBar">
            <i v-show="!isCollapse" class="el-icon-d-arrow-left"></i>
            <i v-show="isCollapse" class="el-icon-d-arrow-right"></i>
          </div>
          <!-- 我是样例菜单 -->
          <el-menu :default-active="this.$route.path" mode="horizontal" @open="handleSelect" active-text-color="#409EFF"
            router>
            <el-menu-item index="/StudentContainer/StudentInfo">个人中心</el-menu-item>
            <el-menu-item index="/StudentContainer/StudentCourse">课程中心</el-menu-item>
            <el-menu-item index="/StudentContainer/StudentCourseScore">我的成绩</el-menu-item>
            <el-menu-item index="/StudentContainer/StudentQuestion">我的问题</el-menu-item>
          </el-menu>
          <!-- 右上用户 -->
          <div class="app-header-userinfo">
            <el-dropdown trigger="hover" :hide-on-click="false">
              <span class="el-dropdown-link">
                {{ username }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>我的消息</el-dropdown-item>
                <el-dropdown-item>设置</el-dropdown-item>
                <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <!-- body -->
        <el-main class="app-body">
          <template>
            <router-view />
          </template>
        </el-main>
        <el-footer></el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'container',
    data() {
      return {
        username: '',
        sId: '',
        isCollapse: false
      }
    },
    methods: {
      toggleSideBar() {
        this.isCollapse = !this.isCollapse
      },
      logout: function () {
        this.$confirm('确认退出?', '提示', {})
          .then(() => {
            sessionStorage.clear();
            this.$router.push('/StudentLogin');
          })
          .catch(() => { });
      },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
    },
    created: function () {
      let user = sessionStorage.getItem('user');
      if (user) {
        this.username = user;
      } else {
        this.$router.push({
          redirect: '/StudentLogin',
        })
      }
    },
  }
</script>

<style>
  .el-main {
    background-color: rgb(255, 255, 255);
    line-height: 20px;
    min-height: calc(100vh - 120px);
  }

  .el-aside {
    background-color: #D3DCE6;
  }
</style>