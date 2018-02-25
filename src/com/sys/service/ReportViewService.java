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
import com.sys.dao.ReportViewDao;
import com.sys.entity.ReportView;
import com.sys.entity.Device;
import com.sys.entity.Project;
import com.sys.entity.User;


@Transactional
@Component(value="reportViewService")
public class ReportViewService {
	
	@Autowired
	private ReportViewDao reportViewDao;
	
	
	public ReportViewDao getReportViewDao() {
		return reportViewDao;
	}

	public void setReportViewDao(ReportViewDao reportViewDao) {
		this.reportViewDao = reportViewDao;
	}
	
	public List<Map<String, Object>> getReportViewByKeys(String keys,List<ReportView> reportViews){
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
					"pipeDescribe",
					"recordDate",
					"pressure",
					"temperature",
					"averageFlow",
					"totalFlow",
					"totalTestTime",
					"pipeDescribe",
					"judge1",
					"judge2",
					"remarks"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (ReportView reportView : reportViews) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(reportView,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(ReportView reportView,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "deviceId":
			return reportView.getReportViewId().getDeviceId();
		case "deviceNumber":
			return reportView.getReportViewId().getDeviceNumber();
		case "deviceAddress":
			return reportView.getReportViewId().getDeviceAddress();
		case "deviceName":
			return reportView.getReportViewId().getDeviceName();
		case "pipeDiameter":
			return reportView.getReportViewId().getPipeDiameter();
		case "pipeDescribe":
			return reportView.getReportViewId().getPipeDescribe();
		case "recordDate":
			return reportView.getReportViewId().getRecordDate();
		case "pressure":
			return reportView.getReportViewId().getPressure();
		case "temperature":
			return reportView.getReportViewId().getTemperature();
		case "averageFlow":
			return reportView.getReportViewId().getAverageFlow();
		case "totalFlow":
			return reportView.getReportViewId().getTotalFlow();
		case "totalTestTime":
			return reportView.getReportViewId().getTotalTestTime();
		case "judge1":
			return reportView.getReportViewId().getJudge1();
		case "judge2":
			return reportView.getReportViewId().getJudge2();
		case "remarks":
			return reportView.getReportViewId().getRemarks();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = reportViewDao.getAllCountByCriteria(criteria);
		List<ReportView> reportViews = reportViewDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getReportViewByKeys(keys,reportViews);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}

	public Map<String, Object> getByDeviceId(String keys, Integer page,
			Integer pageSize, ReportView reportView) {
		DetachedCriteria criteria = reportViewDao.getCriteriaByDeviceId(reportView.getReportViewId());
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
