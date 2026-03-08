import request from './request'

export const getCategoryList = () => {
  return request({ url: '/category', method: 'get' })
}

export const getCategoryDetail = (id) => {
  return request({ url: '/category/detail', method: 'get', params: { id } })
}

export const addCategory = (data) => {
  return request({ url: '/category', method: 'post', data })
}

export const updateCategory = (data) => {
  return request({ url: '/category', method: 'put', data })
}

export const deleteCategory = (id) => {
  return request({ url: '/category', method: 'delete', params: { id } })
}
