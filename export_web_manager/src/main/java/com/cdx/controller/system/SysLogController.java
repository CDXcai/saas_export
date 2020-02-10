package com.cdx.controller.system;

import com.cdx.controller.Base.BaseController;
import com.cdx.domain.system.SysLog;
import com.cdx.service.system.SysLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page",defaultValue = "1") Integer pageIndex,
                       @RequestParam(value = "size",defaultValue = "5") Integer pageSize){
        // 查询日志信息
        PageInfo<SysLog> page =  sysLogService.findAll(companyId,pageIndex,pageSize);
        // 添加到请求域中
        request.setAttribute("page",page);
        // 转发到列表页面
        return "system/log/log-list";
    }
}
