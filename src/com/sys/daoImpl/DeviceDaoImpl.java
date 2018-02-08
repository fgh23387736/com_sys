package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.DeviceDao;
import com.sys.entity.Device;


@Component(value="deviceDaoImpl")
public class DeviceDaoImpl implements DeviceDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Device getById(String id) {
		return hibernateTemplate.get(Device.class, id);
	}
	
	@Override
	public Device add(Device device) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(device);
		return device;
	}
	
	@Override
	public void update(Device device) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(device);
	}
	
	@Override
	public void delete(Device device) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(device);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Device> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Device> newDevices = new ArrayList<Device>();
		if(page != null && pageSize != null){
			newDevices = (List<Device>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newDevices = (List<Device>) hibernateTemplate.findByCriteria(criteria);
		}
		return newDevices;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(String[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Device.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("deviceId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProjectAndName(Device device) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Device.class);
		criteria.add(Property.forName("name").like("%"+device.getName()+"%"));
		criteria.add(Property.forName("project").eq(device.getProject()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTheName(String name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Device.class);
		criteria.add(Property.forName("name").eq(name));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTheAddress(String address) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Device.class);
		criteria.add(Property.forName("address").eq(address));
		return criteria;
	}

	
	

	
	
	
	
	
}

