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

          </light-table>
        </el-col>
      </el-row>

    </div>
  </page-main-body>
</template>

<script>
  import axios from 'axios'; 


  const changeResultById = (temp) => axios.post('/app/api/approveOrder', temp);
  const getOrderInfo = () => axios.post('/app/api/getUnapproveOrder')
  const searchCheckResult = (condition) => axios.post('/app/api/searchUnapproveOrder', condition);

  export default {
    name: "orderInfoApprove",
    data () {
      return {
        loading: false,
        title: '预约订单审核',
        columns: [
          {
            prop: 'id',
            label: '预约订单编号',
            width: '120'
          },
          {
            prop: 'userid',
            label: '用户编号',
            width: '100'
          },
          {
            prop: 'phone',
            label: '联系电话',
            width: '150'
          },
          {
            prop: 'rvnid',
            label: '场馆ID号',
            width: '100'
          },
          {
            prop: 'rvn_roomnum',
            label: '场地房间号',
            width: '100'
          },
          {
            prop: 'timesolt',
            label: '预约的时间段',
            width: '150'
          },
          {
            prop: 'date',
            label: '预约时间',
            width: '100'
          },
          {
            prop: 'order_approved',
            label: '是否审核通过',
          }

        ],
        searchData:[],
        preData:[],

        
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
            changeResultById ({temp}) . then (data => {
              console.log(temp);
              this.loading = false;
              if (data) { 
                this.onAlertError('审核通过成功');
                getOrderInfo().then(data => {
                    this.searchData=data.data;
                    this.preData=data.data;
                }); 
              }
            });
          }
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
    created(){
      getOrderInfo().then(data => {
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
