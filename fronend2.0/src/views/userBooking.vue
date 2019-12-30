<template>
  <el-main>
    <div class="user-title">
      预约
    </div>
    <div class="select-part">
      <el-select v-model="revenue" placeholder="请选择场馆" class="select-revenue">
        <el-option
          v-for="item in revenues"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>
      <div class="select-date">
        <el-date-picker
          v-model="date"
          type="date"
          :picker-options="pickerOptions"
          placeholder="选择日期">
        </el-date-picker>
      </div>
      <el-button type="primary" icon="el-icon-search" @click="searchOrder">搜索</el-button>
    </div>
    <table class="booking-table">
      <tr>
        <th class="table-th">时间段</th>
        <th v-for="i in revenueNumber" class="table-th" v-bind:key="i">{{ i }}号场</th>
      </tr>
      <tr v-for="time in timeSlot" v-bind:key="time">
        <th class="table-time">{{ time+9 }}点 - {{ time+10 }}点</th>
        <td v-for="i in revenueNumber" class="table-td" v-bind:key="i">
          <div v-if="cost[time-1][i-1] > 0" class="booking-td">
            <el-button type="text" class="booking-inner" @click="clickOrder(time, i)">
              ￥{{ cost[time-1][i-1] }}
            </el-button>
          </div>
          <div v-else class="reserved">
            已预约
          </div>
        </td>
      </tr>
    </table>
    <el-dialog
      title="预定场馆"
      :visible.sync="addOrderVisibility"
      width="600px">
      <el-form :model="addOrderForm" ref="addOrderForm">
        <el-form-item
          label="场馆"
          label-width="100px">
          <el-input v-model="addOrderForm.revenue" disabled/>
        </el-form-item>
        <el-form-item
          label="场地号"
          label-width="100px">
          <el-input v-model="addOrderForm.room" disabled/>
        </el-form-item>
        <el-form-item
          label="日期"
          label-width="100px">
          <el-input v-model="addOrderForm.date" disabled/>
        </el-form-item>
        <el-form-item
          label="时间"
          label-width="100px">
          <el-input v-model="addOrderForm.time" disabled/>
        </el-form-item>
        <el-form-item
          label="价格"
          label-width="100px">
          <el-input v-model="addOrderForm.price" readonly/>
        </el-form-item>
        <el-form-item
          label="手机号"
          label-width="100px"
          prop="phoneNumber"
          :rules="[
              { required: true, message: '手机号不可为空'},
              { min: 13000000000, max: 13999999999, type: 'number', message: '请输入11位手机号'}
            ]"
        >
          <el-input type="phoneNumber" v-model.number="addOrderForm.phoneNumber"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="button-cancel" @click="cancelOrder">取 消</el-button>
        <el-button type="primary" @click="orderRevenue">确 定</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>

<script>
export default {
  name: 'userBooking',
  data () {
    return {
      revenues: [],
      revenue: null,
      date: this.getTomorrow(),
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() || time.getTime() > Date.now() + 1000 * 60 * 60 * 24 * 30
        }
      },
      timeSlot: 0,
      revenueNumber: 4,
      cost: [],
      addOrderVisibility: false,
      addOrderForm: {
        revenue: null,
        room: null,
        date: null,
        price: null,
        time: null,
        timeSlot: null,
        phoneNumber: null
      }
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
  created: function () {
    this.$http
      .post('/app/getVenueName')
      .then(response => {
        this.revenues = response.data[0]
        this.revenue = this.revenues[0]
        this.searchOrder()
      })
  },
  methods: {
    clickOrder: function (time, num) {
      if (!this.hasLoggedIn) {
        this.$message.error('您未登录，请登录后预约！')
        return
      }
      this.addOrderVisibility = true
      this.addOrderForm.revenue = this.revenue
      this.addOrderForm.room = num
      this.addOrderForm.date = this.convertTime(this.date)
      this.addOrderForm.time = (time + 9) + '点 - ' + (time + 10) + '点'
      this.addOrderForm.timeSlot = time
      this.addOrderForm.price = this.cost[time - 1][num - 1]
    },
    orderRevenue: function () {
      this.$refs['addOrderForm'].validate((valid) => {
        if (valid) {
          this.$http
            .get('/app/addOrder', {
              params: {
                'addOrderForm': this.addOrderForm,
                'id': this.userId
              } })
            .then(response => {
              this.$refs['addOrderForm'].resetFields()
              this.addOrderVisibility = false
              if (response.data === 1) {
                this.$router.push('order')
              } else if (response.data === -1) {
                this.$message.error('此时间已有人预定，请重新选择！')
                this.searchOrder()
              } else {
                this.$message.error('预定失败，请重试！')
                this.searchOrder()
              }
            })
        }
      })
    },
    cancelOrder: function () {
      this.$refs['addOrderForm'].resetFields()
      this.addOrderVisibility = false
    },
    searchOrder: function () {
      this.$http
        .get('/app/getAvailable', {
          params: {
            'revenueName': this.revenue,
            'date': this.convertTime(this.date)
          } })
        .then(response => {
          this.cost = response.data.available
          this.timeSlot = response.data.available.length
          this.revenueNumber = response.data.available[0].length
        })
    },
    convertTime: function (time) {
      let date = new Date(time)
      return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    },
    getTomorrow: function () {
      let date = new Date()
      return date.setDate(date.getDate() + 1)
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

  .select-part {
    margin: 20px auto;
    text-align: center;
  }

  .select-revenue {
    width: 200px;
    display: inline-block;
    margin-right: 40px;
  }

  .select-date {
    width: 200px;
    display: inline-block;
    margin-right: 40px;
  }

  .booking-table {
    text-align: center;
    margin: 40px auto;
    border: 1px solid #ebebeb;
    border-radius: 3px;
    line-height: 1.5em;
    border-collapse: collapse;
    color: #303133;
  }

  .table-th {
    border: 1px solid #ebebeb;
    width: 120px;
    font-weight: normal;
    height: 50px;
    padding: 0;
  }

  .table-time {
    border: 1px solid #ebebeb;
    width: 140px;
    height: 40px;
    font-weight: normal;
  }

  .table-td {
    border: 1px solid #ebebeb;
    background-color: #f4f4f5;
    padding: 0;
  }

  .booking-td {
    background-color: white;
    height: 42px;
    display: flex;
  }

  .booking-inner {
    margin: auto 0;
    width: 100%;
  }

  .reserved {
    cursor: not-allowed;
    color: #bcbec2;
  }

  .button-cancel {
    margin-right: 10px;
  }

</style>
