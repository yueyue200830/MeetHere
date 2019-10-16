<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 90%">
      <el-row>
        <el-col :span="24">
          <light-table ref="table"
                       :data="searchData" @on-search="searchTable"
                       :columns="columns"

                       :deleteable="true" @on-delete="goToDelete"
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
              <el-form-item label="日志编号" prop="log_id">
                <el-input v-model="dialogTypeForm.log_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="单车编号" prop="bike_id">
                <el-input v-model="dialogTypeForm.bike_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="使用状态" prop="ori_status">
                <el-input v-model="dialogTypeForm.ori_status" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="当前状态" prop="cur_status">
                <el-input v-model="dialogTypeForm.cur_status" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="操作时间" prop="opr_time">
                <el-input v-model="dialogTypeForm.opr_time" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
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
  const searchCheckResult = (condition) => axios.post('/app/api/searchBikelog', condition);
  const addCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const getCheckResultById = (id) => axios.get('/api/checkResult', id);
  const modifyCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const deleteCheckResultById = () => axios.post('/app/api/cleanLog');
  const getBikeInfo = () => axios.post('/app/api/getBikelog')

  export default {
    name: "bikeInfo",
    data () {
      return {
        loading: false,
        title: '单车日志',
        columns: [
          {
            prop: 'log_id',
            label: '日志编号',
            width: '150'
          },
          {
            prop: 'bike_id',
            label: '单车编号',
            width: '150'
          },
          {
            prop: 'ori_status',
            label: '使用状态',
            width: '200'
          },
          {
            prop: 'cur_status',
            label: '当前状态',
            width: '200'
          },
          {
            prop: 'opr_time',
            label: '操作时间',
          },
          // {
          //   prop: 'operation',
          //   label: '操作',
          //   type: {
          //     operate: true
          //   }
          // }
        ],
        searchData:[],
        preData:[],
        dialogTypeForm: {
          title: '添加单车',
          isDelete: false,
          bike_id:'',
          des_longitude:'',
          des_latitude:'',
          using_time:'',
          order_endTime:''
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
        //console.log(condition);
        searchCheckResult({condition}).then(data => {
          this.loading = false;
          if(condition==''){
            this.searchData=this.preData;
          }else{
            //console.log(data.data.length);
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
          bike_id:79699,
          des_longitude:'121.52',
          des_latitude:'31.309',
          using_time:'60',
          order_endTime:'2016-08-01 00:10'
        };  //假数据，真实调试的时候使用下面的方法获取数据
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
          title: '添加单车',
          isDelete: false,
          bike_id:'',
          des_longitude:'',
          des_latitude:'',
          using_time:'',
          order_endTime:''
        };
      },
      saveCheckResult () {
        this.$refs.dialogTypeForm.validate((valid) => {
          if (valid) {
            this.loading = true;
            if (this.dialogTypeForm.id) {
              modifyCheckResult({checkResultForm: this.dialogTypeForm}).then(data => {
                if (data.data.code === '000') {
                  this.showDialogType = false;
                }
              });
            } else {
              addCheckResult({checkResultForm: this.dialogTypeForm}).then(data => {
                this.loading = false;
                if (data.data.code === '000') {
                  this.showDialogType = false;
                  this.onAlertSuccess('保存成功');
                } else {
                  this.onAlertError('保存失败');
                }
              });
            }
          }
        });
      },
      goToDelete () {

        this.loading = true;
        deleteCheckResultById().then(data => {
          this.loading = false;
          if (data.data.affectedRows==0) {
            this.onAlertError('清理失败');

          }else{
            this.onAlertError('清理成功');
            getBikeInfo().then(data => {         
              this.searchData=data.data;
              this.preData=data.data;
            });
          }
        });


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
      getBikeInfo().then(data => {
        console.log(data.data);
        this.searchData=data.data;
        this.preData=data.data;
        if (data.data.code === '000') {

          this.showDialogType = false;
        }
      });
    }
  }
</script>

<style scoped>

</style>
