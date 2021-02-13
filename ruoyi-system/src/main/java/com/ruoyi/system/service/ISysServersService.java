package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysServers;

/**
 * 服务器Service接口
 * 
 * @author ruoyi
 * @date 2020-12-12
 */
public interface ISysServersService 
{
    /**
     * 查询服务器
     * 
     * @param serverId 服务器ID
     * @return 服务器
     */
    public SysServers selectSysServersById(Long serverId);

    /**
     * 查询服务器列表
     * 
     * @param sysServers 服务器
     * @return 服务器集合
     */
    public List<SysServers> selectSysServersList(SysServers sysServers);

    /**
     * 新增服务器
     * 
     * @param sysServers 服务器
     * @return 结果
     */
    public int insertSysServers(SysServers sysServers);

    /**
     * 修改服务器
     * 
     * @param sysServers 服务器
     * @return 结果
     */
    public int updateSysServers(SysServers sysServers);

    /**
     * 批量删除服务器
     * 
     * @param serverIds 需要删除的服务器ID
     * @return 结果
     */
    public int deleteSysServersByIds(Long[] serverIds);

    /**
     * 删除服务器信息
     * 
     * @param serverId 服务器ID
     * @return 结果
     */
    public int deleteSysServersById(Long serverId);
}
