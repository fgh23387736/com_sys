package com.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
	@Id
	@Column(name="USERID")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer userId;
	
	@Column(name="USERNAME")
	private String userName;
	
	@Column(name="TYPE")
	private Integer type;
	
	@Column(name="PASSWORD")
	private String password;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECTID")
	private Project project; 	//项目

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
