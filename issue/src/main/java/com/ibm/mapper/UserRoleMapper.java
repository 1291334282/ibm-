package com.ibm.mapper;

import com.ibm.entity.User;
import com.ibm.entity.UserRole;

public interface UserRoleMapper {
	public UserRole findByName(String name);

	public void addUser(User user);

	public void updateUser(User user);
}
