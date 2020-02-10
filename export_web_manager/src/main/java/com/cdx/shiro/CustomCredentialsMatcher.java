package com.cdx.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义密码比较器
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * 密码比较方法
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取用户输入的用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 获取输入的用户名
        String inUserName = upToken.getUsername();
        // 获取输入的密码
        String inPassWord = new String(upToken.getPassword());
        // 获取数据库的密码, 凭证
        String datePassWord = (String) info.getCredentials();
        // 返回比较结果
        return inPassWord.equals(datePassWord);
    }
}
