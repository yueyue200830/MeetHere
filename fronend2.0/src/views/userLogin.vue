<template>
  <div>
    <el-header>
      <div class="head-title" @click="toMainPage">MeetHere</div>
    </el-header>
    <el-main class="user-body">
      <div class="user-title">
        用户登录
      </div>
      <el-form :model="loginForm" ref="loginForm" class="login-form" label-width="100px">
        <el-form-item
          label="用户名"
          :rules="[
              { required: true, message: '用户名不可为空'},
            ]"
          prop="name"
        >
          <el-input
            type="name"
            v-model="loginForm.name"
            placeholder="请输入用户名"
            maxlength="22"
          />
        </el-form-item>
        <el-form-item
          label="密码"
          :rules="[
              { required: true, message: '密码不可为空'},
            ]"
          prop="password">
          <el-input
            type="password"
            placeholder="请输入密码"
            v-model="loginForm.password"
            show-password
            maxlength="18"
          >
          </el-input>
        </el-form-item>
        <el-form-item class="login-button">
          <el-button type="primary" @click="login" class="login-button-submit" :loading="LoggingIn">登录</el-button>
          <el-button @click="register">注册</el-button>
          <el-button type="text" @click="managerLogin">管理员登录</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <user-footer/>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import UserFooter from '@/components/userFooter.vue'
export default {
  name: 'userLogin',
  components: {
    UserFooter
  },
  data () {
    return {
      loginForm: {
        name: '',
        password: ''
      },
      LoggingIn: false
    }
  },
  methods: {
    ...mapMutations(['userLogin']),
    login: function () {
      this.LoggingIn = true
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
          this.$http
            .get('/app/Login', {
              params: {
                login: this.loginForm
              } })
            .then(response => {
              if (response.data !== -1) {
                this.userLogin({ Authorization: response.data, name: this.loginForm.name })
                this.$router.push('main')
              } else {
                this.$message.error('用户名或密码错误，请重试！')
              }
            })
            .catch(error => {
              this.$message.error('登录失败，请重试！')
            })
            .finally(() => {
              this.LoggingIn = false
            })
        } else {
          this.LoggingIn = false
        }
      })
    },
    managerLogin: function () {
      this.$router.push('/login')
    },
    register: function () {
      this.$router.push('register')
    },
    toMainPage: function () {
      this.$router.push('main')
    }
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
    margin: 0 auto;
    padding: 0 40px;
  }

  .head-title:hover {
    cursor:pointer;
  }

  .user-body {
    min-height: calc(100vh - 120px);
  }

  .user-title {
    text-align: center;
    font-size: 30px;
    margin: 100px 0 40px;
    color: #303133;
  }

  .login-form {
    width: 400px;
    margin: 10px auto;
  }

  .login-button {
    margin-top: 40px;
  }

</style>
