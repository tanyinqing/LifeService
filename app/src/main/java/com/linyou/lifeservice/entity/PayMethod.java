package com.linyou.lifeservice.entity;

public class PayMethod {
	private String paymentId;//支付方式id
	private String paymentName;//支付名称

	@Override
	public String toString() {
		return "PayMethod{" +
				"paymentId='" + paymentId + '\'' +
				", paymentName='" + paymentName + '\'' +
				'}';
	}

	public String getId() {
		return paymentId;
	}
	public void setId(String id) {
		this.paymentId = id;
	}
	public String getName() {
		return paymentName;
	}
	public void setName(String name) {
		this.paymentName = name;
	}
	
}
