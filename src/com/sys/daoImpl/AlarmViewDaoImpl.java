package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.AlarmViewDao;
import com.sys.entity.AlarmView;
import com.sys.entity.AlarmViewId;


@Component(value="alarmViewDaoImpl")
public class AlarmViewDaoImpl implements AlarmViewDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<AlarmView> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<AlarmView> newAlarmViews = new ArrayList<AlarmView>();
		if(page != null && pageSize != null){
			newAlarmViews = (List<AlarmView>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newAlarmViews = (List<AlarmView>) hibernateTemplate.findByCriteria(criteria);
		}
		return newAlarmViews;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}


	@Override
	public DetachedCriteria getCriteriaByDeviceId(AlarmViewId alarmViewId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AlarmView.class);
		
		criteria.add(Property.forName("alarmViewId.deviceId").eq(alarmViewId.getDeviceId()));
		return criteria;
	}

	
	

	
	
	
	
	
}

