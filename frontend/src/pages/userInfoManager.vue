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
            <template slot="operation" slot-scope="props">
              <el-button size="mini" @click="assignManager(props.prop)">升级用户为管理员</el-button>
            </template>
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
  const changeUserRole = (id) => axios.get(`/app/changeUserRole/${id}`);

  export default {
    name: "userInfo",
    data () {
      return {
        loading: false,
        title: '用户信息',
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
      assignManager(temp){
        console.log("用户id"+temp.id+" 用户角色:"+temp.userType);
        if(temp.userType == "Admin"){
          this.onAlertError("该用户已经是管理员！")
        }else{
          changeUserRole(temp.id).then(data => {
            if(data.data){
              this.onAlertSuccess("升级用户为管理员成功")
            }else{
              this.onAlertError("升级用户为管理员失败")
            }
          })
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
      },
      searchTable (condition) {
        this.loading = true;
        condition=this.$refs.table.searchCondition;
        if(condition == ''){
          this.loading = false;
          this.searchData=this.preData;
          this.onAlertError("请输入搜索内容！")
        }else{
          searchCheckResult(condition).then(data => {
            this.loading = false;
            this.searchData=data.data;
            if (data.data.length) {
            } else {
              this.onAlertError('搜索失败');
            }
          });
        }
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
              if (data.data) { 
                this.onAlertSuccess('删除成功');
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
