import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/login'
import UserInfoManager from '@/views/userInfoManager'
import OrderInfoManager from '@/views/orderInfoManager'
import VenueInfoManager from '@/views/venueInfoManager'
import NewsManage from '@/views/newsManage'
import MessageApproveManager from '@/views/messageApproveManager'
import OrderInfoApproveManager from '@/views/orderInfoApproveManager'
import UserComponent from '../components/userComponent'
import UserInfo from '@/views/userInfo'
import UserRevenue from '@/views/userRevenue'
import UserMain from '@/views/userMain'
import UserNews from '@/views/userNews'
import UserOrder from '@/views/userOrder'
import UserMessage from '@/views/userMessage'
import UserBooking from '@/views/userBooking'
import UserLogin from '@/views/userLogin'
import UserRegister from '@/views/userRegister'
import UserMyMessage from '@/views/userMyMessage'
import Main from '@/views/mainPage'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '主页',
    component: Main,
    meta: {},
    beforeEnter: (to, from, next) => {
      next('/user/main')
    }
  },
  {
    path: '/login',
    name: '登录',
    component: Login,
    meta: {}
  },
  {
    path: '/manager/userInfo',
    name: '用户信息',
    component: UserInfoManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true
    },
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('ManagerAuthorization') == null) {
        next('/login')
      } else {
        next()
      }
    }
  },
  {
    path: '/manager/orderInfoApprove',
    name: '预约订单审核',
    component: OrderInfoApproveManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true
    },
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('ManagerAuthorization') == null) {
        next('/login')
      } else {
        next()
      }
    }
  },
  {
    path: '/manager/orderInfo',
    name: '预约订单统计',
    component: OrderInfoManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true
    },
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('ManagerAuthorization') == null) {
        next('/login')
      } else {
        next()
      }
    }
  },
  {
    path: '/manager/venueInfo',
    name: '场馆信息管理',
    component: VenueInfoManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true
    },
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('ManagerAuthorization') == null) {
        next('/login')
      } else {
        next()
      }
    }
  },
  {
    path: '/manager/newsManage',
    name: '新闻动态管理',
    component: NewsManage,
    meta: {
      showInMenu: true,
      showManagerMenu: true
    },
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('ManagerAuthorization') == null) {
        next('/login')
      } else {
        next()
      }
    }
  },
  {
    path: '/manager/messageApprove',
    name: '留言审核',
    component: MessageApproveManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true
    },
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('ManagerAuthorization') == null) {
        next('/login')
      } else {
        next()
      }
    }
  },
  {
    path: '/user',
    component: UserComponent,
    meta: {},
    children: [
      {
        path: '',
        component: UserMain,
        meta: {},
        beforeEnter: (to, from, next) => {
          next('/user/main')
        }
      },
      {
        path: 'main',
        component: UserMain,
        meta: {}
      },
      {
        path: 'info',
        component: UserInfo,
        meta: {},
        beforeEnter: (to, from, next) => {
          if (localStorage.getItem('Authorization') == null) {
            next('/user/login')
          } else {
            next()
          }
        }
      },
      {
        path: 'revenue',
        component: UserRevenue,
        meta: {}
      },
      {
        path: 'news',
        component: UserNews,
        meta: {}
      },
      {
        path: 'order',
        component: UserOrder,
        meta: {}
      },
      {
        path: 'message',
        component: UserMessage,
        meta: {}
      },
      {
        path: 'booking',
        component: UserBooking,
        meta: {}
      },
      {
        path: 'myMessage',
        component: UserMyMessage,
        meat: {},
        beforeEnter: (to, from, next) => {
          if (localStorage.getItem('Authorization') == null) {
            next('/user/login')
          } else {
            next()
          }
        }
      }
    ]
  },
  {
    path: '/user/login',
    component: UserLogin,
    meta: {},
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('Authorization') == null) {
        next()
      } else {
        next('/user/info')
      }
    }
  },
  {
    path: '/user/register',
    component: UserRegister,
    meta: {},
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem('Authorization') == null) {
        next()
      } else {
        next('/user/info')
      }
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  routes: routes
})

export { routes }
export default router
