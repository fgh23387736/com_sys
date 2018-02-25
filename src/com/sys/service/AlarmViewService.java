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
import com.sys.dao.AlarmViewDao;
import com.sys.entity.AlarmView;
import com.sys.entity.Device;
import com.sys.entity.Project;
import com.sys.entity.User;


@Transactional
@Component(value="alarmViewService")
public class AlarmViewService {
	
	@Autowired
	private AlarmViewDao alarmViewDao;
	
	
	public AlarmViewDao getAlarmViewDao() {
		return alarmViewDao;
	}

	public void setAlarmViewDao(AlarmViewDao alarmViewDao) {
		this.alarmViewDao = alarmViewDao;
	}
	
	public List<Map<String, Object>> getAlarmViewByKeys(String keys,List<AlarmView> alarmViews){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"deviceId",
					"deviceNumber",
					"deviceAddress",
					"deviceName",
					"pipeDiameter",
					"recordDate",
					"temperatureAlarm",
					"deviceTtouble"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (AlarmView alarmView : alarmViews) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(alarmView,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(AlarmView alarmView,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "deviceId":
			return alarmView.getAlarmViewId().getDeviceId();
		case "deviceNumber":
			return alarmView.getAlarmViewId().getDeviceNumber();
		case "deviceAddress":
			return alarmView.getAlarmViewId().getDeviceAddress();
		case "deviceName":
			return alarmView.getAlarmViewId().getDeviceName();
		case "pipeDiameter":
			return alarmView.getAlarmViewId().getPipeDiameter();
		case "recordDate":
			return alarmView.getAlarmViewId().getRecordDate();
		case "temperatureAlarm":
			return alarmView.getAlarmViewId().getTemperatureAlarm();
		case "deviceTtouble":
			return alarmView.getAlarmViewId().getDeviceTrouble();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = alarmViewDao.getAllCountByCriteria(criteria);
		List<AlarmView> alarmViews = alarmViewDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getAlarmViewByKeys(keys,alarmViews);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}

	public Map<String, Object> getByDeviceId(String keys, Integer page,
			Integer pageSize, AlarmView alarmView) {
		DetachedCriteria criteria = alarmViewDao.getCriteriaByDeviceId(alarmView.getAlarmViewId());
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
