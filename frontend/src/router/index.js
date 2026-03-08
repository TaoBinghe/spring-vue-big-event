import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue'), meta: { public: true } },
  { path: '/register', name: 'Register', component: () => import('../views/Register.vue'), meta: { public: true } },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/article',
    children: [
      { path: 'article', name: 'ArticleList', component: () => import('../views/article/ArticleList.vue') },
      { path: 'article/edit', name: 'ArticleEdit', component: () => import('../views/article/ArticleEdit.vue') },
      { path: 'article/edit/:id', name: 'ArticleEditId', component: () => import('../views/article/ArticleEdit.vue') },
      { path: 'article/detail/:id', name: 'ArticleDetail', component: () => import('../views/article/ArticleDetail.vue') },
      { path: 'category', name: 'Category', component: () => import('../views/category/Category.vue') },
      { path: 'user/profile', name: 'Profile', component: () => import('../views/user/Profile.vue') }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  if (!to.meta.public && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
