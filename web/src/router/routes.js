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
      },
      {
        path: 'allurls',
        name: 'AllUrls',
        component: () => import('pages/Logined/AllUrls.vue'),
      },
      {
        path: 'myfolder/:folder_id',
        name: 'MyFolder',
        component: () => import('pages/Logined/MyFolder.vue'),
      },
      {
        path: 'ourfolder/:folder_id',
        name: 'OurFolder',
        component: () => import('pages/Logined/OurFolder.vue')
      },
      {
        path: 'settings',
        name: 'Settings',
        component: () => import('pages/Logined/Settings.vue')
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
