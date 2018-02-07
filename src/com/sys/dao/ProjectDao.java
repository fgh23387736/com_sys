package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.Project;


public interface ProjectDao {
	public Project add(Project project);
	public void update(Project project);
	public void delete(Project project);
	public Project getById(Integer id);
	public List<Project> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByName(Project project);
	public DetachedCriteria getCriteriaByTheName(String name);
	public DetachedCriteria getCriteriaByTheAddress(String address);
	
}
