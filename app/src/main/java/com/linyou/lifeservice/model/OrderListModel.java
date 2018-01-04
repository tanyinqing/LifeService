package com.linyou.lifeservice.model;

import java.util.ArrayList;
import java.util.List;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.activity.OrderDetailActivity;
import com.linyou.lifeservice.adapter.OrderAdapter;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Order;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.OrderWeb;

import android.content.Context;
import android.content.Intent;

public class OrderListModel extends BaseModel {

	private OrderAdapter mOrderAdapter;
	private OrderWeb mOrderWeb;

	private int start;
	private Context context;

	public OrderListModel(Context mContext) {
		super(mContext);
		mOrderAdapter = new OrderAdapter(mContext);
		mOrderWeb = new OrderWeb(mContext);
		start = 0;
		this.context=mContext;
	}

	public OrderAdapter getOrderAdapter() {
		return mOrderAdapter;
	}

	public void refreshOrder(final DataListener listener) {
		if (!isLogin()) {
			PublicUtil.ShowToast("请登陆");
			return;
		}
		User mUser = serviceApplication.readUser();
		mOrderWeb.queryOrderList(mUser.getId(), Constant.DEFAULT_LIMIT, "" +0,
				new DataListener<List<Order>>() {
					@Override
					public void onSuccess(List<Order> data) {
						for(Order good:data){
							//p.d(context, good.toString());
							p.L("查看数据是否解析成对象", good.toString());
						}
						mOrderAdapter.appendToListAndClear(data);
						if(null != data)
						{
							start = data.size();
						}
						listener.onSuccess();
					}

					@Override
					public void onFailed() {
						listener.onSuccess();
					}

				});
	}

	public void loadMoreOrder(final DataListener listener) {
		if (!isLogin()) {
			PublicUtil.ShowToast("请登陆");
			return;
		}
		User mUser = serviceApplication.readUser();
		mOrderWeb.queryOrderList(mUser.getId(), Constant.DEFAULT_LIMIT, "" + start,
				new DataListener<List<Order>>() {
					@Override
					public void onSuccess(List<Order> data) {
						mOrderAdapter.appendToList(data);
						if(null != data)
						{
							start += data.size();
						}
						listener.onSuccess();
					}

					@Override
					public void onFailed() {
						listener.onSuccess();
					}

				});
	}
	

	public void selectOrder(int position) {
		Intent i = new Intent();
		 i.putExtra(Constant.INTENT_ORDER_ID, ((Order)mOrderAdapter.getItem(position)).getId()); //把实体对象传过去
		i.setClass(mContext, OrderDetailActivity.class);
		mContext.startActivity(i);
	}

}
