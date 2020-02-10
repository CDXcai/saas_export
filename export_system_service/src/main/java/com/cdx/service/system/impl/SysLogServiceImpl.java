package com.cdx.service.system.impl;

import com.cdx.dao.system.SysLogDao;
import com.cdx.domain.system.SysLog;
import com.cdx.service.system.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;
    @Override
    public void save(SysLog log) {
        // 生成随机id
        log.setIp(UUID.randomUUID().toString());
        // 保存log信息
        sysLogDao.save(log);
    }

    @Override
    public PageInfo<SysLog> findAll(String companyId,Integer pageIndex,Integer pageSize) {
        // 分页
        PageHelper.startPage(pageIndex,pageSize);
        // 查询所有数据
        List<SysLog> list = sysLogDao.findAll(companyId);
        // 返回数据
        return new PageInfo<>(list);

    }
}
