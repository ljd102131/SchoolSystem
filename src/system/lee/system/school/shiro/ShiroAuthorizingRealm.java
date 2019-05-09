package lee.system.school.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import lee.system.school.entity.User;
import lee.system.school.service.impl.UserService;

public class ShiroAuthorizingRealm extends AuthorizingRealm {

    private static final Logger logger = Logger.getLogger(ShiroAuthorizingRealm.class);
    //注入用户管理对象
    @Autowired
    private UserService userService;
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken uPasswordToken) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) uPasswordToken;
        String loginName = upToken.getUsername();
        String passWord = String.valueOf(upToken.getPassword());
        User user = null;
        try {
            user = userService.findUserByLoginName(loginName);
        } catch(Exception ex) {
            logger.warn("获取用户失败\n" + ex.getMessage());
        }
        if (user == null) {
            logger.warn("用户不存在");
            throw new UnknownAccountException("用户不存在");
        }
        else if (!passWord.equals(user.getPassWord())) {
        	 logger.warn("密码错误");
             throw new UnknownAccountException("密码错误");
		}
        logger.info("用户【" + loginName + "】登录成功");
        
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassWord(), user.getUserName());
        Subject subject1 = SecurityUtils.getSubject();
        if (null != subject1) {
            Session session = subject1.getSession();
            if (null != session) {
                session.setAttribute("currentUser", user);
            }
        }
        return authcInfo;
	}

   
}