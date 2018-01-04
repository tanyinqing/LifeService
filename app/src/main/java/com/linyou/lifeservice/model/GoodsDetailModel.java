package com.linyou.lifeservice.model;

import com.linyou.lifeservice.adapter.GoodsImageAdapter;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.GoodsWeb;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;

public class GoodsDetailModel extends BaseModel {

	private UserWeb mUserWeb;
	private GoodsWeb mGoodsWeb;

	private GoodsImageAdapter mGoodsImageAdapter;
	
	public GoodsDetailModel(Context mContext) {
		super(mContext);
		mGoodsWeb = new GoodsWeb(mContext);
		mUserWeb = new UserWeb(mContext);
		mGoodsImageAdapter = new GoodsImageAdapter(mContext);
	}

	public void getGoodsDetail(String goods_id, DataListener<Goods> dataListener) {
		mGoodsWeb.findGoodsInfo(goods_id, dataListener);
	}

	public GoodsImageAdapter getGoodsImageAdapter() {
		return mGoodsImageAdapter;
	}

	public void add2Car(String goodsId,DataListener dataListener) {
		User user = serviceApplication.readUser();
		if (null == user) {
			PublicUtil.ShowToast("请先登录");
			return;
		}

		mUserWeb.addShoppingCar(goodsId, user.getId(), dataListener);
	}

	public void add2Collection(String goodsId,DataListener listener) {
		User user = serviceApplication.readUser();
		if (null == user) {
			PublicUtil.ShowToast("请先登录");
			return;
		}

		mUserWeb.collectGoods(goodsId, user.getId(), listener);
	}

	public void removeCollection(String goodsId,DataListener listener) {
		User user = serviceApplication.readUser();
		if (null == user) {
			PublicUtil.ShowToast("请先登录");
			return;
		}

		mUserWeb.deleteGoodsFromCollect(goodsId, user.getId(),listener);
				
	}

}
