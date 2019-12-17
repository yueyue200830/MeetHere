<template>
    <el-main>
      <el-carousel height="50vw" >
        <el-carousel-item v-for="item in 4" :key="item" >
          <el-image src="https://www.bing.com/th?id=OHR.Seidenschwanz_ZH-CN7486965726_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp"/>
        </el-carousel-item>
      </el-carousel>
      <el-row :gutter="10" class="revenue">
        <el-col
          :xs="24"
          :sm="{span: 8, offset: 1}"
          :md="{span: 8, offset: 1}"
          :lg="{span: 6, offset: 2}"
          :xl="{span: 5, offset: 3}">
          <div class="revenue-name">
            <div class="revenue-card" v-for="r in revenue" :key="r" v-on:click="clickTest(r)">
              <div class="revenue-card-body">
                {{ r }}
              </div>
            </div>
          </div>
        </el-col>
        <el-col :xs="24" :sm="14" :md="14" :lg="14" :xl="13">
          <div class="revenue-image-div">
            <el-image
              class="revenue-image"
              fit="contain"
              src="https://www.bing.com/th?id=OHR.SheepCoteClod_ZH-CN7630556554_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp"
            />
          </div>
        </el-col>
      </el-row>
    </el-main>
</template>

<script>
  export default {
    name: "userMain",
    data () {
      return {
        revenue: ['篮球馆', '足球馆', '乒乓球馆', '羽毛球馆']
      }
    },
    created: function () {
      // window.console.log(token);
    },
    methods: {
      clickTest: function (r) {
        console.log(r);
      }
    }
  }
</script>

<style scoped>
  .revenue {
    margin-top: 40px;
  }

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

  .revenue-image-div {
    margin: 10px;
  }

  .revenue-image {
    width: 100%;
    height: 280px;
  }
</style>
