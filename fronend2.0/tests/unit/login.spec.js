import Vue from 'vue'
import {expect} from 'chai'
import login from '@/views/login.vue'

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
})