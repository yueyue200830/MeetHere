// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import global from '../src/mixins/globalMessage';


import Components from './components/index';
import Mixins from './mixins/index';

Vue.config.productionTip = false;

Object.keys(global).forEach(key => {
  Vue.prototype[key] = global[key];
});


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
