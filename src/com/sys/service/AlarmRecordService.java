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
import com.sys.dao.AlarmRecordDao;
import com.sys.entity.AlarmRecord;
import com.sys.entity.AlarmRecord;
import com.sys.entity.Device;
import com.sys.entity.Project;
import com.sys.entity.User;


@Transactional
@Component(value="alarmRecordService")
public class AlarmRecordService {
	
	@Autowired
	private AlarmRecordDao alarmRecordDao;
	
	
	public AlarmRecordDao getAlarmRecordDao() {
		return alarmRecordDao;
	}

	public void setAlarmRecordDao(AlarmRecordDao alarmRecordDao) {
		this.alarmRecordDao = alarmRecordDao;
	}
	
	public List<Map<String, Object>> getAlarmRecordByKeys(String keys,List<AlarmRecord> alarmRecords){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"alarmRecordId",
					"date",
					"temperatureAlarm",
					"deviceTrouble",
					"device"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (AlarmRecord alarmRecord : alarmRecords) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(alarmRecord,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(AlarmRecord alarmRecord,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "alarmRecordId":
			return alarmRecord.getAlarmRecordId();
		case "date":
			return alarmRecord.getDate();
		case "temperatureAlarm":
			return alarmRecord.getTemperatureAlarm();
		case "deviceTrouble":
			return alarmRecord.getDeviceTrouble();
		case "device":
			theMap = new HashMap<String, Object>();
			Device theDevice = alarmRecord.getDevice();
			theMap.put("deviceId", theDevice.getDeviceId());
			theMap.put("name", theDevice.getName());
			return theMap;
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = alarmRecordDao.getAllCountByCriteria(criteria);
		List<AlarmRecord> alarmRecords = alarmRecordDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getAlarmRecordByKeys(keys,alarmRecords);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public AlarmRecord add(AlarmRecord alarmRecord) {
		return alarmRecordDao.add(alarmRecord);
		
	}
	

	public Map<String, Object> getAlarmRecordByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = alarmRecordDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			AlarmRecord alarmRecord, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return map;
		}
		
		for (Integer integer : idsIntegers) {
			AlarmRecord alarmRecord3 = alarmRecordDao.getById(integer);
			alarmRecord3 = getNewAlarmRecordByKeys(alarmRecord3,alarmRecord,keysArrStrings);
			if(alarmRecord3 != null){
				if(!loginUser.getType().equals('1') 
						&& !(loginUser.getProject() != null 
							&& loginUser.getProject().getProjectId().equals(alarmRecord3.getDevice().getProject().getProjectId()))){
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}else{
					alarmRecordDao.update(alarmRecord3);
				}
				
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
		
		map.put("result", theMap);
		return map;
	}
	
	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			AlarmRecord alarmRecord2 = alarmRecordDao.getById(integer);
			if(alarmRecord2 != null){
				if(!loginUser.getType().equals('1') 
						&& !(loginUser.getProject() != null 
							&& loginUser.getProject().getProjectId().equals(alarmRecord2.getDevice().getProject().getProjectId()))){
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据删除失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据删除失败:您不具有权限;");
					}
				}else{
					alarmRecordDao.delete(alarmRecord2);
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

	private AlarmRecord getNewAlarmRecordByKeys(AlarmRecord alarmRecord, AlarmRecord newAlarmRecord, String[] keysArrStrings) {
		if(alarmRecord == null || newAlarmRecord == null ){
			return null;
		}
		if(keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			List<AlarmRecord> alarmRecords = null;
			switch (key) {
				case "date":
					alarmRecord.setDate(newAlarmRecord.getDate());
					break;
				case "temperatureAlarm":
					alarmRecord.setTemperatureAlarm(newAlarmRecord.getTemperatureAlarm());
					break;
				case "deviceTrouble":
					alarmRecord.setDeviceTrouble(newAlarmRecord.getDeviceTrouble());
					break;
				case "device":
					alarmRecord.setDevice(newAlarmRecord.getDevice());
					break;
				default:
					
			}
		}
		
		return alarmRecord;
	}

	public AlarmRecord getById(Integer alarmRecordId) {
		// TODO 自动生成的方法存根
		return alarmRecordDao.getById(alarmRecordId);
	}


	public Map<String, Object> getByDevice(String keys, Integer page,
			Integer pageSize, AlarmRecord alarmRecord) {
		DetachedCriteria criteria = alarmRecordDao.getCriteriaByDevice(alarmRecord);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
