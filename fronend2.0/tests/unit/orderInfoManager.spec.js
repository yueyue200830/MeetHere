import {expect} from 'chai'
import orderInfoManager from '@/views/orderInfoManager.vue'
import {createLocalVue, mount} from '@vue/test-utils'
import MockAdapter from "axios-mock-adapter"
import ElementUI from 'element-ui'
import axios from 'axios'
import Vuex from 'vuex'

import Mixins from '@/mixins/index';


const localVue = createLocalVue()
localVue.use(ElementUI)
localVue.use(Vuex)
localVue.use(Mixins)
localVue.prototype.$http = axios

const mock = new MockAdapter(localVue.prototype.$http);

describe('orderInfoManager.vue',() => {


    it("test getValue", () => {
      let value = "2019-12-10"
      let condition = 1;
      let name = ["Basketball","football","swimming pool"]
      let data = [2,3,2]
      mock
        .onGet(`/app/getStatistic/${value}`)
        .reply(200, data)
      mock
        .onPost('/app/getVenueNameForChart')
        .reply(200, name)
      const wrapper = mount(orderInfoManager, {localVue})
      wrapper.vm.value = "2019-12-10"
      wrapper.vm.getValue(condition)
    })

})
