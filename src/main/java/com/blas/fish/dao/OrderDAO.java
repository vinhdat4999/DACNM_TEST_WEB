package com.blas.fish.dao;

import java.util.List;

import com.blas.fish.model.Order;
import com.blas.fish.model.OrderDetail;
import com.blas.fish.model.PaginationResult;

public interface OrderDAO {
	
	public String getNewOrderId();
	
	public void saveOrder(Order order);

	public PaginationResult<Order> listOrder(int page, int maxResult, int maxNavigationPage);

	public PaginationResult<Order> listOrderWaiting(int page, int maxResult, int maxNavigationPage);

	public PaginationResult<Order> listOrderDone(int page, int maxResult, int maxNavigationPage);

	public int numberOfWaitingOrder();

	public PaginationResult<Order> listOrderByUser(int page, int maxResult, int maxNavigationPage,
			String userName);

	public Order getOrder(String orderId);

	public List<OrderDetail> listOrderDetailModels(String orderId);
	
	public void cancelOrder(String orderId);
	
	public void doneOrder(String orderId);
	
	public void deliveringOrder(String orderId);
	
	
}
