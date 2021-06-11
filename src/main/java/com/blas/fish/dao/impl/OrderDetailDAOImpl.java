package com.blas.fish.dao.impl;

import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.blas.fish.dao.FishDAO;
import com.blas.fish.dao.OrderDetailDAO;
import com.blas.fish.model.Order;
import com.blas.fish.model.OrderDetail;
import com.blas.fish.model.PaginationResult;

public class OrderDetailDAOImpl implements OrderDetailDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String getNewOrderDetailId() {
		// TODO Auto-generated method stub
		Random rd = new Random();
		int orderDetailId = rd.nextInt(900000000) + 100000000;
		do {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(OrderDetail.class);
			crit.add(Restrictions.eq("id", orderDetailId + ""));
			OrderDetail order = (OrderDetail) crit.uniqueResult();
			if (order == null)
				return orderDetailId + "";
		} while (true);
	}

	@Override
	public void saveOrderDetail(OrderDetail orderDetail) {
		// TODO Auto-generated method stub if (isNew) {
		this.sessionFactory.getCurrentSession().persist(orderDetail);
		this.sessionFactory.getCurrentSession().flush();
	}

	@Override
	public List<OrderDetail> getOrderDetail(String orderId) {
		// TODO Auto-generated method stub
		String sql = "select new " + OrderDetail.class.getName()
				+ "(ord.id, ord.order, ord.fish, ord.price, ord.quantity) from " + OrderDetail.class.getName()
				+ " ord where ord.order.id ='" + orderId + "'";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		return query.list();
	}
}
