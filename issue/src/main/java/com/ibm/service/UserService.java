package com.ibm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.entity.User;
import com.ibm.entity.UserRole;
import com.ibm.mapper.UserRoleMapper;

@Service
public class UserService {
	@Autowired
	UserRoleMapper userMapper;

	public UserRole findByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.findByName(name);
	}

	public void addUser(User user) {
		userMapper.addUser(user);
	}

	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

}
