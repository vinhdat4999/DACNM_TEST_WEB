package com.blas.fish.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fish")
public class Fish implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Category category;
	private byte[] image;
	private double priceOrigin;
	private double pricePromo;
	private String description;
	private boolean isSelling;
	private int count;
	private int view;
	private boolean isInStock;

	public Fish() {
		super();
	}
	
	public Fish(String id, String name, Category category, double priceOrigin, double pricePromo, String description,
			boolean isSelling, int count, int view, boolean isInStock) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.priceOrigin = priceOrigin;
		this.pricePromo = pricePromo;
		this.description = description;
		this.isSelling = isSelling;
		this.count = count;
		this.view = view;
		this.isInStock = isInStock;
	}

	public Fish(String id, String name, Category category, byte[] image, double priceOrigin, double pricePromo,
			String description, boolean isSelling, int count, int view, boolean isInStock) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.image = image;
		this.priceOrigin = priceOrigin;
		this.pricePromo = pricePromo;
		this.description = description;
		this.isSelling = isSelling;
		this.count = count;
		this.view = view;
		this.isInStock = isInStock;
	}

	@Id
	@Column(name = "id", length = 250, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 250, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId", nullable = false, //
			foreignKey = @ForeignKey(name = "fk_fish"))
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Lob
	@Column(name = "image", length = Integer.MAX_VALUE)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Column(name = "priceOrigin", nullable = false)
	public double getPriceOrigin() {
		return priceOrigin;
	}

	public void setPriceOrigin(double priceOrigin) {
		this.priceOrigin = priceOrigin;
	}

	@Column(name = "pricePromo", nullable = false)
	public double getPricePromo() {
		return pricePromo;
	}

	public void setPricePromo(double pricePromo) {
		this.pricePromo = pricePromo;
	}

	@Column(name = "description", length = Integer.MAX_VALUE)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "isSelling")
	public boolean isIsSelling() {
		return isSelling;
	}

	public void setIsSelling(boolean isSelling) {
		this.isSelling = isSelling;
	}
	
	@Column(name = "count")
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Column(name = "view")
	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	@Column(name = "isInStock")
	public boolean isIsInStock() {
		return isInStock;
	}

	public void setIsInStock(boolean isInStock) {
		this.isInStock = isInStock;
	}

	@Override
	public String toString() {
		return "Fish [id=" + id + ", name=" + name + ", category=" + category + ", image=" + image.length
				+ ", priceOrigin=" + priceOrigin + ", pricePromo=" + pricePromo + ", description=" + description
				+ ", isSelling=" + isSelling + ", count=" + count + ", view=" + view + "]";
	}
	
	

}
