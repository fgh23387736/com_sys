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
import com.sys.entity.ReportRecord;
import com.sys.entity.Device;
import com.sys.entity.User;
import com.sys.service.ReportRecordService;
import com.sys.service.DeviceService;
import com.sys.util.PublicUtils;

@Component(value="reportRecordAction")
@Scope(value="prototype")
public class ReportRecordAction extends ActionSupport implements ModelDriven<ReportRecord>{
	
	@Autowired
	private ReportRecordService reportRecordService;
	
	@Autowired
	private DeviceService deviceService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private ReportRecord reportRecord = new ReportRecord();
	
	
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

	public ReportRecord getReportRecord() {
		return reportRecord;
	}

	public void setReportRecord(ReportRecord reportRecord) {
		this.reportRecord = reportRecord;
	}

	@Override
	public String execute() throws Exception {
		return NONE;
	}

	public ReportRecordService getReportRecordService() {
		return reportRecordService;
	}

	public void setReportRecordService(ReportRecordService reportRecordService) {
		this.reportRecordService = reportRecordService;
	}

	@Override
	public ReportRecord getModel() {
		if(reportRecord == null){
			reportRecord = new ReportRecord();	
		}
		return reportRecord;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = reportRecordService.getReportRecordByIds(keys,page,pageSize,idsIntegers);
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
			Map<String, Object> map = reportRecordService.updateByIds(keys,idsIntegers,reportRecord,loginUser);
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
			if(reportRecord.getDevice() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "设备不存在");
			}else{
				Device theDevice = deviceService.getById(reportRecord.getDevice().getDeviceId());
				if(theDevice == null){
					responseBean.setStatus(404);
					responseBean.put("error", "设备不存在");
				}else{
					if(!loginUser.getType().equals('1') 
							&& !(loginUser.getProject() != null 
								&& loginUser.getProject().getProjectId().equals(reportRecord.getDevice().getProject().getProjectId()))){
						responseBean.setStatus(401);
						responseBean.put("error", "您不具有权限");
					}else{
						reportRecord = reportRecordService.add(reportRecord);
						if(reportRecord.getReportRecordId() != null) {
							responseBean.setStatus(201);
							responseBean.put("reportRecordId", reportRecord.getReportRecordId());
						} else {
							responseBean.put("error", "添加失败，系统错误");
							responseBean.setStatus(500);
						}
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
			Map<String, Object> map = reportRecordService.deleteByIds(idsIntegers,loginUser);
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
			Map<String, Object> map = reportRecordService.getByDevice(keys,page,pageSize,reportRecord);
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
