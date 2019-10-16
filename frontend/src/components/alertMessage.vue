<template>
  <transition name="el-alert-fade">
    <div
      class="el-alert"
      :class="[typeClass, center ? 'is-center' : '']"
      v-show="visible"
      role="alert"
    >
      <i class="el-alert__icon" :class="[ iconClass, isBigIcon ]" v-if="showIcon"></i>
      <div class="el-alert__content">
        <span class="el-alert__title" :class="[ isBoldTitle ]" v-if="title || $slots.title">
          <span v-if="!descriptionLinkable">{{ title }}</span>
          <a v-if="descriptionLinkable" :href="linkUrl">{{ title }}</a>
        </span>
        <slot>
          <p class="el-alert__description" v-if="description">{{ description }}</p>
        </slot>
        <div style="position: absolute;top: 16px;right: 65px;cursor:pointer" @click="readMessage()" v-if="type === 'info'">
          <i class="el-alert__icon el-icon-success is-big" style="color: #67c23a;float: left"></i>
          <div  style="line-height: 28px;height: 28px;float: left;margin-left: 5px">已读</div>
          <div style="clear: both"></div>
        </div>
        <div>
          <i v-if="type !== 'error'" style="font-size: 30px;top:16px" class="el-alert__closebtn" :class="{ 'is-customed': closeText !== '', 'el-icon-close': closeText === '' }" v-show="closable" @click="close()">{{closeText}}</i>
        </div>
      </div>
    </div>
  </transition>
</template>

<script type="text/babel">
const TYPE_CLASSES_MAP = {
  'success': 'el-icon-success',
  'warning': 'el-icon-warning',
  'error': 'el-icon-error'
};
export default {
  name: 'alertMessage',

  props: {
    id: {
      type: Number,
      required: true
    },
    title: {
      type: String,
      default: '',
      required: true
    },
    description: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'info'
    },
    closable: {
      type: Boolean,
      default: true
    },
    closeText: {
      type: String,
      default: ''
    },
    showIcon: Boolean,
    center: Boolean,
    descriptionLinkable: {
      type: Boolean,
      default: false
    },
    linkUrl: {
      type: String,
      default: '/#'
    }
  },

  data () {
    return {
      visible: true
    };
  },

  methods: {
    close () {
      this.visible = false;
      this.$emit('close', this.id);
    },
    readMessage () {
      this.$emit('read-message', this.id);
    }
  },

  computed: {
    typeClass () {
      return `el-alert--${this.type}`;
    },

    iconClass () {
      return TYPE_CLASSES_MAP[this.type] || 'el-icon-info';
    },

    isBigIcon () {
      return this.description || this.$slots.default ? 'is-big' : '';
    },

    isBoldTitle () {
      return this.description || this.$slots.default ? 'is-bold' : '';
    }
  }
};
</script>
