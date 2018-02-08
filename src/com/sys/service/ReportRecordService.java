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
import com.sys.dao.ReportRecordDao;
import com.sys.entity.ReportRecord;
import com.sys.entity.ReportRecord;
import com.sys.entity.Device;
import com.sys.entity.Project;
import com.sys.entity.User;


@Transactional
@Component(value="reportRecordService")
public class ReportRecordService {
	
	@Autowired
	private ReportRecordDao reportRecordDao;
	
	
	public ReportRecordDao getReportRecordDao() {
		return reportRecordDao;
	}

	public void setReportRecordDao(ReportRecordDao reportRecordDao) {
		this.reportRecordDao = reportRecordDao;
	}
	
	public List<Map<String, Object>> getReportRecordByKeys(String keys,List<ReportRecord> reportRecords){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"reportRecordId",
					"date",
					"pressure",
					"temperature",
					"averageFlow",
					"totalFlow",
					"totalTestTime",
					"pipeDescribe",
					"judge1",
					"judge2",
					"remarks",
					"device"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (ReportRecord reportRecord : reportRecords) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(reportRecord,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(ReportRecord reportRecord,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "reportRecordId":
			return reportRecord.getReportRecordId();
		case "date":
			return reportRecord.getDate();
		case "pressure":
			return reportRecord.getPressure();
		case "temperature":
			return reportRecord.getTemperature();
		case "averageFlow":
			return reportRecord.getAverageFlow();
		case "totalFlow":
			return reportRecord.getTotalFlow();
		case "totalTestTime":
			return reportRecord.getTotalTestTime();
		case "pipeDescribe":
			return reportRecord.getPipeDescribe();
		case "judge1":
			return reportRecord.getJudge1();
		case "judge2":
			return reportRecord.getJudge2();
		case "remarks":
			return reportRecord.getRemarks();
		case "device":
			theMap = new HashMap<String, Object>();
			Device theDevice = reportRecord.getDevice();
			theMap.put("deviceId", theDevice.getDeviceId());
			theMap.put("name", theDevice.getName());
			return theMap;
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = reportRecordDao.getAllCountByCriteria(criteria);
		List<ReportRecord> reportRecords = reportRecordDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getReportRecordByKeys(keys,reportRecords);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public ReportRecord add(ReportRecord reportRecord) {
		return reportRecordDao.add(reportRecord);
		
	}
	

	public Map<String, Object> getReportRecordByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = reportRecordDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			ReportRecord reportRecord, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return map;
		}
		
		for (Integer integer : idsIntegers) {
			if(!loginUser.getType().equals('1')){
				map.put("code", 400);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
				}
			}else{
				ReportRecord reportRecord3 = reportRecordDao.getById(integer);
				reportRecord3 = getNewReportRecordByKeys(reportRecord3,reportRecord,keysArrStrings);
				if(reportRecord3 != null){
					reportRecordDao.update(reportRecord3);
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
			ReportRecord reportRecord2 = reportRecordDao.getById(integer);
			if(reportRecord2 != null){
				if(loginUser.getType().equals('1')){
					reportRecordDao.delete(reportRecord2);
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

	private ReportRecord getNewReportRecordByKeys(ReportRecord reportRecord, ReportRecord newReportRecord, String[] keysArrStrings) {
		if(reportRecord == null || newReportRecord == null ){
			return null;
		}
		if(keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			List<ReportRecord> reportRecords = null;
			switch (key) {
				case "date":
					reportRecord.setDate(newReportRecord.getDate());
					break;
				case "pressure":
					reportRecord.setPressure(newReportRecord.getPressure());
					break;
				case "temperature":
					reportRecord.setTemperature(newReportRecord.getTemperature());
					break;
				case "averageFlow":
					reportRecord.setAverageFlow(newReportRecord.getAverageFlow());
					break;
				case "totalFlow":
					reportRecord.setTotalFlow(newReportRecord.getTotalFlow());
					break;
				case "totalTestTime":
					reportRecord.setTotalTestTime(newReportRecord.getTotalTestTime());
					break;
				case "pipeDescribe":
					reportRecord.setPipeDescribe(newReportRecord.getPipeDescribe());
					break;
				case "judge1":
					reportRecord.setJudge1(newReportRecord.getJudge1());
					break;	
				case "judge2":
					reportRecord.setJudge2(newReportRecord.getJudge2());
					break;
				case "remarks":
					reportRecord.setRemarks(newReportRecord.getRemarks());
					break;
				case "device":
					reportRecord.setDevice(newReportRecord.getDevice());
					break;
				default:
					
			}
		}
		
		return reportRecord;
	}

	public ReportRecord getById(Integer reportRecordId) {
		// TODO 自动生成的方法存根
		return reportRecordDao.getById(reportRecordId);
	}


	public Map<String, Object> getByDevice(String keys, Integer page,
			Integer pageSize, ReportRecord reportRecord) {
		DetachedCriteria criteria = reportRecordDao.getCriteriaByDevice(reportRecord);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
