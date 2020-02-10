package com.cdx.service.system;

import com.cdx.domain.system.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService {
    // 保存日志信息
    void save(SysLog log);

    // 查询企业的操作日志
    PageInfo<SysLog> findAll(String companyId,Integer pageIndex,Integer pageSize);

}
