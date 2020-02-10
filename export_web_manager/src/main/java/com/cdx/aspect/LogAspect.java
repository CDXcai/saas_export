package com.cdx.aspect;

import com.cdx.controller.Base.BaseController;
import com.cdx.domain.system.SysLog;
import com.cdx.domain.system.User;
import com.cdx.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Aspect声明切面类
 */
@Component
@Aspect
public class LogAspect extends BaseController {

    @Autowired
    private SysLogService sysLogService;
    // 对所有的控制器添加环绕通知方法
    @Around(value = "execution(* com.cdx.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕方法执行");
        // 创建log对象
        SysLog log = new SysLog();
        // 获取登录的user用户
        User user = (User) session.getAttribute("loginInfo");

        {// 获取执行的方法信息

            //方法标记对象
            MethodSignature ms = (MethodSignature)pjp.getSignature();
            // 获取方法
            Method method = ms.getMethod();
            // 获取方法名
            String methodName = method.getName();
            // 方法名设置到log对象中
            log.setMethod(methodName);

            // 获取RequestMapping注解的name属性的名称
            // 判断方法是否有RequestMapping注解
            if(method.isAnnotationPresent(RequestMapping.class)){
                // 获取注解
                RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                // 获取name属性的值,赋值给Log对象
                log.setAction(mapping.name());
            }
        }
        {// 赋值用户数据
            // 赋值用户名
            log.setUserName(user.getUserName());
            // 赋值访问的ip
            log.setIp(request.getRemoteAddr());
            // 赋值操作时间
            log.setTime(new Date());
            // 赋值企业名称
            log.setCompanyId(companyId);
            log.setCompanyName(companyName);

        }
        // 保存log信息
        sysLogService.save(log);
        // 返回方法原本的返回值
        return pjp.proceed();
    }
}
