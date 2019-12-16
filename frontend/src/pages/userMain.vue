<template>
    <el-main>
      <el-carousel height="50vw" >
        <el-carousel-item v-for="item in 4" :key="item" >
          <el-image src="https://www.bing.com/th?id=OHR.Seidenschwanz_ZH-CN7486965726_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp"/>
        </el-carousel-item>
      </el-carousel>
      <div class="revenue">
        <div class="revenue-name">
          <el-card class="revenue-card" v-for="r in revenue" shadow="hover" :key="r">
            {{ r }}
          </el-card>
        </div>
        <div class="revenue-image">
          <el-image src="https://www.bing.com/th?id=OHR.SheepCoteClod_ZH-CN7630556554_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp" />
        </div>
      </div>
    </el-main>
</template>

<script>
  export default {
    name: "userMain",
    data () {
      return {
        revenue: ['篮球馆', '足球管', '乒乓球馆', '羽毛球馆']
      }
    },
    created: function () {
      // window.console.log(token);
    },
    methods: {
    }
  }
</script>

<style scoped>
  .revenue {
    display: flex;
    margin: 40px auto 20px;
    width: 90%;
    min-width: 600px;
  }

  .revenue-name {
    flex-grow: 0;
    flex-shrink: 0;
    width: 30%;
    min-width: 300px;
    margin-right: 40px;
  }

  .revenue-image {
    flex-grow: 1;
  }

  .revenue-card {
    margin: 8px;
  }
</style>
