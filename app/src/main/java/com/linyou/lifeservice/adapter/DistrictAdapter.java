package com.linyou.lifeservice.adapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.activity.DistrictSelectActivity;
import com.linyou.lifeservice.entity.District;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DistrictAdapter extends AdapterBase<District> {

	public DistrictAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		//getItem是父类中的方法 从父类中的方法里得到数据 在这里运用
		District district = (District) getItem(position);
		ViewHolder viewHolder;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.district_item,parent, false);
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textDistrict.setText(""+district.getName());
		return convertView;
	}

	private static class ViewHolder
	{	
		@ViewInject(R.id.textDistrict)
		public TextView textDistrict;
	}
	
}
