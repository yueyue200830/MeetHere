<template>
  <div style="width: 400px;">
    <slot name="slot1"></slot>
    <el-input type="text" v-model="input" @blur="inputOver"></el-input>
    <slot name="slot2"></slot>

  </div>
</template>

<script>
export default {
  name: 'myInput',
  props: {
    myText: {
      type: String,
      default: () => {
        return 'no input';
      }
    }
  },
  data () {
    return {
      input: ''
    };
  },
  methods: {
    inputOver () {
      // console.log(this.input)
      this.$emit('input-ready', this.input);
    }
  }
};
</script>

<style scoped>

</style>
