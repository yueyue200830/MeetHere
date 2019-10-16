<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 90%">
      <el-row>
        <el-col :span="24">
          <light-table ref="table"
                       :data="searchData" @on-search="searchTable"
                       :columns="columns"

                       :rowSelect="true" @select-row="getSelectRow">
            <template slot="operation" slot-scope="props">
              <el-button size="mini" @click="viewTypeDetail(props.prop.bike_id,'edit')">编辑</el-button>
              <el-button size="mini" @click="putIntoBackup(props.prop.bike_id,'backup')">放入备用单车中</el-button>
            </template>
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
              <el-form-item label="单车编号" prop="bike_id">
                <el-input v-model="dialogTypeForm.bike_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="维修位" prop="repair">
                <el-input v-model="dialogTypeForm.repair" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
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
  const searchCheckResult = (condition) => axios.post('/app/api/searchRebike', condition);
  const addCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const getCheckResultById = (condition) => axios.post('/app/api/searchRebike', condition);
  const modifyCheckResult = (checkResultForm) => axios.post('/app/api/modifyRebike', checkResultForm);
  const deleteCheckResultById = (id) => axios.delete('/api/checkResult', id);
  const getRepairBikeInfo = () => axios.post('/app/api/getRepairBike');
  const callRepairBackup = (condition) => axios.post('/app/api/callRep',condition);

  export default {
    name: "repairBike",
    data () {
      return {
        loading: false,
        title: '定期维护单车信息',
        columns: [
          {
            prop: 'bike_id',
            label: '单车编号',
            width: '300'
          },
          {
            prop: 'repair',
            label: '维修位',
            width: '300'
          },
          {
            prop: 'operation',
            label: '操作',
            type: {
              operate: true
            }
          }
        ],
        searchData:[],
        preData:[],
        dialogTypeForm: {
          title: '修改需维修单车',
          isDelete: false,
          bike_id:'',
          repair: '',
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
        this.loading = true;   //调试中，调试结束后把注释符号去掉

        //this.showDialogType=true; //假数据
        console.log(id);
        let condition = id;
        getCheckResultById({condition}).then(data => {
          this.loading = false;
          if (data) {
            // todo
            console.log(data.data[0].bike_id);
            this.dialogTypeForm.bike_id = data.data[0].bike_id;
            this.dialogTypeForm.repair = data.data[0].repair;
            console.log(this.dialogTypeForm);
            this.showDialogType = true;

          } else {
            this.onAlertError('搜索失败');
          }
        });
      },
      putIntoBackup(id, action) {
        this.loading = true;   //调试中，调试结束后把注释符号去掉
        console.log(id);
        let condition = id;
        callRepairBackup({condition}).then(data => {
          this.loading = false;
          if(data.data.affectedRows==0){//affectedRows是数据库返回的信息，表示sql语句影响的row数量，如果等于0说明该过程没有成功执行
            this.onAlertError('操作失败，该单车未被维护');
          }
        });
      },
      addType() {
        this.showDialogType = true;
      },
      dialogTypeClose() {
        this.dialogTypeForm = {
          title: '添加需维修单车',
          isDelete: false,
          bike_id:'',
          repair:'',
        };
      },
     saveCheckResult () {
          this.loading = true;
          console.log(this.dialogTypeForm);
          modifyCheckResult({checkResultForm: this.dialogTypeForm}).then(data => {
            this.loading=false;
            if (data) {
              this.showDialogType = false;
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
      getRepairBikeInfo().then(data => {
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
