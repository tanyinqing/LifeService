package com.linyou.lifeservice.model;

import com.linyou.lifeservice.adapter.OrderGoodsAapater;
import com.linyou.lifeservice.entity.Order;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.web.OrderWeb;

import android.content.Context;

public class OrderDetailModel extends BaseModel {

	private OrderWeb mOrderWeb;
	private OrderGoodsAapater mOrderGoodsAapater;
	public OrderDetailModel(Context mContext) {
		super(mContext);
		mOrderWeb = new OrderWeb(mContext);
		mOrderGoodsAapater = new OrderGoodsAapater(mContext);
	}
	
	public void loadOrder(String order_id,DataListener<Order> listener)
	{
		mOrderWeb.orderDetail(order_id, listener);
	}
	
	public OrderGoodsAapater getOrderGoodsAapater()
	{
		return mOrderGoodsAapater;
	}

}
