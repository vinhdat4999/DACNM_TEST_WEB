package com.blas.fish.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private LocalDateTime orderTime;
	private String zalo;
	private String facebook;
	private String phoneNum;
	private String city;
	private String district;
	private String ward;
	private String address;
	private String note;
	private String status;
	private double total;

	public Order() {
		super();
	}

	public Order(String id, String name, LocalDateTime orderTime, String zalo, String facebook, String phoneNum,
			String city, String district, String ward, String address, String note, String status, double total) {
		super();
		this.id = id;
		this.name = name;
		this.orderTime = orderTime;
		this.zalo = zalo;
		this.facebook = facebook;
		this.phoneNum = phoneNum;
		this.city = city;
		this.district = district;
		this.ward = ward;
		this.address = address;
		this.note = note;
		this.status = status;
		this.total = total;
	}

	@Id
	@Column(name = "id", length = 10, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "orderTime", nullable = false)
	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name = "zalo", length = 50)
	public String getZalo() {
		return zalo;
	}

	public void setZalo(String zalo) {
		this.zalo = zalo;
	}

	@Column(name = "facebook", length = 50)
	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	@Column(name = "phoneNum", length = 20, nullable = false)
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Column(name = "city", length = 50, nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "district", length = 50, nullable = false)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "ward", length = 50, nullable = false)
	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	@Column(name = "address", length = 200, nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "note", length = 50)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "status", length = 30)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "total")
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", orderTime=" + orderTime + ", zalo=" + zalo + ", facebook="
				+ facebook + ", phoneNum=" + phoneNum + ", city=" + city + ", district=" + district + ", ward=" + ward
				+ ", address=" + address + ", note=" + note + ", status=" + status + ", total=" + total + "]";
	}

}
