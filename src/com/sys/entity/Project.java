package com.sys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "PROJECT")
public class Project {
	@Id
	@Column(name="PROJECTID")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer projectId;
	
	@Column(name="PROJECTNAME")
	private String name;
	
	@Column(name="PROJECTADDRESS")
	private String address;
	
	@Column(name="PROJECTREMARKS")
	private String remarks;
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<User> users = new HashSet<User>();
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Device> devices = new HashSet<Device>();

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}
	
	
}
