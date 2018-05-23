package com.biswa.springjwt;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.biswa.springjwt.domains.Authority;
import com.biswa.springjwt.domains.User;
import com.biswa.springjwt.repository.UserRepository;


@SpringBootApplication
public class SpringJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}
	
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userrepo) throws Exception {
		if (userrepo.count()==0)
			userrepo.save(new User("skaterik", "pass", Arrays.asList(new Authority("USER"), new Authority("ACTUATOR"))));
	}
}
