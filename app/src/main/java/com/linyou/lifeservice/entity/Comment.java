package com.linyou.lifeservice.entity;

import com.linyou.lifeservice.utils.DateUtil;

import java.text.SimpleDateFormat;

/*
获取评论列表时使用的封装对象
 */
public class Comment {
	private String comment;
	private String createTime;
	private Goods goods;
	private String id;
	private User user;

	@Override
	public String toString() {
		return "Comment{" +
				"comment='" + comment + '\'' +
				", createTime='" + createTime + '\'' +
				", goods=" + goods +
				", id='" + id + '\'' +
				", user=" + user +
				'}';
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return comment;
	}
	public void setContent(String content) {
		this.comment = content;
	}
	public String getCreateTime() {
		return	DateUtil.TimeStamp2Date(createTime, "yyyy-MM-dd HH:mm:ss ");
		//return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
