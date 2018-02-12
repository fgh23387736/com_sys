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
import com.sys.entity.User;
import com.sys.service.UserService;
import com.sys.util.PublicUtils;

@Component(value="userAction")
@Scope(value="prototype")
public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	@Autowired
	private UserService userService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private User user = new User();
	
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		if(user == null){
			user = new User();	
		}
		return user;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = loginUser.getUserId();
			}
			Map<String, Object> map = userService.getUserByIds(keys,page,pageSize,idsIntegers);
			
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
		}else if(!loginUser.getType().equals('1')){
			responseBean.setStatus(401);
			responseBean.put("error", "您不具有权限操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = loginUser.getUserId();
			}
			Map<String, Object> map = userService.updateByIds(keys,idsIntegers,user,loginUser);
			responseBean.setStatus((int)map.get("code"));
			responseBean.setObjMap((Map<String, Object>)map.get("result"));
			if(idsIntegers[0] == loginUser.getUserId()){
				ServletActionContext.getRequest().getSession().setAttribute("user", userService.getById(loginUser.getUserId()));
			}
		}
		try {
			responseBean.writeTheMap();
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
			responseBean.put("error", "您不具有权限操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = userService.deleteByIds(idsIntegers,loginUser);
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
			responseBean.put("error", "您不具有权限操作");
		}else{
			List<User> userList = userService.getUserByUserName(user.getUserName());
			if(userList != null && userList.size() > 0){
				responseBean.put("error", "用户名已存在");
				responseBean.setStatus(401);
			}else{
				user.setPassword(PublicUtils.getMD5(user.getPassword()));
				user = userService.add(user);
				if(user.getUserId() != null) {
					responseBean.setStatus(201);
					responseBean.put("userId", user.getUserId());
				} else {
					responseBean.put("error", "添加失败，系统错误");
					responseBean.setStatus(500);
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

	
	public void login(){
		ResponseBean response =new ResponseBean();
		List<User> userList = userService.getUserByUserName(user.getUserName());
		if(userList != null && userList.size() > 0){
			User theuser = userList.get(0);
			if(theuser != null){
				if(PublicUtils.getMD5(user.getPassword()).equals(theuser.getPassword())){
					response.setStatus(201);
					response.put("userId", theuser.getUserId());
					response.put("type", theuser.getType());
					if(theuser.getProject() != null){
						response.put("projectId", theuser.getProject().getProjectId());
					}else{
						response.put("projectId", null);
					}
					
					ServletActionContext.getRequest().getSession().setAttribute("user", theuser);
				}else{
					response.put("error", "密码错误");
					response.setStatus(401);
				}
			}else{
				response.put("error", "用户不存在");
				response.setStatus(401);
			}
		}else{
			response.put("error", "用户不存在");
			response.setStatus(401);
		}
		try {
			response.write(response.getJsonString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void signOut(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			ServletActionContext.getRequest().getSession().setAttribute("user", null);
			responseBean.setStatus(201);
			responseBean.setObjMap(null);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	

	
	public void getByProjectAndUserName(){	
		if(user.getUserName() == null){
			user.setUserName("");
		}
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> map = userService.getByProjectAndUserName(keys,page,pageSize,user);
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
