<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 90%">
      <el-row>
        <el-col :span="24">
          <light-table ref="table"
                       :data="searchData" @on-search="searchTable"
                       :columns="columns"
                       
                       :putable="true" @on-put="goToDelete"
                       :rowSelect="true" @select-row="getSelectRow">
            <!-- <template slot="operation" slot-scope="props">
              <el-button size="mini" @click="viewTypeDetail(props.prop.bike_id,'edit')">编辑</el-button>
            </template> -->
          </light-table>
        </el-col>
      </el-row>

    </div>
  </page-main-body>
</template>

<script>
  import axios from 'axios'; 
  const addCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);
  const getCheckResultById = (id) => axios.get('/api/selectBike', id);
  const modifyCheckResult = (checkResultForm) => axios.post('/api/checkResult', checkResultForm);

  const deleteCheckResultById = (temp) => axios.post('/app/api/putBike', temp);
  const getBikeInfo = () => axios.post('/app/api/getBackup')
  const searchCheckResult = (condition) => axios.post('/app/api/searchBackup', condition);

  export default {
    name: "bikeInfo",
    data () {
      return {
        loading: false,
        title: '备用单车',
        columns: [
          {
            prop: 'bike_id',
            label: '单车编号',
            //width: '150'
          },

        //   {
        //     prop: 'operation',
        //     label: '操作',
        //     type: {
        //       operate: true
        //     }
        //   }
        ],
        searchData:[],
        preData:[],
        dialogTypeForm: {
          title: '添加单车',
          isDelete: false,
          bike_id:'',

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
 
      addType() {
        this.showDialogType = true;
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
              if (data) { //如果后端API返回的data有值说明删除成功
                this.onAlertError('投放成功');
                getBikeInfo().then(data => {
                    //console.log(data.data);
                    this.searchData=data.data;
                    this.preData=data.data;
                }); //投放成功之后重新调用后端APIselect * from。。。渲染表格
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
