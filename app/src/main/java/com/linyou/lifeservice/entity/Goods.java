package com.linyou.lifeservice.entity;

import com.linyou.lifeservice.utils.DateUtil;

import java.util.List;

/**
 * 商品
 * @author 志强
 *
 */
public class Goods {
	private String amount;//商品库存数量
	//private String addTime;//创建时间
	private String cateName;//商品描述
	private String goodsCode;//商品编码
	private String goodsId;//商品id
	private String goodsName;//商品名称
	private String defaultImage;//商品默认图片的地址
	private String price;//商品单价
	private String addTime;//上架时间
	private String salesvolumn;//销量
	private String ifShow;
	private String deleted;//1表示上架 0表示下架
	private int count;  //购买的商品的数量
	//数据库里是以字符串的形式存在的  这里是对象链表的格式  所有会发生错误
	private List<GoodsImage> picList;

	@Override
	public String toString() {
		return "Goods{" +
				"amount='" + amount + '\'' +
				", cateName='" + cateName + '\'' +
				", goodsCode='" + goodsCode + '\'' +
				", goodsId='" + goodsId + '\'' +
				", goodsName='" + goodsName + '\'' +
				", defaultImage='" + defaultImage + '\'' +
				", price='" + price + '\'' +
				", addTime='" + addTime + '\'' +
				", salesvolumn='" + salesvolumn + '\'' +
				", ifShow='" + ifShow + '\'' +
				", deleted='" + deleted + '\'' +
				", count=" + count +
				", picList=" + picList +
				'}';
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getIsPutaway() {
		return ifShow;
	}
	public void setIsPutaway(String isPutaway) {
		this.ifShow = isPutaway;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public List<GoodsImage> getPicList() {
		return picList;
	}
	public void setPicList(List<GoodsImage> picList) {
		this.picList = picList;
	}
	public String getAmount() {
		return amount;
		//return "9";
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/*
	public String getCreateTime() {
		return addTime;
	}
	public void setCreateTime(String createTime) {
		this.addTime = createTime;
	}
	 */

	public String getDescribes() {
		return cateName;
	}
	public void setDescribes(String describes) {
		this.cateName = describes;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getId() {
		return goodsId;
	}
	public void setId(String id) {
		this.goodsId = id;
	}
	public String getName() {
		return goodsName;
	}
	public void setName(String name) {
		this.goodsName = name;
	}
	public String getPic() {
		return defaultImage.trim();
	}
	public void setPic(String pic) {
		this.defaultImage = pic;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSaleTime() {
		return DateUtil.TimeStamp2Date(addTime, "yyyy-MM-dd HH:mm:ss ");
	}
	public void setSaleTime(String saleTime) {
		this.addTime = saleTime;
	}
	public String getSalesvolumn() {
		return salesvolumn;
	}
	public void setSalesvolumn(String salesvolumn) {
		this.salesvolumn = salesvolumn;
	}


}
