package com.cdx.service.system.impl;

import com.cdx.dao.system.RoleDao;
import com.cdx.domain.system.Role;
import com.cdx.service.system.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo findByPage(String companyId, Integer pageSize, Integer pageIndex) {
        // 分页
        PageHelper.startPage(pageIndex, pageSize);
        // 查询数据
        List<Role> list = roleDao.findAll(companyId);
        // 返回数据
        return new PageInfo(list);
    }

    @Override
    public Role findById(String id) {
        // 查询指定id的角色信息
        return roleDao.findById(id);
    }

    @Override
    public void save(Role role) {
        // 随机生成Id属性
        role.setId(UUID.randomUUID().toString());
        // 保存角色信息
        roleDao.save(role);
    }

    @Override
    public void update(Role role) {
        // 修改角色信息
        roleDao.update(role);
    }

    @Override
    public void delete(String id) {
        // 删除角色信息
        roleDao.delete(id);
    }

    @Override
    public List<Role> findAll(String companyId) {
        // 查询所有的权限信息
        return roleDao.findAll(companyId);
    }

    @Override
    public List<Role> findByUser(String id) {
        // 查询用户拥有的角色信息
        return roleDao.findByUserId(id);
    }


}
