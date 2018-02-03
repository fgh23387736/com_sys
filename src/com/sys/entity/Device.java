package com.sys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "DEVTCE")
public class Device {
	@Id
	@Column(name="DEVICEID")
    @GeneratedValue(strategy=GenerationType.AUTO) 
	private String deviceId;
	
	@Column(name="DEVICENAME")
	private String name;
	
	@Column(name="PIPEDIAMETER")
	private Double pipediameter;
	
	@Column(name="DEVICEADDRESS")
	private String address;
	
	@Column(name="DEVICENUMBER")
	private Integer number;
	
	@Column(name="DEVICEREMARKS")
	private String remarks;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECTID")
	private Project project;
	
	@OneToMany(mappedBy="device",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<AlarmRecord> alarmRecords = new HashSet<AlarmRecord>();

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPipediameter() {
		return pipediameter;
	}

	public void setPipediameter(Double pipediameter) {
		this.pipediameter = pipediameter;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<AlarmRecord> getAlarmRecords() {
		return alarmRecords;
	}

	public void setAlarmRecords(Set<AlarmRecord> alarmRecords) {
		this.alarmRecords = alarmRecords;
	}


	
	
}
