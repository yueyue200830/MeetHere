import menuComponent from './menuComponent';
import pageMainBody from './pageMainBody';
import lightTable from './lightTable';
import userFooter from "./userFooter";
import userComponent from "./userComponent";

const components = {
  menuComponent,
  pageMainBody,
  lightTable,
  userFooter,
  userComponent,
};

export default function (Vue) {
  Object.keys(components).forEach((key) => {
    Vue.component(key, components[key]);
  });
};
