<template>
  <el-main>
    <div class="user-title">
      个人信息
    </div>
    <el-form :model="userForm" status-icon :rules="rules" ref="userForm" label-width="100px" class="self-form">
      <el-form-item label="用户名" prop="name">
        <el-input v-model.number="userForm.name"></el-input>
      </el-form-item>
      <el-form-item label="原密码" prop="originalPass">
        <el-input type="password" v-model="userForm.originalPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="pass">
        <el-input type="password" v-model="userForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input type="password" v-model="userForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('userForm')">提交</el-button>
        <el-button @click="resetForm('userForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-main>
</template>

<script>
    export default {
        name: "userInfo",
        data() {
            // 发送请求查询用户名
            const checkName = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入用户名'));
                } else {
                    callback();
                }
            };
            const validateOriginalPass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.userForm.checkPass !== '') {
                        this.$refs.userForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.userForm.checkPass !== '') {
                        this.$refs.userForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            const validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.userForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                userForm: {
                    name: 'my name',
                    originalPass: '',
                    pass: '',
                    checkPass: '',
                },
                rules: {
                    name: [
                        { validator: checkName, trigger: 'blur' }
                    ],
                    originalPass: [
                        { validator: validateOriginalPass, trigger: 'blur' }
                    ],
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert("change name");
                        this.$http
                            .get('http://127.0.0.1:8081/updateUserById', {
                                params: {
                                    id: 3,
                                    updateForm: this.userForm,
                                }})
                            .then(response => {
                                window.console.loe(response);
                            });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
  .user-title {
    text-align: center;
    font-size: 30px;
    margin: 20px 0 40px;
    color: #303133;
  }

  .self-form {
    margin: 50px auto 20px;
    width: 400px;
  }
</style>
