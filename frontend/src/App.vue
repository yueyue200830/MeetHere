<template>
  <div class="page-template" v-if="this.$route.meta ? this.$route.meta.showManagerMenu : false">
    <menu-component>
      <router-view/>
    </menu-component>
  </div>
  <div class="page-template"  v-else>
    <router-view/>
  </div>

</template>

<script>
import './css/publicCSS.css';
export default {
  name: 'App',
  created () {
    document.title = 'MeetHere';
  }
};
</script>
