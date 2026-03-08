import request from './request'

export const register = (data) => {
  return request({
    url: '/user/register',
    method: 'post',
    data: new URLSearchParams(data),
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  })
}

export const login = (data) => {
  return request({
    url: '/user/login',
    method: 'post',
    data: new URLSearchParams(data),
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  })
}

export const getUserInfo = () => {
  return request({ url: '/user/userInfo', method: 'get' })
}

export const updateUser = (data) => {
  return request({ url: '/user/update', method: 'put', data })
}

export const updateAvatar = (avatarUrl) => {
  return request({
    url: '/user/updateAvatar',
    method: 'patch',
    params: { avatarUrl }
  })
}

export const updatePwd = (data) => {
  return request({ url: '/user/updatePwd', method: 'patch', data })
}
