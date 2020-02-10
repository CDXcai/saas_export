package com.cdx.controller.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.domain.company.Company;
import com.cdx.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 加入spring容器中
public class ApplyController {
    @Reference // 使用dubbo的company
    private CompanyService companyService;

    @RequestMapping("/apply")
    @ResponseBody
    public String apply(Company company) {
        try {
            // 保存数据
            companyService.save(company);
            return "1";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "2";
    }


}
