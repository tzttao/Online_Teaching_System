// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import VueResource from 'vue-resource'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss'
import axios from "axios"
import qs from 'qs'
import moment from 'moment'
import Blob from '@/excel/Blob.js'
import Export2Excel from '@/excel/Export2Excel.js'

Vue.prototype.$axios = axios
Vue.prototype.$qs = qs  
Vue.prototype.$moment = moment;//赋值使用
         //全局注册，使用方法为:this.qs
//axios.defaults.baseURL = '/api' 

//配置前后端数据交互的请求头：
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
//允许跨域携带session信息了
axios.defaults.withCredentials=true;
//Vue.prototype.HOST='/api'
Vue.use(ElementUI)
Vue.use(VueResource)

Vue.config.productionTip = false
//设置全局表单提交格式
Vue.http.options.emulateJOSN=true;
/* eslint-disable no-new */

//全局过滤器
Vue.filter('dateFmt', (input, formatString = "YYYY-MM-DD") => {
  //es5函数参数设置默认值
  //const lastFormatString = formatString || ''
   // moment(input) 把时间字符串转成时间对象
   // format(formatString) 把时间对象，按照指定格式，格式化成符合条件的字符串
  return moment(input).format(formatString)
})
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
router.beforeEach((to, from, next) => {
  if(to.meta.auth){
    var isLogin = sessionStorage.getItem('user');
    if(isLogin){
      next()
    }else{
      // next({
      //   path:'/TeacherLogin',
      //   query:{
      //     redirect:to.fullPath
      //   }
      // })
    }
  }else{
    next();
  }
})
