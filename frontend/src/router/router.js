import Login from '../pages/login';
import UserInfoManager from '../pages/userInfoManager';
import OrderInfoManager from '../pages/orderInfoManager';
import VenueInfoManager from '../pages/venueInfoManager';
import NewsManage from '../pages/newsManage';
import MessageApproveManager from '../pages/messageApproveManager';
import OrderInfoApproveManager from '../pages/orderInfoApproveManager';
import UserComponent from "../components/userComponent";
import UserInfo from "../pages/userInfo";
import UserRevenue from "../pages/userRevenue"
import UserMain from "../pages/userMain"
import UserNews from "../pages/userNews"
import UserOrder from "../pages/userOrder"
import UserMessage from "../pages/userMessage"
import UserBooking from "../pages/userBooking"
import UserLogin from "../pages/userLogin"
import Main from '../pages/mainPage';
import VueRouter from 'vue-router';
import Vue from 'vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: '主页',
    component: Main,
    meta: {}
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
      showManagerMenu: true,
    }
  },
  {
    path: '/manager/orderInfoApprove',
    name: '预约订单审核',
    component: OrderInfoApproveManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true,
    }
  },
  {
    path: '/manager/orderInfo',
    name: '预约订单统计',
    component: OrderInfoManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true,
    }
  },
  {
    path:'/manager/venueInfo',
    name: '场馆信息管理',
    component: VenueInfoManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true,
    }
  },
  {
    path:'/manager/newsManage',
    name: '新闻动态管理',
    component: NewsManage,
    meta: {
      showInMenu: true,
      showManagerMenu: true,
    }
  },
  {
    path: '/manager/messageApprove',
    name: '留言审核',
    component: MessageApproveManager,
    meta: {
      showInMenu: true,
      showManagerMenu: true,
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
        meta: {}
      },
      {
        path: 'main',
        component: UserMain,
        meta: {}
      },
      {
        path: 'info',
        component: UserInfo,
        meta: {}
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
    ]
  },
  {
    path: '/user/login',
    component: UserLogin,
    meta: {}
  },
];

const router = new VueRouter({
  mode: 'history',
  routes: routes
});

export {routes};
export default router;
