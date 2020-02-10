package com.cdx.controller.system;

import com.cdx.controller.Base.BaseController;
import com.cdx.domain.system.Module;
import com.cdx.service.system.ModuleService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/module") // 模块管理
public class ModelController extends BaseController {

    @Autowired
    private ModuleService moduleService;
    /**
     * 查询所有的模块信息
     * @return
     */
    @RequestMapping(value = "/list",name = "查询所有的模块信息")
    public String list(@RequestParam(value = "size",defaultValue = "5") Integer pageSize,@RequestParam(value = "page",defaultValue = "1") Integer pageIndex){
        // 分页查询模块数据
        PageInfo page = moduleService.findByPage(pageIndex,pageSize);
        // 添加到请求域中
        request.setAttribute("page",page);
        // 请求转发到模块列表页面
        return "system/module/module-list";
    }


    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd",name = "跳转到添加页面")
    public String toAdd(){

        return "system/module/module-add";
    }

    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate",name = "跳转到修改页面")
    public String toUpdate(String id){
        // 查询所有的模块信息
        List<Module> moduleList = moduleService.findAll();
        // 添加到请求域中
        request.setAttribute("menus",moduleList);
        // 查询指定id的模块信息
        Module module = moduleService.findById(id);
        // 添加到请求域中
        request.setAttribute("module",module);
        return "system/module/module-update";
    }

    /**
     * 添加或者修改模块数据
     * @return
     */
    @RequestMapping(value = "/edit",name = "添加或者修改模块数据")
    public String edit(Module module){
        if(StringUtils.isEmpty(module.getId())){
            // 添加数据
            moduleService.save(module);
        }else {
            // 修改数据
            moduleService.update(module);
        }
        // 重定向到查询列表页面
        return "redirect:/system/module/list.do";
    }

    /**
     * 删除模块数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",name = "删除模块数据")
    public String delete(String id){
        moduleService.delete(id);
        // 重定向到查询列表页面
        return "redirect:/system/module/list.do";
    }


    /**
     * ajax请求查询当前选中模块级别的上级模块
     * @return
     */
    @RequestMapping(value = "/parentModule",name = "ajax请求查询当前选中模块级别的上级模块")
    @ResponseBody // 返回json数据
    public List<Module> parentModule(String ctype){
        // 查询上级模块
        List<Module> modules = moduleService.findByParent(ctype);
        return modules;
    }
}
