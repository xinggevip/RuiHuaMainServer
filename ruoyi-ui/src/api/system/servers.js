import request from '@/utils/request'

// 查询服务器列表
export function listServers(query) {
  return request({
    url: '/system/servers/list',
    method: 'get',
    params: query
  })
}

// 查询服务器详细
export function getServers(serverId) {
  return request({
    url: '/system/servers/' + serverId,
    method: 'get'
  })
}

// 新增服务器
export function addServers(data) {
  return request({
    url: '/system/servers',
    method: 'post',
    data: data
  })
}

// 修改服务器
export function updateServers(data) {
  return request({
    url: '/system/servers',
    method: 'put',
    data: data
  })
}

// 删除服务器
export function delServers(serverId) {
  return request({
    url: '/system/servers/' + serverId,
    method: 'delete'
  })
}

// 导出服务器
export function exportServers(query) {
  return request({
    url: '/system/servers/export',
    method: 'get',
    params: query
  })
}