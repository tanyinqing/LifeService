package com.linyou.lifeservice.impl;

import java.util.List;

import com.linyou.lifeservice.entity.Advertisement;
import com.linyou.lifeservice.entity.Classfication;
import com.linyou.lifeservice.entity.Comment;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.listener.DataListener;

public interface ICommonWeb {
	/**
	 * 获取区域列表
	 * @param dataListener
	 */
	public void findDistrict(final DataListener<List<District>> dataListener);

	/*获取店铺的商品的类别*/
	public void findClass(String storeid,final DataListener<List<Classfication>> dataListener);
	
	/**
	 * 广告
	 * @param dataListener
	 */
	public void findAdvertisementList(final DataListener<List<Advertisement>> dataListener);
	
	/**
	 * 获取评论列表
	 * @param goodsId
	 * @param limit
	 * @param start
	 */
	public void findGoodsCommentsByCon(String goodsId,String limit,String start,final DataListener<List<Comment>> dataListener);


    /**
     * 忘记密码
     * @param phoneNum
     * @param phoneCode
     * @param dataListener
     */
    public void forgetPassword(String phoneNum,String phoneCode,DataListener<String> dataListener);
}
