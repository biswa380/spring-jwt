package com.biswa.springjwt.services;

import java.util.List;

import com.biswa.springjwt.domains.User;

public interface UserService {

	User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
}
