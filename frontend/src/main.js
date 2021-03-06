import Vue from 'vue';
import App from './App';
import router from './router/router';
import store from './store/store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import global from '../src/mixins/globalMessage';
import Components from './components/index';
import Mixins from './mixins/index';
import axios from 'axios';

Vue.config.productionTip = false;

Object.keys(global).forEach(key => {
  Vue.prototype[key] = global[key];
});


Vue.use(ElementUI);
Vue.use(Components);
Vue.use(Mixins);
Vue.prototype.$http = axios;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
});
