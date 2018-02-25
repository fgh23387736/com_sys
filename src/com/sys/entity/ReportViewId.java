package com.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReportViewId implements java.io.Serializable{
	
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
	
	@Column(name = "PIPEDESCRIBE")
	private String pipeDescribe;
	
	@Column(name = "RECORDDATE")
	private Date recordDate;
	
	@Column(name = "PRESSURE")
	private Double pressure;
	
	@Column(name = "TEMPERATURE")
	private Double temperature;
	
	@Column(name = "AVERAGEFLOW")
	private Double averageFlow;
	
	@Column(name = "TOTALFLOW")
	private Double totalFlow;
	
	@Column(name = "TOTALTESTTIME")
	private Integer totalTestTime;
	
	@Column(name = "JUDGE1")
	private String judge1;
	
	@Column(name = "JUDGE2")
	private String judge2;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	/** default constructor */  
    public ReportViewId() {  
    }

	public ReportViewId(String deviceId, Integer deviceNumber,
			String deviceAddress, String deviceName, Double pipeDiameter,
			String pipeDescribe, Date recordDate, Double pressure,
			Double temperature, Double averageFlow, Double totalFlow,
			Integer totalTestTime, String judge1, String judge2, String remarks) {
		super();
		this.deviceId = deviceId;
		this.deviceNumber = deviceNumber;
		this.deviceAddress = deviceAddress;
		this.deviceName = deviceName;
		this.pipeDiameter = pipeDiameter;
		this.pipeDescribe = pipeDescribe;
		this.recordDate = recordDate;
		this.pressure = pressure;
		this.temperature = temperature;
		this.averageFlow = averageFlow;
		this.totalFlow = totalFlow;
		this.totalTestTime = totalTestTime;
		this.judge1 = judge1;
		this.judge2 = judge2;
		this.remarks = remarks;
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

	public String getPipeDescribe() {
		return pipeDescribe;
	}

	public void setPipeDescribe(String pipeDescribe) {
		this.pipeDescribe = pipeDescribe;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getAverageFlow() {
		return averageFlow;
	}

	public void setAverageFlow(Double averageFlow) {
		this.averageFlow = averageFlow;
	}

	public Double getTotalFlow() {
		return totalFlow;
	}

	public void setTotalFlow(Double totalFlow) {
		this.totalFlow = totalFlow;
	}

	public Integer getTotalTestTime() {
		return totalTestTime;
	}

	public void setTotalTestTime(Integer totalTestTime) {
		this.totalTestTime = totalTestTime;
	}

	public String getJudge1() {
		return judge1;
	}

	public void setJudge1(String judge1) {
		this.judge1 = judge1;
	}

	public String getJudge2() {
		return judge2;
	}

	public void setJudge2(String judge2) {
		this.judge2 = judge2;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}  
    
    
    
}
