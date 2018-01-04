package com.linyou.lifeservice.entity;

import java.io.Serializable;

/**
 * 收件地址
 * @author 志强
 *
 */
public class DeliveryAddress implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;//地址
	private String creatTime;//创建时间
	private String addrId;//地址id
	private String consignee;//收件人
	private String phoneMob;//收件人电话

	@Override
	public String toString() {
		return "DeliveryAddress{" +
				"address='" + address + '\'' +
				", creatTime='" + creatTime + '\'' +
				", addrId='" + addrId + '\'' +
				", consignee='" + consignee + '\'' +
				", phoneMob='" + phoneMob + '\'' +
				'}';
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreateTime() {
		return creatTime;
	}
	public void setCreateTime(String createTime) {
		this.creatTime = createTime;
	}
	public String getId() {
		return addrId;
	}
	public void setId(String id) {
		this.addrId = id;
	}
	public String getReciever() {
		return consignee;
	}
	public void setReciever(String reciever) {
		this.consignee = reciever;
	}
	public String getRecieverPhone() {
		return phoneMob;
	}
	public void setRecieverPhone(String recieverPhone) {
		this.phoneMob = recieverPhone;
	}
	
	
}
