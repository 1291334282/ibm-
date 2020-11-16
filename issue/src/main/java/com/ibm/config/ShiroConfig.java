package com.ibm.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean(name = "shiroFilterFactoryBean")
	public ShiroFilterFactoryBean getFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("/user/main","authc");
		map.put("/user/userManage", "roles[超级Admin]");
		map.put("/user/createIssue", "perms[普通员工]");
		map.put("/user/issueStatement", "perms[经理]");
		shiroFilterFactoryBean.setLoginUrl("/user/unauth");
		shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	/**
	 * 创建DefaultWebSecurity
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		return securityManager;
	}

	/**
	 * 创建Realm
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}

}
