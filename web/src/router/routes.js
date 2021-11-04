const routes = [
  {
    path: '/',
    component: () => import('layouts/StartLayout.vue'),
    meta: {authRequired: false},
    children: [
      { path: '', component: () => import('pages/NotLogined/Start.vue') }
    ]
  },


  {
    path: '/user',
    component: () => import('layouts/MainLayout.vue'),
    meta: {authRequired: true},
    children: [
      {
        path: '',
        component: () => import('src/pages/Logined/Recommendation.vue'),
      },
      {
        path: 'allurls',
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
