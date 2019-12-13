import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    count: 0,
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : null,
  },
  getters: {
    hasLoggedIn: (state) => {
      return state.Authorization != null;
    },
    getUserId: (state) => {
      if (state.Authorization == null) {
        return null;
      }
      return JSON.parse(state.Authorization).key;
    },
    getUserName: (state) => {
      if (state.Authorization == null) {
        return null;
      }
      return JSON.parse(state.Authorization).name;
    }
  },
  mutations: {
    increment (state) {
      state.count++
    },
    // 将token存入localStorage
    userLogin (state, user) {
      let time = new Date().getTime();
      let authorization = JSON.stringify({key: user.Authorization, time: time, name: user.name});
      state.Authorization = authorization;
      localStorage.setItem('Authorization', authorization);
    },
    // 删除token
    userLogOut (state) {
      state.Authorization = null;
      localStorage.removeItem('Authorization');
    },
    // 检查token是否过期
    checkLogin (state) {
      let authorization = JSON.parse(state.Authorization);
      if (authorization === null) {
        return;
      }
      let expireTime = authorization.time;
      let time = new Date().getTime();
      if (time - expireTime > 1000 * 60 * 60 * 2) {
        this.userLogOut(state);
      }
    },
  }
});

export default store
