package com.linyou.lifeservice.impl;

import java.util.List;

import com.linyou.lifeservice.entity.Order;
import com.linyou.lifeservice.listener.DataListener;

public interface IOrderWeb {
	
	/**
	 * 创建订单
	 * @param goodsIds 商品id（id多个商品用C相隔,id和购买数量用N隔开）
	 * @param userId 下订单用户id
	 * @param addressId 收件地址id
	 * @param payMethodId 付款方式id 现在只有货到付款，id=1
	 * @param remark 备注
	 * @param discountCode 优惠券号码
	 * @param dataListener
	 */
	public void createOrder(String status,String goodsIds,String userId,String addressId,String payMethodId,String remark,String discountCode,final DataListener dataListener);
	
	/**
	 * 获取订单列表
	 * @param listener
	 */
	public void queryOrderList(String userId,String limit,String start,final DataListener<List<Order>> listener);
	
	/**
	 * 获取订单详情
	 * @param id
	 * @param listener
	 */
	public void orderDetail(String id,final DataListener<Order> listener);
	
	/**
	 * 删除订单
	 * @param id
	 * @param listener
	 */
	public void deleteOrder(String id,final DataListener listener);
	
}
