<template>
  <el-container class="layout">
    <el-aside width="200px" class="aside">
      <div class="logo">大事件</div>
      <el-menu
        :default-active="$route.path"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/article">文章管理</el-menu-item>
        <el-menu-item index="/category">分类管理</el-menu-item>
        <el-menu-item index="/user/profile">用户中心</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span class="title">{{ userStore.userInfo?.nickname || userStore.userInfo?.username || '用户' }}</span>
        <el-button type="danger" link @click="userStore.logout">退出</el-button>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { onMounted } from 'vue'
import { useUserStore } from '../stores/user'

const userStore = useUserStore()

onMounted(async () => {
  if (!userStore.userInfo && userStore.token) {
    await userStore.fetchUserInfo()
  }
})
</script>

<style scoped>
.layout { height: 100%; }
.aside {
  background-color: #304156;
  height: 100vh;
}
.logo {
  height: 50px;
  line-height: 50px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
}
.header {
  background: #fff;
  border-bottom: 1px solid #eee;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 20px;
}
.title { margin-right: 16px; }
.main {
  background: #f0f2f5;
  padding: 20px;
  overflow: auto;
}
</style>
