// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
//引入elemant
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(Element)

//axios 跨域相关（跨域用于测试环境与后台服务器的通信）
import apiConfig from '../config/api.config'//解决跨域的问题 这个用于设置axios的baseUrl
import axios from 'axios'
axios.defaults.baseURL = apiConfig.baseUrl//设置axios的baseUrl
Vue.prototype.axios = axios//Vue直接使用axios的第一种方法


//cookie相关的依赖
import Cookies from 'js-cookie';
Vue.prototype.Cookies = Cookies//Vue直接使用Cookies.js的方法



/*
import { Button, Menu,
  Submenu,
  MenuItem,
  MenuItemGroup, Row,Col,Header,Main,Container} from 'element-ui';
Vue.config.productionTip = false

Vue.use(Button)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Row)
Vue.use(Col)
Vue.use(Header)
Vue.use(Container)
Vue.use(Main)

*/

//前置导航守卫
router.beforeEach(function (to, from, next) {
    const nextRoute = [ 'UploadPdf', 'HtmlEdit', 'SeeAllZhoubao','AdminHomePage','HomePage','Help'];
		var userName=Cookies.get('userName');
    //跳转至上述3个页面
    if (nextRoute.indexOf(to.name)>=0) {
        //未登录
        if (!userName) {
            next('/');
        }
    }
    next();
});
 


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
