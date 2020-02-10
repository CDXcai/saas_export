package com.cdx.controller.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component // 添加到spring容器中
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("发生了异常");
        ModelAndView mv = new ModelAndView();
        if(ex instanceof UnauthorizedException){
            // 如果是权限不足，跳转到权限不足页面
            mv.setViewName("redirect:/unauthorized.jsp");
        }else {
            System.out.println("发生了异常");
            //配置了视图解析器 从pages下开始的
            mv.setViewName("error");
            mv.addObject("msg", "对不起,发送不可预知异常,请联系管理员");
        }
        return mv;
    }
}
