package com.cdx.service.system;

import com.cdx.domain.system.Module;
import com.cdx.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {
    // 分页查询模块数据
    PageInfo findByPage(Integer pageIndex, Integer pageSize);

    // 查询所有模块信息
    List<Module> findAll();


    // 根据指定的Id查询模块信息
    Module findById(String id);

    // 保存模块数据
    void save(Module module);

    // 修改模块数据
    void update(Module module);

    // 删除模块数据
    void delete(String id);

    // 查询上级模块
    List<Module> findByParent(String ctype);

    // 查询拥有的模块信息
    String findByRid(String id);

    // 根据角色的权限信息
    void updateToRole(String rid, String moduleIds);

    // 查询用户的模块信息
    List<Module> finByUser(User user);
}
