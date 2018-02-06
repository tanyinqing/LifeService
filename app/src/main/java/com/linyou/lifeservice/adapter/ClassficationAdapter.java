package com.linyou.lifeservice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.entity.Classfication;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.utils.PublicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ClassficationAdapter extends AdapterBase<Classfication> {

	private LayoutParams mLayoutParams;
	private static String image_url;
	private static ImageLoader mImageLoader;
	public ClassficationAdapter(Context mContext) {
		super(mContext);
		mImageLoader=PublicUtil.getImageLoader();
		init();
	}

	private void init() {
		int w= PublicUtil.getDeviceWidth()-20;
		double deta=(140*1.0)/(295);
		int h= (int) (w/2*deta);
		mLayoutParams=new LayoutParams(w/2,h);
		mLayoutParams.setMargins(5,5,5,5);
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		//g etItem是父类中的方法 从父类中的方法里得到数据 在这里运用
		Classfication mClassfication = (Classfication) getItem(position);
		image_url=mClassfication.getImage_url();
		ViewHolder viewHolder;
		ImageView view;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.class_image,parent, false);
			ViewUtils.inject(viewHolder, convertView);  //前边是对象  后边是view变量
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//viewHolder.image.setText("" + mClassfication.getClass_name());
		//上面这句控制图像的显示
		//viewHolder.image.setLayoutParams(mLayoutParams);
		 view=viewHolder.image;
		view.setLayoutParams(mLayoutParams);

		//if(null!=image_url&&!("").equals(image_url)){
		mImageLoader.displayImage(Constant.URLBASE+image_url.trim(),view,PublicUtil.getClassOption());
		//}
		return convertView;
	}

	private static class ViewHolder
	{	
		@ViewInject(R.id.image)
		public ImageView image;
	}
	
}
