<template>
    <div>
      <el-header>
        <div class="head-title">MeetHere</div>
        <el-menu
          mode="horizontal"
          background-color="#409EFF"
          text-color="white"
          active-text-color="white">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">介绍</el-menu-item>
          <el-submenu index="3">
            <template slot="title">预约</template>
            <el-menu-item index="3-1">选项1</el-menu-item>
            <el-menu-item index="3-2">选项2</el-menu-item>
            <el-menu-item index="3-3">选项3</el-menu-item>
          </el-submenu>
          <el-menu-item index="4">订单</el-menu-item>
          <el-menu-item index="5">新闻</el-menu-item>
          <el-menu-item index="6">留言</el-menu-item>
        </el-menu>
        <div class="head-name mine">我</div>
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

  .head-name {
    margin: 0 10px;
  }

  .mine {
    margin-left: auto;
  }

  .user-body {
    min-height: calc(100vh - 120px);
  }
</style>
