import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    count: 0,
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : ''
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
    }
  }
});

export default store
