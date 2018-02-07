package com.sys.typeConverter;

import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import com.sys.entity.Project;

public class ProjectTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		
        if (toType == Project.class) {
        	String theValue = ((String[])value)[0];
        	System.out.println("user\n");
		    Integer params = new Integer(theValue) ;
		    Project project = new Project();
		    project.setProjectId(params);
		    return project;  
		} else if (toType == Integer.class) { 
			System.out.println("user2\n");
		    System.out.println(value);  
		    Project project = (Project) value;  
		    return project.getProjectId();  
		}
        return null;  
    } 
}
