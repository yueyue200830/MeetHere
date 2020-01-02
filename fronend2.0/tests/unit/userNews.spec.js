import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import UserNews from '@/views/userNews.vue'
import MockAdapter from "axios-mock-adapter"
import flushPromises from "flush-promises"
import ElementUI from 'element-ui'
import axios from 'axios'

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.prototype.$http = axios;

const mock = new MockAdapter(localVue.prototype.$http);

describe('userNews.vue', () => {
  it('test http error', async () => {
    mock
      .onPost('/app/getNewsThree')
      .reply(500)
    const wrapper = shallowMount(UserNews, {localVue})
    await flushPromises();
    expect(wrapper.vm.newsList).to.eql([])
    expect(wrapper.vm.loadingNews).to.equal(false)
    mock.reset()
  })

  it('test created function', async () => {
    let newsList = [{
      newsPhoto: 'test photo',
      newsTitle: 'test title',
      newsContent: 'test content'
    }]
    mock
      .onPost('/app/getNewsUserPage')
      .reply(200, newsList)
    const wrapper = shallowMount(UserNews, {localVue})
    await flushPromises();
    expect(wrapper.vm.newsList).to.eql(newsList)
    expect(wrapper.vm.loadingNews).to.equal(false)
    mock.reset()
  })

  it('test convertTime', async () => {
    const wrapper = shallowMount(UserNews, {localVue})
    let date = '2020-01-01 03:08:08.0'
    expect(wrapper.vm.convertTime(date)).to.equal('2020-01-01 03:08')
    mock.reset()
  })

})
