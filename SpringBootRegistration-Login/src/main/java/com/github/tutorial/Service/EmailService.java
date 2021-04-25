package com.github.tutorial.Service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;

	@Autowired
	 public EmailService(JavaMailSender javaMailSender) {
	    this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendEmail(MimeMessage email) {
		javaMailSender.send(email);
	}
}
