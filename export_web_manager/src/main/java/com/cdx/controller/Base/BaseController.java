package com.cdx.controller.Base;

import com.cdx.domain.system.User;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;

    protected String companyName = "传傻播客";
    protected String companyId = "1";
    @ModelAttribute
    public void before(){
        // 获取session中的user对象
        User user = (User) session.getAttribute("loginInfo");
        if(user != null) {
            // 修改默认的值
            companyId = user.getCompanyId();
            companyName = user.getCompanyName();
        }
    }
}
