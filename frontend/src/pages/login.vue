<template>
  <div class="login-body login-body-img" :loading="loading">
    <div class="login-box">
      <el-row style="height: 100%;">

        <el-col  class="login-right-body">
          <el-row style="width: 95%">
            <el-row class="login-row" style="margin-top: 3%">
              <h2>登录</h2>
            </el-row>
            <el-row class="login-row">
              <el-form ref="form" :model="form" :rules="loginRules" label-width="80px" style="width: 90%">
                <el-form-item label="账号" prop="user_name">
                  <el-input v-model="form.user_name"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="user_key">
                  <el-input v-model="form.user_key"></el-input>
                </el-form-item>
              </el-form>
            </el-row>
            <el-row class="login-forget login-row">
              <span><a :href="goToLink('register')">立即注册</a></span>
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
import '../css/loginCSS.css';
import axios from 'axios';
const loginValid =(condition)=> axios.post('/app/api/loginCheck', condition);
export default {
  name: 'login',
  data () {
    return {
      loading: false,
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
    };
  },
  methods: {
    login () {
      let condition = this.form;
      // loginValid({condition}).then(data => {
      //     this.loading = false;
      //     if (data) {
      //       this.$router.push('/index');
      //     } else {
      //       this.onAlertError('用户名与密码不匹配');
      //     }
      // });
      this.$router.push('/manager/userInfo');
    },
    goToLink (action) {
      return `/#/register?action=${action}`;
    }
  }
};
</script>

<style scoped>

</style>
