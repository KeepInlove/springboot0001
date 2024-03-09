import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './style/element/element-variables.scss' // Element 样式覆盖
import './style/index.scss'
import request from '@/utils/request'

//引入富文本
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor);
//引入饿了么UI
Vue.use(ElementUI, { size: 'mini' })
//引入剪切板功能
import VueClipboard from 'vue-clipboard2'
Vue.use(VueClipboard)

Vue.config.productionTip = false
Vue.prototype.request = request

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
