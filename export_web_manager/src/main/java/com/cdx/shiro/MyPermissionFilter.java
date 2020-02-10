package com.cdx.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyPermissionFilter extends AuthorizationFilter {
    /**
     *
     * @param request
     * @param response
     * @param mappedValue 获取所有配置过滤器中的内容
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 调用父类的方法
        Subject subject = getSubject(request, response);
        // 获取配置的filter参数，
        String[] perms = (String[]) mappedValue;
        // 如果有一个权限满足，则返回true
        if(perms != null) {
            for (String perm : perms) {
                // 满足条件
                if (subject.isPermitted(perm)) {
                    return true;
                }
            }
        }
        return false;
    }
}
