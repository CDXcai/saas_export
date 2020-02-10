package com.cdx.controller.system;

import com.cdx.controller.Base.BaseController;
import com.cdx.domain.system.Dept;
import com.cdx.service.system.DeptService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {
    @Autowired
    private DeptService deptService;
    /**
     * 部门查询
     * @return
     */
    @RequestMapping(value = "/list",name = "部门查询")
    public String list(@RequestParam(value = "size",defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "page",defaultValue = "1") Integer pageNum){
        // 查询所有的部门
        PageInfo page =  deptService.findByCompany(companyId,pageNum,pageSize);

        // 添加到请求域中
        request.setAttribute("page",page);
        return "system/dept/dept-list";
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd",name = "跳转到添加页面")
    public String toAdd(){
        // 查询所有的部门信息
        List<Dept> deptList = deptService.findAll(companyId);
        // 添加到请求域中
        request.setAttribute("deptList",deptList);
        return "system/dept/dept-add";
    }


    /**
     * 添加或者修改
     * @return
     */
    @RequestMapping(value = "/edit",name = "添加或者修改")
    public String edit(Dept dept){
        // 赋值企业信息
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        // 修改或者添加部门信息
        if(StringUtils.isEmpty(dept.getId())){
            // 添加部门信息
            deptService.save(dept);
        }else {
            deptService.update(dept);
        }
        // 重定向到列表页面
        return "redirect:/system/dept/list.do";
    }

    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate",name = "跳转到修改页面")
    public String toUpdate(String id){
        // 查找指定id的部门信息
        Dept dept = deptService.findById(id);
        // 添加到请求域中
        request.setAttribute("dept",dept);
        // 查询所有的部门信息
        List<Dept> deptList = deptService.findAll(companyId);
        // 添加到请求域中
        request.setAttribute("deptList",deptList);
        return "system/dept/dept-update";
    }

    /**
     * 删除企业信息
     * @return
     */
    @RequestMapping(value = "/delete",name = "删除企业信息")
    public String delete(String id){
        // 删除指定id的部门信息
        deptService.delete(id);
        // 重定向到列表页面
        return "redirect:/system/dept/list.do";
    }
}
