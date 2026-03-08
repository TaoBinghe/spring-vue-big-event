import request from './request'

export const getArticleList = (params) => {
  return request({ url: '/article', method: 'get', params })
}

export const getArticleDetail = (id) => {
  return request({ url: '/article/detail', method: 'get', params: { id } })
}

export const addArticle = (data) => {
  return request({ url: '/article', method: 'post', data })
}

export const updateArticle = (data) => {
  return request({ url: '/article', method: 'put', data })
}

export const deleteArticle = (id) => {
  return request({ url: '/article', method: 'delete', params: { id } })
}
