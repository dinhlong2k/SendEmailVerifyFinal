package com.github.tutorial.Security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.tutorial.Entities.User;
import com.github.tutorial.Repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepo.findByEmail(username);
		if(user==null) throw new UsernameNotFoundException("User 404");
		
		return new UserPrincipal(user); //tạo mới UserPrincipal để lấy được thông tin user trong interface UserDetail như password,role,...
	}
}
