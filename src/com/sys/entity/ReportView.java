package com.sys.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.AttributeOverride;  
import javax.persistence.AttributeOverrides; 
import javax.persistence.EmbeddedId;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity  
@Table(name = "REPORTVIEW")  
public class ReportView implements java.io.Serializable {  
  
    // Fields  
  
	@EmbeddedId  
    @AttributeOverrides({  
            @AttributeOverride(name = "deviceId", column = @Column(name = "DEVICEID")),  
            @AttributeOverride(name = "deviceNumber", column = @Column(name = "DEVICENUMBER")),  
            @AttributeOverride(name = "deviceAddress", column = @Column(name = "DEVICEADDRESS")),  
            @AttributeOverride(name = "deviceName", column = @Column(name = "DEVICENAME")),  
            @AttributeOverride(name = "pipeDiameter", column = @Column(name = "PIPEDIAMETER")),  
            @AttributeOverride(name = "pipeDescribe", column = @Column(name = "PIPEDESCRIBE")),  
            @AttributeOverride(name = "recordDate", column = @Column(name = "RECORDDATE")),  
            @AttributeOverride(name = "pressure", column = @Column(name = "PRESSURE")),  
            @AttributeOverride(name = "temperature", column = @Column(name = "TEMPERATURE")),  
            @AttributeOverride(name = "averageFlow", column = @Column(name = "AVERAGEFLOW")),  
            @AttributeOverride(name = "totalFlow", column = @Column(name = "TOTALFLOW")),  
            @AttributeOverride(name = "totalTestTime", column = @Column(name = "TOTALTESTTIME")),  
            @AttributeOverride(name = "judge1", column = @Column(name = "JUDGE1")),  
            @AttributeOverride(name = "judge2", column = @Column(name = "JUDGE2")),  
            @AttributeOverride(name = "remarks", column = @Column(name = "REMARKS")) })
    private ReportViewId reportViewId;  
  
    // Constructors  
  
    /** default constructor */  
    public ReportView() {  
    }  
  
    /** full constructor */  
    public ReportView(ReportViewId reportViewId) {  
        this.reportViewId = reportViewId;  
    }

	public ReportViewId getReportViewId() {
		return reportViewId;
	}

	public void setReportViewId(ReportViewId reportViewId) {
		this.reportViewId = reportViewId;
	}  
	
    
}  