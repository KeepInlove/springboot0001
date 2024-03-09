import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { projectName } from '../../config/config.default'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: {
      title: '登录'
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue'),
    meta: {
      title: '注册'
    }
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue'),
    meta: {
      title: '页面失踪啦'
    }
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/front/Front'),
    children: [
      //前台路由
      {
        path: 'home',
        name: 'FrontHome',
        component: () => import('../views/front/Home.vue'),
        meta: {
          title: '首页'
        }
      },
      {
        path: 'detail',
        name: 'Detail',
        component: () => import('../views/front/Detail.vue'),
        meta: {
          title: '宠物详情'
        }
      },
      {
        path: 'password',
        name: 'Password',
        component: () => import('../views/front/Password.vue'),
        meta: {
          title: '修改密码'
        }
      },
      {
        path: 'person',
        name: 'Person',
        component: () => import('../views/front/Person.vue'),
        meta: {
          title: '个人信息'
        }
      },
      {
        path: 'goods',
        name: 'Goods',
        component: () => import('../views/front/Goods.vue'),
        meta: {
          title: '商品列表'
        }
      },
      {
        path: 'goodsDetail',
        name: 'GoodsDetail',
        component: () => import('../views/front/GoodsDetail.vue'),
        meta: {
          title: '商品信息'
        }
      },
      {
        path: 'cart',
        name: 'Cart',
        component: () => import('../views/front/Cart.vue'),
        meta: {
          title: '购物车列表'
        }
      },
      {
        path: 'address',
        name: 'Address',
        component: () => import('../views/front/Address.vue'),
        meta: {
          title: '地址管理'
        }
      },
      {
        path: 'myOrderInfo',
        name: 'MyOrderInfo',
        component: () => import('../views/front/MyOrderInfo.vue'),
        meta: {
          title: '地址管理'
        }
      },
      {
        path: 'pay',
        name: 'Pay',
        component: () => import('../views/front/Pay.vue'),
        meta: {
          title: '支付'
        }
      },
      {
        path: 'article',
        name: 'FrontArticle',
        component: () => import('../views/front/Article.vue'),
        meta: {
          title: '宠物资讯'
        }
      },
      {
        path: 'articleDetail',
        name: 'FrontArticleDetail',
        component: () => import('../views/front/ArticleDetail.vue'),
        meta: {
          title: '宠物资讯'
        }
      },
      {
        path: 'collect',
        name: 'Collect',
        component: () => import('../views/front/Collect.vue'),
        meta: {
          title: '宠物资讯'
        }
      },
      {
        path: 'orders',
        name: 'Orders',
        component: () => import('../views/front/Orders.vue'),
        meta: {
          title: '我的订单'
        }
      },
      //前台路由
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

// 重置路由
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    routes
  })
}

// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
  const storeMenus = localStorage.getItem('menus')
  if (storeMenus) {
    // 拼装动态路由
    const manageRoute = {
      path: '/',
      name: 'Manage',
      component: () => import('../views/Manage.vue'),
      redirect: '/home',
      children: [
        {
          path: 'person',
          name: '个人信息',
          component: () => import('../views/Person.vue'),
          meta: {
            title: '个人信息'
          }
        },
        {
          path: 'password',
          name: '修改密码',
          component: () => import('../views/Password.vue'),
          meta: {
            title: '修改密码'
          }
        },
      ]
    }
    const menus = JSON.parse(storeMenus)
    menus.forEach((item) => {
      if (item.path) {
        // 当且仅当path不为空的时候才去设置路由
        let itemMenu = { path: item.path.replace('/', ''), name: item.name, component: () => import('../views/' + item.pagePath + '.vue'), meta: {title: item.name} }
        manageRoute.children.push(itemMenu)
      } else if (item.children.length) {
        item.children.forEach((item) => {
          if (item.path) {
            let itemMenu = { path: item.path.replace('/', ''), name: item.name, component: () => import('../views/' + item.pagePath + '.vue'), meta: {title: item.name} }
            manageRoute.children.push(itemMenu)
          }
        })
      }
    })

    // 获取当前的路由对象名称数组
    const currentRouteNames = router.getRoutes().map((v) => v.name)
    if (!currentRouteNames.includes('Manage')) {
      // 动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }
  }
}

// 每次刷新页面都要重新设置路由，否则路由就会被重置
setRoutes()

router.beforeEach((to, from, next) => {
  NProgress.start() // 开启加载进度条
  localStorage.setItem('currentPathName', to.name) // 设置当前的路由名称
  store.commit('setPath')
  if (!to.matched.length) {
    const menus = JSON.parse(localStorage.getItem('menus'))
    const user = JSON.parse(localStorage.getItem('user'))
    //第一次打开系统或者登出以后menus和user都为空，则需要跳到登录
    if(menus==null && user==null){
      next("/login")
      return
    }
    //当登录过以后，有后台路由的角色menus不为空
    if (menus.length==0) {
      next('/front/home')
    } else {
      next('/404')
    }
  } else {
    next()
  }
})

router.afterEach((to) => {
  NProgress.done() // 关闭加载进度条
  document.title = to.meta.title ? `${to.meta.title} - ${projectName}` : projectName // 设置页面标题
})

export default router
