package com.linyou.lifeservice.impl;

import java.util.List;

import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.ServiceType;
import com.linyou.lifeservice.listener.DataListener;

public interface IGoodsWeb {
	/**
	 * 获取分类商品信息（分页查找及模糊查询，排序。）
	 * @param goodstype
	 * @param limit 一页显示多少商品（limit）
	 * @param start 从第几个开始（start）
	 * @param orderBy 排序规则 createTime,price,amount,saleTime
	 * @param name
	 * @param sortord 排序方式asc,desc
	 * @param dataListener
	 */
	public void findGoodsByType(String store_id,String goodTypeId,String limit,String start,String orderBy,String name,String sortord,
			final DataListener<List<Goods>> dataListener);

	/**
	 * 获取商品信息
	 * 
	 * @param id
	 * @param dataListener
	 */
	public void findGoodsInfo(String id, final DataListener<Goods> dataListener);

	
	/**
	 * 根据区域查询服务
	 * 
	 * @param serviceId
	 * @param dataListener
	 */
	public void findServiceTypeByDistrict(String id,
			final DataListener<List<ServiceType>> dataListener);

	/**
	 * 根据服务查询商品
	 * 
	 * @param serviceId
	 * @param dataListener
	 */
	public void findGoodsTypeByServiceType(String serviceId,
			final DataListener<List<Goods>> dataListener);
}
