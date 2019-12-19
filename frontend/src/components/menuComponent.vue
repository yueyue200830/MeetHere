<template>
  <el-container style="height:100%; border: 1px solid #eee">
    <el-aside width="auto" style="background-color: white">
      <el-row class="menu-img">
        <img src="../assets/ball.jpg" v-if="!isCollapseLeft"/>
      </el-row>
      <el-row class="menu-button-left">
        <el-button icon="el-icon-d-arrow-left" v-if="!isCollapseLeft" @click="isCollapseLeft = !isCollapseLeft" type="primary" size="mini"></el-button>
        <el-button icon="el-icon-d-arrow-right" v-if="isCollapseLeft" @click="isCollapseLeft = !isCollapseLeft" type="primary" size="mini"></el-button>
      </el-row>
      <el-scrollbar class="menu-body" changeStyle>
        <el-menu :collapse="isCollapseLeft" :default-active="defaultActive"
               @select="selectMenu($event, 'left')" unique-opened >
        <!--<div v-for="(menu, index) in totalLeftMenu" :key="setIndex(index)">-->
        <el-menu-item style="min-width: 200px" v-for="(menu, index) in totalLeftMenu" :key="setIndex('left', index)" :index="setIndex('left',index, undefined, menu.module)" @click="goTo(menu.path)" v-if="!menu.action && menu.meta.showInMenu">
          <i class="el-icon-tickets"></i>
          <span slot="title">{{menu.name}}</span>
        </el-menu-item>
        <!--</div>-->
      </el-menu>
      </el-scrollbar>
    </el-aside>
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <span class="head-time">{{now}}</span>
        <el-dropdown>
          <span v-if="hasLoggedIn" class="hear-user">
            {{managerName}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item >修改密码</el-dropdown-item>
            <el-dropdown-item @click.native="logout">注销</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-scrollbar changeStyle class="page-main-body">
        <el-main>
          <slot></slot>
        </el-main>
      </el-scrollbar>
    </el-container>

  </el-container>
</template>

<script>
import { mapMutations } from 'vuex'
import _ from 'lodash';
import {routes} from '../router/router';
import moment from 'moment';
//todo
//const modifyManagerPassword = (form) => axios.post('/app/modifyManagerPassword', form);

export default {
  name: 'menuComponent',
  data () {
    return {
      isCollapseLeft: false,
      isCollapseRight: false,
      totalLeftMenu: routes,
      menuIndex: {},
      asideWidth: '200px',
      defaultActive: '',
      now: '',
      timer: '',
      // form:{
      //   formalPassword:'',
      //   newPassword:'',
      // }
    };
  },
  computed:{
    managerName () {
      return this.$store.getters.getManagerName;
    },
    managerId(){
      return this.$store.getters.getManagerId;
    },
    hasLoggedIn(){
      return this.$store.getters.hasManagerLoggedIn;
    }
  },
  beforeMount () {
    // 设置定时器，每3秒刷新一次
    let self = this;
    this.timer = setInterval(() => {
      self.now = moment().format('YYYY-MM-DD HH:mm:ss');
    }, 1000);
    self.now = moment().format('YYYY-MM-DD HH:mm:ss');
  },
  mounted () {
    this.checkManagerLogin();
    const pageName = this.$route.name;
    const findLi = document.getElementsByTagName('li');
    _.each(findLi, item => {
      if (item.innerText.trim() === pageName) {
        item.classList.add('is-active');
        // item.parentElement.parentElement.parentElement.parentElement.style.display = '';
        // item.parentElement.parentElement.parentElement.parentElement.parentElement.classList.add('is-opened');
      }
    });
    this.defaultActive = this.menuIndex[this.$route.name];
  },
  beforeDestroy () {
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
  methods: {
    ...mapMutations(['managerLogOut', 'checkManagerLogin']),
    logout(){
      this.managerLogOut();
      this.$router.push('/login');
    },
    // modifyPassword(){

    // },
    goTo (path) {
      this.$router.push(`${path}`);
    },
    selectMenu (index, str) {
      const self = this;
      let removeClass = [];
      const menu = document.getElementsByClassName('menu-body');
      _.each(menu, subMenu => {
        if (subMenu.classList.contains('menu-body-right')) {
          if (str === 'left') {
            _.each(subMenu.getElementsByClassName('is-active'), el => {
              removeClass.push(el);
            });
          } else {
            _.each(subMenu.getElementsByClassName('is-active'), item => {
              if (item && self.menuIndex[item.innerText.trim()] !== index && item.tagName === 'LI' && item.children.length === 0) {
                removeClass.push(item);
              }
            });
          }
        } else {
          if (str === 'right') {
            _.each(subMenu.getElementsByClassName('is-active'), el => {
              removeClass.push(el);
            });
          } else {
            _.each(subMenu.getElementsByClassName('is-active'), item => {
              if (item && self.menuIndex[item.innerText.trim()] !== index && item.tagName === 'LI' && item.children.length === 0) {
                removeClass.push(item);
              }
            });
          }
        }
      });
      if (removeClass.length > 0) {
        _.each(removeClass, item => {
          item.classList.remove('is-active');
        });
      }
    },
    setIndex (str, index, subIndex = undefined, title) {
      if (subIndex !== undefined && subIndex + 1) {
        this.menuIndex[title] = `${str}-${index + 1}-${subIndex + 1}`;
        return `${str}-${index + 1}-${subIndex + 1}`;
      }
      this.menuIndex[title] = `${str}-${index + 1}`;
      return `${str}-${index + 1}`;
    }
  }
};
</script>

<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
    overflow: visible;
  }
  .menu-img {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 80px;
    padding: 0;
    border: 0;
    border-right: solid 1px #e6e6e6;
  }
  .menu-button-left {
    border-right: solid 1px #e6e6e6;
    text-align: right;
    background-color: white;
    height: 40px;
  }
  .menu-button-right {
    border-left: solid 1px #e6e6e6;
    text-align: left;
    background-color: white;
    height: 40px;
  }
  .menu-button-right button {
    position: absolute;
    bottom: 0;
  }
  .menu-body {
    height: calc(100% - 80px - 40px);
    /*overflow-y: auto;*/
    /*overflow-x: hidden;*/
  }
  .menu-body-right {
    height: 100% !important;
    border-left: solid 1px #e6e6e6;
  }
  .page-main-body {
    height: 100%;
  }
  .head-time {
    font-size: 20px;
    margin-right: 25px;
    color: #606266;
  }
  .hear-user {
    cursor: pointer;
    font-size: 20px;
  }
</style>
