<template>
  <div style="height: 100%"  v-loading.fullscreen.lock="loading">
    <h2 v-if="!!title">{{title}}</h2>
      <div class="page-body">
        <slot></slot>
      </div>
      <div class="page-footer">
        <slot name="footer"></slot>
      </div>
  </div>

</template>

<script>
export default {
  name: 'pageMainBody',
  props: {
    title: {
      type: String,
      default: () => {
        return '';
      }
    },
    loading: {
      type: Boolean,
      default: () => {
        return false;
      }
    }
  }
};
</script>

<style scoped>
  .page-body {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .page-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    padding-bottom: 20px;
  }
</style>
