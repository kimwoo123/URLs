import { vuexStore } from 'src/store/index'

const routes = [
  {
    path: '/',
    component: () => import('layouts/StartLayout.vue'),
    meta: {auth: false},
    children: [
      {
        path: '',
        name: 'BeforeLogin',
        component: () => import('pages/NotLogined/Start.vue'),
      }
    ]
  },


  {
    path: '/:id',
    component: () => import('layouts/MainLayout.vue'),
    meta: {auth: true},
    children: [
      {
        path: '',
        name: 'Recommendation',
        component: () => import('src/pages/Logined/Recommendation.vue'),
        beforeEnter: (to, from, next) => {
          vuexStore.dispatch('urls/CLOSE_MEMO')
          next()
        },
      },
      {
        path: 'allurls',
        name: 'AllUrls',
        component: () => import('pages/Logined/AllUrls.vue'),
        beforeEnter: (to, from, next) => {
          vuexStore.dispatch('urls/CLOSE_MEMO')
          next()
        },
      },
      {
        path: 'myfolder/:folder_id',
        name: 'MyFolder',
        component: () => import('pages/Logined/MyFolder.vue'),
        beforeEnter: (to, from, next) => {
          vuexStore.dispatch('urls/CLOSE_MEMO')
          next()
        },
      },
      {
        path: 'ourfolder/:folder_id',
        name: 'OurFolder',
        component: () => import('pages/Logined/OurFolder.vue'),
        beforeEnter: (to, from, next) => {
          vuexStore.dispatch('urls/CLOSE_MEMO')
          next()
        },
      },
      {
        path: 'settings',
        name: 'Settings',
        component: () => import('pages/Logined/Settings.vue'),
        beforeEnter: (to, from, next) => {
          vuexStore.dispatch('urls/CLOSE_MEMO')
          next()
        },
      }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    meta: {auth: true},
    component: () => import('pages/Error404.vue')
  }
]

export default routes
