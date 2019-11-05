<template>
    <div>
      <el-header>
        <div class="head-title">MeetHere</div>
        <el-menu
          mode="horizontal"
          background-color="#409EFF"
          text-color="white"
          active-text-color="white"
          class="user-menu"
          unique-opened
          router>
          <el-menu-item index="main">首页</el-menu-item>
          <el-menu-item index="revenue">介绍</el-menu-item>
          <el-submenu index="3">
            <template slot="title">预约</template>
            <el-menu-item index="booking">选项1</el-menu-item>
            <el-menu-item index="booking">选项2</el-menu-item>
            <el-menu-item index="booking">选项3</el-menu-item>
          </el-submenu>
          <el-menu-item index="order">订单</el-menu-item>
          <el-menu-item index="news">新闻</el-menu-item>
          <el-menu-item index="message">留言</el-menu-item>
          <el-submenu index="7">
            <template slot="title">我</template>
            <el-menu-item index="info">个人信息</el-menu-item>
            <el-menu-item index="7-3">退出</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-header>
      <div class="user-body">
        <router-view></router-view>
      </div>
      <user-footer></user-footer>
    </div>
</template>

<script>
    export default {
        name: "userComponent"
    }
</script>

<style scoped>
  .el-header {
    background-color: #409EFF;
    color: white;
    text-align: center;
    line-height: 60px;
    display: flex;
    flex-direction: row;
  }

  .head-title {
    margin: 0 10px;
    font-size: 18px;
    font-weight: 700;
  }

  .user-menu {
    margin-left: auto;
  }

  .user-body {
    min-height: calc(100vh - 120px);
  }
</style>
