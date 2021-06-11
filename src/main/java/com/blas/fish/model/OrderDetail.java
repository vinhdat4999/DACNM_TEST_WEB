package com.blas.fish.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orderDetail")
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private Order order;
	private Fish fish;
	private double price;
	private int quantity;

	public OrderDetail() {
		super();
	}

	public OrderDetail(String id, Order order, Fish fish, double price, int quantity) {
		super();
		this.id = id;
		this.order = order;
		this.fish = fish;
		this.price = price;
		this.quantity = quantity;
	}

	@Id
	@Column(name = "id", length = 10, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId", nullable = false, //
			foreignKey = @ForeignKey(name = "fk_orderDetail1"))
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fishId", nullable = false, //
			foreignKey = @ForeignKey(name = "fk_orderDetail2"))
	public Fish getFish() {
		return fish;
	}

	public void setFish(Fish fish) {
		this.fish = fish;
	}

	@Column(name = "price", nullable = false)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "quantity", nullable = false)
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", order=" + order + ", fish=" + fish + ", price=" + price + ", quantity="
				+ quantity + "]";
	}
	
}
