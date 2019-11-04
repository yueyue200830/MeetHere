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
              {{news.name}}
            </div>
            <div class="news-time">
              {{news.date}}
            </div>
          </div>
        </template>
        <div class="news-body">
          <div class="news-content">{{news.content}}</div>
          <el-image class="news-image" fit="contain" v-if="news.image != null" :src="news.image">
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
                    date: '2019-10-30',
                    name: '进博会放假',
                    content: '11.2放假一天',
                    image: 'https://camo.githubusercontent.com/8708a8dcb49d365b1786a5093d8f3fd37aeb18a2/68747470733a2f2f7770696d672e77616c6c7374636e2e636f6d2f61353839346331622d663661662d343536652d383264662d3131353164613038333962662e706e67',
                }, {
                    date: '2016-05-04',
                    name: '网球',
                    content: '11.2放假一天',
                }, {
                    date: '2016-05-01',
                    name: '进博会放假',
                    content: '11.2放假一天',
                }, {
                    date: '2016-05-03',
                    name: '进博会放假',
                    content: '11.2放假一天',
                }],
            }
        },
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
    width: 300px;
  }
</style>
