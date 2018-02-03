package com.sys.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.sys.bean.ResponseBean;
import com.sys.entity.User;

@Component(value="specialAction")
@Scope(value="prototype")
public class SpecialAction extends ActionSupport{
	
	private String fileName;  

    //定义输入流，名称和xml里面的<param name="inputName">的名称一样
    private InputStream inputStream;  

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	public String getDb(){
		//获取需要下载的文件的相对文件夹路径
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			
			fileName = "monitor.db";
	        //根据文件夹路径和文件名，创建file文件
	        File file = new File("/data/sqlite",fileName); 

	        //用输入流读取文件
	        try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    

			return "downloadFile";
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return NONE;
        
	}
}
