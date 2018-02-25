package com.sys.action;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sys.bean.ResponseBean;
import com.sys.entity.AlarmView;
import com.sys.entity.AlarmViewId;
import com.sys.entity.User;
import com.sys.service.AlarmViewService;
import com.sys.service.DeviceService;

@Component(value="alarmViewAction")
@Scope(value="prototype")
public class AlarmViewAction extends ActionSupport implements ModelDriven<AlarmViewId>{
	
	@Autowired
	private AlarmViewService alarmViewService;
	
	@Autowired
	private DeviceService deviceService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private AlarmViewId alarmViewId = new AlarmViewId();
	
	
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

	public AlarmViewId getAlarmViewId() {
		return alarmViewId;
	}

	public void setAlarmViewId(AlarmViewId alarmViewId) {
		this.alarmViewId = alarmViewId;
	}

	@Override
	public String execute() throws Exception {
		return NONE;
	}

	public AlarmViewService getAlarmViewService() {
		return alarmViewService;
	}

	public void setAlarmViewService(AlarmViewService alarmViewService) {
		this.alarmViewService = alarmViewService;
	}

	@Override
	public AlarmViewId getModel() {
		if(alarmViewId == null){
			alarmViewId = new AlarmViewId();	
		}
		return alarmViewId;
	}
	
	public void getByDeviceId(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			AlarmView alarmView = new AlarmView();
			alarmView.setAlarmViewId(alarmViewId);
			Map<String, Object> map = alarmViewService.getByDeviceId(keys,page,pageSize,alarmView);
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
