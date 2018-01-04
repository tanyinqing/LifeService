package com.linyou.lifeservice.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.activity.LoginAcitvity;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsAdapter extends AdapterBase<Goods>{

	private static UserWeb mUserWeb;
	private static User mUser;
    private static ImageLoader imageLoader;
    private static Context context;

	//private static OrderUtil mOrderUtil;
	public GoodsAdapter(Context baseContext) {
		super(baseContext);
		mUserWeb = new UserWeb(baseContext);
        context = baseContext;
		imageLoader = PublicUtil.getImageLoader();

		//mOrderUtil = OrderUtil.getInstance();
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		//System.out.println("滚动的位置"+position);
		Goods mGoods = (Goods) getItem(position);
		ViewHolder holder;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.goods_item, parent, false);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.update(mGoods);
		return convertView;
	}
	
	private static class ViewHolder{
		@ViewInject(R.id.imageGoods)
		private ImageView imageGoods;
		@ViewInject(R.id.textGoodsName)
		private TextView textGoodsName;
		@ViewInject(R.id.textPrice)
		private TextView textPrice;
		@ViewInject(R.id.buttonAdd)
		private ImageButton buttonAdd;
		Goods goods;
		void update(Goods goods)
		{
			this.goods = goods;
			textGoodsName.setText(""+goods.getName());
			textPrice.setText("￥"+PublicUtil.priceFormat(goods.getPrice()));
			//List<GoodsImage> pics = goods.getPicList();
			String pic = goods.getPic();
			imageGoods.setImageResource(R.drawable.default_goods);
			if(null != pic && !pic.equals(""))
			{
				//imageLoader.displayImage(Constant.URLBASE+pics.get(0).getPic(), imageGoods,PublicUtil.getGoodsOption());
				imageLoader.displayImage(Constant.URLBASE+pic.trim(), imageGoods,PublicUtil.getGoodsOption());
			}
		}
		
		@OnClick(R.id.buttonAdd)
		void buttonDelClick(View v)
		{
            mUser = ServiceApplication.getInstance().readUser();
			if(null != mUser)
			{
				mUserWeb.addShoppingCar(goods.getId(), mUser.getId(), new DataListener(){
					@Override
					public void onSuccess() {
                        /*goods.setCount(1);
						OrderUtil.getInstance().addGoods(goods);
						PublicUtil.ShowToast("成功加入购物车");*/
						int count = goods.getCount()+1;
						if (count>Integer.parseInt(goods.getAmount())){
							PublicUtil.ShowToast("库存只有"+goods.getAmount());
							return;
						}else {
							goods.setCount(count);
							OrderUtil.getInstance().addGoods(goods);
							PublicUtil.ShowToast("成功加入购物车");
						}

					}
					
				});
				
			}
            else
            {
                Intent i = new Intent();
                i.setClass(context, LoginAcitvity.class);
                context.startActivity(i);
            }
		}
	}

}
