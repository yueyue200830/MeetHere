<template>
    <el-main>
      Main Page

    </el-main>
</template>

<script>
  let token = localStorage.getItem('Authorization');
  export default {
    name: "userMain",
    created: function () {
      // window.console.log(token);
    }
  }
</script>

<style scoped>

</style>
