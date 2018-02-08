package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.ReportRecordDao;
import com.sys.entity.ReportRecord;


@Component(value="reportRecordDaoImpl")
public class ReportRecordDaoImpl implements ReportRecordDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public ReportRecord getById(Integer id) {
		return hibernateTemplate.get(ReportRecord.class, id);
	}
	
	@Override
	public ReportRecord add(ReportRecord reportRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(reportRecord);
		return reportRecord;
	}
	
	@Override
	public void update(ReportRecord reportRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(reportRecord);
	}
	
	@Override
	public void delete(ReportRecord reportRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(reportRecord);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<ReportRecord> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<ReportRecord> newReportRecords = new ArrayList<ReportRecord>();
		if(page != null && pageSize != null){
			newReportRecords = (List<ReportRecord>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newReportRecords = (List<ReportRecord>) hibernateTemplate.findByCriteria(criteria);
		}
		return newReportRecords;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReportRecord.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("reportRecordId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByDevice(ReportRecord reportRecord) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReportRecord.class);
		criteria.add(Property.forName("device").eq(reportRecord.getDevice()));
		return criteria;
	}

	
	

	
	
	
	
	
}

