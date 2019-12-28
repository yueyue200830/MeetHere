<template>
  <div class="login-body login-body-img" :loading="loading">
    <div class="login-box">
      <el-row style="height: 100%;">

        <el-col  class="login-right-body">
          <el-row style="width: 95%">
            <el-row class="login-row" style="margin-top: 3%">
              <h2 id="h2">管理员登录</h2>
            </el-row>
            <el-row class="login-row">
              <el-form ref="form" :model="form" :rules="loginRules" label-width="80px" style="width: 90%">
                <el-form-item label="账号" prop="user_name">
                  <el-input v-model="form.user_name"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="user_key">
                  <el-input :type="passw" v-model="form.user_key">
                    <i slot="suffix" :class="icon" @click="showPass"></i>
                  </el-input>

                </el-form-item>
              </el-form>
            </el-row>
            <el-row class="login-forget login-row">
              <a @click="goToLink">返回首页</a>
              <!-- <span><a :href="goToLink('forget')">忘记密码</a></span> -->
            </el-row>
            <el-row style="margin-top: 3%">
              <el-button type="primary" size="small" @click="login">登录</el-button>
            </el-row>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import axios from 'axios'
const loginValid = (condition) => axios.post('/app/managerLoginCheck', condition)
export default {
  name: 'login',
  data () {
    return {
      loading: false,
      passw: 'password',
      icon: 'el-input__icon el-icon-view',
      form: {
        user_name: '',
        user_key: ''
      },
      loginRules: {
        user_name: [
          this.requiredInput('用户名')
        ],
        user_key: [
          this.requiredInput('密码')
        ]
      }
    }
  },
  methods: {
    ...mapMutations(['managerLogin']),
    showPass () {
      // 点击图标是密码隐藏或显示
      if (this.passw == 'text') {
        this.passw = 'password'
        this.icon = 'el-input__icon el-icon-view'
      } else {
        this.passw = 'text'
        this.icon = 'el-input__icon el-icon-loading'
      }
    },
    login () {
      let condition = this.form
      if (condition.user_name && condition.user_key) {
        loginValid(condition).then(data => {
          this.loading = false
          if (data.data) {
            this.managerLogin({ Authorization: data.data, name: this.form.user_name })
            this.$router.push('/manager/userInfo')
          } else {
            this.onAlertError('用户名与密码不匹配')
          }
        })
      } else {
        this.onAlertError('用户名和密码不能为空！')
      }

      // 正式版本要注释掉
      // let data = 1;
      // this.managerLogin({ Authorization: data, name: this.form.user_name });
      // this.$router.push('/manager/userInfo');
    },
    goToLink () {
      this.$router.push('/user/main')
    }
  }
}
</script>

<style scoped>
  .login-body {
    width: 100%;
    height: 100%;
    display:-webkit-flex;
    display: flex;
    -webkit-justify-content:center;
    justify-content:center;
    align-items: center;
    text-align: center;
  }

  .login-box {
    width: 50%;
    height: 55%;
    min-width: 280px;
    border: 1px black solid;
    box-shadow: 8px 8px 12px #eaeaf782;
  }

  .login-div {
    border: #aaaaaa 1px solid;
    padding: 0 20px 20px 0;
  }

  .login-forget {
    font-size: 15px;
    text-align: right;
    width: 90%;
  }

  .login-forget span {
    margin-left: 10px;
  }

  .left-login {
    height: 100%;
    text-align: center;
    display:-webkit-flex;
    display: flex;
    -webkit-justify-content:center;
    justify-content:center;
    align-items: center;
    background-color: rgba(255, 255, 255, 0.2);
    /*opacity: 0.5;*/
    /*filter:alpha(opacity=50); !* 针对 IE8 以及更早的版本 *!*/
  }

  .login-body-img {
    background-image: url("../assets/background.jpg");
    background-position: center center;
    background-repeat: no-repeat;
    background-size:cover;
    background-attachment:fixed;
  }

  .login-row{
    margin-bottom: 5%;
  }

  .login-right-body {
    height: 100%;
    background-color: white;
    display:-webkit-flex;
    display: flex;
    -webkit-justify-content:center;
    justify-content:center;
    align-items: center;
  }

</style>
