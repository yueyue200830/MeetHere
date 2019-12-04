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
          <el-image class="news-image" fit="contain" v-if="news.newsPhoto != null" :src="news.newsPhoto">
          </el-image>
        </div>
      </el-collapse-item>
    </el-collapse>
  </el-main>
</template>

<script>
    export default {
        name: "userNews",
        data() {
            return {
                newsList: [{
                    newsTime: '2019-10-30',
                    newsTitle: '进博会放假',
                    newsContent: '11.2放假一天',
                    newsPhoto: 'https://camo.githubusercontent.com/8708a8dcb49d365b1786a5093d8f3fd37aeb18a2/68747470733a2f2f7770696d672e77616c6c7374636e2e636f6d2f61353839346331622d663661662d343536652d383264662d3131353164613038333962662e706e67',
                }, {
                    newsTime: '2016-05-04',
                    newsTitle: '网球',
                    newsContent: '11.2放假一天',
                }, {
                    newsTime: '2016-05-01',
                    newsTitle: '进博会放假',
                    newsContent: '11.2放假一天',
                    newsPhoto: 'https://upload.wikimedia.org/wikipedia/zh/4/44/Tenki_no_ko_Key_Visual.jpg',
                }, {
                    newsTime: '2016-05-03',
                    newsTitle: '进博会放假',
                    newsContent: '11.2放假一天',
                }],
            }
        },
        created: function () {
            this.$http
                .post('http://127.0.0.1:8081/getNewsUserPage')
                .then(response => {
                    this.newsList = response.data[0];
                })
        },
        methods: {
            convertTime: function (time) {
                let date = new Date(time);
                return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate()
                    + " " + date.getHours() + ":" + date.getMinutes();
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
