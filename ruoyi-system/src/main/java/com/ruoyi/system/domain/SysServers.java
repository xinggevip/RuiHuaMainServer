package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务器对象 sys_servers
 * 
 * @author ruoyi
 * @date 2020-12-12
 */
public class SysServers extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务器id */
    private Long serverId;

    /** 服务器名字 */
    @Excel(name = "服务器名字")
    private String serverName;

    /** 服务器ip */
    @Excel(name = "服务器ip")
    private String serverIp;

    /** 服务器cpuid */
    @Excel(name = "服务器cpuid")
    private String serverCpuid;

    /** 服务器本地文件路径 */
    @Excel(name = "服务器本地文件路径")
    private String serverLocalPath;

    /** 服务器上传文件路径 */
    @Excel(name = "服务器上传文件路径")
    private String serverBackupPath;

    /** 是否开启备份 */
    @Excel(name = "是否开启备份")
    private Integer serverOpenBackup;

    public void setServerId(Long serverId) 
    {
        this.serverId = serverId;
    }

    public Long getServerId() 
    {
        return serverId;
    }
    public void setServerName(String serverName) 
    {
        this.serverName = serverName;
    }

    public String getServerName() 
    {
        return serverName;
    }
    public void setServerIp(String serverIp) 
    {
        this.serverIp = serverIp;
    }

    public String getServerIp() 
    {
        return serverIp;
    }
    public void setServerCpuid(String serverCpuid) 
    {
        this.serverCpuid = serverCpuid;
    }

    public String getServerCpuid() 
    {
        return serverCpuid;
    }
    public void setServerLocalPath(String serverLocalPath) 
    {
        this.serverLocalPath = serverLocalPath;
    }

    public String getServerLocalPath() 
    {
        return serverLocalPath;
    }
    public void setServerBackupPath(String serverBackupPath) 
    {
        this.serverBackupPath = serverBackupPath;
    }

    public String getServerBackupPath() 
    {
        return serverBackupPath;
    }
    public void setServerOpenBackup(Integer serverOpenBackup) 
    {
        this.serverOpenBackup = serverOpenBackup;
    }

    public Integer getServerOpenBackup() 
    {
        return serverOpenBackup;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("serverId", getServerId())
            .append("serverName", getServerName())
            .append("serverIp", getServerIp())
            .append("serverCpuid", getServerCpuid())
            .append("serverLocalPath", getServerLocalPath())
            .append("serverBackupPath", getServerBackupPath())
            .append("serverOpenBackup", getServerOpenBackup())
            .toString();
    }
}
