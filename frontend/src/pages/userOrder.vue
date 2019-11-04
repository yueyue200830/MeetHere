<template>
    <el-main>
      <div class="order-title">
        我的订单
      </div>
      <el-table
        :data="tableData"
        border
        class="order-table">
        <el-table-column
          type="index"
          width="50">
        </el-table-column>
        <el-table-column
          prop="date"
          label="日期"
          width="180">
        </el-table-column>
        <el-table-column
          prop="revenue"
          label="场馆"
          width="80">
        </el-table-column>
        <el-table-column
          prop="number"
          label="场地号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          width="80">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号"
          width="140">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">修改手机号</el-button>
            <el-button
              size="mini"
              type="danger"
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
          <el-button @click="deleteDialogVisibility = false">取 消</el-button>
          <el-button type="primary" @click="deleteOrder()">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="修改手机号"
        :visible.sync="changePhoneVisibility"
        width="450px">
        <el-form :model="changePhoneForm" ref="changePhoneForm">
          <el-form-item label="原手机号" label-width="100px">
            <el-input v-model="changePhoneForm.originalNumber" class="phone-input" disabled></el-input>
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
            <el-input type="newNumber" v-model.number="changePhoneForm.newNumber" autocomplete="off" class="phone-input"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="changePhoneVisibility = false">取 消</el-button>
          <el-button type="primary" @click="changePhone()">确 定</el-button>
        </div>
      </el-dialog>
    </el-main>
</template>

<script>
    export default {
        name: "userOrder",
        data() {
            return {
                tableData: [{
                    date: '2016-05-02',
                    revenue: '篮球',
                    number: '1',
                    price: '50',
                    phone: '12312312312',
                }, {
                    date: '2016-05-04',
                    revenue: '网球',
                    number: '7',
                    price: '55',
                    phone: '12312312312',
                }, {
                    date: '2016-05-01',
                    revenue: '乒乓球',
                    number: '2',
                    price: '30',
                    phone: '12312312312',
                }, {
                    date: '2016-05-03',
                    revenue: '足球',
                    number: '4',
                    price: '100',
                    phone: '12312312312',
                }],
                deleteDialogVisibility: false,
                changePhoneVisibility: false,
                deleteIndex: -1,
                changePhoneForm: {
                    index: '',
                    originalNumber: '123',
                    newNumber: '',
                },
            }
        },
        methods: {
            handleEdit(index, row) {
                this.changePhoneVisibility = true;
                this.changePhoneForm.originalNumber = row.phone;
                this.changePhoneForm.index = index;
            },
            handleDelete(index, row) {
                console.log(index, row);
                this.deleteDialogVisibility = true;
                this.deleteIndex = index;
            },
            deleteOrder() {
                // Send delete index to backend.
                this.deleteDialogVisibility = false;
            },
            changePhone() {
                // Send new phone number to backend.
                this.$refs['changePhoneForm'].resetFields();
                this.changePhoneVisibility = false;
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
    width: 800px;
    margin: 40px auto 10px;
  }

  .phone-input {
    width: 250px;
  }
</style>
