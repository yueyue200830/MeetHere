<template>
  <div>

    <grid-layout :layout="formColumns"
                 :col-num="12"
                 :row-height="30"
                 :is-draggable="draggable"
                 :is-resizable="resizable"
                 :vertical-compact="true"
                 :margin="[1, 1]"
                 :use-css-transforms="true">
      <grid-item v-for="item in formColumns" :key="item.i"
                 :x="item.x"
                 :y="item.y"
                 :w="item.w"
                 :h="item.h"
                 :i="item.i"
                 @dblclick.native="modifyColumns(item)">
        <span class="vue-delete-handle" @click="deleteColumns(item)"></span>
        <div class="text">
          <div>{{item.message}}</div>
        </div>
      </grid-item>
    </grid-layout>

  </div>

</template>

<script>
import VueGridLayout from 'vue-grid-layout';
import '../css/reportForm.css';
export default {
  name: 'reportForm',
  components: {
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem
  },
  props: {
    formColumns: {
      type: Array,
      default: () => {
        return [];
      }
    },
    draggable: {
      type: Boolean,
      required: false,
      default: () => {
        return true;
      }
    },
    resizable: {
      type: Boolean,
      default: () => {
        return true;
      }
    }
  },
  methods: {
    deleteColumns (column) {
      this.$emit('delete-columns', column);
    },
    modifyColumns (column) {
      this.$emit('modify-columns', column);
    }
  }
};
</script>

<style scoped>

</style>
