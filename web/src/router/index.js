import { route, store } from 'quasar/wrappers'
import { createRouter, createMemoryHistory, createWebHistory, createWebHashHistory } from 'vue-router'
import routes from './routes'

export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory)

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(process.env.MODE === 'ssr' ? void 0 : process.env.VUE_ROUTER_BASE)
  })

  // 전역가드
  Router.beforeEach(function (to, from, next) {
    if (to.meta.auth && !store.getters.islogin) {
      console.log('store 동작?', store.getters.islogin)
      alert('로그인이 필요합니다.')
      return;
    }
    next()
  })

  return Router
})
