<template>
  <el-main>
    <div class="user-title">
      个人信息
    </div>
    <el-row :gutter="10">
      <el-col
        :xs="{span: 22, offset: 1}"
        :sm="{span: 8, offset: 1}"
        :md="{span: 7, offset: 2}"
        :lg="{span: 6, offset: 3}"
        :xl="{span: 4, offset: 4}"
      >
        <div class="news-card">
          <el-image :src="userAvatar" class="avatar-img" />
          <div class="avatar-upload" @click="changeAvatar">
            <div class="avatar-upload-text">
              修改头像
            </div>
          </div>
          <my-upload
            field="file"
            @crop-success="cropSuccess"
            @crop-upload-success="cropUploadSuccess"
            @crop-upload-fail="cropUploadFail"
            v-model="showAvatarCrop"
            :width="300"
            :height="300"
            url="http://127.0.0.1:8081/UploadPhoto"
            :params="params"
            img-format="png"
          />
        </div>
      </el-col>
      <el-col
        :xs="24"
        :sm="{span: 14, offset: 0}"
        :md="{span: 14, offset: 0}"
        :lg="{span: 12, offset: 0}"
        :xl="{span: 10, offset: 0}"
      >
        <el-form :model="userForm" status-icon :rules="rules" ref="userForm" label-width="100px" class="self-form">
          <el-form-item label="用户名" prop="name">
            <el-input v-model.number="userForm.name"/>
          </el-form-item>
          <el-form-item label="原密码" prop="originalPass">
            <el-input type="password" v-model="userForm.originalPass" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="新密码" prop="pass">
            <el-input type="password" v-model="userForm.pass" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="userForm.checkPass" autocomplete="off"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('userForm')">提交</el-button>
            <el-button @click="resetForm('userForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
  import myUpload from 'vue-image-crop-upload';

  export default {
    name: "userInfo",
    components: {'my-upload': myUpload},
    data() {
      const checkName = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'));
        } else {
          this.$http
            .get('http://127.0.0.1:8081/checkUserNameExist', {
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
          name: '',
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
        },
        userAvatar: require('../assets/avatar.png'),
        showAvatarCrop: false,
        params: {
          id: null,
        },
        imgDataUrl: ''
      }
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
      this.userForm.name = this.userName;
      this.params.id = this.userId;

      this.$http
        .get('http://127.0.0.1:8081/GetPhoto', {
          params: {
            id: this.userId,
          }})
        .then(response => {
          if (response.data !== '') {
            this.userAvatar = response.data
          }
        });
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http
              .get('http://127.0.0.1:8081/updateUserById', {
                params: {
                  id: this.userId,
                  updateForm: this.userForm,
                }})
              .then(response => {
                if (response.data === 1) {
                  this.$message({
                    message: '修改成功',
                    type: 'success'
                  });
                  this.resetForm(formName);
                } else {
                  this.$message.error('修改失败，请重试！');
                }
              });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      changeAvatar() {
        this.showAvatarCrop = !this.showAvatarCrop;
      },
      cropSuccess(imgDataUrl, field) {
        this.imgDataUrl = imgDataUrl;
      },
      cropUploadSuccess(jsonData, field) {
        console.log(jsonData);
        // this.userAvatar = jsonData.data.img;
        this.userAvatar = this.imgDataUrl;
        console.log(this.imgDataUrl);
        this.showAvatarCrop = false;
      },
      cropUploadFail(status, field) {
        // console.log('-------- upload fail --------');
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

  .news-card {
    margin: 8px;
    border: 1px solid #EBEEF5;
    border-radius: 4px;
    color: #303133;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .04), 0 0 6px rgba(0, 0, 0, .04);
    max-width: 320px;
  }

  .avatar-img {
    height: 220px;
    margin: 30px 30px 40px;
    display: block;
    border-radius: 4px;
  }

  .avatar-upload {
    display: flex;
    background-color: rgba(0, 0, 0, .03);
    border-top: 1px solid rgba(0, 0, 0, .1);
    -moz-border-radius-bottomleft: 4px;
    -moz-border-radius-bottomright: 4px;
    transition: .5s;
  }

  .avatar-upload:hover {
    cursor:pointer;
    background-color: #409EFF;
    color: white;
  }

  .avatar-upload-text {
    margin: 20px auto;
  }


</style>
