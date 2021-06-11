package com.blas.fish.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blas.fish.dao.AdminDAO;
import com.blas.fish.model.Admin;

@Transactional
@Repository
public class AdminDAOImpl implements AdminDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Admin findAdmin(String username) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Admin.class);
		criteria.add(Restrictions.eq("username", username));
		return (Admin) criteria.uniqueResult();
	}

	@Override
	public void save(Admin admin) {
		// TODO Auto-generated method stub
		String username = admin.getUsername();
		Admin admin2 = null;
		boolean isNew = false;
		if (username != null) {
			admin2 = this.findAdmin(username);
		}
		if (admin2 == null) {
			isNew = true;
			admin2 = new Admin();
		}
		admin2.setUsername(username);
		admin2.setName(admin.getUsername());
		admin2.setPassword(admin.getPassword());
		admin2.setRole(admin.getRole());
		if (isNew) {
			this.sessionFactory.getCurrentSession().persist(admin2);
		}
		this.sessionFactory.getCurrentSession().flush();
	}
}