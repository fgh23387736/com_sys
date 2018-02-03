package com.sys.entity;

import java.util.Date;

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
@Table(name = "ALARMRECORD")
public class AlarmRecord {
	@Id
	@Column(name="ALARMID")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer alarmId;
	
	@Column(name="RECORDDATE")
	private Date date;
	
	@Column(name="TEMPERATUREALARM")
	private String temperatureAlarm;
	
	@Column(name="DEVICETROUBLE")
	private String deviceTrouble;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEVICEID")
	private Device device;

	public Integer getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTemperatureAlarm() {
		return temperatureAlarm;
	}

	public void setTemperatureAlarm(String temperatureAlarm) {
		this.temperatureAlarm = temperatureAlarm;
	}

	public String getDeviceTrouble() {
		return deviceTrouble;
	}

	public void setDeviceTrouble(String deviceTrouble) {
		this.deviceTrouble = deviceTrouble;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
