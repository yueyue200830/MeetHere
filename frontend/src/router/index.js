import Vue from 'vue';
import Router from 'vue-router';
import totalMenu from '../router/routerManage';
import { oaMenu } from '../router/oa-menu-config';
import _ from 'lodash';


Vue.use(Router);

function getMenuComponent (menuList) {
  const newMenu = [];
  _.each(menuList, menu => {
    let menuConfig = {};
    if (typeof (menu.action) === 'object') {
      _.each(menu.action, submenu => {
        menuConfig = { name: submenu.title, path: submenu.path, component: submenu.desktop, meta: {} };
        if (submenu.notShowInMenu) {
          menuConfig.meta.notShowInMenu = submenu.notShowInMenu;
        }
        if (submenu.notShowMenu) {
          menuConfig.meta.notShowMenu = submenu.notShowMenu;
        }
        newMenu.push(menuConfig);
      });
    } else {
      menuConfig = { name: menu.module, path: menu.path, component: menu.desktop, meta: {} };
      if (menu.notShowInMenu) {
        menuConfig.meta.notShowInMenu = menu.notShowInMenu;
      }
      if (menu.notShowMenu) {
        menuConfig.meta.notShowMenu = menu.notShowMenu;
      }
      newMenu.push(menuConfig);
    }
  });
  return newMenu;
}

const newRouter = getMenuComponent(totalMenu.concat(oaMenu));
export default new Router({
  routes: newRouter
});
