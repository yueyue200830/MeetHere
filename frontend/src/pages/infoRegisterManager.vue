<template>
    <page-main-body :title="title">
      <div style="width: 95%">
        <light-table ref="table"
                     :columns="columns"
                     :addable="true" @on-add="addInformation"
                     :deleteable="true" @on-delete="goToDelete"
                     :remote="true" :remoteMethod="doQuery"
                     :rowSelect="true" @select-row="getSelectRow">
          <template slot="operateTable">
            <el-button @click="goToCommit">提交</el-button>
          </template>
          <template slot="operation" slot-scope="props">
            <el-button size="mini" @click="goToModify(props.prop)">编辑</el-button>
          </template>
        </light-table>
      </div>
    </page-main-body>
</template>

<script>
import axios from 'axios';
import _ from 'lodash';
const getSampleDataList = (condition) => axios.get('/api/sample', {params: condition});
const deleteSampleById = (id) => axios.delete('/api/sample', {data: id});
const commitSampleById = (idStatus) => axios.put('/api/sample', idStatus);
export default {
  name: 'infoRegister',
  data () {
    return {
      title: '样品登记',
      columns: [
        {
          prop: 'number',
          label: '序号'
        },
        {
          prop: 'sampleNo',
          label: '样品单号'
        },
        {
          prop: 'inspectCompany',
          label: '企业名称'
        },
        {
          prop: 'sampleName',
          label: '样品名称'
        },
        {
          prop: 'checkType',
          label: '检验类型'
        },
        {
          prop: 'completeDate',
          label: '要求完成日期'
        },
        {
          prop: 'department',
          label: '部门'
        },
        {
          prop: 'sampleStatus',
          label: '状态'
        },
        {
          prop: 'operation',
          label: '操作',
          type: {
            operate: true
          }
        }
      ],
      selectRow: []
    };
  },
  methods: {
    addInformation () {
      this.$router.push('/newSample');
    },
    doQuery (searchCondition, page, size) {
      return getSampleDataList({ searchCondition, page, size }).then(data => {
        if (data.data.code === '000') {
          return this.formatGetTableData(data.data.datas);
        } else {
          this.onAlertError('查询失败');
          return [];
        }
      });
    },
    goToModify (row) {
      this.$router.push(`/newSample?id=${row.id}`);
    },
    goToDelete () {
      if (this.selectRow.length === 0) {
        this.onAlertError('选中数量为0条');
      } else {
        deleteSampleById({id: this.selectRow}).then(data => {
          if (data.data.code === '000') {
            this.onAlertError('删除成功');
            this.$refs.table.doRemoteMethod();
          }
        });
      }
    },
    goToCommit () {
      if (this.selectRow.length === 0) {
        this.onAlertError('选中数量为0条');
      } else {
        commitSampleById({id: this.selectRow, status: 'pre_test'}).then(data => {
          if (data.data.code === '000') {
            this.onAlertSuccess('提交成功');
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

  }
};
</script>

<style scoped>

</style>
