package com.cdx.service.company.impl;

import com.cdx.dao.company.CompanyDao;
import com.cdx.domain.company.Company;
import com.cdx.service.company.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service // 加入到spring容器中
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Override
    public List<Company> findAll() {
        return companyDao.selectByExample(null);
    }

    @Override
    public PageInfo findByPage(Integer pageIndex, Integer pageSize) {
        // 分页查询
        PageHelper.startPage(pageIndex,pageSize);
        // 查询企业信息
        List<Company> list = companyDao.selectByExample(null);
        // 返回分页后的数据
        return new PageInfo(list);
    }

    @Override
    public void save(Company company) {
        // 随机生成id属性
        company.setId(UUID.randomUUID().toString());
        // 添加企业信息
        companyDao.insert(company);
    }

    @Override
    public void update(Company company) {
        // 修改企业信息
        companyDao.updateByPrimaryKeySelective(company);
    }

    @Override
    public Company findById(String id) {
        return companyDao.selectByPrimaryKey(id);
    }

    @Override
    public void delete(String id) {
        // 删除企业信息
        companyDao.deleteByPrimaryKey(id);
    }


}
