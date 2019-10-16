<template>
  <el-dialog
    :title="title"
    :visible.sync="showDialog"
    width="80%"
    :close-on-click-modal="false"
    @close="dialogClose"
    center>
    <el-form :model="dialogForm" :rules="dialogRules" ref="dialogForm" label-width="100px" v-if="isAddable">
      <el-row>
        <el-col :span="24">
          <el-form-item label="审批意见" prop="reason">
            <el-checkbox-group v-model="dialogForm.reason">
              <el-col :span="6" v-for="item in reasonList" :key="item.label">
                <el-checkbox :label="item.label" :disabled="item.disable"></el-checkbox>
              </el-col>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" :autosize="$textareaAutosize" v-model="dialogForm.remark" :placeholder="$placeholder.inputTextarea"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer" v-if="isAddable">
      <el-button type="primary" @click="saveReturn">保存</el-button>
    </span>
    <light-table :columns="columns" ref="table" v-if="!isAddable"
                 :remote="true" :remoteMethod="doQuery">
    </light-table>
  </el-dialog>
</template>

<script>
import axios from 'axios';
const findReturnDataList = (id, pageIndex, pageSize) => axios.post('/api/findReturnDataList', id, pageIndex, pageSize);
const saveReturnById = (id, form) => axios.post('/api/saveReturnById', id, form);

export default {
  name: 'returnLookup',
  props: {
    showDialog: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    isAddable: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    id: {
      type: String,
      default: () => {
        return '';
      }
    }
  },
  data () {
    return {
      title: '返工记录',
      columns: [
        {
          prop: 'number',
          label: '序号'
        },
        {
          prop: 'people1',
          label: '主检人'
        },
        {
          prop: 'people2',
          label: '校核人'
        },
        {
          prop: 'people3',
          label: '批准人'
        },
        {
          prop: 'people4',
          label: '被返工人'
        },
        {
          prop: 'people5',
          label: '返工人'
        },
        {
          prop: 'reason',
          label: '返工原因'
        },
        {
          prop: 'time',
          label: '返工时间'
        },
        {
          prop: 'detail',
          label: '返工说明'
        }
      ],
      dialogForm: {
        reason: [],
        remark: ''
      },
      dialogRules: {

      },
      reasonList: [
        { label: '结论不准确', disable: false },
        { label: '标准不准确', disable: false },
        { label: '非法定计量单位', disable: false },
        { label: '修改不规范', disable: false },
        { label: '不能反映检验过程', disable: false },
        { label: '报告不完整', disable: false },
        { label: '修约有问题', disable: false },
        { label: '信息量不足', disable: false },
        { label: '信息输入错误', disable: false }
      ]
    };
  },
  watch: {
    showDialog (newVal) {
      if (newVal && !this.isAddable) {
        this.$refs.table.doRemoteMethod();
      }
    }
  },
  methods: {
    doQuery (searchCondition, pageIndex, pageSize) {
      findReturnDataList({ id: this.id, pageIndex, pageSize }).then(data => {
        if (data.data.code === '000') {
          this.title = '123';
          return data.data.data;
        }
      });
    },
    dialogClose () {
      this.$emit('on-close', false);
    },
    saveReturn () {
      if (this.dialogForm.reason.length > 0) {
        saveReturnById({id: this.id, form: this.dialogForm}).then(data => {
          if (data.data.code === '000') {
            this.dialogClose();
            this.onAlertSuccess('保存成功');
          } else {
            this.onAlertError('保存失败');
          }
        });
      } else {
        this.onAlertError(`审批意见选中数量为必须为1条`);
      }
    }
  }
};
</script>

<style scoped>

</style>
