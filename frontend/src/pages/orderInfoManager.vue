<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 90%">
      <el-row>
        <el-col :span="24">
          <light-table ref="table"
                       :data="searchData" @on-search="searchTable"
                       :columns="columns"
                       :refreshable="true" @on-refresh="goToRefresh"
                       :rowSelect="true" @select-row="getSelectRow">
            <template slot="operation" slot-scope="props">
              <el-button size="mini" @click="viewTypeDetail(props.prop.bike_id,'edit')">编辑</el-button>
              <el-button size="mini" @click="spbikeOperation(props.prop.bike_id,'opt')">处理</el-button>
            </template>
          </light-table>
        </el-col>
      </el-row>
      <el-dialog
        :title="dialog1Title"
        :visible.sync="showDialog1"
        width="50%"
        :close-on-click-modal="false"
        center>
        <el-select v-model="opt" placeholder="操作选择">
            <el-option v-for="item in options"
              :label="item.text"
              :key="item.value"
              :value="item.value"
              >
            </el-option>
        </el-select>
        <span slot="footer" class="dialog-footer">
          <el-button @click="showDialog1 = false">取消</el-button>
          <el-button type="primary" @click="saveOptResult">确认</el-button>
        </span>
      </el-dialog>
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

          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="回收位" prop="recover">
                <el-input v-model="dialogTypeForm.recover" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
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
  const searchCheckResult = (condition) => axios.post('/app/api/searchSpbike', condition);
  const optResult=({id,operation}) => axios.post('/app/api/optSpbike',{id,operation});
  const addCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const getCheckResultById = (condition) => axios.post('/app/api/searchSpbike', condition);
  const modifyCheckResult = (checkResultForm) => axios.post('/app/api/modifySpbike', checkResultForm);
  const deleteCheckResultById = (id) => axios.delete('/api/checkResult', id);
  const getSpecialBikeInfo= () => axios.post("/app/api/getSpecialBike");
  const callRefresh= () => axios.post("/app/api/refreshSpbike");

  export default {
    name: "orderInfo",
    data () {
      return {
        dialog1Title: '请选择操作',
        loading: false,
        title: '预约订单统计',
        opt:0,
        optBike:0,
        options: [
          {
            value:0, 
            text:"放入备用单车"
          },
          {
            value:1,
            text:"放入维护单车"
          },
          {
            value:2,
            text:"单车报废，删除单车"
          }
        ],
        columns: [
          {
            prop: 'bike_id',
            label: '单车编号',
            width: '350'
          },
          {
            prop: 'recover',
            label: '回收位',
            width: '350'
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
          title: '修改特殊单车',
          isDelete: false,
          bike_id:'',
          recover:''
        },
        showDialog1: false,
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
      spbikeOperation(id,action){
        
        this.optBike=id;
        this.showDialog1=true;
      },//显示操作弹窗，设置操作的单车ID
      saveOptResult(){
        let id = this.optBike;
        let operation=this.opt;
        this.loading=true
        optResult({id,operation}).then(data => {
          this.loading=false;
          console.log(data);
          if(data.data.affectedRows==0){//affectedRows是数据库返回的信息，表示sql语句影响的row数量，如果等于0说明该过程没有成功执行
            this.onAlertError('操作失败，该单车未被回收');
          }
          this.showDialog1=false;
        })
      }, //跨域请求api
      viewTypeDetail(id, action) {
        this.loading = true;   //调试中，调试结束后把注释符号去掉

        //this.showDialogType=true; //假数据
        console.log(id);
        let condition = id;
        getCheckResultById({condition}).then(data => {
          this.loading = false;
          if (data) {
            // todo
            console.log(data.data);
            this.dialogTypeForm.bike_id = data.data[0].bike_id;
            this.dialogTypeForm.recover = data.data[0].recover; //获得后端返回的数据，显示在弹窗中
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
          title: '修改特殊单车',
          isDelete: false,
          bike_id:'',
          recover:''
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
      goToRefresh(){
        this.loading=true;
        callRefresh().then(data => {
          this.loading=false;
        });
        getSpecialBikeInfo().then(data => {
          console.log(data.data);
          this.searchData=data.data;
          this.preData=data.data;
          if (data.data.code === '000') {

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
      getSpecialBikeInfo().then(data => {
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
