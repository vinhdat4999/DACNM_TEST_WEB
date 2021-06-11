package com.blas.fish.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private byte[] image;

	public Category() {
		super();
	}

	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Category(String id, String name, byte[] image) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
	}
	
	@Id
	@Column(name = "id", length = 30, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 30, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob
	@Column(name = "image", length = Integer.MAX_VALUE)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", image=" + Arrays.toString(image) + "]";
	}
	
	
}
