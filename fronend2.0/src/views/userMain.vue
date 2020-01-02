<template>
    <el-main>
      <el-carousel height="50vw" >
        <el-carousel-item>
          <el-image :src="require('../assets/mainPage0.jpg')"/>
        </el-carousel-item>
        <el-carousel-item>
          <el-image :src="require('../assets/mainPage1.jpg')"/>
        </el-carousel-item>
        <el-carousel-item>
          <el-image :src="require('../assets/mainPage2.jpg')"/>
        </el-carousel-item>
        <el-carousel-item>
          <el-image :src="require('../assets/mainPage3.jpg')"/>
        </el-carousel-item>
      </el-carousel>
      <div class="main-title">
        <div class="main-title-text" id="venue-title">
          场馆
        </div>
      </div>
      <el-row :gutter="10">
        <el-col
          :xs="24"
          :sm="{span: 8, offset: 2}"
          :md="{span: 8, offset: 3}"
          :lg="{span: 6, offset: 4}"
          :xl="{span: 5, offset: 5}"
        >
          <div class="revenue-name">
            <div class="revenue-card" v-for="r in revenues" :key="r.rvnName" v-on:click="clickTest(r)">
              <div class="revenue-card-body">
                {{ r.rvnName }}
              </div>
            </div>
          </div>
        </el-col>
        <el-col
          :xs="24"
          :sm="{span: 11, offset: 0}"
          :md="{span: 9, offset: 1}"
          :lg="{span: 7, offset: 2}"
          :xl="{span: 5, offset: 2}"
        >
          <div class="revenue-info-div">
            <div class="revenue-info" v-if="revenues.length != 0">
              {{ revenues[currentRevenue].rvnIntro }}
            </div>
          </div>
        </el-col>
      </el-row>
      <div class="main-title">
        <div class="main-title-text" id="news-title">
          新闻
        </div>
      </div>
      <el-row class="news-row">
        <el-col
          :xs="24"
          :sm="24"
          :md="{span: 22, offset: 1}"
          :lg="{span: 18, offset: 3}"
          :xl="{span: 14, offset: 5}"
        >
          <el-row :gutter="20" v-loading="loadingNews">
            <el-col
              :xs="24"
              :sm="{span: 8, offset: 0}"
              :md="{span: 8, offset: 0}"
              :lg="{span: 8, offset: 0}"
              :xl="{span: 8, offset: 0}"
              v-for="news in newsList"
              :key="news.id"
            >
              <div class="news-card">
                <el-image class="news-image" :src="news.newsPhoto"/>
                <div class="news-headline">
                  <div class="news-headline-text">
                    {{ news.newsTitle }}
                  </div>
                </div>
                <div class="news-content">
                  {{ news.newsContent }}
                </div>
              </div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-main>
</template>

<script>
export default {
  name: 'userMain',
  data () {
    return {
      loadingNews: false,
      newsList: [{
        newsPhoto: '',
        newsTitle: '',
        newsContent: ''
      }],
      revenues: [],
      currentRevenue: 0
    }
  },
  created: function () {
    this.loadingNews = true
    this.$http
      .post('/app/getVenueUserPage')
      .then(response => {
        this.revenues = response.data[0]
      })
      .catch(error => {
        this.$message.error('获取场馆信息失败')
      })
    this.$http
      .post('/app/getNewsThree')
      .then(response => {
        this.newsList = response.data
      })
      .catch(error => {
        this.$message.error('加载新闻失败')
      })
      .finally(() => {
        this.loadingNews = false
      })
  },
  methods: {
    clickTest: function (r) {
      let i = 0
      for (i = 0; i < this.revenues.length; i++) {
        if (r === this.revenues[i])
          break
      }
      this.currentRevenue = i
    }
  }
}
</script>

<style scoped>
  .revenue-name {
    margin-right: 40px;
  }

  .revenue-card {
    margin: 8px;
    border: 1px solid #EBEEF5;
    border-radius: 4px;
    color: #303133;
    transition: .3s;
  }

  .revenue-card-body {
    padding: 20px;
  }

  .revenue-card:hover {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    cursor:pointer;
  }

  .revenue-info-div {
    margin: 10px;
    height: 270px;
    display: flex;
    flex-direction: column;
  }

  .revenue-info {
    border: 1px solid #EBEEF5;
    border-radius: 8px;
    color: #303133;
    transition: .3s;
    box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.08);
    padding: 10px;
    margin: auto 10px;
    max-width: 350px;
  }

  .main-title {
    font-size: 42px;
    display: flex;
    flex-direction: row;
  }

  .main-title-text {
    margin: 40px auto 20px;
  }

  .news-row {
    margin: 20px auto;
  }

  .news-card {
    margin: 8px;
    border: 1px solid #EBEEF5;
    border-radius: 4px;
    color: #303133;
    transition: .3s;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .04), 0 0 6px rgba(0, 0, 0, .04);
    padding: 10px;
  }

  .news-card:hover {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .news-image {
    margin: 15px 20px 10px;
    border-radius: 4px;
  }

  .news-headline {
    margin: 10px auto;
    font-size: 22px;
    display: flex;
    flex-direction: row;
  }

  .news-headline-text {
    margin: 0 auto;
  }

  .news-content {
    margin: 0 20px 20px;
    min-height: 100px;
  }

</style>
