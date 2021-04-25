package com.github.tutorial.Entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idrole;
	
	@Column(name="rolename")
	private String nameRole;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public Roles() {
		super();
	}

	public Roles(String nameRole, Set<User> users) {
		super();
		this.nameRole = nameRole;
		this.users = users;
	}

	public int getIdrole() {
		return idrole;
	}

	public void setIdrole(int idrole) {
		this.idrole = idrole;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
		
}
