package com.github.tutorial.Event;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import com.github.tutorial.Entities.User;
import com.github.tutorial.Service.EmailService;
import com.github.tutorial.Service.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent>{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
		// TODO Auto-generated method stub
		try {
			this.confirmRegistration(event);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void confirmRegistration(OnRegistrationSuccessEvent event) throws MessagingException, IOException, TemplateException  {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userService.createVerificationToken(token,user);
		
		String recipient = user.getEmail();
		String subject = "Registration Confirmation";
        String url 
          ="http://localhost:8080/confirmRegistration?token=" + token;
        
        Map model=new HashMap();
        model.put("tokenURL", url);
        MimeMessage message=emailSender.createMimeMessage();
        MimeMessageHelper  email = new MimeMessageHelper (message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        email.addInline("logo", new ClassPathResource("imgages/logo-light.png"));
        
        Template template = emailConfig.getTemplate("mailConfirmForm.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(html,true);
        email.setFrom("nguyendinhlongndl@gmail.com");
        
        emailService.sendEmail(message);
	}
}
