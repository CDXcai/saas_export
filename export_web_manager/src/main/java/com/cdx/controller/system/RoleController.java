package com.cdx.controller.system;

import com.cdx.controller.Base.BaseController;
import com.cdx.domain.system.Module;
import com.cdx.domain.system.Role;
import com.cdx.service.system.ModuleService;
import com.cdx.service.system.RoleService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    /**
     * 查询角色信息
     * @return
     */
    @RequestMapping(value = "/list",name = "查询角色信息")
    public String list(@RequestParam(value = "size",defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "page",defaultValue = "1") Integer pageIndex){
        // 分页查询角色信息
        PageInfo pageInfo = roleService.findByPage(companyId,pageSize,pageIndex);
        // 添加到请求域中
        request.setAttribute("page",pageInfo);
        return "system/role/role-list";
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd",name = "跳转到添加页面")
    public String toAdd(){
        return "system/role/role-add";
    }


    /**
     * 跳转到修改角色页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdate",name = "跳转到修改角色页面")
    public String toUpdate(String id){
        // 根据id查询角色信息
        Role role = roleService.findById(id);
        // 添加到请求域中
        request.setAttribute("role",role);
        return "system/role/role-update";
    }

    /**
     * 添加或者修改角色信息
     * @param role
     * @return
     */
    @RequestMapping(value = "/edit",name = "添加或者修改角色信息")
    public String edit(Role role){
        // 赋值企业数据
        role.setCompanyName(companyName);
        role.setCompanyId(companyId);
        if(StringUtils.isEmpty(role.getId())){
            // 添加角色信息
            roleService.save(role);
        }else {
            // 修改角色信息
            roleService.update(role);
        }
        // 重定向到列表页面
        return "redirect:/system/role/list.do";
    }

    /**
     * 删除指定id的角色信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",name = "删除指定id的角色信息")
    public String delete(String id){
        // 删除角色信息
        roleService.delete(id);
        // 重定向到列表页面
        return "redirect:/system/role/list.do";
    }


    /**
     * 跳转到分配权限页面
     * @param rid
     * @return
     */
    @RequestMapping(value = "/roleModule",name = "跳转到分配权限页面")
    public String roleModule(@RequestParam("roleid") String rid){
        // 查询指定的角色
        Role role = roleService.findById(rid);
        // 添加到请求域中
        request.setAttribute("role",role);
        return "system/role/role-module";
    }

    /**
     * 初始化权限分配
     * @param id
     * @return
     */
    @RequestMapping(value = "/initModuleData",name = "初始化权限分配")
    @ResponseBody
    public List initModuleData(String id){
        // 查询指定角色拥有权限信息
        String modules = moduleService.findByRid(id);
        String[] split = modules.split(",");

        // 查询所有的权限信息
        List<Module> moduleAll = moduleService.findAll();
        // 组建返回的数据
        List<Map<String,String>> initMap = new ArrayList<>();
        // 创建map数据，赋值给list列表
        Map<String,String> moduleMap = null;
        // 遍历组建数据
        for (Module module : moduleAll) {
            // 创建新的map
            moduleMap= new HashMap<>();
            // 添加数据
            moduleMap.put("id",module.getId());
            moduleMap.put("pId",module.getParentId());
            moduleMap.put("name",module.getName());
            // 判断角色是否拥有该权限
            for (String s : split) {
                if(s.equals(module.getId())){
                    // 如果有该权限，则默认选中
                    moduleMap.put("checked","true");
                    System.out.println(module.getName());
                    break;
                }
            }

            // 添加到list列表
            initMap.add(moduleMap);
        }
        // 返回数据
        return initMap;
    }

    /**
     * 修改模块
     * @param roleid
     * @param moduleIds
     * @return
     */
    @RequestMapping(value = "/updateRoleModule",name = "修改模块")
    public String updateRoleModule(String roleid,String moduleIds){
        // 更新权限信息
        moduleService.updateToRole(roleid,moduleIds);
        // 重定向到列表页面
        return "redirect:/system/role/list.do";
    }
}
