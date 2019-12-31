import Vue from 'vue'
import {expect} from 'chai'
import messageApproveManager from '@/views/messageApproveManager.vue'

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

})