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
import com.blas.fish.dao.OrderDAO;
import com.blas.fish.model.Order;
import com.blas.fish.model.OrderDetail;
import com.blas.fish.model.PaginationResult;

public class OrderDAOImpl implements OrderDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private FishDAO productDAO;

	@Override
	public String getNewOrderId() {
		// TODO Auto-generated method stub
		Random rd = new Random();
		int orderId = rd.nextInt(900000000) + 100000000;
		do {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Order.class);
			crit.add(Restrictions.eq("id", orderId + ""));
			Order order = (Order) crit.uniqueResult();
			if (order == null)
				return orderId + "";
		} while (true);
	}

	@Override
	public void saveOrder(Order order) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().persist(order);
		this.sessionFactory.getCurrentSession().flush();
	}

	@Override
	public PaginationResult<Order> listOrder(int page, int maxResult, int maxNavigationPage) {
		// TODO Auto-generated method stub
		String sql = "select new " + Order.class.getName() + "(ord.id, ord.name,"
				+ " ord.orderTime, ord.zalo, ord.facebook, ord.phoneNum, ord.city, ord.district, ord.ward, ord.address, ord.note, ord.status, ord.total) from "
				+ Order.class.getName() + " ord order by ord.orderTime desc";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
//		List<Order> list = query.list();
		return new PaginationResult<Order>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<Order> listOrderByUser(int page, int maxResult, int maxNavigationPage, String userName) {
		// TODO Auto-generated method stub
		String sql = "select new " + Order.class.getName() + "(ord.id, ord.name,"
				+ " ord.orderTime, ord.zalo, ord.facebook, ord.phoneNum, ord.city, ord.district, ord.ward, ord.address, ord.note, ord.status, ord.total) from "
				+ Order.class.getName() + " ord where ord.username= '" + userName + "' order by ord.orderTime desc";
		System.out.println("sql: " + sql);
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		List<Order> list = query.list();
		return new PaginationResult<Order>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public Order getOrder(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Order.class);
		crit.add(Restrictions.eq("id", id));
		Order order = (Order) crit.uniqueResult();
		if (order == null)
			return null;
		return order;
	}

	@Override
	public List<OrderDetail> listOrderDetailModels(String id) {
		// TODO Auto-generated method stub
		String sql = "select new " + OrderDetail.class.getName()
				+ "(ord.id, ord.productId, ord.price, ord.quantity, ord.orderId) from " + OrderDetail.class.getName()
				+ " ord where ord.orderId ='" + id + "'";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		List<OrderDetail> list = query.list();
		return list;
	}

	@Override
	public PaginationResult<Order> listOrderWaiting(int page, int maxResult, int maxNavigationPage) {
		// TODO Auto-generated method stub
		String sql = "select new " + Order.class.getName() + "(ord.id, ord.name,"
				+ " ord.orderTime, ord.zalo, ord.facebook, ord.phoneNum, ord.city, ord.district, ord.ward, ord.address, ord.note, ord.status, ord.total) from "
				+ Order.class.getName() + " ord where ord.status= 'Đặt hàng thành công' order by ord.orderTime desc";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		List<Order> list = query.list();
		return new PaginationResult<Order>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<Order> listOrderDone(int page, int maxResult, int maxNavigationPage) {
		// TODO Auto-generated method stub
		String sql = "select new " + Order.class.getName() + "(ord.id, ord.name,"
				+ " ord.orderTime, ord.zalo, ord.facebook, ord.phoneNum, ord.city, ord.district, ord.ward, ord.address, ord.note, ord.status, ord.total) from "
				+ Order.class.getName() + " ord where ord.status= 'Hoàn thành' order by ord.orderTime desc";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		List<Order> list = query.list();
		return new PaginationResult<Order>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public int numberOfWaitingOrder() {
		// TODO Auto-generated method stub
		String sql = "select new " + Order.class.getName() + "(ord.id, ord.name,"
				+ " ord.orderTime, ord.zalo, ord.facebook, ord.phoneNum, ord.city, ord.district, ord.ward, ord.address, ord.note, ord.status, ord.total) from "
				+ Order.class.getName() + " ord where ord.status= 'Đặt hàng thành công' order by ord.orderTime desc";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		List<Order> list = query.list();
		return list.size();
	}

	@Override
	public void cancelOrder(String orderId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE " + Order.class.getName() + " set status = 'Đã hủy' WHERE id = '" + orderId + "'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void doneOrder(String orderId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE " + Order.class.getName() + " set status = 'Hoàn thành' WHERE id = '" + orderId + "'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void deliveringOrder(String orderId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE " + Order.class.getName() + " set status = 'Đang giao' WHERE id = '" + orderId + "'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}
}
