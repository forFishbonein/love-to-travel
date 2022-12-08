import request from '@/utils/system/request'

// 获取数据api
// export function getData(data: object) {
export function getAllUser() {
  return request({
    url: '/user',
    method: 'get',
    baseURL: 'http://localhost:8081',
    // data
  })
}

export function getByUserId(id: string) {
  return request({
    url: '/user'+id,
    method: 'get',
    baseURL: 'http://localhost:8081',
    // data
  })
}

// 新增
export function add(data: object) {
  return request({
    url: '/system/user/add',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

// 编辑
export function update(data: object) {
  return request({
    url: '/system/user/update',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

// 状态变更
export function updateStatus(data: object) {
  return request({
    url: '/system/user/updateStatus',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

// 删除
export function del(data: object) {
  return request({
    url: '/system/user/del',
    method: 'post',
    baseURL: '/mock',
    data
  })
}