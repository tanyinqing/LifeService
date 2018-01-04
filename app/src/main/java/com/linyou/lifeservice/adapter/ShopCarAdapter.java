package com.linyou.lifeservice.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.MyDialog;
import com.linyou.lifeservice.utils.MyLog;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//buttonSubClick 点击加号后的操作
public class ShopCarAdapter extends AdapterBase<Goods> {
	private static ShopCarAdapter mShopCarAdapter;
	private static UserWeb mUserWeb;
	private static User mUser;
	private static OrderUtil mOrderUtil;
    private static ImageLoader imageLoader;
	private static Context context;

	private static Handler mHandler;

	public ShopCarAdapter(Context baseContext,Handler mHandler) {
		super(baseContext);
		this.context=baseContext;
		mUserWeb = new UserWeb(baseContext);
		mUser = ServiceApplication.getInstance().readUser();
		mShopCarAdapter = this;
		mOrderUtil = OrderUtil.getInstance();
		imageLoader = PublicUtil.getImageLoader();
		this.mHandler=mHandler;
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null == convertView)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.shopcar_item, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		//从父类中获得商品的数据
		holder.update((Goods)getItem(position));
		return convertView;
	}
	private static class ViewHolder{
		@ViewInject(R.id.imageGoods)
		ImageView imageGoods;
		@ViewInject(R.id.textGoodsName)
		TextView textGoodsName;
		@ViewInject(R.id.textPrice)
		TextView textPrice;
		@ViewInject(R.id.textAmount)
		TextView textAmount;
		@ViewInject(R.id.buttonSub)
		ImageButton buttonSub;
		@ViewInject(R.id.buttonPlus)
		ImageButton buttonPlus;
		@ViewInject(R.id.buttonDel)
		ImageButton buttonDel;
		
		Goods goods;
		public void update(Goods goods)
		{
			this.goods = goods;
			textGoodsName.setText(""+goods.getName());
			textPrice.setText("￥"+PublicUtil.priceFormat(goods.getPrice()));
			int count = goods.getCount();
			if(count < 1)
			{
				goods.setCount(1);
			}
			textAmount.setText(""+goods.getCount());
			

			//List<GoodsImage> pics = goods.getPicList();//这个是商品的图片的链接结合
			//if(null != pics && pics.size() > 0)
			String pic=goods.getPic();
			if(null!=pic&&!pic.equals(""))
			{
				//imageLoader.displayImage(Constant.URLBASE+pics.get(0).getPic(), imageGoods,PublicUtil.getGoodsOption());
				imageLoader.displayImage(Constant.URLBASE+pic, imageGoods,PublicUtil.getGoodsOption());
			}
			else
			{
				imageGoods.setImageResource(R.drawable.default_goods);
			}
		}
		@OnClick(R.id.buttonPlus)
		void buttonPlusClick(View v)
		{

			int count = goods.getCount()+1;
			if (count>Integer.parseInt(goods.getAmount())){
				PublicUtil.ShowToast("库存只有"+goods.getAmount());
				return;
			}else {
				mOrderUtil.notifyDataChanged();
				goods.setCount(count);
				textAmount.setText(""+count);
			}
			mHandler.sendEmptyMessage(2);
		}
		@OnClick(R.id.buttonSub)
		void buttonSubClick(View v)
		{
			int count = goods.getCount()-1;
			if(count < 1)
			{
				return;
			}
			else
			{
                mOrderUtil.notifyDataChanged();
				goods.setCount(count);
				textAmount.setText("" + count);
			}
			mHandler.sendEmptyMessage(1);
		}

		//创造事件  向购物车移出商品
		@OnClick(R.id.buttonDel)
		void buttonDelClick(View v)
		{
			MyDialog dialog = new MyDialog(context)
					.setTitle(R.string.Dialog_title)
					.setMessage(R.string.Dialog_message)
					.setPositiveButton(R.string.is_positive,
							new View.OnClickListener() {

								@SuppressLint("WorldWriteableFiles")
								@Override
								public void onClick(View arg0) {
									if(null != mUser)
			{
				mUserWeb.deleteGoodsFromShoppingCart(goods.getId(), mUser.getId(), new DataListener(){
					@Override
					public void onSuccess() {
						mOrderUtil.remove(goods);
						mShopCarAdapter.removeItem(goods);
						PublicUtil.ShowToast("移除成功");
						mHandler.sendEmptyMessage(1);
					}

				});

			}
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
