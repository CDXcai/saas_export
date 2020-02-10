package com.cdx.service.system.impl;

import com.cdx.dao.system.ModuleDao;
import com.cdx.domain.system.Module;
import com.cdx.domain.system.ModuleExample;
import com.cdx.domain.system.User;
import com.cdx.service.system.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo findByPage(Integer pageIndex, Integer pageSize) {
        // 分页查询
        PageHelper.startPage(pageIndex,pageSize);
        // 查询所有的数据
        List<Module> moduleList = moduleDao.selectByExample(null);
        // 返回查询到的数据
        return new PageInfo(moduleList);
    }

    @Override
    public List<Module> findAll() {
        // 查询所有的模块信息
        return moduleDao.selectByExample(null);
    }

    @Override
    public Module findById(String id) {
        // 查询指定Id的模块信息
        return moduleDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Module module) {
        // 随机生成id属性
        module.setId(UUID.randomUUID().toString());
        // 保存模块数据
        moduleDao.insertSelective(module);
    }

    @Override
    public void update(Module module) {
        // 修改模块数据
        moduleDao.updateByPrimaryKeySelective(module);
    }

    @Override
    public void delete(String id) {
        // 删除模块数据
        moduleDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Module> findByParent(String ctype) {
        List<Module> moduleList = new ArrayList<>();
        if(ctype.equals("2")){// 如果是按钮吗，则返回二级菜单
            // 查询ctpye为1的数据
            ModuleExample moduleExample = new ModuleExample();
            ModuleExample.Criteria criteria = moduleExample.createCriteria();
            criteria.andCtypeEqualTo(1l);
            moduleList=  moduleDao.selectByExample(moduleExample);
        }else if (ctype.equals("1")){//如果是二级菜单，则返回主菜单
            // 查询ctpye为0的数据
            ModuleExample moduleExample = new ModuleExample();
            ModuleExample.Criteria criteria = moduleExample.createCriteria();
            criteria.andCtypeEqualTo(0l);
            moduleList =  moduleDao.selectByExample(moduleExample);
        }
        // 如果是主菜单，则返回空数据
        return moduleList;
    }

    @Override
    public String findByRid(String id) {
        // 声明返回的数据
        StringBuffer moduleIds = new StringBuffer();
        // 遍历查询到的数据，讲id拼接为一个字符串
        for (Module module : moduleDao.findByRid(id)) {
            moduleIds.append(module.getId()+",");
        }
        return moduleIds.toString();
    }

    @Override
    public void updateToRole(String rid, String moduleIds) {
        // 删除之前所有的权限信息
        moduleDao.deleteToRole(rid);
        // 遍历更新数据
        for (String moduleId : moduleIds.split(",")) {
            // 更新角色的权限信息
            moduleDao.insertToRole(rid,moduleId);
        }

    }

    @Override
    public List<Module> finByUser(User user) {
        List<Module> moduleList = null;
        // 判断用户的角色
        if(user.getDegree() == 0){
            // saas管理员
            moduleList = moduleDao.findByBelong("0");
        }else if(user.getDegree() == 1){
            // 企业管理员
            moduleList = moduleDao.findByBelong("1");
        }else {
            // 普通员工
            moduleList = moduleDao.findByUser(user.getId());
        }
        // 查询用户的模块信息
        return moduleList;
    }
}
