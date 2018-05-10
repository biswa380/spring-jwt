package com.biswa.springjwt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biswa.springjwt.domains.User;
import com.biswa.springjwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Override
	public User findById(Long id) {
		User u = userRepository.findOne( id );
        return u;
	}

	@Override
	public User findByUsername(String username) {
		User u = userRepository.findByUsername( username );
        return u;
	}

	@Override
	public List<User> findAll() {
		List<User> result = userRepository.findAll();
        return result;
	}

}
