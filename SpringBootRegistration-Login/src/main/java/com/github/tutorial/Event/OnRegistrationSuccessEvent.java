package com.github.tutorial.Event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.github.tutorial.Entities.User;

public class OnRegistrationSuccessEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	private String appUrl;
	private User user;
	private Locale locale;
	
	public OnRegistrationSuccessEvent(User user, Locale locale, String appUrl) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	 
}
