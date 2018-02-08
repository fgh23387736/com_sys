package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.Device;


public interface DeviceDao {
	public Device add(Device device);
	public void update(Device device);
	public void delete(Device device);
	public Device getById(String id);
	public List<Device> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(String[] ids);
	public DetachedCriteria getCriteriaByProjectAndName(Device device);
	public DetachedCriteria getCriteriaByTheName(String name);
	public DetachedCriteria getCriteriaByTheAddress(String address);
	
}
