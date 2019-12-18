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
          </light-table>
        </el-col>
      </el-row>
    </div>
  </page-main-body>
</template>

<script>
  import axios from 'axios';
  const searchCheckResult = (condition) => axios.get(`/app/searchUser/${condition}`);
  const deleteCheckResultById = (temp) => axios.get(`/app/deleteUser/${temp}`);
  const getUserInfo = () => axios.post('/app/getUser');


  export default {
    name: "userInfo",
    data () {
      return {
        loading: false,
        title: '用户管理',
        columns: [
          {
            prop: 'id',
            label: '用户编号',
            width: '150'
          },
          {
            prop: 'userName',
            label: '用户名',
            width: '250'
          },
          {
            prop: 'userType',
            label: '用户类型',
            width: '250'
          },
          {
            prop: 'orderNum',
            label: '订单数量',
          }
        ],
        searchData:[],
        preData:[],
        dialogTypeForm: {
          title: '添加用户',
          isDelete: false,
          id:'',
          user_name:'',
          user_type:'',
          order_num:'',
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
        searchCheckResult(condition).then(data => {
          this.loading = false;
          if(condition==''){
            this.searchData=this.preData;
          }else{
            //console.log(data.data.length);
            this.searchData=data.data;
            if (data) {
              
              // todo
            } else {
              this.onAlertError('搜索失败');
            }
          }

        });
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
          for (i=0;i<id.length;i++) {
            let temp=id[i];
            deleteCheckResultById(temp).then(data => {
              this.loading = false;
              if (data) { 
                this.onAlertError('删除成功');
                getUserInfo().then(data => {
                  var l = data.data.length;
                  for(var i =0;i < l;i++){
                    if(data.data[i].userType == 1){
                      data.data[i].userType = "Admin";
                    }else{
                      data.data[i].userType = "User";
                    }
                      
                  }
                  this.searchData=data.data;
                  this.preData=data.data;
                });
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
          id.push(item.id);
        });
        this.selectRow = id;
        //console.log(id);
      }
    },
    created(){
      getUserInfo().then(data => {
        var l = data.data.length;
        for(var i =0;i < l;i++){
          if(data.data[i].userType == 1){
            data.data[i].userType = "Admin";
          }else{
            data.data[i].userType = "User";
          }
                      
        }
        this.searchData=data.data;
        this.preData=data.data;

      });
    }
  }
</script>

<style scoped>

</style>
