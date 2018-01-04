package com.linyou.lifeservice.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.model.OrderListModel;

@ContentView(R.layout.activity_orderlist)
public class OrderListActivity extends TitleBarActivity {

	private OrderListModel mOrderListModel;
	@ViewInject(R.id.listOrder)
	private ListView listOrder;

	@ViewInject(R.id.mPullRefreshView)
	private AbPullToRefreshView mPullRefreshView;

	@Override
	void setListeners() {
		mPullRefreshView
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(final AbPullToRefreshView view) {
						mOrderListModel.refreshOrder(new DataListener() {
							@Override
							public void onSuccess() {
								view.onHeaderRefreshFinish();
							}
						});
					}
				});

		mPullRefreshView.setOnFooterLoadListener(new OnFooterLoadListener() {

			@Override
			public void onFooterLoad(final AbPullToRefreshView view) {

				mOrderListModel.loadMoreOrder(new DataListener() {
					@Override
					public void onSuccess() {
						view.onFooterLoadFinish();
					}
				});

			}
		});
	}

	@OnItemClick(R.id.listOrder)
	public void SelectedDistrict(AdapterView<?> parent, View view,
			int position, long id) {
		mOrderListModel.selectOrder(position);
	}

	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_orders);
		mOrderListModel = new OrderListModel(context);
		listOrder.setAdapter(mOrderListModel.getOrderAdapter());
		mPullRefreshView.headerRefreshing();
	}

	@Override
	void RightButtonClicked() {

	}

	@Override
	void LeftButtonClicked() {
		finish();

	}

}
