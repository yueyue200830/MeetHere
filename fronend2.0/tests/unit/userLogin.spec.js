import { expect } from 'chai'
import {createLocalVue, mount} from '@vue/test-utils'
import UserLogin from '@/views/userLogin.vue'
import MockAdapter from "axios-mock-adapter"
import ElementUI from 'element-ui'
import VueRouter from 'vue-router'
import axios from 'axios'
import Vuex from 'vuex'
import sinon from 'sinon'

const localVue = createLocalVue()
localVue.use(ElementUI)
localVue.use(Vuex)
localVue.prototype.$http = axios
localVue.use(VueRouter)
const router = new VueRouter()

const mock = new MockAdapter(localVue.prototype.$http);

describe('userLogin.vue', () => {
  it('test login', () => {
    let mutations = {
      userLogin: sinon.fake()
    }
    let store = new Vuex.Store({
      state: {},
      mutations
    })
    let loginForm = {
      name: 'testUser',
      password: '123456zA-'
    }

    mock
      .onGet('/app/Login', {params: {login: loginForm}})
      .reply(200, 1)
    const wrapper = mount(UserLogin, {localVue, store, router})

    wrapper.find('input[type=name]').setValue(loginForm.name)
    wrapper.find('input[type=password]').setValue(loginForm.password)

    expect(wrapper.vm.loginForm).to.eql(loginForm)
    wrapper.vm.login()
    expect(mutations.userLogin.calledOnce);
  })

  it('wrong password', () => {
    let mutations = {
      userLogin: sinon.fake()
    }
    let store = new Vuex.Store({
      state: {},
      mutations
    })
    let loginForm = {
      name: 'testUser',
      password: '123456zA-'
    }

    mock
      .onGet('/app/Login', {params: {login: loginForm}})
      .reply(200, -1)
    const wrapper = mount(UserLogin, {localVue, store})

    wrapper.find('input[type=name]').setValue(loginForm.name)
    wrapper.find('input[type=password]').setValue(loginForm.password)

    expect(wrapper.vm.loginForm).to.eql(loginForm)
    wrapper.vm.login()
    sinon.assert.notCalled(mutations.userLogin)
  })
})
