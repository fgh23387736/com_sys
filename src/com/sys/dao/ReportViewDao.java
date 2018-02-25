package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.ReportView;
import com.sys.entity.ReportViewId;


public interface ReportViewDao {
	public List<ReportView> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByDeviceId(ReportViewId reportViewId);
	
}
