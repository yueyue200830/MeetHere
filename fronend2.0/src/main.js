import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import global from '../src/mixins/globalMessage'
import Components from './components/index'
import Mixins from './mixins/index';
import axios from 'axios'

Vue.config.productionTip = false

Object.keys(global).forEach(key => {
  Vue.prototype[key] = global[key]
})

Vue.use(ElementUI)
Vue.use(Components)
Vue.use(Mixins)
Vue.prototype.$http = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
