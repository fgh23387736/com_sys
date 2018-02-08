package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.DeviceDao;
import com.sys.entity.Device;
import com.sys.entity.Project;
import com.sys.entity.User;


@Transactional
@Component(value="deviceService")
public class DeviceService {
	
	@Autowired
	private DeviceDao deviceDao;
	
	
	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}
	
	public List<Map<String, Object>> getDeviceByKeys(String keys,List<Device> devices){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"deviceId",
					"name",
					"address",
					"remarks",
					"pipeDiameter",
					"number",
					"project"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Device device : devices) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(device,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Device device,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "deviceId":
			return device.getDeviceId();
		case "name":
			return device.getName();
		case "address":
			return device.getAddress();
		case "remarks":
			return device.getRemarks();
		case "pipeDiameter":
			return device.getPipeDiameter();
		case "number":
			return device.getNumber();
		case "project":
			theMap = new HashMap<String, Object>();
			Project theProject = device.getProject();
			theMap.put("projectId", theProject.getProjectId());
			theMap.put("name", theProject.getName());
			return theMap;
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = deviceDao.getAllCountByCriteria(criteria);
		List<Device> devices = deviceDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getDeviceByKeys(keys,devices);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Device add(Device device) {
		return deviceDao.add(device);
		
	}
	

	public Map<String, Object> getDeviceByIds(String keys,Integer page,Integer pageSize,String[] ids) {
		DetachedCriteria criteria = deviceDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}
	
	
	public Map<String, Object> getByProjectAndName(String keys,
			Integer page, Integer pageSize, Device device) {
		DetachedCriteria criteria = deviceDao.getCriteriaByProjectAndName(device);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	

	public Map<String, Object> updateByIds(String keys, String[] ids,
			Device device, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return map;
		}
		
		for (String integer : ids) {
			if(!loginUser.getType().equals('1') && !loginUser.getProject().getProjectId().equals(integer)){
				map.put("code", 400);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
				}
			}else{
				Device device3 = deviceDao.getById(integer);
				device3 = getNewDeviceByKeys(device3,device,keysArrStrings);
				if(device3 != null){
					deviceDao.update(device3);
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
	
	public Map<String, Object> deleteByIds(String[] idsIntegers, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (String integer : idsIntegers) {
			Device device2 = deviceDao.getById(integer);
			if(device2 != null){
				if(loginUser.getType().equals('1')){
					deviceDao.delete(device2);
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

	private Device getNewDeviceByKeys(Device device, Device newDevice, String[] keysArrStrings) {
		if(device == null || newDevice == null ){
			return null;
		}
		if(keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			List<Device> devices = null;
			switch (key) {
				case "name":
					device.setName(newDevice.getName());
					break;
				case "pipeDiameter":
					device.setPipeDiameter(newDevice.getPipeDiameter());;
					break;
				case "number":
					device.setNumber(newDevice.getNumber());
					break;
				case "project":
					device.setProject(newDevice.getProject());
					break;
				case "address":
					devices = this.getDeviceByTheAddress(newDevice.getAddress());
					if(devices == null || devices.size() == 0){
						device.setAddress(newDevice.getAddress());
					}
					
					break;
				case "remarks":
					device.setRemarks(newDevice.getRemarks());
					break;
				default:
					
			}
		}
		
		return device;
	}

	public Device getById(String deviceId) {
		// TODO 自动生成的方法存根
		return deviceDao.getById(deviceId);
	}

	public List<Device> getDeviceByTheAddress(String address) {
		// TODO Auto-generated method stub
		return deviceDao.getDataByCriteria(null, null, deviceDao.getCriteriaByTheAddress(address));
	}

}
