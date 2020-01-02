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
    let revenue = ['篮球馆', '足球场']
    let newsList = [{
      newsPhoto: 'test photo',
      newsTitle: 'test title',
      newsContent: 'test content'
    }]
    mock
      .onPost('/app/getVenueName')
      .reply(200, [revenue])
    mock
      .onPost('/app/getNewsThree')
      .reply(200, newsList)
    const wrapper = shallowMount(UserMain, {localVue})
    await flushPromises();
    expect(wrapper.vm.revenue).to.eql(revenue)
    expect(wrapper.vm.newsList).to.eql(newsList)
  })
})
