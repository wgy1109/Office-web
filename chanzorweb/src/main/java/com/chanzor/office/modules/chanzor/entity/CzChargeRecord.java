/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.chanzor.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.chanzor.office.common.persistence.DataEntity;

public class CzChargeRecord extends DataEntity<CzChargeRecord> {
	
	private static final long serialVersionUID = 1L;
	private Date createTime;		// create_time
	private String productId;		// product_id
	private String amount;		// amount
	private String alipayType;		// alipay_type
	private Date chargeArrivalTime;		// charge_arrival_time
	private String ip;		// ip
	
	public CzChargeRecord() {
		super();
	}

	public CzChargeRecord(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getAlipayType() {
		return alipayType;
	}

	public void setAlipayType(String alipayType) {
		this.alipayType = alipayType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getChargeArrivalTime() {
		return chargeArrivalTime;
	}

	public void setChargeArrivalTime(Date chargeArrivalTime) {
		this.chargeArrivalTime = chargeArrivalTime;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}