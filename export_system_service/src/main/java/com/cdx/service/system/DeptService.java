package com.cdx.service.system;

import com.cdx.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {
    // 查询所有的部门信息
    PageInfo findByCompany(String companyId,Integer pageNum,Integer pageSize);

    // 添加部门信息
    void save(Dept dept);

    // 修改部门信息
    void update(Dept dept);

    // 查询所有的部门信息
    List<Dept> findAll(String companyId);

    // 根据id查询部门信息
    Dept findById(String id);

    // 删除部门信息
    void delete(String id);
}
