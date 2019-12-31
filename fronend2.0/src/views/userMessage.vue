<template>
    <el-main>
      <div class="user-title">
        留言
      </div>
      <el-button type="primary" icon="el-icon-refresh-right" circle class="refresh" @click="refresh"/>
      <el-button type="primary" icon="el-icon-plus" circle class="add-comment" @click="addMessageButton"/>
      <div v-loading="loadingMessage" class="message-div">
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
              <el-image class="image" fit="contain" v-if="comment.image != null" :src="comment.image" />
            </div>
          </el-card>
        </div>
      </div>
      <div class="more">
        <el-button type="primary" @click="loadMore" :loading="loadingMore">
          更多
        </el-button>
      </div>
      <el-dialog
        title="添加留言"
        :visible.sync="addMessageVisibility"
        width="50%">
        <el-form
          :model="addMessageForm"
          ref="addMessageForm"
          label-width="90px"
        >
          <el-form-item
            label="标题"
            :rules="[
              { required: true, message: '标题不可为空'},
            ]"
            prop="title">
            <el-input
              type="title"
              v-model="addMessageForm.title"
              placeholder="请输入标题"
              maxlength="50"
            />
          </el-form-item>
          <el-form-item
            label="内容"
            :rules="[
              { required: true, message: '内容不可为空'},
            ]"
            prop="content">
            <el-input
              type="textarea"
              :autosize="{ minRows: 3, maxRows: 6}"
              placeholder="请输入内容"
              maxlength="1000"
              show-word-limit
              v-model="addMessageForm.content"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button class="button-cancel" @click="cancelAddMessage">取 消</el-button>
          <el-button type="primary" @click="addMessage" :loading="submitting">确 定</el-button>
        </div>
      </el-dialog>
    </el-main>
</template>

<script>
export default {
  name: 'userMessage',
  data () {
    return {
      comments: [],
      addMessageVisibility: false,
      loadingMessage: false,
      submitting: false,
      loadingMore: false,
      addMessageForm: {
        title: '',
        content: ''
      }
    }
  },
  created: function () {
    this.refresh()
  },
  computed: {
    hasLoggedIn () {
      return this.$store.getters.hasLoggedIn
    },
    userId () {
      return this.$store.getters.getUserId
    }
  },
  methods: {
    loadMore: function () {
      this.loadingMore = true
      this.$http
        .get('/app/getMoreMessage', {
          params: {
            lastTime: this.comments[this.comments.length - 1].messageTime,
            number: 2
          } })
        .then(response => {
          this.loadingMore = false
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
    refresh: function () {
      this.loadingMessage = true
      this.$http
        .post('/app/getLatestMessage')
        .then(response => {
          this.comments = response.data[0]
        })
        .catch(error => {
          this.$message.error('刷新失败')
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
    addMessage: function () {
      this.submitting = true
      this.$refs['addMessageForm'].validate((valid) => {
        if (valid) {
          this.$http
            .get('/app/addMessage', {
              params: {
                'addMessageForm': this.addMessageForm,
                'id': this.userId
              } })
            .then(response => {
              if (response.data === 0) {
                this.$message.error('添加失败')
              } else {
                this.$message({
                  message: '添加留言成功',
                  type: 'success'
                })
              }
              this.$refs['addMessageForm'].resetFields()
              this.addMessageVisibility = false
              this.refresh()
            })
            .catch(error => {
              this.$message.error('添加失败')
            })
            .finally(() => {
              this.submitting = false
            })
        } else {
          this.submitting = false
        }
      })
    },
    addMessageButton: function () {
      if (this.hasLoggedIn) {
        this.addMessageVisibility = true
      } else {
        this.$message.error('您未登录，不可留言')
      }
    },
    cancelAddMessage: function () {
      this.$refs['addMessageForm'].resetFields()
      this.addMessageVisibility = false
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
    position: fixed;
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

  .message-div {
    min-height: 40px;
  }
</style>
