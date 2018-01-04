package com.linyou.lifeservice.model;

import java.util.ArrayList;
import java.util.List;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.activity.GoodsDetailActivity;
import com.linyou.lifeservice.activity.OrderDetailActivity;
import com.linyou.lifeservice.adapter.CollectionAdapter;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.GoodsWeb;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CollectionModel extends BaseModel {

	private CollectionAdapter mCollectionAdapter;
	private UserWeb mUserWeb;
	private Context context;

	public CollectionModel(Context mContext) {
		super(mContext);
		mCollectionAdapter = new CollectionAdapter(mContext);
		mUserWeb = new UserWeb(mContext);
		this.context=mContext;
	}

	public CollectionAdapter getCollectionAdapter() {
		return mCollectionAdapter;
	}

	public void refreshCollection() {
		if(!isLogin())
		{
			PublicUtil.ShowToast("请登陆");
			return;
		}
		User u = serviceApplication.readUser();
		mUserWeb.findCollectGoods(u.getId(), new DataListener<List<Goods>>() {

			@Override
			public void onSuccess(List<Goods> data) {
				for(Goods g:data){
					p.L2("查看收藏商品数据是否解析成对象", g.toString());
					p.d2(context, g.toString());
				}
				mCollectionAdapter.appendToListAndClear(data);
			}

		});
	}

	private void loadMoreCollection() {
		if (!isLogin()) {
			PublicUtil.ShowToast("请登陆");
			return;
		}
		mUserWeb.findCollectGoods("", new DataListener<List<Goods>>() {

			@Override
			public void onSuccess(List<Goods> data) {
				mCollectionAdapter.appendToList(data);
			}

		});
	}

	private List<Goods> testData() {
		List<Goods> list = new ArrayList<Goods>();
		for (int i = 0; i < 5; i++) {
			list.add(new Goods());
		}
		return list;
	}

	public void selectOrder(int position) {

		Intent i = new Intent();
		 i.putExtra(Constant.INTENT_GOODS_ID,
		 ((Goods)mCollectionAdapter.getItem(position)).getId());
		i.setClass(mContext, GoodsDetailActivity.class);
		mContext.startActivity(i);
	}
}
