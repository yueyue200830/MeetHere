<template>
  <page-main-body :title="title">
    <div style="width: 50%">
      <el-form :model="passwordForm" ref="passwordForm" label-width="100px">
        <el-row>
          <el-form-item label="年号" prop="userYear">
            <el-date-picker
              v-model="passwordForm.useYear"
              type="year"
              :placeholder="$placeholder.selectYear">
            </el-date-picker>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="部门" prop="department">
            <el-select v-model="passwordForm.department" clearable :placeholder="$placeholder.select">
              <el-option
                v-for="item in departmentList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="用户名" prop="userName">
            <el-select v-model="passwordForm.userName" clearable :placeholder="$placeholder.select">
              <el-option
                v-for="item in userNameList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
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
  name: 'findPassword',
  data () {
    return {
      title: '找回密码',
      passwordForm: {
        userYear: '',
        department: '',
        password: '',
        confirmPassword: ''
      },
      departmentList: [
        { value: '化工一室', label: '化工一室' },
        { value: '化工二室', label: '化工二室' }
      ],
      userNameList: [
        { value: 'wanglian', label: '王练' },
        { value: 'admin', label: '管理员' }
      ]
    };
  }
};
</script>

<style scoped>

</style>
