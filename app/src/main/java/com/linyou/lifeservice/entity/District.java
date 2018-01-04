package com.linyou.lifeservice.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 区域数据类型
 * @author 志强
 *
 */
public class District implements Serializable{
	
	/**private String createTime;//创建时间
	 private String id;//区域id
	 private String name;//区域名称
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addTime;//创建时间
	private String storeId;//区域id
	private String storeName;//区域名称
	private List<Classfication> gcategory;

	public List<Classfication> getClasslist() {
		return gcategory;
	}

	public void setClasslist(List<Classfication> classlist) {
		this.gcategory = classlist;
	}

	@Override
	public String toString() {
		return "District{" +
				"addTime='" + addTime + '\'' +
				", storeId='" + storeId + '\'' +
				", storeName='" + storeName + '\'' +
				", gcategory=" + gcategory.toString() +
				'}';
	}

	public String getCreateTime() {
		return addTime;
	}
	public void setCreateTime(String createTime) {
		this.addTime = createTime;
	}
	public String getId() {
		return storeId;
	}
	public void setId(String id) {
		this.storeId = id;
	}
	public String getName() {
		return storeName;
	}
	public void setName(String name) {
		this.storeName = name;
	}


}
