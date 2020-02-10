package com.cdx.service.system.impl;

import com.cdx.dao.system.DeptDao;
import com.cdx.domain.system.Dept;
import com.cdx.service.system.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    @Override
    public PageInfo findByCompany(String companyId,Integer pageNum,Integer pageSize) {
        // 分页
        PageHelper.startPage(pageNum,pageSize);
        // 查询所有的模块信息
        List<Dept> deptList = deptDao.findAll(companyId);
        // 返回分页数据
        return new PageInfo(deptList);
    }

    @Override
    public void save(Dept dept) {
        // 生成随机id属性
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }

    @Override
    public void update(Dept dept) {
        // 修改部门信息
        deptDao.update(dept);
    }

    @Override
    public List<Dept> findAll(String companyId) {
        // 查询所有的部门信息
        return deptDao.findAll(companyId);
    }

    @Override
    public Dept findById(String id) {
        // 查找指定id的部门信息
        return deptDao.findById(id);
    }

    @Override
    public void delete(String id) {
        // 删除部门信息
        deptDao.delete(id);
    }
}
