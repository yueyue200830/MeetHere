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
              <el-button size="mini" @click="viewPhoto(props.prop.id)">查看图片</el-button>
            </template> -->
          </light-table>
        </el-col>
      </el-row>

    </div>
  </page-main-body>
</template>

<script>
import axios from 'axios'
import _ from 'lodash'

const changeResultById = (temp) => axios.get(`/app/approveMessage/${temp}`)
const getOrderInfo = () => axios.post('/app/getUnapproveMessage')
const searchCheckResult = (condition) => axios.get(`/app/searchUnapproveMessage/${condition}`)

export default {
  name: 'messageApprove',
  data () {
    return {
      loading: false,
      title: '留言审核',
      columns: [
        {
          prop: 'id',
          label: '留言编号',
          width: '100'
        },
        {
          prop: 'userId',
          label: '用户编号',
          width: '100'
        },
        {
          prop: 'messageTitle',
          label: '留言标题',
          width: '250'
        },
        {
          prop: 'messageContent',
          label: '留言内容',
          width: '500'
        },
        {
          prop: 'messageVisibility',
          label: '是否审核通过'
        }
        // {
        //   prop: 'operation',
        //   label: '操作',
        //   type: {
        //     operate: true
        //   }
        // }

      ],
      searchData: [],
      preData: [],
      selectRow: []
    }
  },
  methods: {
    searchTable (condition) {
      this.loading = true
      condition = this.$refs.table.searchCondition
      var regNumber = /\d+/
      if (condition == '' || !regNumber.test(condition)) {
        this.loading = false
        this.searchData = this.preData
        this.onAlertError('请输入搜索内容！')
      } else {
        try{
          searchCheckResult(condition).then(data => {
            this.loading = false
            this.searchData = data.data
            if (data.data.length) {
              this.onAlertSuccess('搜索成功')
            } else {
              this.onAlertError('搜索失败')
            }
          })
        }finally{
          this.loading = true
        }
      }

    },
    goToDelete (id) {
      if (this.selectRow.length === 0) {
        this.onAlertError('选中数量为0条')
      } else {
        this.loading = true
        id = this.selectRow
        let i = 0
        for (i = 0; i < id.length; i++) {
          let temp = id[i]
          changeResultById(temp).then(data => {
            this.loading = false
            if (data) {
              this.onAlertSuccess('审核通过成功')
              getOrderInfo().then(data => {
                var l = data.data.length
                for (var i = 0; i < l; i++) {
                  if (data.data[i].messageVisibility == 1) {
                    data.data[i].messageVisibility = 'Yes'
                  } else {
                    data.data[i].messageVisibility = 'No'
                  }
                }
                this.searchData = data.data
                this.preData = data.data
              })
            }
          })
        }
      }
    },
    getSelectRow (val) {
      let id = []
      _.each(val, item => {
        id.push(item.id)
      })
      this.selectRow = id
    }
  },
  created () {
    getOrderInfo().then(data => {
      var l = data.data.length
      for (var i = 0; i < l; i++) {
        if (data.data[i].messageVisibility == 1) {
          data.data[i].messageVisibility = 'Yes'
        } else {
          data.data[i].messageVisibility = 'No'
        }
      }
      this.searchData = data.data
      this.preData = data.data
    })
  }
}
</script>

<style scoped>

</style>
