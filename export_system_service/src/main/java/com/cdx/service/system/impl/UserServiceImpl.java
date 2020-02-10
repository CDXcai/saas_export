package com.cdx.service.system.impl;

import com.cdx.dao.system.UserDao;
import com.cdx.domain.system.User;
import com.cdx.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public PageInfo findByPage(String companyId, Integer pageSize, Integer pageNum) {
        // 分页
        PageHelper.startPage(pageNum,pageSize);
        // 查询用户数据
        List<User> list = userDao.findAll(companyId);
        // 返回分页数据
        return new PageInfo(list);
    }

    @Override
    public void save(User user) {
        // 随机生成id
        user.setId(UUID.randomUUID().toString());
        // 保存用户数据
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        // 修改用户数据
        userDao.update(user);
    }

    @Override
    public User findById(String id) {
        // 查找指定id的用户
        return userDao.findById(id);

    }

    @Override
    public void delete(String id) {
        // 删除用户数据
        userDao.delete(id);
    }

    @Override
    public void updateToRole(String userid, String roleIds) {

        // 删除之前用户拥有的所有角色
        userDao.deleteRoleAll(userid);
        // 修改用户的权限信息
        for (String roleId : roleIds.split(",")) {
            // 添加用户的角色信息
            userDao.saveRole(userid,roleId);
        }
    }

    @Override
    public User findByEmail(String email) {
        // 根据邮箱查找用户信息
        return userDao.findByEmail(email);
    }
}
