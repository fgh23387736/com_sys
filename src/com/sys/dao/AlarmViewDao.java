package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.AlarmView;
import com.sys.entity.AlarmViewId;


public interface AlarmViewDao {
	public List<AlarmView> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByDeviceId(AlarmViewId alarmViewId);
	
}
