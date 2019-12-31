<template>
  <el-main>
    <div class="user-title">
      我的留言
    </div>
    <div class="message-body" v-loading="loadingMessage">
      <div class="main-card" v-for="comment in comments" :key="comment.id">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="headline">
            <div class="title">{{ comment.messageTitle }}</div>
            <div class="time">{{ convertTime(comment.messageTime) }}</div>
            <el-link type="primary" class="edit-message" @click="editMessage(comment)">编辑</el-link>
            <el-link type="primary" class="delete-message" @click="deleteMessage(comment.id)">删除</el-link>
          </div>
          <div class="card-content">
            <div class="text">
              {{ comment.messageContent }}
            </div>
            <el-image class="image" fit="contain" v-if="comment.image != null" :src="comment.image" />
          </div>
        </el-card>
      </div>
    </div>
    <div class="more">
      <el-button type="primary" :loading="loadingMore" @click="loadMore">
        更多
      </el-button>
    </div>
    <el-dialog
      title="修改留言"
      :visible.sync="editMessageVisibility"
      width="50%">
      <el-form :model="editMessageForm" ref="editMessageForm">
        <el-form-item
          label="标题"
          label-width="100px"
          :rules="[
              { required: true, message: '标题不可为空'},
            ]"
          prop="messageTitle">
          <el-input maxlength="50" type="title" v-model="editMessageForm.messageTitle"/>
        </el-form-item>
        <el-form-item
          label="内容"
          label-width="100px"
          :rules="[
              { required: true, message: '内容不可为空'},
            ]"
          prop="messageContent">
          <el-input
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 6}"
            placeholder="请输入内容"
            v-model="editMessageForm.messageContent"
            maxlength="1000"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="button-cancel" @click="cancelEditMessage">取 消</el-button>
        <el-button type="primary" @click="sendEditMessage" :loading="changingMessage">确 定</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>

<script>
export default {
  name: 'userMyMessage',
  data () {
    return {
      comments: [],
      editMessageVisibility: false,
      loadingMessage: false,
      loadingMore: false,
      changingMessage: false,
      editMessageForm: {
        id: '',
        messageTitle: '',
        messageContent: ''
      }
    }
  },
  created: function () {
    this.refresh()
  },
  computed: {
    userId () {
      return this.$store.getters.getUserId
    }
  },
  methods: {
    refresh: function () {
      this.loadingMessage = true
      this.$http
        .get('/app/getMyMessage', {
          params: {
            userId: this.userId
          }
        })
        .then(response => {
          this.comments = response.data[0]
        })
        .catch(error => {
          this.$message.error('刷新失败！')
        })
        .finally(() => {
          this.loadingMessage = false
        })
    },
    convertTime: function (time) {
      let date = new Date(time)
      let mon = date.getMonth() + 1
      if (mon < 10) { mon = '0' + mon }
      let day = date.getDate()
      if (day < 10) { day = '0' + day }
      let hour = date.getHours()
      if (hour < 10) { hour = '0' + hour }
      let min = date.getMinutes()
      if (min < 10) { min = '0' + min }
      return date.getFullYear() + '-' + mon + '-' + day + ' ' + hour + ':' + min
    },
    deleteMessage: function (id) {
      this.$http
        .get('/app/deleteMessage', {
          params: {
            id: id
          } })
        .then(response => {
          if (response.data === 0) {
            this.$message.error('删除失败，请重试')
          } else {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.refresh()
          }
        })
        .catch(error => {
          this.$message.error('删除失败，请重试')
        })
    },
    editMessage: function (comment) {
      this.editMessageForm.messageTitle = comment.messageTitle
      this.editMessageForm.messageContent = comment.messageContent
      this.editMessageForm.id = comment.id
      this.editMessageVisibility = true
    },
    loadMore: function () {
      this.loadingMore = true
      this.$http
        .get('/app/getMoreMyMessage', {
          params: {
            lastTime: this.comments[this.comments.length - 1].messageTime,
            number: 2,
            userId: this.userId
          } })
        .then(response => {
          for (let i = 0; i < response.data[0].length; i++) {
            this.comments.push(response.data[0][i])
          }
        })
        .catch(error => {
          this.$message.error('加载更多失败')
        })
        .finally(() => {
          this.loadingMore = false
        })
    },
    cancelEditMessage: function () {
      this.$refs['editMessageForm'].resetFields()
      this.editMessageVisibility = false
    },
    sendEditMessage: function () {
      this.changingMessage = true
      this.$refs['editMessageForm'].validate((valid) => {
        if (valid) {
          this.$http
            .get('/app/editMessage', {
              params: {
                'editMessageForm': this.editMessageForm
              } })
            .then(response => {
              if (response.data === 0) {
                this.$message.error('修改失败')
              } else {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
              }
              this.$refs['editMessageForm'].resetFields()
              this.editMessageVisibility = false
              this.refresh()
            })
            .catch(error => {
              this.$message.error('修改失败')
            })
            .finally(() => {
              this.changingMessage = false
            })
        } else {
          this.changingMessage = false
        }
      })
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
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .time {
    margin-left: 10px;
    margin-top: 2px;
    flex-shrink: 0;
  }

  .edit-message {
    margin-left: 10px;
    font-size: 14px;
    flex-shrink: 0;
  }

  .delete-message {
    margin-left: 10px;
    font-size: 14px;
    flex-shrink: 0;
  }

  .card-content {
    display: flex;
    flex-direction: row;
  }

  .text {
    font-size: 14px;
    flex-grow: 1;
    word-break: break-word;
  }

  .image {
    flex-grow: 0;
    flex-shrink: 0;
    max-width: 35%;
    max-height: 150px;
    margin-left: 10px;
  }

  .more {
    margin: 20px;
    text-align: center;
  }

  .message-body {
    min-height: 40px;
  }

</style>
