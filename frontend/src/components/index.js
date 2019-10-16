import menuComponent from './menuComponent';
import pageMainBody from './pageMainBody';
import lightTable from './lightTable';

const components = {
  menuComponent,
  pageMainBody,
  lightTable
};

export default function (Vue) {
  Object.keys(components).forEach((key) => {
    Vue.component(key, components[key]);
  });
};
