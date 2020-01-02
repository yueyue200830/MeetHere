import Vue from 'vue'
import {expect} from 'chai'
import login from '@/views/login.vue'
import {createLocalVue, mount} from '@vue/test-utils'
import MockAdapter from "axios-mock-adapter"
import ElementUI from 'element-ui'
import axios from 'axios'
import Vuex from 'vuex'
import sinon from 'sinon'


const localVue = createLocalVue()
localVue.use(ElementUI)
localVue.use(Vuex)
localVue.prototype.$http = axios

const mock = new MockAdapter(localVue.prototype.$http);

describe('login.vue',() => {
    it('点击显示密码图标，icon改变', () => {
        const vm = new Vue(login).$mount();
        vm.passw = 'text';
        vm.showPass();
        expect(vm.icon).to.equal('el-input__icon el-icon-view')
        expect(vm.passw).to.equal('password')
    })

    it('点击隐藏密码图标，icon改变', () => {
        const vm = new Vue(login).$mount();
        vm.passw = 'password';
        vm.showPass();
        expect(vm.icon).to.equal('el-input__icon el-icon-loading')
        expect(vm.passw).to.equal('text')
    })

    it('测试登录', () => {
        let mutations = {
            managerLogin: sinon.fake()
          }
          let store = new Vuex.Store({
            state: {},
            mutations
          })
          let loginForm = {
            user_name: 'testManager',
            user_key: 'Rua33@'
          }
          mock
            .onGet('/app/managerLoginCheck', loginForm)
            .reply(200, 1)
          const wrapper = mount(login, {localVue, store})
          wrapper.vm.form = loginForm   
          expect(wrapper.vm.form).to.eql(loginForm)
          wrapper.vm.login()
    })
})