package com.cdx.service.system;

import com.cdx.domain.system.User;
import com.github.pagehelper.PageInfo;

public interface UserService {
    // 分页查询用户数据
    PageInfo findByPage(String companyId, Integer pageSize, Integer pageNum);

    // 保存用户信息
    void save(User user);

    // 更新用户数据
    void update(User user);

    // 查找指定的id数据
    User findById(String id);

    // 删除指定的用户数据
    void delete(String id);

    // 修改用户的角色信息
    void updateToRole(String userid, String roleIds);


    // 根据邮箱查询用户信息
    User findByEmail(String email);
}
