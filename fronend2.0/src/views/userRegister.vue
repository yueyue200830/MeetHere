<template>
  <div>
    <el-header>
      <div class="head-title" @click="toMainPage">MeetHere</div>
    </el-header>
    <el-main class="user-body">
      <div class="user-title">
        用户注册
      </div>
      <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" label-width="100px" class="register-form">
        <el-form-item label="用户名" prop="name">
          <el-input
            type="name"
            v-model="registerForm.name"
            maxlength="20"
            minlength="4"
            placeholder="请输入用户名"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            placeholder="请输入密码"
            show-password
            maxlength="16"
            minlength="6"
            v-model="registerForm.password"
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            type="password"
            placeholder="请确认密码"
            show-password
            maxlength="16"
            minlength="6"
            v-model="registerForm.confirmPassword"
          />
        </el-form-item>
        <el-form-item class="register-button">
          <el-button class="button-margin" type="primary" @click="register" :loading="signningUp">注册</el-button>
          <el-button class="button-margin" @click="resetForm('registerForm')">重置</el-button>
          <el-button type="text" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <user-footer/>
  </div>
</template>

<script>
export default {
  name: 'userRegister',
  data () {
    const checkName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'))
      } else {
        let nameVerifier = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{4,20}$/
        if (nameVerifier.test(value)) {
          this.$http
            .get('/app/checkUserNameExist', {
              params: {
                user_name: value
              } })
            .then(response => {
              if (response.data === 0) {
                callback()
              } else {
                callback(new Error('用户名已存在'))
              }
            })
            .catch(error => {
              this.$message.error('验证用户名失败')
            })
        } else {
          callback(new Error('用户名不合法，请输入4-20个字符'))
        }
      }
    }
    const checkPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        let passwordVerifier = /^.*(?=.{6,16})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? _-]).*$/
        if (passwordVerifier.test(value)) {
          callback()
        } else {
          callback(new Error('输入6-16位密码，需包含大小写字母，数字和特殊字符'))
        }
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      signningUp: false,
      registerForm: {
        name: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        name: [
          { validator: checkName, trigger: ['blur', 'change'], required: true }
        ],
        password: [
          { validator: checkPassword, trigger: ['blur', 'change'], required: true }
        ],
        confirmPassword: [
          { validator: validatePassword, trigger: 'blur', required: true }
        ]
      }
    }
  },
  methods: {
    register: function () {
      this.signningUp = true
      this.$refs['registerForm'].validate((valid) => {
        if (valid) {
          this.$http
            .get('/app/Register', {
              params: {
                form: this.registerForm
              } })
            .then(response => {
              if (response.data !== -1) {
                this.$router.push('login')
              } else {
                this.$message.error('注册失败，请重试！')
              }
            })
            .catch(error => {
              this.$message.error('注册失败，请重试！')
            })
            .finally(() => {
              this.signningUp = false
            })
        } else {
          this.signningUp = false
        }
      })
    },
    login: function () {
      this.$router.push('login')
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
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

  .register-form {
    width: 400px;
    margin: 10px auto;
  }

  .register-button {
    margin-top: 40px;
  }
</style>
