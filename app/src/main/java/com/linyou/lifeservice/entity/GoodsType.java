package com.linyou.lifeservice.entity;

/**
 * 商品分类
 * @author 志强
 *
 */
public class GoodsType {
	
	private String id;//类型id
	private String createTime;//创建时间
	private String name;//名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
