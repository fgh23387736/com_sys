package com.sys.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sys.bean.ResponseBean;
import com.sys.entity.Device;
import com.sys.entity.User;
import com.sys.service.DeviceService;
import com.sys.util.PublicUtils;

@Component(value="deviceAction")
@Scope(value="prototype")
public class DeviceAction extends ActionSupport implements ModelDriven<Device>{
	
	@Autowired
	private DeviceService deviceService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Device device = new Device();
	
	
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@Override
	public Device getModel() {
		if(device == null){
			device = new Device();	
		}
		return device;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			String[] idsString = PublicUtils.getStringIdsByString(ids, "\\+");
			Map<String, Object> map = deviceService.getDeviceByIds(keys,page,pageSize,idsString);
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public void updateByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			String[] idsString = PublicUtils.getStringIdsByString(ids, "\\+");
			Map<String, Object> map = deviceService.updateByIds(keys,idsString,device,loginUser);
			responseBean.setStatus((int)map.get("code"));
			responseBean.setObjMap((Map<String, Object>)map.get("result"));
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void add(){
		ResponseBean responseBean =new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			boolean isHaveAuth = false;
			if(loginUser.getType().equals('1')){
				isHaveAuth = true;
			}else{
				if(loginUser.getType().equals('2') 
						&& loginUser.getProject() != null 
						&& device.getProject() != null 
						&& loginUser.getProject().getProjectId().equals(device.getProject().getProjectId())){
					isHaveAuth = true;
				}
			}
			if(isHaveAuth){
				Device theDevice = deviceService.getById(device.getDeviceId());
				if(theDevice == null){
					List<Device> deviceList = deviceService.getDeviceByTheAddress(device.getAddress());
					if(deviceList != null && deviceList.size() > 0){
						responseBean.put("error", "设备地址不可重复");
						responseBean.setStatus(401);
					}else{
						device = deviceService.add(device);
						if(device.getDeviceId() != null) {
							responseBean.setStatus(201);
							responseBean.put("deviceId", device.getDeviceId());
						} else {
							responseBean.put("error", "添加失败，系统错误");
							responseBean.setStatus(500);
						}
					}
				}else{
					responseBean.put("error", "设备已存在");
					responseBean.setStatus(401);
				}
			}else{
				responseBean.setStatus(401);
				responseBean.put("error", "您不具有权限");
			}	
		}
		
		try {
			responseBean.write(responseBean.getJsonString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void deleteByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			String[] idsString = PublicUtils.getStringIdsByString(ids, "\\+");
			Map<String, Object> map = deviceService.deleteByIds(idsString,loginUser);
			responseBean.setStatus((int)map.get("code"));
			responseBean.setObjMap((Map<String, Object>)map.get("result"));
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByProjectAndName(){
		if(device.getName() == null){
			device.setName("");
		}
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> map = deviceService.getByProjectAndName(keys,page,pageSize,device);
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
}
