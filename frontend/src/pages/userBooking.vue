<template>
  <el-main>
    <div class="user-title">
      预约
    </div>
    <div class="select-part">
      <el-select v-model="revenue" placeholder="请选择场馆" class="select-revenue">
        <el-option
          v-for="item in revenues"
          :key="item.value"
          :label="item.value"
          :value="item.value">
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
        <th></th>
        <th v-for="i in revenueNumber" class="table-th">{{ i }}号场</th>
      </tr>
      <tr v-for="time in timeSlot">
        <th class="table-time">{{ time+9 }}点 - {{ time+10 }}点</th>
        <td v-for="i in revenueNumber" class="table-td">
          <div v-if="cost[time][i-1] > 0" class="booking-td">
            <el-button type="text" class="booking-inner" @click="clickOrder(time, i)">
              ￥{{ cost[time][i-1] }}
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
          <el-input v-model="addOrderForm.revenue" disabled></el-input>
        </el-form-item>
        <el-form-item
          label="日期"
          label-width="100px">
          <el-input v-model="addOrderForm.date" disabled></el-input>
        </el-form-item>
        <el-form-item
          label="时间"
          label-width="100px">
          <el-input v-model="addOrderForm.time" disabled></el-input>
        </el-form-item>
        <el-form-item
          label="手机号"
          label-width="100px"
          prop="phoneNumber"
          :rules="[
              { required: true, message: '手机号不可为空'},
              { type: 'number', message: '手机号必须为数字'}
            ]"
        >
          <el-input type="phoneNumber" v-model.number="addOrderForm.phoneNumber"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelOrder">取 消</el-button>
        <el-button type="primary" @click="orderRevenue">确 定</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>

<script>
    export default {
        name: "userBooking",
        data() {
            return {
                revenues: [{
                    value: '篮球',
                    id: 1,
                }, {
                    value: '排球',
                    id: 2,
                }, {
                    value: '乒乓球',
                    id: 3,
                }, {
                    value: '羽毛球',
                    id: 4,
                }],
                revenue: '篮球',
                date: '2019-11-6',
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() < Date.now();
                    },
                },
                timeSlot: 12,
                revenueNumber: 4,
                cost: [[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 0, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ],[
                    50, 50, 50, 50
                ]],
                addOrderVisibility: false,
                addOrderForm: {
                    revenue: '',
                    date: '',
                    time: '',
                    phoneNumber: '',
                },
            }
        },
        methods: {
            clickOrder: function (time, num) {
                this.addOrderVisibility = true;
                this.addOrderForm.revenue = this.revenue;
                this.addOrderForm.date = this.date;
                this.addOrderForm.time = (time+9) + "点 - " + (time+10) + "点";
            },
            orderRevenue: function () {
                // Send data to backend.
                this.$http
                    .get('http://127.0.0.1:8081/addOrder', {
                        params: {
                            "addOrderForm": this.addOrderForm,
                            "id": 1
                        }})
                    .then(response => {
                        window.console.log(response);
                        // Go to my order
                        this.$refs['addOrderForm'].resetFields();
                        this.addOrderVisibility = false;
                    });

            },
            cancelOrder: function () {
                this.$refs['addOrderForm'].resetFields();
                this.addOrderVisibility = false;
            },
            searchOrder: function () {
                // Send search data to backend
                this.$http
                    .get('http://127.0.0.1:8081/getAvailable', {
                        params: {
                            "revenueId": this.revenue,
                            "date": this.date
                        }})
                    .then(response => {
                        window.console.log(response);
                        // this.cost = response.data.cost;
                    });
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
    /*background: #67C23A;*/
    background-color: white;
    height: 42px;
    display: flex;
  }

  .booking-inner {
    margin: auto 0;
    width: 100%;
    /*color: #eeeeee;*/
  }

  .reserved {
    cursor: not-allowed;
    color: #bcbec2;
  }
</style>
