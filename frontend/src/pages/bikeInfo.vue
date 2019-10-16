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
              <el-button size="mini" @click="viewTypeDetail(props.prop.bike_id,'edit')">编辑</el-button>
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
              <el-form-item label="单车编号" prop="bike_id">
                <el-input v-model="dialogTypeForm.bike_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="终点经度" prop="end_lon">
                <el-input v-model="dialogTypeForm.end_lon" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="终点纬度" prop="end_lat">
                <el-input v-model="dialogTypeForm.end_lat" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="使用时长" prop="using_time">
                <el-input v-model="dialogTypeForm.using_time" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="最后一次订单结束时间" prop="order_endTime">
                <el-input v-model="dialogTypeForm.order_endTime" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
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
  const searchCheckResult = (condition) => axios.post('/app/api/searchBike', condition);
  const addCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const getCheckResultById = (id) => axios.get('/api/selectBike', id);
  const modifyCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const deleteCheckResultById = (temp) => axios.post('/app/api/deleteBike', temp);
  const getBikeInfo = () => axios.post('/app/api/getBike')


  export default {
    name: "bikeInfo",
    data () {
      return {
        loading: false,
        title: '使用中单车信息',
        columns: [
          {
            prop: 'bike_id',
            label: '单车编号',
            width: '150'
          },
          {
            prop: 'end_lon',
            label: '终点经度',
            width: '200'
          },
          {
            prop: 'end_lat',
            label: '终点纬度',
            width: '200'
          },
          {
            prop: 'using_time',
            label: '使用时长',
            width: '200'
          },
          {
            prop: 'order_endTime',
            label: '最后一次订单结束时间',
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
          end_lon:'',
          end_lat:'',
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
          end_lon:'121.52',
          end_lat:'31.309',
          using_time:'60',
          order_endTime:'2016-08-01 00:10'
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
          title: '添加单车',
          isDelete: false,
          bike_id:'',
          end_lon:'',
          end_lat:'',
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
      goToDelete (id) {
        if (this.selectRow.length === 0) {
          this.onAlertError('选中数量为0条');
        } else {
          this.loading = true;
          id=this.selectRow;
          let i = 0;
          for(i=0;i<id.length;i++){
            let temp=id[i];
            //console.log(temp);
            deleteCheckResultById({temp}).then(data => {
              console.log(temp);
              this.loading = false;//测试不会改变bike关系表，而是对数据库中的备份表bike_copy进行删除
              if (data.data.affectedRows!=0) { //如果后端API返回的data有值说明删除成功
                this.onAlertError('删除成功');
                getBikeInfo().then(data => {
                  //console.log(data.data);
                  this.searchData=data.data;
                  this.preData=data.data;
                  if (data.data.code === '000') {

                    this.showDialogType = false;
                  }
                });//删除成功之后重新获取bike信息,调用后端的API
              }else{
                this.onAlertError('删除失败');
              }
            });
          }

        }
      },
      getSelectRow (val) {
        let id = [];
        _.each(val, item => {
          //console.log(item);
          id.push(item.bike_id);
        });
        this.selectRow = id;
        //console.log(id);
      }
    },
    created(){
      getBikeInfo().then(data => {
        //console.log(data.data);
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
