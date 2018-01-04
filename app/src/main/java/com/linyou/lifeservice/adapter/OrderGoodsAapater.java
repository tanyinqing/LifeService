package com.linyou.lifeservice.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.activity.CommentActivity;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.entity.OrderMapGoods;
import com.linyou.lifeservice.utils.PublicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderGoodsAapater extends AdapterBase<OrderMapGoods> {

    private static ImageLoader imageLoader;
	private static Context mContext;
	public OrderGoodsAapater(Context baseContext) {
		super(baseContext);
		mContext = baseContext;
		imageLoader = PublicUtil.getImageLoader();//显示图片的工具
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		OrderMapGoods mOrderMapGoods =(OrderMapGoods)getItem(position);
		ViewHolder holder;
		if(null == convertView)
		{
			holder = new ViewHolder();
			convertView =mInflater.inflate(R.layout.order_goods_item,parent, false);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.update(mOrderMapGoods);
		return convertView;
	}
	
	
	private static class ViewHolder{
		@ViewInject(R.id.imageOrderGoods)
		ImageView imageOrderGoods;
		@ViewInject(R.id.textGoodsName)
		TextView textGoodsName;
		@ViewInject(R.id.textPrice)
		TextView textPrice;
		@ViewInject(R.id.textAmount)
		TextView textAmount;
		@ViewInject(R.id.buttonComment)
		ImageButton buttonComment;

		private Goods mGoods;

		@OnClick(R.id.buttonComment)
		void OnClick(View v)
		{
			Intent i = new Intent();
			i.putExtra(Constant.INTENT_GOODS_ID,mGoods.getId());
			i.setClass(mContext, CommentActivity.class);
			((Activity)mContext).startActivity(i);
		}


		void update(OrderMapGoods mOrderMapGoods)
		{
			mGoods = mOrderMapGoods.getGoods();
			System.out.println( "mGoods getName ----------> "+mGoods.getName());
			textGoodsName.setText(""+mGoods.getName());
			textAmount.setText("数量："+mOrderMapGoods.getCount());
			textPrice.setText("￥"+ PublicUtil.priceFormat(mOrderMapGoods.getPrice()));
			//List<GoodsImage> pics = mGoods.getPicList();
			//String imageUrl = "";
			String imageUrl = mGoods.getPic();
			imageOrderGoods.setImageResource(R.drawable.default_goods);
			//if(null != pics && pics.size() > 0)
			if(null != imageUrl && !imageUrl.equals(""))
			{
				//imageUrl = pics.get(0).getPic();
				imageOrderGoods.setTag(imageUrl);
			}
			else
			{
				imageUrl= "";
				imageOrderGoods.setTag(null);
			}
			imageLoader.displayImage(Constant.URLBASE+imageOrderGoods.getTag(), imageOrderGoods,PublicUtil.getGoodsOption());

		}
	}

}
