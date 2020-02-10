package com.cdx.controller.company;


import com.cdx.controller.Base.BaseController;
import com.cdx.domain.company.Company;
import com.cdx.service.company.CompanyService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller // 加入到spring容器中
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;
    /**
     * 企业查询
     */
    @RequestMapping(value = "/list",name = "企业查询")
    public String list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageIndex,
                       @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        // 获取分页大小信息
        Object Size = session.getAttribute("pageSize");
        // 如果有分页大小信息，赋值给pageSize
        if(Size != null){
            pageSize = (Integer) Size;
        }
        //分页查询企业信息
        PageInfo page = companyService.findByPage(pageIndex, pageSize);
        // 添加到请求域中
        request.setAttribute("page",page);
        return "company/company-list";
    }

    /**
     * 修改分页每页显示的数据
     * @param pageSize
     * @return
     */
    @RequestMapping("/setPageSize")
    @ResponseBody
    public String setPageSize(Integer pageSize){
        session.setAttribute("pageSize",pageSize);
        return "1";
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        // 跳转到添加页面
        return "company/company-add";
    }

    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        // 根据id查询企业信息
        Company company = companyService.findById(id);
        // 添加到请域中
        request.setAttribute("company",company);
        // 跳转到修改页面
        return "company/company-update";
    }

    /**
     * 添加或者修改企业信息
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Company company){
        if(StringUtils.isEmpty(company.getId())){
            // 添加
            companyService.save(company);
        }else {
            // 修改
            companyService.update(company);
        }
        // 重定向到企业列表页面
        return "redirect:/company/list.do";
    }


    /**
     * 删除企业信息
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        // 删除指定的企业
        companyService.delete(id);
        // 重定向到企业列表页面
        return "redirect:/company/list.do";
    }
}
