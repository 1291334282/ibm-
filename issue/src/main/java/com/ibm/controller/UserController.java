package com.ibm.controller;

import org.apache.shiro.subject.Subject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.entity.User;
import com.ibm.entity.UserRole;
import com.ibm.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api(tags = "用户登陆注册接口")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/main")
	public User main() {
		return new User();
	}

	@ApiOperation("功能：登陆接口")
	@PostMapping("/login")
	public String login(@ApiParam("用户名") @RequestParam(value = "name", required = true) String name,
			@ApiParam("密码") @RequestParam(value = "password", required = true) String password, Model model) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		try {
			subject.login(token);
			model.addAttribute("name", name);
			return "登录成功";
		} catch (UnknownAccountException e) {
			return "用户名不存在";
		} catch (IncorrectCredentialsException e) {
			return "密码错误";
		}

	}

	@ApiOperation("功能：未授权的网页跳转")
	@GetMapping("/unauth")
	public String unauth() {
		return "未授权，无法访问！";
	}

	@ApiOperation("功能：退出登录")
	@GetMapping("/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "success";
	}

	@ApiOperation("功能：注册")
	@PostMapping("/register")
	public String register(@ApiParam("用户ID") @RequestParam(value = "userID", required = true) Integer userID,
			@ApiParam("用户名") @RequestParam(value = "name", required = true) String name,
			@ApiParam("邮箱") @RequestParam(value = "email", required = true) String email,
			@ApiParam("密码") @RequestParam(value = "password", required = true) String password) {
		if (userService.findByName(name)!=null) {
			return "用户名已存在";
		}
		User userRole=new UserRole();
		userRole.setUserID(userID);
		userRole.setRoleID(0);
		userRole.setName(name);
		userRole.setEmail(email);
		userRole.setPassword(password);
		userService.addUser(userRole);
		return "注册成功";
	}

//	@GetMapping("/se")
//	public UserRole se(String name) {
//		UserRole userRole=userService.findByName(name);
//		return userRole;
//	}

}
