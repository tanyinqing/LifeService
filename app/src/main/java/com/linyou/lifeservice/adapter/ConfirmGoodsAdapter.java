package com.linyou.lifeservice.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.utils.PublicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfirmGoodsAdapter extends AdapterBase<Goods> {

    private static ImageLoader imageLoader;
	public ConfirmGoodsAdapter(Context baseContext) {
		super(baseContext);
		imageLoader = PublicUtil.getImageLoader();
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {

		Goods mGoods = (Goods) getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.confirm_goods_item,
					parent, false);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.update(mGoods);
		return convertView;
	}

	private static class ViewHolder {
		@ViewInject(R.id.imageGoods)
		private ImageView imageGoods;
		@ViewInject(R.id.textGoodsName)
		private TextView textGoodsName;
		@ViewInject(R.id.textPrice)
		private TextView textPrice;
		@ViewInject(R.id.textCount)
		private TextView textCount;

		void update(Goods mGoods) {
			textGoodsName.setText("" + mGoods.getName());
			textPrice.setText("￥" + PublicUtil.priceFormat(mGoods.getPrice()));
			textCount.setText("数量："+mGoods.getCount());

			//List<GoodsImage> pics = mGoods.getPicList();
			//if(null != pics && pics.size() > 0)
			String pic=mGoods.getPic();
			if (null!=pic&&!pic.equals(""))
			{
				//imageLoader.displayImage(Constant.URLBASE+pics.get(0).getPic(), imageGoods,PublicUtil.getGoodsOption());
				imageLoader.displayImage(Constant.URLBASE+pic, imageGoods,PublicUtil.getGoodsOption());
			}
			else
			{
				imageGoods.setImageResource(R.drawable.default_goods);
			}
		}
	}

}
