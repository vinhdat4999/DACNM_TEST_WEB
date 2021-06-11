package com.blas.fish.dao;

import java.util.List;

import com.blas.fish.model.OrderDetail;

public interface OrderDetailDAO {

	public String getNewOrderDetailId();
	
	public List<OrderDetail> getOrderDetail(String orderId);
	
	public void saveOrderDetail(OrderDetail orderDetail);
	
}
