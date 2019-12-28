<template>
  <div class="page-template" v-if="this.$route.meta ? this.$route.meta.showManagerMenu : false">
    <menu-component>
      <router-view/>
    </menu-component>
  </div>
  <div class="page-template" v-else>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  created () {
    document.title = 'MeetHere'
  }
}
</script>

<style>
#app {
  font-family: Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

body, html {
  margin: 0;
  height: 100%;
  width: 100%;
  padding: 0;
}

a:link {color: black}
a:visited {color: black}
a:hover {color: red}
a:active {color: black}

.page-template {
  margin: 0;
  height: 100%;
  width: 100%;
}

.el-date-editor.el-input, .el-date-editor.el-input__inner {
  width: 100% !important;
}

.el-form-item__label {
  text-align: center !important;
}

img {
  width: auto;
  height: auto;
  vertical-align: middle;
}

.el-select {
  width: 100%;
}

[changeStyle] .el-scrollbar__wrap {
  overflow-x: hidden !important;
}
[changeStyle] .is-horizontal {
  display: none !important;
}

.head-span {
  margin-bottom: 20px;
  color: #909399;
}
</style>
