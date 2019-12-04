<template>
  <el-main>
    <div class="user-title">
      场馆介绍
    </div>
    <div class="main-card" v-for="revenue in revenues" :key="revenue.id">
      <el-card class="box-card" shadow="hover">
        <div slot="header" class="headline">
          <div class="name">{{ revenue.rvnName }}</div>
          <div class="price">单价：{{ revenue.rvnPrice }}元/时</div>
        </div>
        {{ revenue.rvnIntro }}
      </el-card>
    </div>
  </el-main>
</template>

<script>
    export default {
        name: "userRevenue",
        data () {
            return {
                revenues: [],
            }
        },
        created: function () {
            this.$http
                .post('http://127.0.0.1:8081/getVenueUserPage')
                .then(response => {
                    this.revenues = response.data[0];
                })
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

  .main-card {
    margin: 0 auto;
    max-width: 800px;
    width: 70%;
  }

  .box-card {
    width: 100%;
    min-height: 400px;
    text-align: left;
    margin: 10px;
  }

  .headline {
    display: flex;
    flex-direction: row;
    height: 1.5em;
    font-size: 20px;
  }

  .name {
    flex-grow: 3;
    margin-right: 10px;
    text-align: left;
  }

  .price {
    flex-grow: 1;
    margin-left: 10px;
    text-align: right;
  }



</style>
