<template>
  <el-main>
    <div class="user-title">
      新闻
    </div>
    <el-collapse accordion class="news-part">
      <el-collapse-item v-for="news in newsList" :key="news.id">
        <template slot="title">
          <div class="news-title">
            <div class="news-name">
              {{ news.newsTitle }}
            </div>
            <div class="news-time">
              {{ convertTime(news.newsTime) }}
            </div>
          </div>
        </template>
        <div class="news-body">
          <div class="news-content">{{ news.newsContent }}</div>
          <el-image class="news-image" fit="contain" v-if="news.newsPhoto != null" :src="news.newsPhoto" />
        </div>
      </el-collapse-item>
    </el-collapse>
  </el-main>
</template>

<script>
export default {
  name: 'userNews',
  data () {
    return {
      newsList: []
    }
  },
  created: function () {
    this.$http
      .post('/app/getNewsUserPage')
      .then(response => {
        this.newsList = response.data
      })
  },
  methods: {
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

  .news-part {
    max-width: 800px;
    margin: 10px auto 20px;
    padding: 0 20px;
    border-left: 1px solid #EBEEF5;
    border-right: 1px solid #EBEEF5;
  }

  .news-title {
    display: flex;
    flex-direction: row;
    width: 100%;
    margin-right: 20px;
    font-size: 16px;
  }

  .news-time {
    margin-left: auto;
  }

  .news-body {
    display: flex;
    flex-direction: row;
  }

  .news-content {
    flex-grow: 1;
  }

  .news-image {
    flex-shrink: 0;
    max-width: 300px;
  }
</style>
