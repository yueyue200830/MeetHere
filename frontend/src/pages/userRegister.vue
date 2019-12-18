<template>
  <div>
    <el-header>
      <div class="head-title">MeetHere</div>
    </el-header>
    <el-main class="user-body">
      <div class="user-title">
        用户注册
      </div>
      <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" label-width="100px" class="register-form">
        <el-form-item label="用户名" prop="name">
          <el-input type="name" v-model="registerForm.name" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" placeholder="请输入密码" v-model="registerForm.password">
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" placeholder="请确认密码" v-model="registerForm.confirmPassword">
          </el-input>
        </el-form-item>
        <el-form-item class="register-button">
          <el-button type="primary" @click="register">注册</el-button>
          <el-button @click="resetForm('registerForm')">重置</el-button>
          <el-button type="text" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <user-footer/>
  </div>
</template>

<script>
  export default {
    name: "userRegister",
    data () {
      const checkName = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'));
        } else {
          this.$http
            .get('http://127.0.0.1:8081/getUserIdByName', {
              params: {
                user_name: value,
              }})
            .then(response => {
              if (response.data === 0) {
                callback();
              } else {
                callback(new Error('用户名已存在'));
              }
            });
        }
      };
      const validatePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.registerForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        registerForm: {
          name: '',
          password: '',
          confirmPassword: '',
        },
        rules: {
          name: [
            { validator: checkName, trigger: 'blur', required: true }
          ],
          password: [
            { validator: true, trigger: 'blur', required: true }
          ],
          confirmPassword: [
            { validator: validatePassword, trigger: 'blur', required: true }
          ],
        }
      }
    },
    methods: {
      register: function() {
        this.$refs['registerForm'].validate((valid) => {
          if (valid) {
            this.$http
              .get('http://127.0.0.1:8081/Register', {
                params: {
                  form: this.registerForm,
                }})
              .then(response => {
                if (response.data !== -1) {
                  this.$router.push('login');
                } else {
                  this.$message.error('注册失败，请重试！');
                }
              });
          }
        });
      },
      login: function () {
        this.$router.push('login');
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
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

  .register-form {
    width: 400px;
    margin: 10px auto;
  }

  .register-button {
    margin-top: 40px;
  }
</style>
