package com.blas.fish.model;

public class CartItem {
	private Fish fish;
	private int quantity;

	public CartItem() {
		super();
	}

	public CartItem(Fish fish, int quantity) {
		super();
		this.fish = fish;
		this.quantity = quantity;
	}

	public Fish getFish() {
		return fish;
	}

	public void setFish(Fish fish) {
		this.fish = fish;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartItem [fish=" + fish + ", quantity=" + quantity + "]";
	}

}
