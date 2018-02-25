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
@Table(name = "REPORTRECORD")
public class ReportRecord {
	@Id
	@Column(name="REPORTID")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer reportRecordId;
	
	@Column(name="RECORDDATE")
	private Date date;
	
	@Column(name="PRESSURE")
	private Double pressure;
	
	@Column(name="TEMPERATURE")
	private Double temperature;
	
	@Column(name="AVERAGEFLOW")
	private Double averageFlow;
	
	@Column(name="TOTALFLOW")
	private Double totalFlow;
	
	@Column(name="TOTALTESTTIME")
	private Integer totalTestTime;
	
	@Column(name="JUDGE1")
	private String judge1;
	
	@Column(name="JUDGE2")
	private String judge2;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEVICEID")
	private Device device;

	

	public Integer getReportRecordId() {
		return reportRecordId;
	}

	public void setReportRecordId(Integer reportRecordId) {
		this.reportRecordId = reportRecordId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	

}
