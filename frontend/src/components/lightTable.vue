<template>
  <div style="margin-top: 10px; margin-bottom: 10px">
    <div style="margin-bottom: 10px">
      <el-row>
        <el-col :span="9" v-if="showSearch">
          <el-row>
            <el-col :span="16">
              <el-input v-model="searchCondition"></el-input>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" style="margin-left: 20px" @click="searchClick">搜索</el-button>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="tableHeadCol" class="table-title-button">
          <el-button type="primary" v-if="addable" @click="onAdd">添加</el-button>
          <el-button type="primary" v-if="putable" @click="onPut">投放</el-button>
          <el-button type="primary" plain v-if="refreshable" @click="onRefresh">自动生成</el-button>
          <el-button type="danger" v-if="rowSelect && deleteable" @click="onDelete">删除</el-button>
          <slot name="operateTable"></slot>
          <slot name="showColor"></slot>
        </el-col>
      </el-row>
    </div>
    <div v-if="!!tableTitle">
      <h4>{{tableTitle}}</h4>
    </div>
    <el-table
      :data="showData"
      :height="tableHeight"
      :row-class-name="rowClass"
      border
      @selection-change="handleSelectionChange"
      style="width: 100%">
      <el-table-column v-if="rowSelect"
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column  type="index"  label="序号" v-if="needIndex">
      </el-table-column>
      <el-table-column  v-for="column in columns" :key="column.prop"
                        :prop="column.prop"
                        :label="column.label"
                        :width="column.width">
        <template slot-scope="scope">
          <div style="text-align: center"  v-if="column.type && column.type.operate">
            <slot :name="column.prop" :prop="scope.row"></slot>
          </div>
          <el-input v-else-if="column.type && column.type.text" v-model="scope.row[column.prop]" :placeholder="$placeholder.input"></el-input>
          <el-input v-else-if="column.type && column.type.textarea" type="textarea" :autosize="$textareaAutosize" v-model="scope.row[column.prop]" :placeholder="$placeholder.inputTextarea"></el-input>
          <el-select v-else-if="column.type && column.type.select" v-model="scope.row[column.prop]" clearable :placeholder="$placeholder.select">
            <el-option
              v-for="item in column.type.selectList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <a  v-else-if="column.type && column.type.linkable" :href="linkGoTo(column, scope)">{{scope.row[column.prop]}}</a>
          <span v-else>{{scope.row[column.prop]}}</span>
        </template>
      </el-table-column>
    </el-table>
    <div class="page-bar-body">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalData">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'lightTable',
  props: {
    data: {
      type: Array,
      default: () => {
        return [];
      }
    },
    needIndex: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    columns: {
      type: Array,
      default: () => {
        return [];
      }
    },
    rowClass: {
      type: Function,
      default: () => {
        return '';
      }
    },
    tableTitle: {
      type: String,
      default: () => {
        return '';
      }
    },
    tableHeight: {
      type: Number,
      default: () => {
        return window.innerHeight * 0.7;
      }
    },
    rowSelect: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    showSearch: {
      type: Boolean,
      default: () => {
        return true;
      }
    },
    addable: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    putable: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    refreshable: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    deleteable: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    remote: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    remoteMethod: {
      type: Function
    }
  },
  data () {
    return {
      searchCondition: '',
      totalData: 0,
      currentPage: 1,
      pageSize: 10,
      pageIndex: 1,
      showData: []
    };
  },
  computed: {
    tableHeadCol () {
      return 24 - this.showSearch ? 9 : 0;
    }
  },
  mounted () {
    this.searchTable();
  },
  watch: {
    data () {
      this.searchTable();
    }
  },
  methods: {
    onAdd () {
      this.$emit('on-add');
    },
    onPut () {
      this.$emit('on-put');
    },
    onRefresh () {
      this.$emit('on-refresh');
    },
    onDelete () {
      this.$emit('on-delete');
    },
    linkGoTo (column, scope) {
      if (column.type.linkUrl) {
        return column.type.linkUrl(scope.row);
      }
    },
    searchClick () {
      this.searchTable();
      this.$emit('on-search');
    },
    handleSizeChange (val) {
      this.pageSize = val;
      this.searchTable();
    },
    handleCurrentChange (val) {
      this.pageIndex = val;
      this.searchTable();
    },
    async searchTable () { // can not filter condition if not remote
      if (this.remote) {
        const findData = await this.remoteMethod(this.searchCondition, this.pageIndex - 1, this.pageSize);
        this.totalData = findData.totalLength;
        this.showData = findData.totalData;
      } else {
        this.totalData = this.data.length;
        this.showData = this.data.slice((this.pageIndex - 1) * this.pageSize, this.pageIndex * this.pageSize);
      }
    },
    handleSelectionChange (val) {
      this.$emit('select-row', val);
    },
    doRemoteMethod () {
      this.searchTable();
    }
  }
};
</script>

<style>
  .el-table .delete-row {
    background: #909399;
  }
  .el-table .warning-row {
    background: #FFCC99;
  }
  .el-table .danger-row {
    background: #FFCCCC;
  }
  .table-delete-row {
    background: #909399;
  }
  .table-warning-row {
    background: #FFCC99;
  }
  .table-danger-row {
    background: #FFCCCC;
  }
  .el-button+.el-button {
    margin-left: 0;
  }
  .page-bar-body {
    text-align: right;
  }
  .table-title-button .el-button {
    margin-left: 20px;
  }
</style>
