import Vue from 'vue'
import {expect} from 'chai'
import orderInfoApproveManager from '@/views/orderInfoApproveManager.vue'

describe('orderInfoApproveManager.vue',() => {
    let vm
    beforeEach( () =>{
        vm = new Vue(orderInfoApproveManager).$mount();
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

    it('增加按钮显示弹窗', () => {
        vm.addType();
        expect(vm.showDialogType).to.true
    })

})