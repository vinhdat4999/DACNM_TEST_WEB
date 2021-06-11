package com.blas.fish.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.blas.fish.dao.FishDAO;
import com.blas.fish.model.Fish;
import com.blas.fish.model.OrderDetail;
import com.blas.fish.model.PaginationResult;

@Transactional
public class FishDAOImpl implements FishDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Fish findFish(String fishId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Fish.class);
		crit.add(Restrictions.eq("id", fishId));
		return (Fish) crit.uniqueResult();
	}

	@Override
	public List<Fish> getPopularFish() {
		// TODO Auto-generated method stub
		String sql = "select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) from "
				+ Fish.class.getName() + " f order by f.count desc";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		List<Fish> list = query.list();
		List<Fish> rtList = new ArrayList<Fish>();
		for (int i = 0; i < 3; i++) {
			rtList.add(list.get(i));
		}
		return rtList;
	}

	@Override
	public List<Fish> getStandoutFish() {
		// TODO Auto-generated method stub
		String sql = "select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) from "
				+ Fish.class.getName() + " f order by f.count desc";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		List<Fish> list = query.list();
		List<Fish> rtList = new ArrayList<Fish>();
		int i=0;
		while(rtList.size()<3) {
			if(list.get(i).isIsSelling()) {
				rtList.add(list.get(i));
			}
			i++;
		}
		return rtList;
	}

	@Override
	public List<Fish> getFishList() {
		// TODO Auto-generated method stub
		String sql = "select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) from "
				+ Fish.class.getName() + " f";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		List<Fish> list = query.list();
		return list;
	}

	@Override
	public PaginationResult<Fish> queryFishes(int page, int maxResult, int maxNavigationPage) {
		// TODO Auto-generated method stub
		return queryFishes(page, maxResult, maxNavigationPage, null);
	}

	@Override
	public PaginationResult<Fish> queryFishes(int page, int maxResult, int maxNavigationPage, String likeName) {
		// TODO Auto-generated method stub
		String sql = "Select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) "
				+ " from " + Fish.class.getName() + " f where f.isSelling = 1";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return new PaginationResult<Fish>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<Fish> queryFishesByCategory(int page, int maxResult, int maxNavigationPage,
			String likeName) {
		// TODO Auto-generated method stub
		String sql = "Select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) "
				+ " from " + Fish.class.getName() + " f where f.category = '" + likeName + "' and f.isSelling = 1";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return new PaginationResult<Fish>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<Fish> queryFishesByCategorySortPrice(int page, int maxResult, int maxNavigationPage,
			String likeName, String sortType) {
		// TODO Auto-generated method stub
		String sql = "Select new " + Fish.class.getName() //
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) "
				+ " from "//
				+ Fish.class.getName() + " f where f.category='" + likeName + "' and f.isSelling= 1  order by f.origin "
				+ sortType;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return new PaginationResult<Fish>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<Fish> searchFish(int page, int maxResult, int maxNavigationPage, String likeName) {
		// TODO Auto-generated method stub
		String sql = "Select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) "
				+ "from " + Fish.class.getName() + " f where f.name like '%" + likeName + "%' and f.isSelling= 1";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return new PaginationResult<Fish>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<Fish> searchAllFish(int page, int maxResult, int maxNavigationPage, String likeName) {
		// TODO Auto-generated method stub
		String sql = "Select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) "
				+ "from " + Fish.class.getName() + " f where f.name like '%" + likeName + "%'";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return new PaginationResult<Fish>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<Fish> searchFishSortPrice(int page, int maxResult, int maxNavigationPage, String likeName,
			String sortType) {
		// TODO Auto-generated method stub
		String sql = "Select new " + Fish.class.getName()
				+ "(f.id, f.name, f.category, f.priceOrigin, f.pricePromo, f.description, f.isSelling, f.count, f.view, f.isInStock) "
				+ " from " + Fish.class.getName() + " f where f.name like '%" + likeName
				+ "%' and f.isSelling= 1 order by f.price " + sortType;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return new PaginationResult<Fish>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public void save(Fish fish) {
		// TODO Auto-generated method stub
		String id = fish.getId();
		Fish fish2 = null;
		boolean isNew = false;
		if (id != null) {
			fish2 = this.findFish(id);
		}
		if (fish2 == null) {
			isNew = true;
			fish2 = new Fish();
		}
		fish2.setId(fish.getId());
		fish2.setName(fish.getName());
		fish2.setCategory(fish.getCategory());
		fish2.setImage(fish.getImage());
		fish2.setPriceOrigin(fish.getPriceOrigin());
		fish2.setPricePromo(fish.getPricePromo());
		fish2.setDescription(fish.getDescription());
		fish2.setIsSelling(fish.isIsSelling());
		fish2.setCount(fish.getCount());
		fish2.setView(fish.getView());
		fish2.setIsInStock(fish.isIsInStock());
		if (isNew) {
			System.out.println("new");
			this.sessionFactory.getCurrentSession().persist(fish2);
		} else {
			System.out.println("old");
		}
		this.sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void toggleSellingFish(String fishId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Fish fish = findFish(fishId);
		String hql = "";
		if (fish.isIsSelling()) {
			hql = "UPDATE " + Fish.class.getName() + " SET isSelling = 0 WHERE id = '" + fishId + "'";
		} else {
			hql = "UPDATE " + Fish.class.getName() + " SET isSelling = 1 WHERE id = '" + fishId + "'";
		}
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void IncViewOfFish(String fishId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE " + Fish.class.getName() + " SET view = view + 1 WHERE id = '" + fishId + "'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

}
