<template>
    <el-main>
      <el-button type="primary" icon="el-icon-refresh-right" circle class="refresh" @click="refresh"></el-button>
      <el-button type="primary" icon="el-icon-plus" circle class="add-comment" ></el-button>
      <div class="main-card" v-for="comment in comments" :key="comment.id">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="headline">
            <div class="title">{{ comment.title }}</div>
            <div class="user">{{ comment.user }}</div>
            <div class="time">{{ comment.time }}</div>
          </div>
          <div class="card-content">
            <div class="text">
              {{ comment.comment }}
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
    </el-main>
</template>

<script>
    export default {
        name: "userMessage",
        data () {
            return {
                comments: [{
                    user: 'Kitty',
                    time: '2019-9-9',
                    title: 'hello title',
                    comment: 'hello',
                    image: 'https://img3.doubanio.com/view/photo/l/public/p2542484062.webp',
                },{
                    user: 'Tom',
                    time: '2019-9-9',
                    title: 'hello title',
                    comment: 'hello'
                }],
            }
        },
        methods: {
            loadMore: function() {
                this.$http
                    .post('http://127.0.0.1:8888/loadMore', this.comments[this.comments.length - 1])
                    .then(response => {
                        window.console.log(response);
                        for (let i = 0; i < response.data.moreComments[0].length; i++) {
                            this.comments.push(response.data.moreComments[0][i]);
                        }
                    })
            },
            refresh: function () {
                this.$http
                    .post('http://127.0.0.1:8888/refresh')
                    .then(response => {
                        window.console.log(response);
                        window.console.log(response.data.comments[0]);
                        this.comments = response.data.comments[0];
                    })
            },
            convertTime: function (time) {
                let date = new Date(time);
                return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate()
                    + " " + date.getHours() + ":" + date.getMinutes();
            }
        }
    }
</script>

<style scoped>
  .main-card {
    margin: 0 auto;
    width: 70%;
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
