package com.linyou.lifeservice.model;

import java.util.List;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.activity.GoodsDetailActivity;
import com.linyou.lifeservice.adapter.AdImageAdapter;
import com.linyou.lifeservice.adapter.GoodsAdapter;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Advertisement;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.web.CommonWeb;
import com.linyou.lifeservice.web.GoodsWeb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProdutListModel extends BaseModel {

	private GoodsWeb mGoodsWeb;
	private CommonWeb mCommonWeb;
	private AdImageAdapter mAdImageAdapter;
	private GoodsAdapter mGoodsAdapter;
	private int start=0;
	private Context context;

	public ProdutListModel(Context mContext) {
		super(mContext);
		mGoodsWeb = new GoodsWeb(mContext);
		mCommonWeb = new CommonWeb(mContext);

		mAdImageAdapter = new AdImageAdapter(mContext);
		mGoodsAdapter = new GoodsAdapter(mContext);
		this.context=mContext;

	}
	//获取广告的商品信息
	public void requestAds(final DataListener dataListener) {
		mCommonWeb
				.findAdvertisementList(new DataListener<List<Advertisement>>() {
					@Override
					public void onSuccess(List<Advertisement> data) {
						mAdImageAdapter.appendToListAndClear(data);
						dataListener.onSuccess();
					}

					@Override
					public void onFailed() {
						dataListener.onFailed();
					}
					
				});
	}

	public AdImageAdapter getAdImageAdapter() {
		return mAdImageAdapter;
	}

	public GoodsAdapter getGoodsAdapter() {
		return mGoodsAdapter;
	}

	//刚开始添加新的商品
	public void refreshProducts(String store_id,String goodTypeId,
			String orderBy, String name, String sortord,
			final DataListener dataListener) {
		serviceApplication.mPrefUtil.putSetting("tiShi",start);
		mGoodsWeb.findGoodsByType(store_id,goodTypeId, Constant.DEFAULT_LIMIT, "0", orderBy, name,
				sortord, new DataListener<List<Goods>>() {

					@Override
					public void onSuccess(List<Goods> data) {

//						//查看数据是否解析成对象
//						for(Goods good:data){
//							p.d1(context, good.toString());
//							p.t1(context, good.toString());
//							p.L1("查看数据是否解析成对象", good.toString());
//						}

						mGoodsAdapter.appendToListAndClear(data);
						if (data != null) {
							start = mGoodsAdapter.getCount();
						}
						dataListener.onSuccess();
					}

					@Override
					public void onFailed(List<Goods> data) {
						//查看数据是否解析成对象
//						for(Goods good:data){
//							Log.d("查看数据是否解析成对象",good.toString());
//							PromptManager.showDialogTest1(context,good.toString());
//						}
//
//						mGoodsAdapter.appendToListAndClear(data);
//						if (data != null) {
//							start = mGoodsAdapter.getCount();
//						}
						dataListener.onSuccess();
					}
				});
	}

	//获得更多的商品信息
	public void loadMoreGoods(String store_id,String goodTypeId, String orderBy,
			String name, String sortord, final DataListener dataListener) {
		serviceApplication.mPrefUtil.putSetting("tiShi",start);
		mGoodsWeb.findGoodsByType(store_id,goodTypeId, Constant.DEFAULT_LIMIT, "" + start, orderBy, name,
				sortord, new DataListener<List<Goods>>() {

					@Override
					public void onSuccess(List<Goods> data) {

//						//查看数据是否解析成对象
//						for (Goods good:data){
//							Log.d("查看数据是否解析成对象",good.toString());
//						}

						mGoodsAdapter.appendToList(data);
						if (data != null) {
							start = mGoodsAdapter.getCount();
						}
						dataListener.onSuccess();
					}

					@Override
					public void onFailed() {
						dataListener.onSuccess();
					}

				});
	}

	public void selectGoods(int position) {
		
		Intent i = new Intent();
		i.putExtra(Constant.INTENT_GOODS_ID, ((Goods)mGoodsAdapter.getItem(position)).getId());
		i.setClass(mContext, GoodsDetailActivity.class);
		mContext.startActivity(i);
	}
}
