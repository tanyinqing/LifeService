package com.linyou.lifeservice.entity;

import java.io.Serializable;

/**
 * 区域数据对应的商品分类的类型
 * @author 志强
 *
 */
public class Classfication implements Serializable{

	/*商品类别的cate_id值
	商品类别的名称
	商品类别的图片*/
	private static final long serialVersionUID = 1L;
	private String cateId;//创建时间
	private String cateName;//区域id
	private String imageApp;//区域名称

	@Override
	public String toString() {
		return "Classfication{" +
				"cateId='" + cateId + '\'' +
				", cateName='" + cateName + '\'' +
				", imageApp='" + imageApp + '\'' +
				'}';
	}

	public String getCate_id() {
		return cateId;
	}

	public void setCate_id(String cate_id) {
		this.cateId = cate_id;
	}

	public String getClass_name() {
		return cateName;
	}

	public void setClass_name(String class_name) {
		this.cateName = class_name;
	}

	public String getImage_url() {
		return imageApp;
	}

	public void setImage_url(String image_url) {
		this.imageApp = image_url;
	}
}
