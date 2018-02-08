package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.AlarmRecord;


public interface AlarmRecordDao {
	public AlarmRecord add(AlarmRecord alarmRecord);
	public void update(AlarmRecord alarmRecord);
	public void delete(AlarmRecord alarmRecord);
	public AlarmRecord getById(Integer id);
	public List<AlarmRecord> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByDevice(AlarmRecord alarmRecord);
	
}
