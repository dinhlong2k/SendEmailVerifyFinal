package com.github.tutorial.Controller;

import java.util.Calendar;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.github.tutorial.DTO.UserDTO;
import com.github.tutorial.Entities.User;
import com.github.tutorial.Entities.VerificationToken;
import com.github.tutorial.Event.OnRegistrationSuccessEvent;
import com.github.tutorial.Repository.TokenRepository;
import com.github.tutorial.Service.UserService;

@Controller
public class RegistorController {
	
	private final Logger LOGGER =LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@GetMapping("/registration")
	public String register(ModelMap modelMap) {
		UserDTO userDto=new UserDTO();
		modelMap.addAttribute("user", userDto);
		return "registerForm";
	}
	
	@PostMapping("/registration")
	public String confirmRegister(@Valid @ModelAttribute("user") UserDTO userDTO,BindingResult result,ModelMap modelMap,WebRequest request) {
		User existing = userService.findByEmail(userDTO.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        
        if(userDTO.getPassword().equals(userDTO.getMatchesPassword())==false) {
        	result.rejectValue("password", null,"Password don't match");
        }
        if (result.hasErrors()){
            return "registerForm";
        }
        
        User registeredUser=new User();
        registeredUser =userService.register(userDTO);
        try {
        	String appUrl = null;
			eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(), appUrl));
		}catch(Exception re) {
			re.printStackTrace();
//			throw new Exception("Error while sending confirmation email");
		}
        LOGGER.debug("Registering user account with information: {}", registeredUser);
		return "registrationSuccess";
	}
	
	@RequestMapping(value="/confirmRegistration", method= {RequestMethod.GET})
	public String confirmRegistration(WebRequest request,Model model,@RequestParam("token") String token) {
		Locale locale=request.getLocale();
		VerificationToken verificationToken=tokenRepo.findByToken(token);
		if(verificationToken == null) {
			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return "redirect:access-denied";
		}
		User user = verificationToken.getUser();
		Calendar calendar = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
			String message = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", message);
			return "redirect:access-denied";
		}
		
		user.setEnabled(true);
		userService.saveAccount(user);
		return "success" ;
	}
}
