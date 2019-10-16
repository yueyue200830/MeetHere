// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import global from '../src/mixins/globalMessage';
import BaiduMap from 'vue-baidu-map'
//import axios from 'axios';

import Components from './components/index';
import Mixins from './mixins/index';
Vue.use(BaiduMap, {
  ak: 'BN1u4h8K2bAiKTdgmvGyj5pWgzLBzkrX'
})
Vue.config.productionTip = false;

Object.keys(global).forEach(key => {
  Vue.prototype[key] = global[key];
});

// axios.interceptors.request.use(function(config,a){
//   debugger
//   return config;
// }, function(error){
//   //请求错误
//   return Promise.reject(error);
// });

Vue.use(ElementUI);
Vue.use(Components);
Vue.use(Mixins);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
