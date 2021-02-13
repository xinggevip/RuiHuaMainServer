package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysServersMapper;
import com.ruoyi.system.domain.SysServers;
import com.ruoyi.system.service.ISysServersService;

/**
 * 服务器Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-12
 */
@Service
public class SysServersServiceImpl implements ISysServersService 
{
    @Autowired
    private SysServersMapper sysServersMapper;

    /**
     * 查询服务器
     * 
     * @param serverId 服务器ID
     * @return 服务器
     */
    @Override
    public SysServers selectSysServersById(Long serverId)
    {
        return sysServersMapper.selectSysServersById(serverId);
    }

    /**
     * 查询服务器列表
     * 
     * @param sysServers 服务器
     * @return 服务器
     */
    @Override
    public List<SysServers> selectSysServersList(SysServers sysServers)
    {
        return sysServersMapper.selectSysServersList(sysServers);
    }

    /**
     * 新增服务器
     * 
     * @param sysServers 服务器
     * @return 结果
     */
    @Override
    public int insertSysServers(SysServers sysServers)
    {
        return sysServersMapper.insertSysServers(sysServers);
    }

    /**
     * 修改服务器
     * 
     * @param sysServers 服务器
     * @return 结果
     */
    @Override
    public int updateSysServers(SysServers sysServers)
    {
        return sysServersMapper.updateSysServers(sysServers);
    }

    /**
     * 批量删除服务器
     * 
     * @param serverIds 需要删除的服务器ID
     * @return 结果
     */
    @Override
    public int deleteSysServersByIds(Long[] serverIds)
    {
        return sysServersMapper.deleteSysServersByIds(serverIds);
    }

    /**
     * 删除服务器信息
     * 
     * @param serverId 服务器ID
     * @return 结果
     */
    @Override
    public int deleteSysServersById(Long serverId)
    {
        return sysServersMapper.deleteSysServersById(serverId);
    }
}
