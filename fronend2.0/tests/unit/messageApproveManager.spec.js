import Vue from 'vue'
import {expect} from 'chai'
import messageApproveManager from '@/views/messageApproveManager.vue'
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

describe('messageApproveManager.vue',() => {
    let vm
    beforeEach( () =>{
        vm = new Vue(messageApproveManager).$mount();
    })

    it('获取选择行数据的id', () => {
        var l = [{
            id: 1
          },
          {
            id: 2
          },
          {
            id: 3
          }
        ]
        vm.getSelectRow(l)
        expect(vm.selectRow).to.deep.equal([1,2,3])
    })

    it("test searchTable", () => {
      let condition = 1;
      let data = [{
        id: '1',
        userId:'1',
        messageTitle:"Test",
        messageContent:"test",
        messageVisibility: "0",
      }]
      mock
        .onGet(`/app/searchUnapproveMessage/${condition}`)
        .reply(200, data)
      const wrapper = mount(messageApproveManager, {localVue})
        wrapper.vm.$refs.table.searchCondition = 1;
      wrapper.vm.searchTable(condition);
    })

    it("test delete", () => {
      let id = 1;
      let data = [{
        id: '2',
        userId:'1',
        messageTitle:"Test",
        messageContent:"test",
        messageVisibility: "0",
      }]
      const wrapper = mount(messageApproveManager, {localVue})
      wrapper.vm.selectRow.length = 1;
      mock
        .onGet(`/app/approveMessage/${id}`)
        .reply(200, 1)
      mock
        .onPost('/app/getUnapproveMessage')
        .reply(200,data)
      wrapper.vm.goToDelete(id);
    })
})
