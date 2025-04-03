package com.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.Entities.Users;
import com.library.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Users saveUser(Users user) {
		return userRepository.save(user);
	}

	@Override
	public List<Users> findAllUsers() {
		return userRepository.findAll();
	}
	@Override
	public Optional<Users> findById(long id) {
	    return userRepository.findById(id);
	}


	@Override
	public void deleteUser(Users user) {
		userRepository.delete(user);

	}

	@Override
	public Users findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

}
