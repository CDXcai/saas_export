package com.cdx.shiro;

import com.cdx.domain.system.Module;
import com.cdx.domain.system.User;
import com.cdx.service.system.ModuleService;
import com.cdx.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义Realm域，需要继承AuthorizingRealm抽象类
 */
public class AutoRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    /**
     *  获得授权信息
     *  方法名称:  doGetAuthorizationInfo 执行获得授权的信息(哪些权限可以被放行)
     *  方法参数:  PrincipalCollection 重要数据的集合(最主要的就是user)
     *  方法的返回值:  授权数据信息 AuthorizationInfo
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 创建一个AuthorizationInfo对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取登录的用户
        User user = (User) principals.getPrimaryPrincipal();
        // 查询用户的模块信息
        List<Module> modules = moduleService.finByUser(user);
        // 创建一个set集合
        Set<String> moduleSet = new HashSet<>();
        for (Module module : modules) {
            moduleSet.add(module.getName());
        }
        // 添加给授权信息
        authorizationInfo.setStringPermissions(moduleSet);
        return authorizationInfo;
    }


    /**
     * 获得认证信息 当前方法的目的 其实就是登录
     * 看方法名称 doGetAuthenticationInfo 执行获得认证(安全)数据的方法
     * 看参数 :  AuthenticationToken 用户输入的用户名和密码
     * 看返回值 : AuthenticationInfo 返回数据库中的安全数据
     * 如果有数据 默认继续执行密码比较器
     * 如果没有数据 直接抛出异常 登录失败
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        // 获取用户名数据
        String inUserName = usernamePasswordToken.getUsername();
        // 查找数据库的数据
        User user = userService.findByEmail(inUserName);
        // 判断是否查找到用户
        if(user == null || usernamePasswordToken.getPassword() == null){
            // 返回null会抛出异常
            return null;
        }else {
            // 返回安全数据
            //@param principal   数据库的用户数据.
            //@param credentials 数据库中的密码
            //@param realmName   唯一标示，唯一即可.
            return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
            // 自动调用密码比较器
        }

    }
}
