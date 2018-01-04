package com.linyou.lifeservice.entity;

public class GoodsImage {
	/*
	private String id;
	private String goods;
	private String pic;
	 */
	private String imageId;
	private String goodsId;
	private String thumbnail;

	@Override
	public String toString() {
		return "GoodsImage{" +
				"imageId='" + imageId + '\'' +
				", goodsId='" + goodsId + '\'' +
				", thumbnail='" + thumbnail + '\'' +
				'}';
	}

	public String getId() {
		return imageId;
	}
	public void setId(String id) {
		this.imageId = id;
	}
	public String getGoods() {
		return goodsId;
	}
	public void setGoods(String goods) {
		this.goodsId = goods;
	}
	public String getPic() {
		return thumbnail;
	}
	public void setPic(String pic) {
		this.thumbnail = pic;
	}
}
