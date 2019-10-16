import Login from '../pages/login';
import UserInfo from '../pages/userInfoManager';
import OrderInfo from '../pages/orderInfoManager';
import VenueInfo from '../pages/venueInfoManager';
import NewsManage from '../pages/newsManage';
import MessageApprove from '../pages/messageApproveManager';
import OrderInfoApprove from '../pages/orderInfoApproveManager';
const router = [
  {
    path: '/',
    module: '登录',
    desktop: Login,
    notShowInMenu: true,
    notShowMenu: true,
    seq: 100
  },

  {
    path: '/manager/userInfo',
    module: '用户信息',
    desktop: UserInfo,
    seq: 202
  },
  {
    path: '/manager/orderInfoApprove',
    module: '预约订单审核',
    desktop: OrderInfoApprove,
    seq: 203
  },
  {
    path: '/manager/orderInfo',
    module: '预约订单统计',
    desktop: OrderInfo,
    seq: 204
  },
  {
    path:'/manager/venueInfo',
    module: '场馆信息管理',
    desktop: VenueInfo,
    seq: 205
  },
  {
    path:'/manager/newsManage',
    module: '新闻动态管理',
    desktop: NewsManage,
    seq: 206
  },
  {
    path: '/manager/messageApprove',
    module: '留言审核',
    desktop: MessageApprove,
    seq: 207
  }
];

export const menu = (() => {
  // todo
  return router;
})();
