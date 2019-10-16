<template>
  <page-main-body :title="title">
    <div style="width: 50%">
      <el-form :model="passwordForm" ref="passwordForm" label-width="100px">
        <el-row>
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="passwordForm.userName" :placeholder="$placeholder.input" disabled></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="密码" prop="password">
            <el-input v-model="passwordForm.password" :placeholder="$placeholder.input"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="passwordForm.confirmPassword" :placeholder="$placeholder.input"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button type="primary">保存</el-button>
    </div>
  </page-main-body>
</template>

<script>
    export default {
        name: "changePassword",
      data () {
          return {
            title: '修改密码',
            passwordForm: {
              userName: '用户1',
              password: '',
              confirmPassword: ''
            }
          }
      }
    }
</script>

<style scoped>

</style>
