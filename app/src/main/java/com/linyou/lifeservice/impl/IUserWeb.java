package com.linyou.lifeservice.impl;

import java.io.File;
import java.util.List;

import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.TouXiang;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;

public interface IUserWeb {
	
	/**
	 * 获取验证码
	 * @param phoneNum
	 * @param dataListener
	 */
	public void getCode(String phoneNum,final DataListener<String> dataListener); 
	
	/**
	 * 登陆
	 * @param phoneNum
	 * @param password
	 * @param dataListener
	 */
	public void login(String phoneNum, String password,
			final DataListener<User> dataListener);
	
	/**
	 * 注册
	 * @param password
	 * @param phoneNum
	 * @param phoneCode
	 * @param dataListener
	 */
	public void register(String password, String phoneNum,
			String phoneCode,
			final DataListener<User> dataListener);
	
	/**
	 * 获取收货地址列表
	 * @param id
	 * @param dataListener
	 */
	public void findAddressByUser(String id,final DataListener<List<DeliveryAddress>> dataListener);

	/**
	 * 收货地址详情
	 * 
	 * @param id
	 * @param dataListener
	 */
	public void findAddressById(String id,
			final DataListener<DeliveryAddress> dataListener);
	
	/**
	 * 添加收货地址
	 * @param id
	 * @param reciever
	 * @param recieverPhone
	 * @param addresss
	 * @param dataListener
	 */
	public void addAddress(String id,String reciever, String recieverPhone, String addresss,final DataListener dataListener) ;
	
	/**
	 * 删除地址
	 * 
	 * @param id
	 * @param dataListener
	 */
	public void deleteAddress(String id, final DataListener dataListener);
	
	/**
	 * 意见反馈
	 * @param userId
	 * @param advice
	 * @param dataListener
	 */
	public void advice(String userId,String advice, final DataListener dataListener);
	
	/**
	 * 上传头像
	 * @param file
	 * @param fileFileName
	 * @param userId
	 * @param dataListener
	 */
	public void uploadHeadPortrait(String file,String fileFileName,String userId,DataListener<TouXiang> dataListener);

	/**
	 * 获取头像
	 * @param userId
	 * @param dataListener
	 */
	public void viewHeadPortraitUrl(String userId,DataListener<String> dataListener);
	
	/**
	 * 获取个人资料
	 * @param id
	 * @param dataListener
	 */
	public void findUserInfoById(String id,DataListener<User> dataListener);
	
	/**
	 * 修改用户信息
	 * @param id
	 * @param nickName
	 * @param email
	 * @param age
	 * @param sex
	 * @param dataListener
	 */
	public void modifyUserInfo(String id,String nickName,String email,String age,String sex,final DataListener<User> dataListener);
	
	/**
	 * 修改密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @param dataListener
	 */
	public void modifyPassword(String id,String oldPassword,String newPassword,DataListener dataListener);

	
	
	/**
	 * 收藏商品
	 * 
	 * @param goodsId
	 * @param userId
	 * @param dataListener
	 */
	public void collectGoods(String goodsId,String userId,final DataListener dataListener);

	/**
	 * 删除收藏
	 * @param goodsId
	 * @param userId
	 * @param dataListener
	 */
	public void deleteGoodsFromCollect(String goodsId,String userId,DataListener dataListener);
	
	/**
	 * 查看收藏列表
	 * 
	 * @param dataListener
	 */
	public void findCollectGoods(String userId,final DataListener<List<Goods>> dataListener);


	/**
	 * 添加到购物车
	 * @param goodsId
	 * @param userId
	 * @param dataListener
	 */
	public void addShoppingCar(String goodsId,String userId,
			final DataListener dataListener);

	/**
	 * 查看购物车
	 * 
	 * @param dataListener
	 */
	public void showShoppingCar(String userId,final DataListener<List<Goods>> dataListener);

	/**
	 * 从购物车删除
	 * 
	 * @param id
	 * @param dataListener
	 */
	public void deleteGoodsFromShoppingCart(String goodsId,String userId,
			final DataListener dataListener);

	/**
	 * 添加评论
	 * @param goodsId
	 * @param content
	 * @param dataListener
	 */
	public void addGoodsComment(String goodsId,String content,String orderId,final DataListener dataListener);
}
