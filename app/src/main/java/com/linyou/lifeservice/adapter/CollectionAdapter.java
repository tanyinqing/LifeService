package com.linyou.lifeservice.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.MyDialog;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectionAdapter extends AdapterBase<Goods> {

	private static User mUser = ServiceApplication.getInstance().readUser();
	private static UserWeb mUserWeb;
	private static CollectionAdapter mAdapter;
	private static Context context;

    private static ImageLoader imageLoader;
	public CollectionAdapter(Context mContext) {
		super(mContext);
		this.context=mContext;
		mAdapter = this;
		mUserWeb = new UserWeb(mContext);
		imageLoader = PublicUtil.getImageLoader();
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		
		Goods mGoods = (Goods) getItem(position);
		ViewHolder holder;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.collection_item, parent, false);
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
		@ViewInject(R.id.buttonRemove)
		private ImageButton buttonRemove;
		Goods mGoods;
		void update(Goods mGoods)
		{
			this.mGoods = mGoods;
			System.out.println( "mGoods getName ----------> "+mGoods.getName());
			textGoodsName.setText(""+mGoods.getName());
			textPrice.setText("￥"+PublicUtil.priceFormat(mGoods.getPrice()));
			List<GoodsImage> pics = mGoods.getPicList();
			if(null != pics && pics.size() > 0)
			{
				System.out.println( "mGoods pics[0]----------> "+ pics.get(0).getPic());
				imageLoader.displayImage(Constant.URLBASE+pics.get(0).getPic(), imageGoods,PublicUtil.getGoodsOption());
			}
			else
			{
				System.out.println( "mGoods no pics----------> ");
				imageGoods.setImageResource(R.drawable.default_goods);
			}
		}
		
		@OnClick(R.id.buttonRemove)
		void buttonRemoveClick(View v){
			MyDialog dialog = new MyDialog(context)
					.setTitle(R.string.Dialog_title)
					.setMessage(R.string.Dialog_message)
					.setPositiveButton(R.string.is_positive,
							new View.OnClickListener() {

								@SuppressLint("WorldWriteableFiles")
								@Override
								public void onClick(View arg0) {
									if (mUser != null) {

										mUserWeb.deleteGoodsFromCollect(mGoods.getId(), mUser.getId(), new DataListener() {

											@Override
											public void onSuccess() {
												PublicUtil.ShowToast("删除收藏成功！");
												mAdapter.removeItem(mGoods);
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
