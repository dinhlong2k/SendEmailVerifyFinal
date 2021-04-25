package com.github.tutorial.Service;

import com.github.tutorial.DTO.UserDTO;
import com.github.tutorial.Entities.User;
import com.github.tutorial.Entities.VerificationToken;

public interface UserService {
	User register(UserDTO userDTO);
	User findByEmail(String email);
	void createVerificationToken(String token,User user);
	VerificationToken getVerificationToken(String token);
	void saveAccount(User user);
}
