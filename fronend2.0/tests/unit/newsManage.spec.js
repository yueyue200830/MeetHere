import Vue from 'vue'
import {expect} from 'chai'
import newsManager from '@/views/newsManage.vue'

describe('newsManager.vue',() => {
    let vm
    beforeEach( () =>{
        vm = new Vue(newsManager).$mount();
    })

    it('添加新闻，修改弹窗title', () => {
        vm.addType();
        expect(vm.dialogTypeForm.title).to.equal("添加新闻");
        expect(vm.showDialogType).to.true;
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