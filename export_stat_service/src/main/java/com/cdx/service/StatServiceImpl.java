package com.cdx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdx.dao.stat.StatDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;
    @Override
    public List<Map> findFactoryData(String companyId) {
        return statDao.findFactoryData(companyId);
    }

    @Override
    public List<Map> findSellData(String companyId) {
        return statDao.findSellData(companyId);
    }

    @Override
    public List<Map> findOnlineData(String companyId) {
        return statDao.findOnlineData(companyId);
    }
}
