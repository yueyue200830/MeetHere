<template>
    <el-main>
      <div class="order-title">
        我的订单
      </div>
      <el-table
        :data="orderList"
        border
        class="order-table">
        <el-table-column
          type="index"
          width="50">
        </el-table-column>
        <el-table-column
          label="日期"
          width="120">
          <template slot-scope="scope">
            {{ convertTime(scope.row.orderDate) }}
          </template>
        </el-table-column>
        <el-table-column
          label="时间"
          width="120">
          <template slot-scope="scope">
            {{ convertTimeSlot(scope.row.timeSlot) }}
          </template>
        </el-table-column>
        <el-table-column
          prop="revenue"
          label="场馆"
          width="80">
        </el-table-column>
        <el-table-column
          prop="rvnRoomNum"
          label="场地号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="orderPrice"
          label="价格"
          width="80">
        </el-table-column>
        <el-table-column
          prop="orderPhone"
          label="手机号"
          width="140">
        </el-table-column>
        <el-table-column
          label="审核情况"
          width="85">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.orderApproved === 1">
              通过
            </el-tag>
            <el-tag type="info" v-else>
              待审核
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="184">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">修改手机号</el-button>
            <el-button
              size="mini"
              type="danger"
              class="button-delete"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog
        title="提示"
        :visible.sync="deleteDialogVisibility"
        width="400px">
        <span>确定删除此订单？</span>
        <span slot="footer" class="dialog-footer">
          <el-button class="button-cancel" @click="deleteDialogVisibility=false">取 消</el-button>
          <el-button type="primary" @click="deleteOrder()">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="修改手机号"
        :visible.sync="changePhoneVisibility"
        width="450px">
        <el-form :model="changePhoneForm" ref="changePhoneForm">
          <el-form-item label="原手机号" label-width="100px">
            <el-input v-model="changePhoneForm.originalNumber" class="phone-input" disabled/>
          </el-form-item>
          <el-form-item
            label="新手机号"
            label-width="100px"
            prop="newNumber"
            :rules="[
              { required: true, message: '手机号不可为空'},
              { type: 'number', message: '手机号必须为数字'}
            ]"
          >
            <el-input type="newNumber" v-model.number="changePhoneForm.newNumber" autocomplete="off"
                      class="phone-input"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button class="button-cancel" @click="cancelChangePhone">取 消</el-button>
          <el-button type="primary" @click="changePhone()">确 定</el-button>
        </div>
      </el-dialog>
    </el-main>
</template>

<script>
export default {
  name: 'userOrder',
  data () {
    return {
      orderList: [],
      deleteDialogVisibility: false,
      changePhoneVisibility: false,
      deleteId: -1,
      changePhoneForm: {
        index: null,
        originalNumber: null,
        newNumber: null,
        orderId: null
      }
    }
  },
  created: function () {
    if (!this.hasLoggedIn) {
      this.$message.error('您未登录！')
    } else {
      this.getMyOrder()
    }
  },
  computed: {
    hasLoggedIn () {
      return this.$store.getters.hasLoggedIn
    },
    userId () {
      return this.$store.getters.getUserId
    }
  },
  methods: {
    getMyOrder: function () {
      this.$http
        .get('/app/getMyOrder', {
          params: {
            'id': this.userId
          } })
        .then(response => {
          this.orderList = response.data
        })
    },
    handleEdit: function (index, row) {
      this.changePhoneVisibility = true
      this.changePhoneForm.originalNumber = row.orderPhone
      this.changePhoneForm.index = index
      this.changePhoneForm.orderId = row.orderId
    },
    handleDelete: function (index, row) {
      this.deleteDialogVisibility = true
      this.deleteId = row.orderId
    },
    deleteOrder: function () {
      this.$http
        .get('/app/deleteOrder', {
          params: {
            'deleteOrderId': this.deleteId
          } })
        .then(response => {
          this.deleteDialogVisibility = false
          this.getMyOrder()
          if (response.data === 1) {
            this.$message({
              message: '删除订单成功',
              type: 'success'
            })
          } else {
            this.$message.error('删除订单失败，请重试！')
          }
        })
    },
    changePhone: function () {
      this.$refs['changePhoneForm'].validate((valid) => {
        if (valid) {
          this.$http
            .get('/app/updatePhone', {
              params: {
                'phone': this.changePhoneForm.newNumber,
                'id': this.changePhoneForm.orderId
              } })
            .then(response => {
              this.$refs['changePhoneForm'].resetFields()
              this.changePhoneVisibility = false
              this.getMyOrder()
              if (response.data === 1) {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
              } else {
                this.$message.error('修改失败，请重试！')
              }
            })
        }
      })
    },
    convertTime: function (time) {
      let date = new Date(time)
      return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    },
    convertTimeSlot: function (time) {
      return (time + 9) + '点 - ' + (time + 10) + '点'
    },
    cancelChangePhone: function () {
      this.$refs['changePhoneForm'].resetFields()
      this.changePhoneVisibility = false
    }
  }
}
</script>

<style scoped>
  .order-title {
    text-align: center;
    font-size: 30px;
    margin: 20px;
    color: #303133;
  }

  .order-table {
    width: 940px;
    margin: 40px auto 10px;
  }

  .phone-input {
    width: 250px;
  }
</style>
