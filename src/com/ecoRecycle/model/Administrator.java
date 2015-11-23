package com.ecoRecycle.model;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ADMINISTRATOR")
public class Administrator {
	
	@Id 
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "username", unique = true)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "createDateTime")
	private Date createDateTime;
	
	@Column(name = "updateDateTime")
	private Date updateDateTime;
	
	public Rmos getRmos() {
		return rmos;
	}

	public void setRmos(Rmos rmos) {
		this.rmos = rmos;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "RMOS",
	inverseJoinColumns={@JoinColumn(name="administratorId")})
	private Rmos rmos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Override
	public String toString() {
		return "Administrator [id=" + id + ", username=" + username
				+ ", password=" + password + "]";
	}
	
}
