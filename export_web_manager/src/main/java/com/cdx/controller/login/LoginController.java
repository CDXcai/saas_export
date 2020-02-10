package com.cdx.controller.login;


import com.cdx.common.utils.Encrypt;
import com.cdx.controller.Base.BaseController;
import com.cdx.domain.system.Module;
import com.cdx.domain.system.User;
import com.cdx.service.system.ModuleService;
import com.cdx.service.system.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;

//    @RequestMapping(value = "/login",name = "登录验证")
//    public String login(String password,String email){
//        // 判断输入的用户名和密码是否为空
//        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(email)){
//            // 如果未空，重定向到登录页面
//            return "redirect:/login.jsp";
//        }
//        // 查找指定邮箱的用户
//        User user = userService.findByEmail(email);
//        // 密码进行加密
//        password = Encrypt.md5(password,email);
//        // 判断用户名或者密码是否正确
//        if(user == null || !password.equals(user.getPassword())){
//            // 为找到用户名或者密码不正确
//            request.setAttribute("error","用户名或者密码错误");
//            // 请求转发到登录页面
//            return "forward:/login.jsp";
//        }
//        // 如果登录成功，则跳转到主页
//        // 讲登录信息添加到session中
//        session.setAttribute("loginInfo",user);
//        // 查询用户的模块信息
//        List<Module> moduleList = moduleService.finByUser(user);
//        // 添加到请求域中
//        request.setAttribute("moduleList",moduleList);
//        return "home/main";
//    }

    /**
     * 登录验证
     */
    @RequestMapping(value = "/login", name = "登录验证")
    public String login(String password, String email) {

        // 判断输入的用户名和密码是否为空
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(email)) {
            // 如果未空，重定向到登录页面
            return "redirect:/login.jsp";
        }
        try {
            // 密码进行加密
            password = Encrypt.md5(password,email);
            // 使用shiro提供的静态工具方法获取subject工具类
            Subject subject = SecurityUtils.getSubject();
            // 调用subject的login方法进行登录验证
            // 创建token对象记录邮箱和密码
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email,password);
            // 调用login方法记性登录验证
            subject.login(usernamePasswordToken);

            // ****************************************************************
            // 如果没有发生异常，则表示登录成功 ,获取登录成功后的用户
            User user = (User) subject.getPrincipal();
            // 把用户保存到session中
            session.setAttribute("loginInfo",user);
            // 查询用户的模块信息
            List<Module> moduleList = moduleService.finByUser(user);
            // 添加到请求域中
            request.setAttribute("moduleList",moduleList);
            return "home/main";
        }catch (Exception e){
            // 发生异常，表示登录失败，请求转发到登录页面
            e.printStackTrace();
            // 添加异常提醒
            request.setAttribute("error","用户名或者密码错误");
            return "forward:/login.jsp";
        }
    }

    @RequestMapping(value = "/logout", name = "退出登录")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();// 退出登录
        // 重定向到登录页面
        return "redirect:/login.jsp";
    }
}
