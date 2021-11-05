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
        component: () => import('pages/Logined/AllUrls.vue')
      },
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
