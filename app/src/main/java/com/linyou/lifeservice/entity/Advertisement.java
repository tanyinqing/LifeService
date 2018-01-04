package com.linyou.lifeservice.entity;

import java.io.Serializable;

/**
 * 广告
 * @author 志强
 *
 */
public class Advertisement implements Serializable{
	/*
	private String content; //广告内容
	private String createTime;
	private String id;//广告id
	private String name;//广告名称
	private String isChain; //是否是链接广告
	private String picurl;//图片地址
	 */
	private String content; //广告内容
	private String time;
	private String id;//广告id
	private String title;//广告名称
	private String adShow; //是否是链接广告
	private String image;//图片地址

	@Override
	public String toString() {
		return "Advertisement{" +
				"content='" + content + '\'' +
				", time='" + time + '\'' +
				", id='" + id + '\'' +
				", title='" + title + '\'' +
				", adShow='" + adShow + '\'' +
				", image='" + image + '\'' +
				'}';
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return time;
	}
	public void setCreateTime(String createTime) {
		this.time = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return title;
	}
	public void setName(String name) {
		this.title = name;
	}
	public String getIsChain() {
		return adShow;
	}
	public void setIsChain(String isChain) {
		this.adShow = isChain;
	}
	public String getPicurl() {
		return image;
	}
	public void setPicurl(String picurl) {
		this.image = picurl;
	}
	
	
	
}
