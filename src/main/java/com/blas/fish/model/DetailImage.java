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
@Table(name = "detailImage")
public class DetailImage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private Fish fish;
	private byte[] image;

	public DetailImage() {
		super();
	}
	
	public DetailImage(String id, Fish fish) {
		super();
		this.id = id;
		this.fish = fish;
	}
	
	public DetailImage(String id, Fish fish, byte[] image) {
		super();
		this.id = id;
		this.fish = fish;
		this.image = image;
	}

	@Id
	@Column(name = "id", length = 100, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fishId", nullable = false, //
			foreignKey = @ForeignKey(name = "fk_detailImage"))
	public Fish getFish() {
		return fish;
	}

	public void setFish(Fish fish) {
		this.fish = fish;
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
		return "DetailImage [id=" + id + ", fish=" + fish + ", image=" + Arrays.toString(image) + "]";
	}
	
}
