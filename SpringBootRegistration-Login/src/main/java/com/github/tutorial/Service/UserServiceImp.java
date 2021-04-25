package com.github.tutorial.Service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.tutorial.DTO.UserDTO;
import com.github.tutorial.Entities.User;
import com.github.tutorial.Entities.VerificationToken;
import com.github.tutorial.Error.UserAlreadyExistException;
import com.github.tutorial.Repository.RoleRepository;
import com.github.tutorial.Repository.TokenRepository;
import com.github.tutorial.Repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	public PasswordEncoder passwordEncode;
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@Override
	public User register(UserDTO userDTO) {
		if(checkIfUserExist(userDTO.getEmail())) {
			throw new UserAlreadyExistException("Email Exits");
		}
		final User user =new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncode.encode(userDTO.getPassword()));
		user.setRoles(Arrays.asList(roleRepo.findByNameRole("ROLE_USER")));
		return userRepo.save(user);
	}
	
	public boolean checkIfUserExist(String email) {
		return userRepo.findByEmail(email) !=null ? true : false;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	@Override
	public void createVerificationToken(String token,User user) {
		// TODO Auto-generated method stub
		VerificationToken mytoken=new VerificationToken(token,user);
		tokenRepo.save(mytoken);
	}
	
	public void saveAccount(User user) {
		userRepo.save(user);
	}

	@Override
	public VerificationToken getVerificationToken(String token) {
		// TODO Auto-generated method stub
		return tokenRepo.findByToken(token);
	}
}
