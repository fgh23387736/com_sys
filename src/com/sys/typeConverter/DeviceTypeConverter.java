package com.sys.typeConverter;

import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import com.sys.entity.Device;
import com.sys.entity.Project;

public class DeviceTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		
        if (toType == Device.class) {
        	String theValue = ((String[])value)[0];
		    Device device = new Device();
		    device.setDeviceId(theValue);
		    return device;  
		} else if (toType == String.class) { 
		    Device device = (Device) value;  
		    return device.getDeviceId();  
		}
        return null;  
    } 
}
