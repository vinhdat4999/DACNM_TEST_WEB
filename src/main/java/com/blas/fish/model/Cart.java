package com.blas.fish.model;

public class Cart {

	private String fishId;
	private int quantity;

	public Cart() {
		super();
	}

	public Cart(String fishId, int quantity) {
		super();
		this.fishId = fishId;
		this.quantity = quantity;
	}

	public String getFishId() {
		return fishId;
	}

	public void setFishId(String fishId) {
		this.fishId = fishId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Cart [fishId=" + fishId + ", quantity=" + quantity + "]";
	}

}
