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
        <el-menu-item style="min-width: 200px" v-if="!menu.action && menu.meta.showInMenu" v-for="(menu, index) in totalLeftMenu" :key="setIndex('left', index)" :index="setIndex('left',index, undefined, menu.module)" @click="goTo(menu.path)" >
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
          <span v-if="hasLoggedIn" class="hear-user" id="managerName">
            {{managerName}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="showDialog = true">修改密码</el-dropdown-item>
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
    <el-dialog
      :title="this.dialogTitle"
      :visible.sync="showDialog"
      width="50%"
      :close-on-click-modal="false"

      center>
      <el-form :model="passwordForm" ref="passwordForm" label-width="100px">
        <el-row>
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="passwordForm.userName" :placeholder="$placeholder.input" disabled></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="原先密码" prop="oldPassword">
            <el-input :type="passw" v-model="passwordForm.oldPassword" :placeholder="$placeholder.input">
              <i slot="suffix" :class="icon" @click="showPass"></i>
            </el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="新密码" prop="newPassword">
            <el-input :type="passw" v-model="passwordForm.newPassword" :placeholder="$placeholder.input">
              <i slot="suffix" :class="icon" @click="showPass"></i>
            </el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input :type="passw" v-model="passwordForm.confirmPassword" :placeholder="$placeholder.input">
              <i slot="suffix" :class="icon" @click="showPass"></i>
            </el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="saveResult">保存</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import { mapMutations } from 'vuex'
import _ from 'lodash'
import { routes } from '../router/index'
import moment from 'moment'
import axios from 'axios'

const modifyManagerPassword = (passwordForm) => axios.post('/app/modifyManagerPassword', passwordForm)

export default {
  name: 'menuComponent',
  data () {
    return {
      dialogTitle: '修改密码',
      isCollapseLeft: false,
      isCollapseRight: false,
      totalLeftMenu: routes,
      passw: 'password',
      icon: 'el-input__icon el-icon-view',
      menuIndex: {},
      asideWidth: '200px',
      defaultActive: '',
      now: '',
      timer: '',
      showDialog: false,
      passwordForm: {
        userName: '',
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  computed: {
    managerName () {
      return this.$store.getters.getManagerName
    },
    managerId () {
      return this.$store.getters.getManagerId
    },
    hasLoggedIn () {
      return this.$store.getters.hasManagerLoggedIn
    }
  },
  beforeMount () {
    // 设置定时器，每3秒刷新一次
    let self = this
    this.timer = setInterval(() => {
      self.now = moment().format('YYYY-MM-DD HH:mm:ss')
    }, 1000)
    self.now = moment().format('YYYY-MM-DD HH:mm:ss')
  },
  mounted () {
    this.passwordForm.userName = this.$store.getters.getManagerName
    this.checkManagerLogin()
    const pageName = this.$route.name
    const findLi = document.getElementsByTagName('li')
    _.each(findLi, item => {
      if (item.innerText.trim() === pageName) {
        item.classList.add('is-active')
        // item.parentElement.parentElement.parentElement.parentElement.style.display = '';
        // item.parentElement.parentElement.parentElement.parentElement.parentElement.classList.add('is-opened');
      }
    })
    this.defaultActive = this.menuIndex[this.$route.name]
  },
  beforeDestroy () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  },
  methods: {
    ...mapMutations(['managerLogOut', 'checkManagerLogin']),
    showPass () {
      // 点击图标是密码隐藏或显示
      if (this.passw === 'text') {
        this.passw = 'password'
        this.icon = 'el-input__icon el-icon-view'
      } else {
        this.passw = 'text'
        this.icon = 'el-input__icon el-icon-loading'
      }
    },
    //注销登出
    logout () {
      this.managerLogOut()
      this.$router.push('/login')
    },
    //点击修改密码的保存按钮
    saveResult () {
      var regNumber = /^.*(?=.{6,16})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? _-]).*$/;
      if(this.passwordForm.newPassword != this.passwordForm.confirmPassword){
        this.onAlertError("两次密码不一致");
      }else if(!regNumber.test(this.passwordForm.newPassword)){
        this.onAlertError("修改密码失败，新密码格式不正确(6-16位，大小写字母数字特殊字符)")
      }else{
        modifyManagerPassword(this.passwordForm).then(data => {
          if(data.data == 1){
            this.onAlertSuccess("修改密码成功");
          }else if(data.data == 0){
            this.onAlertError("修改密码失败，用户名与密码不匹配");
          }else{
            this.onAlertError("修改密码失败，新密码格式不正确(6-16位，大小写字母数字特殊字符)");
          }
        })
        this.showDialog = false
      }
    },
    //跳转页面
    goTo (path) {
      this.$router.push(`${path}`)
    },
    selectMenu (index, str) {
      const self = this
      let removeClass = []
      const menu = document.getElementsByClassName('menu-body')
      _.each(menu, subMenu => {
        if (subMenu.classList.contains('menu-body-right')) {
          if (str === 'left') {
            _.each(subMenu.getElementsByClassName('is-active'), el => {
              removeClass.push(el)
            })
          } else {
            _.each(subMenu.getElementsByClassName('is-active'), item => {
              if (item && self.menuIndex[item.innerText.trim()] !== index && item.tagName === 'LI' && item.children.length === 0) {
                removeClass.push(item)
              }
            })
          }
        } else {
          if (str === 'right') {
            _.each(subMenu.getElementsByClassName('is-active'), el => {
              removeClass.push(el)
            })
          } else {
            _.each(subMenu.getElementsByClassName('is-active'), item => {
              if (item && self.menuIndex[item.innerText.trim()] !== index && item.tagName === 'LI' && item.children.length === 0) {
                removeClass.push(item)
              }
            })
          }
        }
      })
      if (removeClass.length > 0) {
        _.each(removeClass, item => {
          item.classList.remove('is-active')
        })
      }
    },
    setIndex (str, index, subIndex = undefined, title) {
      if (subIndex !== undefined && subIndex + 1) {
        this.menuIndex[title] = `${str}-${index + 1}-${subIndex + 1}`
        return `${str}-${index + 1}-${subIndex + 1}`
      }
      this.menuIndex[title] = `${str}-${index + 1}`
      return `${str}-${index + 1}`
    }
  }
}
</script>

<style scoped>
  img {
    max-width: 100%;
    max-height: 100%;
  }

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
