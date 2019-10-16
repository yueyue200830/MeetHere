import Login from '../pages/login';
import Index from '../pages/index';
import BikeInfo from '../pages/bikeInfo';
import SpecialBike from '../pages/specialBike';
import OrderInfo from '../pages/orderInfo';
import RepairBike from '../pages/repairBike';
import BikeLog from '../pages/bikeLog';
import Backup from '../pages/backupBike';
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
    path: '/index',
    module: '首页',
    desktop: Index,
    seq: 200
  },

  {
    path: '/bikeInfo',
    module: '使用中单车信息',
    desktop: BikeInfo,
    seq: 202
  },
  {
    path: '/backupBike',
    module: '备用单车',
    desktop: Backup,
    seq: 203
  },
  {
    path: '/specialBike',
    module: '特殊单车',
    desktop: SpecialBike,
    seq: 204
  },
  {
    path:'/orderInfo',
    module: '订单信息',
    desktop: OrderInfo,
    seq: 205
  },
  {
    path:'/repairBike',
    module: '定期维护单车信息',
    desktop: RepairBike,
    seq: 206
  },
  {
    path: '/bikeLog',
    module: '单车日志',
    desktop: BikeLog,
    seq: 207
  }
];

export const menu = (() => {
  // todo
  return router;
})();
