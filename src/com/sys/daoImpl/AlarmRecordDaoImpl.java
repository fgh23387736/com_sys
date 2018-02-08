package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.AlarmRecordDao;
import com.sys.entity.AlarmRecord;


@Component(value="alarmRecordDaoImpl")
public class AlarmRecordDaoImpl implements AlarmRecordDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public AlarmRecord getById(Integer id) {
		return hibernateTemplate.get(AlarmRecord.class, id);
	}
	
	@Override
	public AlarmRecord add(AlarmRecord alarmRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(alarmRecord);
		return alarmRecord;
	}
	
	@Override
	public void update(AlarmRecord alarmRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(alarmRecord);
	}
	
	@Override
	public void delete(AlarmRecord alarmRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(alarmRecord);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<AlarmRecord> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<AlarmRecord> newAlarmRecords = new ArrayList<AlarmRecord>();
		if(page != null && pageSize != null){
			newAlarmRecords = (List<AlarmRecord>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newAlarmRecords = (List<AlarmRecord>) hibernateTemplate.findByCriteria(criteria);
		}
		return newAlarmRecords;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AlarmRecord.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("alarmRecordId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByDevice(AlarmRecord alarmRecord) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AlarmRecord.class);
		criteria.add(Property.forName("device").eq(alarmRecord.getDevice()));
		return criteria;
	}

	
	

	
	
	
	
	
}

