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
              <!-- <span><a :href="goToLink('register')">立即注册</a></span> -->
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
import '../css/loginCSS.css';
import axios from 'axios';
const loginValid =(condition)=> axios.post('/app/managerLoginCheck', condition);
export default {
  name: 'login',
  data () {
    return {
      loading: false,
      passw:"password",
      icon:"el-input__icon el-icon-view",
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
    ...mapMutations(['managerLogin']),
    showPass(){
　　　//点击图标是密码隐藏或显示
      if( this.passw=="text"){
        this.passw="password";
        this.icon="el-input__icon el-icon-view";
      }else {
        this.passw="text";
        this.icon="el-input__icon el-icon-loading";
      };
    },
    login () {
      let condition = this.form;
      if(condition.user_name && condition.user_key){
        loginValid(condition).then(data => {
            this.loading = false;
            if (data.data) {
              this.managerLogin({ Authorization: data.data, name: this.form.user_name });
              this.$router.push('/manager/userInfo');
            } else {
              this.onAlertError('用户名与密码不匹配');
            }
        });
      }else{
        this.onAlertError("用户名和密码不能为空！");
      }

      //正式版本要注释掉
      // let data = 1;
      // this.managerLogin({ Authorization: data, name: this.form.user_name });
      // this.$router.push('/manager/userInfo');
    },
    goToLink (action) {
      return `/#/register?action=${action}`;
    }
  }
};
</script>

<style scoped>

</style>
