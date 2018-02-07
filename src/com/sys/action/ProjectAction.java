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
import com.sys.entity.Project;
import com.sys.entity.User;
import com.sys.service.ProjectService;
import com.sys.util.PublicUtils;

@Component(value="projectAction")
@Scope(value="prototype")
public class ProjectAction extends ActionSupport implements ModelDriven<Project>{
	
	@Autowired
	private ProjectService projectService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Project project = new Project();
	
	
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@Override
	public Project getModel() {
		if(project == null){
			project = new Project();	
		}
		return project;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = projectService.getProjectByIds(keys,page,pageSize,idsIntegers);
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
			Map<String, Object> map = projectService.updateByIds(keys,idsIntegers,project,loginUser);
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
		}else if(!loginUser.getType().equals('1')){
			responseBean.setStatus(401);
			responseBean.put("error", "您不具有权限");
		}else{
			List<Project> projectList = projectService.getProjectByTheName(project.getName());
			if(projectList != null && projectList.size() > 0){
				responseBean.put("error", "项目名已存在");
				responseBean.setStatus(401);
			}else{
				projectList = null;
				projectList = projectService.getProjectByTheAddress(project.getAddress());
				if(projectList != null && projectList.size() > 0){
					responseBean.put("error", "项目地址不可重复");
					responseBean.setStatus(401);
				}else{
					project = projectService.add(project);
					if(project.getProjectId() != null) {
						responseBean.setStatus(200);
						responseBean.put("projectId", project.getProjectId());
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
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = projectService.deleteByIds(idsIntegers,loginUser);
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
	
	public void getByName(){
		if(project.getName() == null){
			project.setName("");
		}
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> map = projectService.getByName(keys,page,pageSize,project);
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
