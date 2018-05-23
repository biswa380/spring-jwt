package com.biswa.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles all request
 * @author subrata
 *
 */
@Controller
public class HomeController {
	
	
	@RequestMapping("/")
	public String getVillain(){
		return "";
	}
	
}