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
              <el-button size="mini" @click="viewTypeDetail(props.prop.id,'edit')">编辑</el-button>
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
              <el-form-item label="场馆价格" prop="rvnPrice">
                <el-input v-model="dialogTypeForm.rvnPrice" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="场馆介绍" prop="rvnIntro">
                <el-input v-model="dialogTypeForm.rvnIntro" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
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
  const searchCheckResult = (condition) => axios.get(`/app/searchVenue/${condition}`);
  const getCheckResultById = (id) => axios.get(`/app/checkVenue/${id}`);
  const modifyCheckResult = (checkResultForm) => axios.post('/app/modifyVenue', checkResultForm);
  
  const getVenueInfo = () => axios.post('/app/api/getVenue');

  export default {
    name: "VenueInfo",
    data () {
      return {
        loading: false,
        title: '场馆信息管理',
        columns: [
          {
            prop: 'id',
            label: '场馆ID号',
            width: '100'
          },
          {
            prop: 'rvnName',
            label: '场馆名',
            width: '100'
          },
          {
            prop: 'rvnRoomnum',
            label: '场馆房间号',
            width: '100',
          },
          {
            prop: 'rvnPrice',
            label: '场馆价格',
            width: '100'
          },
          {
            prop: 'rvnIntro',
            label: '场馆介绍',
            width: '200'
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
          title: '修改场馆信息',
          isDelete: false,
          id: '',
          rvnPrice: '',
          rvnIntro: ''
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
        searchCheckResult(condition).then(data => {
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

      },
      viewTypeDetail(id, action) {
        this.loading = true;   //调试中，调试结束后把注释符号去掉
        getCheckResultById(id).then(data => {
          this.loading = false;
          if (data) {
            // todo
            this.dialogTypeForm.id = data.data[0].id;
            this.dialogTypeForm.rvnPrice = data.data[0].rvnPrice;
            this.dialogTypeForm.rvnIntro = data.data[0].rvnIntro;
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
          title: '修改场馆信息',
          isDelete: false,
          id: '',
          rvnPrice: '',
          rvnIntro: ''
        };
      },
      saveCheckResult () {
          this.loading = true;
          modifyCheckResult(this.dialogTypeForm).then(data => {
            this.loading=false;
            if (data) {
              getVenueInfo().then(data => {
                this.searchData=data.data;
                this.preData=data.data;
                if (data.data.code === '000') {
                  this.showDialogType = false;
                }
              })
              this.showDialogType = false;
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
      getVenueInfo().then(data => {
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
