import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : null,
    ManagerAuthorization: localStorage.getItem('ManagerAuthorization') ? localStorage.getItem('ManagerAuthorization') : null
  },
  getters: {
    hasLoggedIn: (state) => {
      return state.Authorization != null
    },
    hasManagerLoggedIn: (state) => {
      return state.ManagerAuthorization != null
    },
    getUserId: (state) => {
      if (state.Authorization == null) {
        return null
      }
      return JSON.parse(state.Authorization).key
    },
    getManagerId: (state) => {
      if (state.ManagerAuthorization == null) {
        return null
      }
      return JSON.parse(state.ManagerAuthorization).key
    },
    getUserName: (state) => {
      if (state.Authorization == null) {
        return null
      }
      return JSON.parse(state.Authorization).name
    },
    getManagerName: (state) => {
      if (state.ManagerAuthorization == null) {
        return null
      }
      return JSON.parse(state.ManagerAuthorization).name
    }
  },
  mutations: {
    // 将token存入localStorage
    userLogin (state, user) {
      let time = new Date().getTime()
      let authorization = JSON.stringify({ key: user.Authorization, time: time, name: user.name })
      state.Authorization = authorization
      localStorage.setItem('Authorization', authorization)
    },
    managerLogin (state, manager) {
      let time = new Date().getTime()
      let managerAuthorization = JSON.stringify({ key: manager.Authorization, time: time, name: manager.name })
      state.ManagerAuthorization = managerAuthorization
      localStorage.setItem('ManagerAuthorization', managerAuthorization)
    },
    // 删除token
    userLogOut (state) {
      state.Authorization = null
      localStorage.removeItem('Authorization')
    },
    managerLogOut (state) {
      state.ManagerAuthorization = null
      localStorage.removeItem('ManagerAuthorization')
    },
    // 检查token是否过期
    checkLogin (state) {
      let authorization = JSON.parse(state.Authorization)
      if (authorization === null) {
        return
      }
      let expireTime = authorization.time
      let time = new Date().getTime()
      if (time - expireTime > 1000 * 60 * 60 * 2) {
        state.Authorization = null
        localStorage.removeItem('Authorization')
      }
    },
    checkManagerLogin (state) {
      let authorization = JSON.parse(state.ManagerAuthorization)
      if (authorization === null) {
        return
      }
      let expireTime = authorization.time
      let time = new Date().getTime()
      if (time - expireTime > 1000 * 60 * 60 * 2) {
        state.ManagerAuthorization = null
        localStorage.removeItem('ManagerAuthorization')
      }
    },
    changeUserName (state, name) {
      let key = JSON.parse(state.ManagerAuthorization).key
      let time = JSON.parse(state.ManagerAuthorization).time
      let authorization = JSON.stringify({ key: key, time: time, name: name })
      state.Authorization = authorization
      localStorage.setItem('Authorization', authorization)
    }
  },
  actions: {
  },
  modules: {
  }
})
