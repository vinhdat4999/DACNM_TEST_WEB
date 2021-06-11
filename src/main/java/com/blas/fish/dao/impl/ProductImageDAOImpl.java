package com.blas.fish.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.blas.fish.dao.ProductImageDAO;
import com.blas.fish.model.DetailImage;
import com.blas.fish.model.OrderDetail;
import com.blas.fish.model.PaginationResult;

@Transactional
public class ProductImageDAOImpl implements ProductImageDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public DetailImage findProductImage(String imageId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(DetailImage.class);
		crit.add(Restrictions.eq("id", imageId));
		return (DetailImage) crit.uniqueResult();
	}

	@Override
	public void addImage(DetailImage productImage) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.persist(productImage);
	}

//	@Override
//	public ProductImageModel findProductImageModel(String imageId) {
//		// TODO Auto-generated method stub
//		DetailImage productImage = this.findProductImage(imageId);
//		if (productImage == null) {
//			return null;
//		}
//		return new ProductImageModel(productImage);
//	}
//
//	@Override
//	public List<ProductImageModel> getImageIdList(String productId) {
//		// TODO Auto-generated method stub
//		String sql = "select new " + ProductImageModel.class.getName() + "(p.id, p.productId) from "
//				+ DetailImage.class.getName() + " p where p.productId ='" + productId + "'";
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery(sql);
//		List<ProductImageModel> list = query.list();
//		return list;
//	}
//
//	@Override
//	public void deleteImage(String imageId) {
//		// TODO Auto-generated method stub
//		String hql = "DELETE FROM " + DetailImage.class.getName() + " WHERE id = '" + imageId + "'";
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery(hql);
//		query.executeUpdate();
//	}
//
//	@Override
//	public void addImage(DetailImage productImage) {
//		// TODO Auto-generated method stub
//		Session session = sessionFactory.getCurrentSession();
//		session.persist(productImage);
//	}
//
//	@Override
//	public PaginationResult<ProductImageModel> queryProductImages(int page, int maxResult, int maxNavigationPage) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public PaginationResult<ProductImageModel> queryProductImages(int page, int maxResult, int maxNavigationPage,
//			String likeName) {
//		// TODO Auto-generated method stub
//		String sql = "Select new " + ProductImageModel.class.getName() //
//				+ "(p.id, p.productId) " + " from " + DetailImage.class.getName() + " p";
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery(sql);
//		return new PaginationResult<ProductImageModel>(query, page, maxResult, maxNavigationPage);
//	}

	@Override
	public DetailImage findDetailImage(String imageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<DetailImage> queryProductImages(int page, int maxResult, int maxNavigationPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<DetailImage> queryProductImages(int page, int maxResult, int maxNavigationPage,
			String likeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetailImage> getImageIdList(String productId) {
		// TODO Auto-generated method stub
		String sql = "select new " + DetailImage.class.getName()
				+ "(d.id, d.fish) from " + DetailImage.class.getName()
				+ " d where d.fish.id ='" + productId + "'";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		List<DetailImage> list = query.list();
		return list;
	}

	@Override
	public void deleteImage(String imageId) {
		// TODO Auto-generated method stub
		String hql = "DELETE FROM " + DetailImage.class.getName() + " WHERE id = '" + imageId + "'";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}
}
