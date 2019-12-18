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
              <el-button size="mini" @click="viewPhoto(props.prop.id)">查看图片</el-button>
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
              <el-form-item label="内容" prop="newsContent">
                <el-input v-model="dialogTypeForm.newsContent" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="新闻标题" prop="newsTitle">
                <el-input v-model="dialogTypeForm.newsTitle" :disabled="dialogTypeForm.isDelete" :placeholder="$placeholder.input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="图片" prop="newsPhoto">
                <el-upload

                  class="upload-demo"
                  action="https://jsonplaceholder.typicode.com/posts/"
                  :on-change="handleChange"
                  :on-remove="handleRemove"
                  :on-preview="handlePreview"
                  :file-list="photoList"
                  list-type="picture">
                  <el-button size="small" type="primary">点击上传</el-button>
                  <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
                </el-upload>
              </el-form-item>

            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="showDialogType = false">取消</el-button>
          <el-button v-if="!dialogTypeForm.isDelete" type="primary" @click="saveCheckResult">保存</el-button>
        </span>

      </el-dialog>
      <el-dialog
        :title="this.dialogTitle"
        :visible.sync="showPhoto"
        width="50%"
        :close-on-click-modal="false"

        center>
        <img :src="temp" width="132" height="132" id="imgs"/>
      </el-dialog>
    </div>
  </page-main-body>
</template>

<script>
  import axios from 'axios';
  const addCheckResult = (checkResultForm) => axios.post('/app/addNews', checkResultForm);
  const getCheckResultById = (id) => axios.get(`/app/searchNews/${id}`);
  const modifyCheckResult = (checkResultForm) => axios.post('/app/modifyNews', checkResultForm);
  const deleteCheckResultById = (id) => axios.get(`/app/deleteNews/${id}`);
  const getPhotoById = (id) => axios.get(`/app/getPhoto/${id}`);
  const getNewsInfo = () => axios.post('/app/getNews');

  export default {
    name: "newsManage",
    data () {
      return {
        loading: false,
        title: '新闻动态管理',
        dialogTitle: '新闻图片',
        columns: [
          {
            prop: 'id',
            label: '新闻编号',
            width: '150'
          },
          {
            prop: 'newsTitle',
            label: '新闻标题',
            width: '250'
          },
          {
            prop: 'newsContent',
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
        fileObj: '',
        temp: '',
        photoList:[],
        searchData:[],
        preData:[],
        dialogTypeForm: {
          title: '添加新闻',
          isDelete: false,
          id: '',
          newsContent: '',
          newsTitle: '',
          newsPhoto:''
        },
        showDialogType: false,
        showPhoto: false,
        dialogTypeRules: {

        },
        selectRow: []
      };
    },
    methods:{
      handleRemove(file,fileList) {
        //console.log(this.dialogTypeForm);
      },
      handleChange(file) {
        this.fileObj = file.raw;
        var reader = new FileReader();
        reader.readAsDataURL(this.fileObj);
        var temp = this.dialogTypeForm;
        reader.onload = function(e){
          temp.newsPhoto = this.result;
        }
        this.dialogTypeForm = temp;
        this.showPhoto = true;
        document.getElementById("imgs").src = this.dialogTypeForm.newsPhoto;
        //console.log(this.dialogTypeForm);
        console.log(document.getElementById("imgs"));
      },


      handlePreview(file) {
        //console.log(file.response);
      },
      //点击编辑按钮时，出现弹窗，表格中填充该行新闻的内容
      viewTypeDetail(id, action) {
        this.dialogTypeForm.title = '修改新闻';
        this.loading = true;   //调试中，调试结束后把注释符号去掉
        //通过ID搜索，填充内容（id,content,title,photo)
        getCheckResultById(id).then(data => {
          this.loading = false;
          if (data) {
            console.log(data);
            this.dialogTypeForm.newsContent = data.data[0].newsContent;
            this.dialogTypeForm.newsPhoto = data.data[0].newsPhoto;
            this.dialogTypeForm.newsTitle = data.data[0].newsTitle;
            this.dialogTypeForm.title = '修改新闻';
            this.showDialogType = true;
          } else {
            this.onAlertError('搜索失败');
          }
        });
      },
      //点击查看图片后，出现弹窗，显示图片
      viewPhoto(id) {
        this.loading = true;
        //this.showPhoto = true;
        //this.temp = document.getElementById("imgs");
        getPhotoById(id).then(data => {
          this.loading = false;

          if(data){
            //console.log(this.temp);

            this.temp = data.data;
            this.showPhoto = true;
            console.log(this.temp);
          }else{
            this.onAlertError('该条新闻没有图片');
          }
        })
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
          newsContent: '',
          newsTitle: '',
          newsPhoto:''
        };
      },
      //点击保存按钮之后，如果是修改新闻，则后端API为update，如果是添加新闻则后端API为添加
      saveCheckResult () {
        this.loading = true;

        if (this.dialogTypeForm.title == '修改新闻') {
            modifyCheckResult(this.dialogTypeForm).then(data => {
                this.loading=false;
                this.showDialogType = false;
                if (data.data) {
                    getNewsInfo().then(data => {
                      this.searchData=data.data;
                      this.preData=data.data;
                    })
                  this.onAlertSuccess('修改成功');
                }else{
                  this.onAlertError('修改失败')
                }
            });
        } else {
            console.log(this.dialogTypeForm.newsPhoto);
            addCheckResult(this.dialogTypeForm).then(data => {
                this.loading = false;
                this.showDialogType = false;
                if (data.data) {
                    getNewsInfo().then(data => {
                      this.searchData=data.data;
                      this.preData=data.data;
                    })
                    this.onAlertSuccess('添加成功');
                } else {

                    this.onAlertError('添加失败');
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
            deleteCheckResultById(temp).then(data => {
              this.loading = false;
              if (data.data) {
                this.onAlertSuccess('删除成功');
                getNewsInfo().then(data => {
                  this.searchData=data.data;
                  this.preData=data.data;
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
      getNewsInfo().then(data => {
        this.searchData=data.data;
        this.preData=data.data;
      })
    }
  }
</script>

<style scoped>

</style>
