package com.cdx.service.system;

import com.cdx.domain.system.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    // 分页查询角色信息
    PageInfo findByPage(String companyId, Integer pageSize, Integer pageIndex);

    // 根据id查询角色信息
    Role findById(String id);

    // 保存角色信息
    void save(Role role);

    // 修改角色信息
    void update(Role role);

    // 删除角色信息
    void delete(String id);

    // 查询所有的权限信息
    List<Role> findAll(String companyId);


    // 查询用户拥有的角色信息
    List<Role> findByUser(String id);

}
