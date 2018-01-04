package com.linyou.lifeservice.model;

import java.util.List;

import com.linyou.lifeservice.adapter.ShopCarAdapter;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.GoodsWeb;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;
import android.os.Handler;
import android.text.format.Time;

public class ShoppingCarModel extends BaseModel {
	
	private UserWeb mUserWeb;
	private GoodsWeb mGoodsWeb;
	
	private ShopCarAdapter mShopCarAdapter;
	private OrderUtil mOrderUtil;

	public ShoppingCarModel(Context mContext,Handler mHandler) {
		super(mContext);
		mUserWeb = new UserWeb(mContext);
		mGoodsWeb = new GoodsWeb(mContext);
		mOrderUtil = OrderUtil.getInstance();
		mShopCarAdapter = new ShopCarAdapter(mContext,mHandler);
	}
	
	public ShopCarAdapter getShopCarAdapter()
	{
		return mShopCarAdapter;
	}
	
	public void showCar() {
		User user = serviceApplication.readUser();
		if (null == user) {
			PublicUtil.ShowToast("请先登录");
			return;
		}

		mUserWeb.showShoppingCar(user.getId(), new DataListener<List<Goods>>() {

			@Override
			public void onSuccess(List<Goods> data) {
				for (Goods goods : data) {
					goods.setCount(1);
				}
//				mShopCarAdapter.appendToListAndClear(data);
				mOrderUtil.updateGoodsList(data);
				//这里是想链表里放入数据
				mShopCarAdapter.appendToListAndClear(mOrderUtil.getGoodsList());
				super.onSuccess(data);
			}
		});

	}

	/**
	 *判断系统时间是否在指定时间范围内
	 * @param beginHour 开始的小时数
	 * @param beginMin 开始的分钟数
	 * @param endHour 结束的小时数
	 */
	public static boolean iscurrentInTimeScope(int beginHour,int beginMin,int endHour,int endMin) {
		Boolean result = false;
		final long aDayInMillis = 1000 * 60 * 60 * 24;
		final long currentTimeMillis = System.currentTimeMillis();

		Time now = new Time();
		now.set(currentTimeMillis);

		Time startTime = new Time();
		startTime.set(currentTimeMillis);
		startTime.hour = beginHour;
		startTime.minute = beginMin;

		Time endTime = new Time();
		endTime.set(currentTimeMillis);
		endTime.hour = endHour;
		endTime.minute = endMin;

		// 跨天的特殊情况（比如22:00-8:00）
		if (!startTime.before(endTime)) {
			startTime.set(startTime.toMillis(true) - aDayInMillis);
			result = !now.before(startTime) && !now.before(endTime);
			Time startTimeInThisDay = new Time();
			startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
			if (!now.before(startTimeInThisDay)) {
				result = true;}
			} else {
				//普通情况(比如 8:00 - 14:00)  // startTime <= now <= endTime
				result = !now.before(startTime) && !now.after(endTime);
			}
			return result;
		}
	}

