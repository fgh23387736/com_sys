package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.ReportViewDao;
import com.sys.entity.ReportView;
import com.sys.entity.ReportViewId;


@Component(value="reportViewDaoImpl")
public class ReportViewDaoImpl implements ReportViewDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<ReportView> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<ReportView> newReportViews = new ArrayList<ReportView>();
		if(page != null && pageSize != null){
			newReportViews = (List<ReportView>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newReportViews = (List<ReportView>) hibernateTemplate.findByCriteria(criteria);
		}
		return newReportViews;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}


	@Override
	public DetachedCriteria getCriteriaByDeviceId(ReportViewId reportViewId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReportView.class);
		
		criteria.add(Property.forName("reportViewId.deviceId").eq(reportViewId.getDeviceId()));
		return criteria;
	}

	
	

	
	
	
	
	
}

