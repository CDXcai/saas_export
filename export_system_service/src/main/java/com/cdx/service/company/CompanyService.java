package com.cdx.service.company;

import com.cdx.domain.company.Company;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CompanyService {
    // 查询所有的企业信息
    List<Company> findAll();
    // 分页查询企业信息
    PageInfo findByPage(Integer pageSize, Integer pageIndex);
    // 添加企业信息
    void save(Company company);

    // 修改企业信息
    void update(Company company);

    // 根据企业id查询企业信息
    Company findById(String id);

    // 删除企业信息
    void delete(String id);
}
