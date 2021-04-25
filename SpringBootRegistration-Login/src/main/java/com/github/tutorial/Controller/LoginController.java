package com.github.tutorial.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
	
	@GetMapping("/home")
	public String home() {
		
		return "home";
	}
	
	@GetMapping("/login")
	public String Login() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String Logout() {
		return "logout";
	}
}

