import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    count: 0,
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
  },
  mutations: {
    increment (state) {
      state.count++
    },
    // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      state.Authorization = user.Authorization;
      let time = new Date().getTime();
      localStorage.setItem('Authorization', JSON.stringify({key: user.Authorization, time: time}));
    },
    // 删除token
    userLogOut () {
      localStorage.removeItem('Authorization');
    },
    // 检查token是否过期
    checkLogin() {
      let authorization = JSON.parse(localStorage.getItem('Authorization'));
      if (authorization === null) {
        return;
      }
      let expireTime = authorization.time;
      let time = new Date().getTime();
      if (time - expireTime > 1000 * 60 * 60 * 2) {
        this.userLogOut();
      }
    },
    hasLoggedIn() {
      return localStorage.getItem('Authorization') !== null;
    }
  }
});

export default store
