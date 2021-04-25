package com.github.tutorial.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authProvider(){ //hàm xác thực tài khoản như tài khoản là admin or user,..token,...
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(); //Xác thực DaoAuthenticationProvider
																			//Xác thực tài khoản trong field MySQL
		provider.setUserDetailsService(userDetailsService); //Xét role, xác thực role ,... của user
		provider.setPasswordEncoder(encoder()); //set Password tai khoan la password đã được mã hoá
		return provider;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authProvider());
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		/*http
			.csrf().disable()
			.authorizeRequests().antMatchers("/login").permitAll()         //các request tới login cho phép mọi người truy cập
			.anyRequest().authenticated() //các request khác sẽ được bảo mật
			.antMatchers(
					"/**"
					,"/registration"
					).permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.and()
		.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/home")
			.failureUrl("/login?error")
		.and()
			.logout()
		    .logoutUrl("/logout")
		    .logoutSuccessUrl("/login?logout")
		.and()
			.exceptionHandling().accessDeniedPage("/login?accessDenied");*/
		
		
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/anonymous*").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/registration").permitAll()
			.antMatchers("/home").permitAll()
		.and()
		.formLogin()
			.loginPage("/login")   //trang custom login page
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/home",true)	//direct toi trang sau khi login
			//.permitAll()
		.and()
		.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login").permitAll()   //trang sau khi dang xuat thanh cong
		.and()
			.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400).userDetailsService(userDetailsService)
		.and()
			.csrf().disable();
	}
}
