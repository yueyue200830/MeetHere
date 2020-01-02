import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import UserMain from '@/views/userMain.vue'
import MockAdapter from "axios-mock-adapter"
import flushPromises from "flush-promises"
import ElementUI from 'element-ui'
import axios from 'axios'

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.prototype.$http = axios;

const mock = new MockAdapter(localVue.prototype.$http);

describe('userMain.vue', () => {

  it('test created function', async () => {
    let revenue = [{
      rvnName: '足球场',
      rvnIntro: '测试',
    }, {
      rvnName: '游泳池',
      rvnIntro: '测试',
    }]
    let newsList = [{
      newsPhoto: 'test photo',
      newsTitle: 'test title',
      newsContent: 'test content'
    }]
    mock
      .onPost('/app/getVenueUserPage')
      .reply(200, [revenue])
    mock
      .onPost('/app/getNewsThree')
      .reply(200, newsList)
    const wrapper = shallowMount(UserMain, {localVue})
    await flushPromises();
    expect(wrapper.vm.revenues).to.eql(revenue)
    expect(wrapper.vm.newsList).to.eql(newsList)
    mock.reset()
  })

  it('test created function', async () => {
    let revenue = [{
      rvnName: '足球场',
      rvnIntro: '测试',
    }, {
      rvnName: '游泳池',
      rvnIntro: '测试',
    }]
    mock
      .onPost('/app/getVenueUserPage')
      .reply(200, [revenue])
    const wrapper = shallowMount(UserMain, {localVue})
    await flushPromises()
    wrapper.vm.clickTest(revenue[1])
    expect(wrapper.vm.currentRevenue).to.equal(1)
    mock.reset()
  })

  it('test http error', async () => {
    mock
      .onPost('/app/getVenueUserPage')
      .reply(500)
    mock
      .onPost('/app/getNewsThree')
      .reply(500)
    const wrapper = shallowMount(UserMain, {localVue})
    await flushPromises();
    expect(wrapper.vm.revenues).to.eql([])
    expect(wrapper.vm.newsList).to.eql([{
      newsPhoto: '',
      newsTitle: '',
      newsContent: ''
    }])
    mock.reset()
  })
})
