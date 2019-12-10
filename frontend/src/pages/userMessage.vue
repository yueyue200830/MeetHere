<template>
    <el-main>
      <div class="user-title">
        留言
      </div>
      <el-button type="primary" icon="el-icon-refresh-right" circle class="refresh" @click="refresh"/>
      <el-button type="primary" icon="el-icon-plus" circle class="add-comment" @click="addNewsButton"/>
      <div class="main-card" v-for="comment in comments" :key="comment.id">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="headline">
            <div class="title">{{ comment.messageTitle }}</div>
            <div class="user">{{ comment.user }}</div>
            <div class="time">{{ convertTime(comment.messageTime) }}</div>
          </div>
          <div class="card-content">
            <div class="text">
              {{ comment.messageContent }}
            </div>
            <el-image class="image" fit="contain" v-if="comment.image != null"
                      :src="comment.image">
            </el-image>
          </div>
        </el-card>
      </div>
      <div class="more">
        <el-button type="primary" :loading="false" @click="loadMore">
          More
        </el-button>
      </div>
      <el-dialog
        title="添加留言"
        :visible.sync="addNewsVisibility"
        width="50%">
        <el-form :model="addNewsForm" ref="addNewsForm">
          <el-form-item
            label="标题"
            label-width="100px"
            :rules="[
              { required: true, message: '标题不可为空'},
            ]"
            prop="title">
            <el-input type="title" v-model="addNewsForm.title"/>
          </el-form-item>
          <el-form-item
            label="内容"
            label-width="100px"
            :rules="[
              { required: true, message: '内容不可为空'},
            ]"
            prop="content">
            <el-input
              type="textarea"
              :autosize="{ minRows: 3, maxRows: 6}"
              placeholder="请输入内容"
              v-model="addNewsForm.content">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelAddNews">取 消</el-button>
          <el-button type="primary" @click="addNews">确 定</el-button>
        </div>
      </el-dialog>
    </el-main>
</template>

<script>
  export default {
    name: "userMessage",
    data () {
      return {
        comments: [],
        addNewsVisibility: false,
        addNewsForm: {
          title: '',
          content: '',
        },
      }
    },
    created: function () {
      this.$http
        .post('http://127.0.0.1:8081/getLatestMessage')
        .then(response => {
          this.comments = response.data[0];
        });
    },
    methods: {
      loadMore: function() {
        this.$http
          .get('http://127.0.0.1:8081/getMoreMessage', {
            params: {
              lastTime: this.comments[this.comments.length - 1].messageTime,
              number: 2,
            }})
          .then(response => {
            for (let i = 0; i < response.data[0].length; i++) {
              this.comments.push(response.data[0][i]);
            }
          });
      },
      refresh: function () {
        this.$http
          .post('http://127.0.0.1:8081/getLatestMessage')
          .then(response => {
            this.comments = response.data[0];
          });
      },
      convertTime: function (time) {
        let date = new Date(time);
        return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate()
          + " " + date.getHours() + ":" + date.getMinutes();
      },
      addNews: function () {
        this.$http
          .get('http://127.0.0.1:8081/addMessage', {
            params: {
              "addNewsForm": this.addNewsForm,
              "id": 1
            }})
          .then(response => {
            if (response.data === 0) {
              this.$message.error('添加失败');
            }
            this.$refs['addNewsForm'].resetFields();
            this.addNewsVisibility = false;
            this.refresh();
          });
      },
      addNewsButton: function () {
        this.addNewsVisibility = true;
      },
      cancelAddNews: function () {
        this.$refs['addNewsForm'].resetFields();
        this.addNewsVisibility = false;
      }
    }
  }
</script>

<style scoped>
  .user-title {
    text-align: center;
    font-size: 30px;
    margin: 20px 0 40px;
    color: #303133;
  }

  .main-card {
    margin: 0 auto;
    width: 70%;
    min-width: 600px;
  }

  .more {
    margin: 20px;
    text-align: center;
  }

  .refresh {
    position: absolute;
    right: 100px;
    top: 100px;
  }

  .add-comment {
    position: absolute;
    right: 100px;
    bottom: 100px;
  }

  .box-card {
    text-align: left;
    margin: 10px;
  }

  .headline {
    display: flex;
    flex-direction: row;
    height: 20px;
  }

  .title {
    flex-grow: 1;
    margin-right: auto;
    text-align: left;
  }

  .user {
    margin: 0 10px;
  }

  .time {
    margin-left: 10px;
  }

  .card-content {
    display: flex;
    flex-direction: row;
  }

  .text {
    font-size: 14px;
    flex-grow: 1;
  }

  .image {
    flex-grow: 0;
    flex-shrink: 0;
    max-width: 35%;
    max-height: 150px;
    margin-left: 10px;
  }
</style>
