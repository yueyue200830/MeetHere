<template>
  <div>
    <div class="main-body">
      <div class="main-text">
        hello
      </div>
      <div class="main-btn">
        <el-button plain class="btn" @click="login">登录</el-button>
        <el-button plain class="btn" @click="signUp">注册</el-button>
      </div>
    </div>
    <user-footer></user-footer>
  </div>
</template>

<script>
    export default {
        name: "mainPage",
        methods: {
            login: function() {
                this.$router.push('/login');
            },
            signUp: function() {

            }
        }
    }
</script>

<style scoped>
  .main-body {
    height: calc(100vh - 60px);
    background: url("https://s1.tuchong.com/welcome-image/large/57476708.jpg") center center no-repeat;
    background-size: cover;
  }

  .main-text {
    font-size: 60px;
    line-height: 1.5em;
    text-align: center;
    margin-bottom: 20px;
    padding-top: 35vh;
    color: white;
  }

  .main-btn {
    margin: 20px auto auto;
    text-align: center;
  }

  .btn {
    font-size: 20px;
    margin: 10px;
  }
</style>
