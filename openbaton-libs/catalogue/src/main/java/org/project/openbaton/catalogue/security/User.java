package org.project.openbaton.catalogue.security;

import org.project.openbaton.catalogue.util.IdGenerator;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	private	String id;

	@Column(unique = true)
	private	String	username;

	private	String	password;

	private	String	firstName;

	private	String	lastName;

	private	boolean	admin;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@PrePersist
	public void ensureId(){
		id=IdGenerator.createUUID();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	

}
