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
import com.sys.entity.ReportView;
import com.sys.entity.ReportViewId;
import com.sys.entity.User;
import com.sys.service.ReportViewService;
import com.sys.service.DeviceService;

@Component(value="reportViewAction")
@Scope(value="prototype")
public class ReportViewAction extends ActionSupport implements ModelDriven<ReportViewId>{
	
	@Autowired
	private ReportViewService reportViewService;
	
	@Autowired
	private DeviceService deviceService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private ReportViewId reportViewId = new ReportViewId();
	
	
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

	public ReportViewId getReportViewId() {
		return reportViewId;
	}

	public void setReportViewId(ReportViewId reportViewId) {
		this.reportViewId = reportViewId;
	}

	@Override
	public String execute() throws Exception {
		return NONE;
	}

	public ReportViewService getReportViewService() {
		return reportViewService;
	}

	public void setReportViewService(ReportViewService reportViewService) {
		this.reportViewService = reportViewService;
	}

	@Override
	public ReportViewId getModel() {
		if(reportViewId == null){
			reportViewId = new ReportViewId();	
		}
		return reportViewId;
	}
	
	public void getByDeviceId(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			ReportView reportView = new ReportView();
			reportView.setReportViewId(reportViewId);
			Map<String, Object> map = reportViewService.getByDeviceId(keys,page,pageSize,reportView);
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
