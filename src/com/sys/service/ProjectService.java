package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jndi.cosnaming.IiopUrl.Address;
import com.sys.dao.ProjectDao;
import com.sys.entity.Project;
import com.sys.entity.Project;
import com.sys.entity.User;


@Transactional
@Component(value="projectService")
public class ProjectService {
	
	@Autowired
	private ProjectDao projectDao;
	
	
	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
	public List<Map<String, Object>> getProjectByKeys(String keys,List<Project> projects){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"projectId",
					"name",
					"address",
					"remarks"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Project project : projects) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(project,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Project project,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "projectId":
			return project.getProjectId();
		case "name":
			return project.getName();
		case "address":
			return project.getAddress();
		case "remarks":
			return project.getRemarks();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = projectDao.getAllCountByCriteria(criteria);
		List<Project> projects = projectDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getProjectByKeys(keys,projects);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Project add(Project project) {
		return projectDao.add(project);
		
	}
	

	public Map<String, Object> getProjectByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = projectDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}
	
	
	public Map<String, Object> getByName(String keys,
			Integer page, Integer pageSize, Project project) {
		DetachedCriteria criteria = projectDao.getCriteriaByName(project);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Project project, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return map;
		}
		
		for (Integer integer : idsIntegers) {
			if(!loginUser.getType().equals('1') && !loginUser.getProject().getProjectId().equals(integer)){
				map.put("code", 400);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
				}
			}else{
				Project project3 = projectDao.getById(integer);
				project3 = getNewProjectByKeys(project3,project,keysArrStrings);
				if(project3 != null){
					projectDao.update(project3);
				}else{
					map.put("code", 400);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败;");
					}
				}
			}
			
		}
		
		map.put("result", theMap);
		return map;
	}
	
	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			Project project2 = projectDao.getById(integer);
			if(project2 != null){
				if(loginUser.getType().equals('1')){
					projectDao.delete(project2);
				}else{
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据删除失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据删除失败:您不具有权限;");
					}
				}
			}else{
				map.put("code", 404);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据删除失败：数据不存在;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据删除失败：数据不存在;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	private Project getNewProjectByKeys(Project project, Project newProject, String[] keysArrStrings) {
		if(project == null || newProject == null ){
			return null;
		}
		if(keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			List<Project> projects = null;
			switch (key) {
				case "name":
					projects = this.getProjectByTheName(newProject.getName());
					if(projects == null || projects.size() == 0){
						project.setName(newProject.getName());
					}
					break;
				case "address":
					projects = this.getProjectByTheAddress(newProject.getAddress());
					if(projects == null || projects.size() == 0){
						project.setAddress(newProject.getAddress());
					}
					
					break;
				case "remarks":
					project.setRemarks(newProject.getRemarks());
					break;
				default:
					
			}
		}
		
		return project;
	}

	public Project getById(Integer projectId) {
		// TODO 自动生成的方法存根
		return projectDao.getById(projectId);
	}

	public List<Project> getProjectByTheName(String name) {
		// TODO Auto-generated method stub
		return projectDao.getDataByCriteria(null, null, projectDao.getCriteriaByTheName(name));
	}

	public List<Project> getProjectByTheAddress(String address) {
		// TODO Auto-generated method stub
		return projectDao.getDataByCriteria(null, null, projectDao.getCriteriaByTheAddress(address));
	}

}
