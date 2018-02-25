package com.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AlarmViewId implements java.io.Serializable{
	
	@Column(name = "DEVICEID")
	private String deviceId;
	
	@Column(name = "DEVICENUMBER")
	private Integer deviceNumber;
	
	@Column(name = "DEVICEADDRESS")
	private String deviceAddress;
	
	@Column(name = "DEVICENAME")
	private String deviceName;
	
	@Column(name = "PIPEDIAMETER")
	private Double pipeDiameter;
	
	@Column(name = "RECORDDATE")
	private Date recordDate;
	
	@Column(name = "TEMPERATUREALARM")
	private String temperatureAlarm;
	
	@Column(name = "DEVICETROUBLE")
	private String deviceTrouble;
	
	/** default constructor */  
    public AlarmViewId() {  
    }

	

	public AlarmViewId(String deviceId, Integer deviceNumber,
			String deviceAddress, String deviceName, Double pipeDiameter,
			Date recordDate, String temperatureAlarm,
			String deviceTrouble) {
		super();
		this.deviceId = deviceId;
		this.deviceNumber = deviceNumber;
		this.deviceAddress = deviceAddress;
		this.deviceName = deviceName;
		this.pipeDiameter = pipeDiameter;
		this.recordDate = recordDate;
		this.temperatureAlarm = temperatureAlarm;
		this.deviceTrouble = deviceTrouble;
	}



	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(Integer deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Double getPipeDiameter() {
		return pipeDiameter;
	}

	public void setPipeDiameter(Double pipeDiameter) {
		this.pipeDiameter = pipeDiameter;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
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

}
