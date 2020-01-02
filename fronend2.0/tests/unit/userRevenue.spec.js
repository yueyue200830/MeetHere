import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import UserRevenue from '@/views/userRevenue.vue'
import MockAdapter from "axios-mock-adapter"
import flushPromises from "flush-promises"
import ElementUI from 'element-ui'
import axios from 'axios'

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.prototype.$http = axios;

const mock = new MockAdapter(localVue.prototype.$http);

describe('userRevenue.vue', () => {
  it('test http error', async () => {
    mock
      .onPost('/app/getVenueUserPage')
      .reply(500)
    const wrapper = shallowMount(UserRevenue, {localVue})
    await flushPromises();
    expect(wrapper.vm.revenues).to.eql([])
    expect(wrapper.vm.loadingRevenue).to.equal(false)
    mock.reset()
  })

  it('test created function', async () => {
    let revenueList = [{
      rvnName: 'test name',
      rvnPrice: 'test price',
      rvnIntro: 'test intro'
    }]
    mock
      .onPost('/app/getVenueUserPage')
      .reply(200, [revenueList])
    const wrapper = shallowMount(UserRevenue, {localVue})
    await flushPromises();
    expect(wrapper.vm.revenues).to.eql(revenueList)
    expect(wrapper.vm.loadingRevenue).to.equal(false)
    mock.reset()
  })

})
