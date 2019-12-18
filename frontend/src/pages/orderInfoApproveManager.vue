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


  const changeResultById = (temp) => axios.get(`/app/approveOrder/${temp}`);
  const getOrderInfo = () => axios.post('/app/getUnapproveOrder')
  const searchCheckResult = (condition) => axios.get(`/app/searchUnapproveOrder${condition}`);

  export default {
    name: "orderInfoApprove",
    data () {
      return {
        loading: false,
        title: '预约订单审核',
        columns: [
          {
            prop: 'id',
            label: '订单编号',
            width: '100'
          },
          {
            prop: 'userId',
            label: '用户编号',
            width: '100'
          },
          {
            prop: 'orderPhone',
            label: '联系电话',
            width: '100'
          },
          {
            prop: 'rvnId',
            label: '场馆ID',
            width: '100'
          },
          {
            prop: 'rvnRoomNum',
            label: '场地房间号',
            width: '100'
          },
          {
            prop: 'timeSlot',
            label: '时间段',
            width: '100'
          },
          {
            prop: 'orderDate',
            label: '预约时间',
            width: '200'
          },
          {
            prop: 'orderApproved',
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
            changeResultById (temp) . then (data => {
              this.loading = false;
              if (data) {
                this.onAlertSuccess('审核通过成功');
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
      });
    }
  }
</script>

<style scoped>

</style>
