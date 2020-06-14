package com.izy.springshiro01.shiro;

import com.izy.springshiro01.pojo.User;
import com.izy.springshiro01.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zouyu
 * @description 自定义shiro realm
 * @date 2020/5/28
 */
public class ShiroRealm extends AuthorizingRealm {
@Autowired
    UserService userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        /* User user =  (User)principalCollection.getPrimaryPrincipal();*/
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        authorizationInfo.addStringPermission(user.getPerms());
        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证");
        String userName = (String) authenticationToken.getPrincipal();
        UsernamePasswordToken token =  (UsernamePasswordToken)authenticationToken;
        User user = userService.findUser(token.getUsername());
        if(null== user){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),  getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
        return simpleAuthenticationInfo;
    }
    public  static void main (String[] args){
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "3";//密码原值
        Object salt = "sx";//盐值
        int hashIterations = 2;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        System.out.println(result);
    }
}
