import {menu} from './menu-config';

import _ from 'lodash';

const totalMenu = [].concat(menu);


export default _.sortBy(totalMenu, item => Number(item.seq));
