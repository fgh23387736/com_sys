package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.ProjectDao;
import com.sys.entity.Project;


@Component(value="projectDaoImpl")
public class ProjectDaoImpl implements ProjectDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Project getById(Integer id) {
		return hibernateTemplate.get(Project.class, id);
	}
	
	@Override
	public Project add(Project project) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(project);
		return project;
	}
	
	@Override
	public void update(Project project) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(project);
	}
	
	@Override
	public void delete(Project project) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(project);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Project> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Project> newProjects = new ArrayList<Project>();
		if(page != null && pageSize != null){
			newProjects = (List<Project>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newProjects = (List<Project>) hibernateTemplate.findByCriteria(criteria);
		}
		return newProjects;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Project.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("projectId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByName(Project project) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Project.class);
		criteria.add(Property.forName("name").like("%"+project.getName()+"%"));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTheName(String name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Project.class);
		criteria.add(Property.forName("name").eq(name));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTheAddress(String address) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Project.class);
		criteria.add(Property.forName("address").eq(address));
		return criteria;
	}

	
	

	
	
	
	
	
}

