<template>
  <div class="page-template" v-if="$route.meta ? $route.meta.notShowMenu: false">
    <router-view/>
  </div>
  <div class="page-template"  v-else>
    <menu-component>
      <router-view/>
    </menu-component>
  </div>

</template>

<script>
import './css/publicCSS.css';
export default {
  name: 'App',
  created () {
    document.title = '共享单车管理系统';
  }
};
</script>
