package com.cdx.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdx.dao.cargo.FactoryDao;
import com.cdx.domain.cargo.Factory;
import com.cdx.domain.cargo.FactoryExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    private FactoryDao factoryDao;
    @Override
    public void save(Factory factory) {

    }

    @Override
    public void update(Factory factory) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Factory findById(String id) {
        return null;
    }

    @Override
    public List<Factory> findAll(FactoryExample example) {
        return factoryDao.selectByExample(example);
    }
}
