import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfo as getUserIdApi, login as loginApi } from '../api/user'
import router from '../router'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)

  const setToken = (t) => {
    token.value = t
    if (t) localStorage.setItem('token', t)
    else localStorage.removeItem('token')
  }

  const setUserInfo = (info) => {
    userInfo.value = info
  }

  const login = async (data) => {
    const res = await loginApi(data)
    setToken(res.data)
    await fetchUserInfo()
  }

  const fetchUserInfo = async () => {
    const res = await getUserIdApi()
    setUserInfo(res.data)
    return res.data
  }

  const logout = () => {
    setToken('')
    setUserInfo(null)
    router.push('/login')
  }

  return { token, userInfo, setToken, setUserInfo, login, fetchUserInfo, logout }
})
