package com.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.Entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	public Users findByEmail(String email);
}
