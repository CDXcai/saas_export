package com.cdx.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    /**
     * 跳转到home页面
     * @return
     */
    @RequestMapping("/home")
    public String home(){
        return "home/home";
    }
}
