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
@Table(name = "ALARMVIEW")  
public class AlarmView implements java.io.Serializable {  
  
    // Fields  
  
	@EmbeddedId  
    @AttributeOverrides({  
            @AttributeOverride(name = "deviceId", column = @Column(name = "DEVICEID")),  
            @AttributeOverride(name = "deviceNumber", column = @Column(name = "DEVICENUMBER")),  
            @AttributeOverride(name = "deviceAddress", column = @Column(name = "DEVICEADDRESS")),  
            @AttributeOverride(name = "deviceName", column = @Column(name = "DEVICENAME")),  
            @AttributeOverride(name = "pipeDiameter", column = @Column(name = "PIPEDIAMETER")),  
            @AttributeOverride(name = "temperatureAlarm", column = @Column(name = "TEMPERATUREALARM")),  
            @AttributeOverride(name = "deviceTtouble", column = @Column(name = "DEVICETROUBLE")) })
    private AlarmViewId alarmViewId;  
  
    // Constructors  
  
    /** default constructor */  
    public AlarmView() {  
    }  
  
    /** full constructor */  
    public AlarmView(AlarmViewId alarmViewId) {  
        this.alarmViewId = alarmViewId;  
    }

	public AlarmViewId getAlarmViewId() {
		return alarmViewId;
	}

	public void setAlarmViewId(AlarmViewId alarmViewId) {
		this.alarmViewId = alarmViewId;
	}  
	
    
}  