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
import com.sys.entity.AlarmRecord;
import com.sys.entity.Device;
import com.sys.entity.User;
import com.sys.service.AlarmRecordService;
import com.sys.service.DeviceService;
import com.sys.util.PublicUtils;

@Component(value="alarmRecordAction")
@Scope(value="prototype")
public class AlarmRecordAction extends ActionSupport implements ModelDriven<AlarmRecord>{
	
	@Autowired
	private AlarmRecordService alarmRecordService;
	
	@Autowired
	private DeviceService deviceService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private AlarmRecord alarmRecord = new AlarmRecord();
	
	
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

	public AlarmRecord getAlarmRecord() {
		return alarmRecord;
	}

	public void setAlarmRecord(AlarmRecord alarmRecord) {
		this.alarmRecord = alarmRecord;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public AlarmRecordService getAlarmRecordService() {
		return alarmRecordService;
	}

	public void setAlarmRecordService(AlarmRecordService alarmRecordService) {
		this.alarmRecordService = alarmRecordService;
	}

	@Override
	public AlarmRecord getModel() {
		if(alarmRecord == null){
			alarmRecord = new AlarmRecord();	
		}
		return alarmRecord;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = alarmRecordService.getAlarmRecordByIds(keys,page,pageSize,idsIntegers);
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
		}else if(loginUser.getType().equals('3')){
			responseBean.setStatus(401);
			responseBean.put("error", "您不具有权限");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = alarmRecordService.updateByIds(keys,idsIntegers,alarmRecord,loginUser);
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
			if(alarmRecord.getDevice() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "设备不存在");
			}else{
				Device theDevice = deviceService.getById(alarmRecord.getDevice().getDeviceId());
				if(theDevice == null){
					responseBean.setStatus(404);
					responseBean.put("error", "设备不存在");
				}else{
					alarmRecord = alarmRecordService.add(alarmRecord);
					if(alarmRecord.getAlarmRecordId() != null) {
						responseBean.setStatus(200);
						responseBean.put("alarmRecordId", alarmRecord.getAlarmRecordId());
					} else {
						responseBean.put("error", "添加失败，系统错误");
						responseBean.setStatus(500);
					}
				}
				
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
		}else if(!loginUser.getType().equals('1')){
			responseBean.setStatus(401);
			responseBean.put("error", "您不具有权限");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = alarmRecordService.deleteByIds(idsIntegers,loginUser);
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
	
	public void getByDevice(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> map = alarmRecordService.getByDevice(keys,page,pageSize,alarmRecord);
			responseBean.setObjMap(map);
			
		}
		
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
