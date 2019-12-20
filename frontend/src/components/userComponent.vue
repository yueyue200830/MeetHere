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
          :default-active="activePath"
          unique-opened
          router>
          <el-menu-item index="main">首页</el-menu-item>
          <el-menu-item index="revenue">介绍</el-menu-item>
          <el-menu-item index="booking">预约</el-menu-item>
          <el-menu-item index="order">订单</el-menu-item>
          <el-menu-item index="news">新闻</el-menu-item>
          <el-menu-item index="message">留言</el-menu-item>
          <el-submenu index="7" v-if="hasLoggedIn">
            <template slot="title">{{ userName }}</template>
            <el-menu-item index="info">个人信息</el-menu-item>
            <el-menu-item index="myMessage">我的留言</el-menu-item>
            <el-menu-item @click="logOut">退出</el-menu-item>
          </el-submenu>
          <el-menu-item v-else index="login">登录</el-menu-item>
        </el-menu>
      </el-header>
      <div class="user-body">
        <router-view/>
      </div>
      <user-footer/>
    </div>
</template>

<script>
  import { mapMutations } from 'vuex'
  export default {
    name: "userComponent",
    data() {
      return {
        activePath: '',
        // userName: 'apple',
      };
    },
    computed:{
      hasLoggedIn () {
        return this.$store.getters.hasLoggedIn;
      },
      userId () {
        return this.$store.getters.getUserId;
      },
      userName () {
        return this.$store.getters.getUserName;
      }
    },
    created() {
      let p = this.$route.path;
      this.activePath = p.split('/')[2];
      this.checkLogin();
    },
    methods: {
      ...mapMutations(['userLogOut', 'checkLogin']),
      logOut() {
        this.userLogOut();
      },
    }
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
