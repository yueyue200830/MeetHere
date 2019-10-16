<template>
  <div class="login-body login-body-img">
    <div class="login-box">
      <el-row style="height: 100%;">
        <el-col :span="8" class="left-login">
          <div>
            <div>
              <h3>W E L C O M E</h3>
            </div>
            <div>
              <h3>共享单车管理系统</h3>
            </div>
          </div>
        </el-col>
        <el-col :span="16" class="login-right-body">
          <el-row style="width: 95%">
            <el-row class="login-row" style="margin-top: 3%">
              <h2>登录</h2>
            </el-row>
            <el-row class="login-row">
              <el-form ref="form" :model="form" :rules="loginRules" label-width="80px" style="width: 90%">
                <el-form-item label="账号" prop="username">
                  <el-input v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                  <el-input v-model="form.password"></el-input>
                </el-form-item>
              </el-form>
            </el-row>
            <el-row class="login-forget login-row">
              <span><a :href="goToLink('register')">立即注册</a></span>
              <span><a :href="goToLink('forget')">忘记密码</a></span>
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
export default {
  name: 'login',
  data () {
    return {
      form: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [
          this.requiredInput('用户名')
        ],
        password: [
          this.requiredInput('密码')
        ]
      }
    };
  },
  methods: {
    login () {
      // this.$refs.form.validate(valid => {
      //
      // });
      this.$router.push('/index');
    },
    goToLink (action) {
      return `/#/register?action=${action}`;
    }
  }
};
</script>

<style scoped>

</style>
