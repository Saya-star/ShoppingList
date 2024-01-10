package com.example.shopping.entity;

import java.time.LocalDate;

//import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long shopId;

	@Column
	 /** ManyToOne */
	private long appUserId;
	
	@Column
	private String shopName;
	
	@Column
	private LocalDate createdDate;

	public Long getShopId(){
		return shopId;
	}
	
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	public Long getAppUserId() {
		return appUserId;
	}
	
	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}
	
	public String getShopName() {
		return shopName;
	}
	
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
}