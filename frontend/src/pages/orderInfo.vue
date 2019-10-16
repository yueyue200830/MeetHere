<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 90%">
      <el-row>
        <el-col :span="24">
          <light-table ref="table"
                       :data="searchData" @on-search="searchTable"
                       :columns="columns"
                       :addable="true" @on-add="addType"
                       :rowSelect="true" @select-row="getSelectRow">
            <!-- <template slot="operation" slot-scope="props">
              <el-button size="mini" @click="viewTypeDetail(props.prop.id,'edit')">编辑</el-button>
            </template> -->
          </light-table>
        </el-col>
      </el-row>
      <el-dialog
        :title="dialogTypeForm.title"
        :visible.sync="showDialogType"
        width="50%"
        :close-on-click-modal="false"
        @close="dialogTypeClose"
        center>
        <el-form :model="dialogTypeForm" :rules="dialogTypeRules" ref="dialogTypeForm" label-width="100px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="订单编号" prop="order_id">
                <el-input v-model="dialogTypeForm.order_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="用户编号" prop="user_id">
                <el-input v-model="dialogTypeForm.user_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="单车编号" prop="bike_id">
                <el-input v-model="dialogTypeForm.bike_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="开始时间" >
                <el-input v-model="dialogTypeForm.startTime" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="起点经度" >
                <el-input v-model="dialogTypeForm.startLon" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="起点纬度" >
                <el-input v-model="dialogTypeForm.startLat" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="结束时间" >
                <el-input v-model="dialogTypeForm.endTime" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="终点经度" >
                <el-input v-model="dialogTypeForm.endLon" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="终点纬度" >
                <el-input v-model="dialogTypeForm.endLat" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="showDialogType = false">取消</el-button>
          <el-button v-if="!dialogTypeForm.isDelete" type="primary" @click="saveCheckResult">保存</el-button>
        </span>
      </el-dialog>
    </div>
  </page-main-body>
</template>

<script>
  import axios from 'axios';
  const searchCheckResult = (condition) => axios.post('/app/api/searchOrder', condition);
  const addCheckResult = (checkResultForm) => axios.post('/app/api/addOrder', checkResultForm);
  const getCheckResultById = (id) => axios.get('/api/checkResult', id);
  const modifyCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const deleteCheckResultById = (id) => axios.delete('/api/checkResult', id);
  const getOrderInfo = () => axios.post('/app/api/getOrder');

  export default {
    name: "orderInfo",
    data () {
      return {
        loading: false,
        title: '订单信息',
        columns: [
          {
            prop: 'order_id',
            label: '订单编号',
            width: '250'
          },
          {
            prop: 'user_id',
            label: '用户编号',
            width: '300'
          },
          {
            prop: 'bike_id',
            label: '单车编号',
   
          }
        ],
        searchData:[],
        preData:[],
        dialogTypeForm: {
          title: '添加订单',
          isDelete: false,
          order_id:'',
          user_id:'',
          bike_id:'',
          startTime: '',
          startLon:'',
          startLat:'',
          endTime:'',
          endLon:'',
          endLat:'',
        },
        showDialogType: false,
        dialogTypeRules: {

        },
        selectRow: []
      };
    },
    methods:{
      searchTable (condition) {
        this.loading = true;
        condition=this.$refs.table.searchCondition;
        console.log(condition);
        searchCheckResult({condition}).then(data => {
          this.loading = false;
          if(condition==''){
            this.searchData=this.preData;
          }else{
            console.log(data.data.length);
            this.searchData=data.data;

            if (data.data.length) {
              
              // todo
            } else {
              this.onAlertError('搜索失败');
            }
          }

        });
        // if(condition==''){
        //   console.log(this.preData);
        //   this.searchData=this.preData;

        // }
      },
      viewTypeDetail(id, action) {
        //this.loading = true;   调试中，调试结束后把注释符号去掉
        this.dialogTypeForm = {
          order_id:'31289',
          user_id:'1229',
          bike_id:'79699',
        };
        this.showDialogType=true;
        getCheckResultById({id}).then(data => {
          this.loading = false;
          if (data.data.code === '000') {
            // todo
            this.dialogTypeForm = data.data.datas;
            this.showDialogType = true;


          } else {
            this.onAlertError('搜索失败');
          }
        });
      },
      addType() {
        this.showDialogType = true;
      },
      dialogTypeClose() {
        this.dialogTypeForm = {
          title: '添加订单',
          isDelete: false,
          order_id:'',
          user_id:'',
          bike_id:'',
          startTime: '',
          startLon:'',
          startLat:'',
          endTime:'',
          endLon:'',
          endLat:'',
        };
      },
      saveCheckResult () {
        addCheckResult({checkResultForm: this.dialogTypeForm}).then(data => {
          this.loading = false;
          if (data.data.affectedRows ==0) {
            this.onAlertError('保存失败');
          } else {
            this.showDialogType = false;
            this.onAlertSuccess('保存成功');
          }
        });
      },
      goToDelete () {
        if (this.selectRow.length === 0) {
          this.onAlertError('选中数量为0条');
        } else {
          this.loading = true;
          deleteCheckResultById({id: this.selectRow}).then(data => {
            this.loading = false;
            if (data.data.code === '000') {
              this.onAlertError('删除成功');
              this.$refs.table.doRemoteMethod();
            }
          });
        }
      },
      getSelectRow (val) {
        let id = [];
        _.each(val, item => {
          id.push(item.id);
        });
        this.selectRow = id;
      }
    },
    mounted(){
      getOrderInfo().then(data => {
        console.log(data.data);
        this.searchData=data.data;
        this.preData=data.data;
        if (data.data.code === '000') {

          this.showDialogType = false;
        }
      })
    }
  }
</script>

<style scoped>

</style>
