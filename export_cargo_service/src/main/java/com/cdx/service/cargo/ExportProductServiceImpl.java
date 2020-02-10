package com.cdx.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdx.dao.cargo.ExportProductDao;
import com.cdx.domain.cargo.ExportProduct;
import com.cdx.domain.cargo.ExportProductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class ExportProductServiceImpl implements ExportProductService {
    @Autowired
    private ExportProductDao exportProductDao;
    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        return exportProductDao.selectByExample(example);
    }
}
