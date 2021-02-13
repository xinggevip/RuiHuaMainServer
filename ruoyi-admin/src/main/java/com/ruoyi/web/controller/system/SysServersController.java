package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysServers;
import com.ruoyi.system.service.ISysServersService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务器Controller
 * 
 * @author ruoyi
 * @date 2020-12-12
 */
@RestController
@RequestMapping("/system/servers")
public class SysServersController extends BaseController
{
    @Autowired
    private ISysServersService sysServersService;

    /**
     * 查询服务器列表
     */
    @PreAuthorize("@ss.hasPermi('system:servers:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysServers sysServers)
    {
        startPage();
        List<SysServers> list = sysServersService.selectSysServersList(sysServers);
        return getDataTable(list);
    }

    /**
     * 导出服务器列表
     */
    @PreAuthorize("@ss.hasPermi('system:servers:export')")
    @Log(title = "服务器", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysServers sysServers)
    {
        List<SysServers> list = sysServersService.selectSysServersList(sysServers);
        ExcelUtil<SysServers> util = new ExcelUtil<SysServers>(SysServers.class);
        return util.exportExcel(list, "servers");
    }

    /**
     * 获取服务器详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:servers:query')")
    @GetMapping(value = "/{serverId}")
    public AjaxResult getInfo(@PathVariable("serverId") Long serverId)
    {
        return AjaxResult.success(sysServersService.selectSysServersById(serverId));
    }

    /**
     * 新增服务器
     */
    @PreAuthorize("@ss.hasPermi('system:servers:add')")
    @Log(title = "服务器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysServers sysServers)
    {
        return toAjax(sysServersService.insertSysServers(sysServers));
    }

    /**
     * 修改服务器
     */
    @PreAuthorize("@ss.hasPermi('system:servers:edit')")
    @Log(title = "服务器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysServers sysServers)
    {
        return toAjax(sysServersService.updateSysServers(sysServers));
    }

    /**
     * 删除服务器
     */
    @PreAuthorize("@ss.hasPermi('system:servers:remove')")
    @Log(title = "服务器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{serverIds}")
    public AjaxResult remove(@PathVariable Long[] serverIds)
    {
        return toAjax(sysServersService.deleteSysServersByIds(serverIds));
    }
}
