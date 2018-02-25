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

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "DEVICE")
public class Device {
	@Id
	@Column(name="DEVICEID")
	@GenericGenerator(name = "id",strategy = "assigned")
	@GeneratedValue(generator = "id") 
	private String deviceId;
	
	@Column(name="DEVICENAME")
	private String name;
	
	@Column(name="PIPEDIAMETER")
	private Double pipeDiameter;
	
	@Column(name="K")
	private Integer k;
	
	@Column(name="PIPEDESCRIBE")
	private String pipeDescribe;
	
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

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public String getPipeDescribe() {
		return pipeDescribe;
	}

	public void setPipeDescribe(String pipeDescribe) {
		this.pipeDescribe = pipeDescribe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Double getPipeDiameter() {
		return pipeDiameter;
	}

	public void setPipeDiameter(Double pipeDiameter) {
		this.pipeDiameter = pipeDiameter;
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
