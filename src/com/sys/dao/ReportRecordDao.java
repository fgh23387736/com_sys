package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.ReportRecord;


public interface ReportRecordDao {
	public ReportRecord add(ReportRecord reportRecord);
	public void update(ReportRecord reportRecord);
	public void delete(ReportRecord reportRecord);
	public ReportRecord getById(Integer id);
	public List<ReportRecord> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByDevice(ReportRecord reportRecord);
	
}
