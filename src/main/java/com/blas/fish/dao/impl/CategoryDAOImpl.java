package com.blas.fish.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.blas.fish.dao.CategoryDAO;
import com.blas.fish.model.Category;
import com.blas.fish.model.Fish;
import com.blas.fish.utils.ToURLId;

@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Category findCategory(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Category.class);
		crit.add(Restrictions.eq("id", id));
		return (Category) crit.uniqueResult();
	}

	@Override
	public List<Category> getCategoryList() {
		// TODO Auto-generated method stub
		String sql = "select new " + Category.class.getName() + "(c.id,c.name,c.image) from " + Category.class.getName()
				+ " c";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		List<Category> list = query.list();
		return list;
	}

	@Override
	public void save(Category category) {
		// TODO Auto-generated method stub
		String id = category.getId();
		Category category2 = null;
		boolean isNew = false;
		if (id != null) {
			category2 = this.findCategory(id);
		}
		if (category2 == null) {
			isNew = true;
			category2 = new Category();
		}
		category2.setId(category.getId());
		category2.setName(category.getName());
		category2.setImage(category.getImage());
		if (isNew) {
			System.out.println("new");
			this.sessionFactory.getCurrentSession().persist(category2);
		} else {
			System.out.println("old");
		}
		this.sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void deleteCategory(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM " + Category.class.getName() + "  WHERE id = '" + id + "'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public boolean checkHaveProductInCategory(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Fish.class);
		crit.add(Restrictions.eq("category.id", id));
		List<Fish> list = crit.list();
		if (list.size() == 0)
			return false;
		return true;
	}

}
