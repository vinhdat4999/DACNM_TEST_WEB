package com.blas.fish.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ProductForm {

	private String id;
	private String name;
	private String categoryId;
	private CommonsMultipartFile image;
	private double priceOrigin;
	private double pricePromo;
	private String description;
	private boolean isSelling;
	private int count;
	private int view;
	private boolean isInStock;

	public ProductForm() {
		super();
	}

	public ProductForm(String id, String name, String categoryId, CommonsMultipartFile image, double priceOrigin,
			double pricePromo, String description, boolean isSelling, int count, int view, boolean isInStock) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.image = image;
		this.priceOrigin = priceOrigin;
		this.pricePromo = pricePromo;
		this.description = description;
		this.isSelling = isSelling;
		this.count = count;
		this.view = view;
		this.isInStock = isInStock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public CommonsMultipartFile getImage() {
		return image;
	}

	public void setImage(CommonsMultipartFile image) {
		this.image = image;
	}

	public double getPriceOrigin() {
		return priceOrigin;
	}

	public void setPriceOrigin(double priceOrigin) {
		this.priceOrigin = priceOrigin;
	}

	public double getPricePromo() {
		return pricePromo;
	}

	public void setPricePromo(double pricePromo) {
		this.pricePromo = pricePromo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isIsSelling() {
		return isSelling;
	}

	public void setIsSelling(boolean isSelling) {
		this.isSelling = isSelling;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public boolean isIsInStock() {
		return isInStock;
	}

	public void setIsInStock(boolean isInStock) {
		this.isInStock = isInStock;
	}

	@Override
	public String toString() {
		return "ProductForm [id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", fileDatas=" + image
				+ ", priceOrigin=" + priceOrigin + ", pricePromo=" + pricePromo + ", description=" + description
				+ ", isSelling=" + isSelling + ", count=" + count + ", view=" + view + "]";
	}

}
