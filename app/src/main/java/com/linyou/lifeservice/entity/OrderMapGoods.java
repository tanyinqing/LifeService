package com.linyou.lifeservice.entity;

/**
 * 订单中的商品
 * @author 志强
 *
 */
public class OrderMapGoods {
	
	private String quantity;//购买数量
	private Goods goods;//商品
	private String goodsId;
	private String price;//价格

	@Override
	public String toString() {
		return "OrderMapGoods{" +
				"quantity='" + quantity + '\'' +
				", goods=" + goods +
				", goodsId='" + goodsId + '\'' +
				", price='" + price + '\'' +
				'}';
	}

	public String getCount() {
		return quantity;
	}
	public void setCount(String count) {
		this.quantity = count;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getId() {
		return goodsId;
	}
	public void setId(String id) {
		this.goodsId = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
