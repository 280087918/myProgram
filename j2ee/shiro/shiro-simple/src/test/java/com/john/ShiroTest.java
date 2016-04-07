package com.john;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhang.hc
 * @date 2016年4月7日 上午10:31:34
 */
public class ShiroTest {
	private static final transient Logger log = LoggerFactory.getLogger(ShiroTest.class);
	
	@Test
	public void test() {
		//利用工厂方法获取SecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:my-shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		
		SecurityUtils.setSecurityManager(securityManager);
		
		/**
		 * 获取认证授权的主体
		 * 这里的理解有一个舞曲，一开始我以为这个currentUser是相当于realm，其实只是单个用户的认证主体
		 */
		Subject currentUser = SecurityUtils.getSubject();
		
		//Shiro里面有一个Session可以使用，不依赖HttpSession
		Session session = currentUser.getSession();
		session.setAttribute("myKey", "123456");
		
		if("123456".equals(session.getAttribute("myKey"))) {
			log.info("获取session成功.");
		} else {
			log.info("获取session失败!");
		}
		
		if(!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken("zhanghc", "haocheng");
//			UsernamePasswordToken token = new UsernamePasswordToken("root", "123456");
			try {
				currentUser.login(token);//登录方法
			} catch (UnknownAccountException uae) {
                log.info("用户名无效: " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("账户密码不匹配 " + token.getPrincipal());
            } catch (LockedAccountException lae) {
                log.info("账户被锁定 " + token.getPrincipal());
            }
		}
		
		if(currentUser.isAuthenticated()) {//判断用户是否认证成功
			log.info("用户 [" + currentUser.getPrincipal() + "] 登录成功.");
		} else {
			log.info("用户 [" + currentUser.getPrincipal() + "] 登录失败!");
		}
		
		if (currentUser.hasRole("role1")) {//判断用户是否拥有role1的权限
            log.info("账户拥有权限:[role1]");
        } else {
            log.info("账户没有权限:[role1]");
        }
		
		if(currentUser.isPermitted("order:list:view")) {//判断用户是否拥有某一项操作
			log.info("账户允许操作[order:list:view]");
		} else {
			log.info("账户不允许操作[order:list:view]!");
		}
	}
}
