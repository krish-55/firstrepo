package com.library.service;

import java.util.List;
import java.util.Optional;

import com.library.Entities.Users;

public interface UserService {

	public Users saveUser(Users user);
	public List<Users> findAllUsers();
	public Optional<Users> findById(long id);
	public void deleteUser(Users user);
	public Users findByEmail(String email);
}
