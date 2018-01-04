package com.linyou.lifeservice.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.entity.Order;
import com.linyou.lifeservice.entity.OrderMapGoods;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.DensityUtil;
import com.linyou.lifeservice.utils.MyDialog;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.OrderWeb;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class OrderAdapter extends AdapterBase<Order> {

	private static LayoutParams imageParam;
	private static Context mContext;
	private static OrderWeb mOrderWeb;
	private static OrderAdapter mOrderAdapter;
	private static Context context;

    private static ImageLoader imageLoader;
	public OrderAdapter(Context baseContext) {
		super(baseContext);
		this.context=baseContext;
		mContext = baseContext;
		mOrderAdapter = this;
		int len = DensityUtil.dip2px(context, 80);
		imageParam = new LayoutParams(len, len, 1);
        imageParam.setMargins(5,5,5,5);
		mOrderWeb = new OrderWeb(baseContext);
		imageLoader = PublicUtil.getImageLoader();
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Order mOrder = (Order) getItem(position);

		Log.d("查看当订单的详情",mOrder.toString());
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.order_item, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.update(mOrder);
		return convertView;
	}

	private static class ViewHolder {
		@ViewInject(R.id.textOrderNum)
		TextView textOrderNum;
		@ViewInject(R.id.textTime)
		TextView textTime;
		@ViewInject(R.id.textState)
		TextView textState;
		@ViewInject(R.id.linearImages)
		LinearLayout linearImages;
		@ViewInject(R.id.textMoney)
		TextView textMoney;
		@ViewInject(R.id.buttonDel)
		ImageButton buttonDel;
		@ViewInject(R.id.storeName)
		TextView storeName;
		@ViewInject(R.id.etUsername)
		private TextView etUsername;
		Order mOrder;

		void update(Order mOrder) {
			this.mOrder = mOrder;

			List<OrderMapGoods> list = mOrder.getOrderMapGoodsList();
			
			ImageView imageView;
			if (list != null) {
				linearImages.removeAllViews();
				for (int i = 0; i < 4; i++) {
					imageView = new ImageView(mContext);
					imageView.setLayoutParams(imageParam);
					linearImages.addView(imageView);
				}
				int size = mOrder.getOrderMapGoodsList().size();
				size = size > 4 ? 4 : size;
				list = mOrder.getOrderMapGoodsList().subList(0, size);
				for (int i = 0; i < size; i++) {
					imageView = (ImageView) linearImages.getChildAt(i);
					//List<GoodsImage> pics = list.get(i).getGoods().getPicList();
					String pic=list.get(i).getGoods().getPic();
					//if(null != pics && pics.size() > 0)
					if(null != pic && !pic.equals(""))
					{
						//imageLoader.displayImage(Constant.URLBASE+pics.get(0).getPic(), imageView,PublicUtil.getGoodsOption());
						imageLoader.displayImage(Constant.URLBASE+pic, imageView,PublicUtil.getGoodsOption());
					}
					else
					{
						imageView.setImageResource(R.drawable.default_goods);
					}
				}
			}

			if (!"".equals(mOrder.getOrderCode())){
				textOrderNum.setText("" + mOrder.getOrderCode());
			}
			textMoney.setText("￥" + PublicUtil.priceFormat(mOrder.getPrice()));
			//订单会以数字的格式返回  所以判断不同的状态
			if (mOrder.getState().equals("10")){
				//textState.setText("" + mOrder.getState());
				textState.setText("订单已提交");
			}else if (mOrder.getState().equals("11")){
				textState.setText("等待买家付款");
			}else if (mOrder.getState().equals("20")){
				textState.setText("买家已付款，等待卖家发货");
			}else if (mOrder.getState().equals("30")){
				textState.setText("卖家已接单");
//				textState.setText("卖家已发货");
			}else if (mOrder.getState().equals("40")){
				textState.setText("交易成功");
			}else if (mOrder.getState().equals("0")){
				textState.setText("交易已取消");
			}

			textTime.setText("" + mOrder.getCreateTime().replace("T"," "));
			storeName.setText(mOrder.getGoodsUser());
			if ("0".equals(mOrder.getReceipt())) {
				etUsername.setText("");
			}else {
				etUsername.setText(mOrder.getReceipt());
			}
		}

		@OnClick(R.id.buttonDel)
		void buttonDelClick(View v) {
			MyDialog dialog = new MyDialog(context)
					.setTitle(R.string.Dialog_title)
					.setMessage(R.string.Dialog_message)
					.setPositiveButton(R.string.is_positive,
							new View.OnClickListener() {

								@SuppressLint("WorldWriteableFiles")
								@Override
								public void onClick(View arg0) {
									mOrderWeb.deleteOrder(mOrder.getId(), new DataListener() {

										@Override
										public void onSuccess() {
											PublicUtil.ShowToast("删除订单成功");
											mOrderAdapter.removeItem(mOrder);
										}

									});
								}
							})
					.setNegativeButton(R.string.cancel,
							new View.OnClickListener() {

								@Override
								public void onClick(View arg0) {
									//MyLog.ShowLog("你选择了取消");
								}
							});

			dialog.create(null);
			dialog.showMyDialog();

		}
	}

}
