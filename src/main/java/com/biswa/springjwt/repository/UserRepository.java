package com.biswa.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biswa.springjwt.domains.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	User findOne(Long id);

	List<User> findAll();

}
