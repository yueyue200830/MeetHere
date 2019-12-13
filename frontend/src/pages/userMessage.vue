<template>
    <el-main>
      <div class="user-title">
        留言
      </div>
      <el-button type="primary" icon="el-icon-refresh-right" circle class="refresh" @click="refresh"/>
      <el-button type="primary" icon="el-icon-plus" circle class="add-comment" @click="addMessageButton"/>
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
            <el-image class="image" fit="contain" v-if="comment.image != null" :src="comment.image">
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
        :visible.sync="addMessageVisibility"
        width="50%">
        <el-form :model="addMessageForm" ref="addMessageForm">
          <el-form-item
            label="标题"
            label-width="100px"
            :rules="[
              { required: true, message: '标题不可为空'},
            ]"
            prop="title">
            <el-input type="title" v-model="addMessageForm.title"/>
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
              v-model="addMessageForm.content">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelAddMessage">取 消</el-button>
          <el-button type="primary" @click="addMessage">确 定</el-button>
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
        addMessageVisibility: false,
        addMessageForm: {
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
    computed: {
      hasLoggedIn() {
        return this.$store.getters.hasLoggedIn;
      },
      userId () {
        return this.$store.getters.getUserId;
      },
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
        let mon = date.getMonth() + 1;
        if (mon < 10)
          mon = "0" + mon;
        let day = date.getDate();
        if (day < 10)
          day = "0" + day;
        let hour = date.getHours();
        if (hour < 10)
          hour = "0" + hour;
        let min = date.getMinutes();
        if (min < 10)
          min = "0" + min;
        return date.getFullYear() + "-" + mon + "-" + day + " " + hour + ":" + min;
      },
      addMessage: function () {
        this.$http
          .get('http://127.0.0.1:8081/addMessage', {
            params: {
              "addMessageForm": this.addMessageForm,
              "id": this.userId,
            }})
          .then(response => {
            if (response.data === 0) {
              this.$message.error('添加失败');
            }
            this.$refs['addMessageForm'].resetFields();
            this.addMessageVisibility = false;
            this.refresh();
          });
      },
      addMessageButton: function () {
        if (this.hasLoggedIn) {
          this.addMessageVisibility = true;
        } else {
          this.$message.error('您未登录，不可留言');
        }

      },
      cancelAddMessage: function () {
        this.$refs['addMessageForm'].resetFields();
        this.addMessageVisibility = false;
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
