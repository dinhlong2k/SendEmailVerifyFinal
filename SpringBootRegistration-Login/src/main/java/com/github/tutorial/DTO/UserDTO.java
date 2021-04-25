package com.github.tutorial.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.github.tutorial.Validator.PasswordMatches;
import com.github.tutorial.Validator.ValidEmail;

@PasswordMatches
public class UserDTO {
	
	@NotNull
	@NotEmpty
	private String firstName;
	
	@NotNull
	@NotEmpty
	private String lastName;
	
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String password;
	
	@NotNull
	@NotEmpty
	private String matchesPassword;

	public UserDTO() {
		super();
	}

	public UserDTO(@NotNull @NotEmpty String firstName, @NotNull @NotEmpty String lastName,
			@NotNull @NotEmpty String email, @NotNull @NotEmpty String password,
			@NotNull @NotEmpty String matchesPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.matchesPassword = matchesPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchesPassword() {
		return matchesPassword;
	}

	public void setMatchesPassword(String matchesPassword) {
		this.matchesPassword = matchesPassword;
	}
	
	
}
