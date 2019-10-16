<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 90%">
      <el-row>
        <el-col :span="24">
          <light-table ref="table"
                       :data="searchData" 
                       :columns="columns"
                       :searchable= "false"
                       :addable="true" @on-add="addType"
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
              <el-form-item label="内容" prop="content">
                <el-input v-model="dialogTypeForm.content" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="新闻标题" prop="bike_id">
                <el-input v-model="dialogTypeForm.bike_id" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="图片" >
                <el-input v-model="dialogTypeForm.photo" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
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
  const addCheckResult = (checkResultForm) => axios.post('/app/api/addNews', checkResultForm);
  const getCheckResultById = (id) => axios.get('/app/api/searchNews', id);
  const modifyCheckResult = (checkResultForm) => axios.post('/app/api/modifyNews', checkResultForm);
  const deleteCheckResultById = (id) => axios.delete('/app/api/deleteNews', id);
  const getOrderInfo = () => axios.post('/app/api/getOrder');

  export default {
    name: "newsManage",
    data () {
      return {
        loading: false,
        title: '新闻动态管理',
        columns: [
          {
            prop: 'id',
            label: '新闻编号',
            width: '150'
          },
          {
            prop: 'title',
            label: '新闻标题',
            width: '250'
          },
          {
            prop: 'content',
            label: '内容',
            width: '400'
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
          title: '添加新闻',
          isDelete: false,
          id: '',
          content: '',
          title: '',
          photo: ''
        },
        showDialogType: false,
        dialogTypeRules: {

        },
        selectRow: []
      };
    },
    methods:{
      //点击编辑按钮时，出现弹窗，表格中填充该行新闻的内容
      viewTypeDetail(id, action) {
        this.loading = true;   //调试中，调试结束后把注释符号去掉
        this.dialogTypeForm.title = '修改新闻';
        //通过ID搜索，填充内容（id,content,title,photo)
        getCheckResultById({id}).then(data => {
          this.loading = false;
          if (data.data.code === '000') {
            // todo
            this.dialogTypeForm = data.data.datas;
            this.showDialogType = true;
          } else {
            this.onAlertError('搜索失败');
          }
        });
      },
      //点击添加按钮时，将表格的标题改成添加新闻，展现表格
      addType() {
        this.dialogTypeForm.title = '添加新闻';
        this.showDialogType = true;
      },
      //关闭弹窗之后初始化dialogTypeForm的内容
      dialogTypeClose() {
        this.dialogTypeForm = {
          title: '添加新闻',
          isDelete: false,
          id:'',
          content: '',
          title: '',
          photo: ''
        };
      },
      //点击保存按钮之后，如果是修改新闻，则后端API为update，如果是添加新闻则后端API为添加
      saveCheckResult () {
        this.loading = true;
        if (this.dialogTypeForm.title == '修改新闻') {
            modifyCheckResult({checkResultForm: this.dialogTypeForm}).then(data => {
                this.loading=false;
                if (data) {
                    this.showDialogType = false;
                }
            });
        } else {
            addCheckResult({checkResultForm: this.dialogTypeForm}).then(data => {
                this.loading = false;
                if (data.data.affectedRows ==0) {
                    this.onAlertError('保存失败');
                } else {
                    this.showDialogType = false;
                    this.onAlertSuccess('保存成功');
                }
            });
        }
      },
      //删除新闻
      goToDelete (id) {
        if (this.selectRow.length === 0) {
          this.onAlertError('选中数量为0条');
        } else {
          this.loading = true;
          id=this.selectRow;
          let i = 0;
          for (i=0;i<id.length;i++) {
            let temp=id[i];
            deleteCheckResultById({temp}).then(data => {
              console.log(temp);
              this.loading = false;
              if (data.data.affectedRows!=0) { 
                this.onAlertError('删除成功');
                getUserInfo().then(data => {
                  this.searchData=data.data;
                  this.preData=data.data;
                  if (data.data.code === '000') {
                    this.showDialogType = false;
                  }
                });
              } else {
                this.onAlertError('删除失败');
              }
            });
          }
        }
      },
      //获取选中新闻的id数组
      getSelectRow (val) {
        let id = [];
        _.each(val, item => {
          id.push(item.id);
        });
        this.selectRow = id;
      }
    },
    mounted(){
      //渲染表格数据
      getOrderInfo().then(data => {
        console.log(data.data);
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
