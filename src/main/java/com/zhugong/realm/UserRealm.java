package com.zhugong.realm;

import com.zhugong.entity.Worker;
import com.zhugong.service.WorkerService;
import com.zhugong.utils.PasswordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {
    @Resource
    private WorkerService userService;

    @Resource
    private PasswordHelper passwordHelper;
    /**
     * 权限校验
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 身份校验
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //1.把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken userToken = (UsernamePasswordToken) authcToken;

        //2.从UsernamePasswordToken中获取username
        String username = userToken.getUsername();

        Worker user = userService.selectWorkerByName(username);
        if (user == null) {
            throw new UnknownAccountException(); //没有找到账号
        }

        //交给AuthenticationRealm不加密
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getName(), //用户名
                //passwordHelper.encryptPassword(user.getPassword()), //密码
                user.getPassword(),
                ByteSource.Util.bytes(user.getName()),
                getName() //realm name
        );
        //System.out.println("***********加密后的密码(1231234)："+passwordHelper.encryptPassword(user.getName(),"1231234"));
        return authenticationInfo;
    }
}
