package com.linyou.lifeservice.adapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.activity.AddrModificationActivity;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.MyDialog;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AddrAdapter extends AdapterBase<DeliveryAddress> {

	private static AddrAdapter mAddrAdapter;
	private static UserWeb mUserWeb;
    private static int temp;
	private String action;
	private static Context context;
	public AddrAdapter(Context baseContext) {
		super(baseContext);
		this.context=baseContext;
		mAddrAdapter = this;
		mUserWeb = new UserWeb(baseContext);
        temp = -1;
	}

	//对外设置一个接口  可以动态的设置数据
	public void setAction(String action)
	{
		this.action = action;
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		//getItem是父类中的方法 从父类中的方法里得到数据 在这里运用
		DeliveryAddress address = (DeliveryAddress) getItem(position);
		ViewHolder holder;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.addr_item, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.checkSel.setVisibility(View.GONE);
//		if(Constant.addr_manage.equals(action))
//		{
//			holder.checkSel.setVisibility(View.GONE);
//		}
//		else
//		{
//			holder.checkSel.setId(position);
//	        holder.checkSel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//	            @Override
//	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//	                if (isChecked) {
//	                    if (temp != -1) {
//	                    	CheckBox tempButton = (CheckBox)((Activity)context).findViewById(temp);
//	                        if (tempButton != null) {
//	                            tempButton.setChecked(false);
//	                        }
//	                    }
//	                    temp = buttonView.getId();
//	                }
//	            }
//	        });
//	        if (position == temp) {
//	            System.out.println("position ---> "+position +"temp ---> " +temp);
//	            holder.checkSel.setChecked(true);
//	        } else {
//	            holder.checkSel.setChecked(false);
//	        }
//		}
		
		holder.update(address);
		return convertView;
	}
	
	private static class ViewHolder
	{
		@ViewInject(R.id.checkSel)
		CheckBox checkSel;
		@ViewInject(R.id.textAddr)
		TextView textAddr;
		@ViewInject(R.id.buttonDelete)
		ImageButton buttonDelete;
		@ViewInject(R.id.buttonModification)
		ImageButton buttonModification;
		
		DeliveryAddress address;
		
		void update(DeliveryAddress address){
			this.address = address;
			textAddr.setText(""+address.getAddress());
		}
		@OnClick(R.id.buttonDelete)
		void buttonDeleteClick(View v)
		{
			MyDialog dialog = new MyDialog(context)
					.setTitle(R.string.Dialog_title)
					.setMessage(R.string.Dialog_message)
					.setPositiveButton(R.string.is_positive,
							new View.OnClickListener() {

								@SuppressLint("WorldWriteableFiles")
								@Override
								public void onClick(View arg0) {
									mUserWeb.deleteAddress(address.getId(), new DataListener() {

										@Override
										public void onSuccess() {
											PublicUtil.ShowToast("删除地址成功");
											mAddrAdapter.removeItem(address);
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
		@OnClick(R.id.buttonModification)
		void buttonModificationClick(View v)
		{
			/*思路 1写一个intent把对象带到下一个页面，修改后把数据上传到服务台
			* 2把常用地址页面关闭   当关闭修改地址页面的时候在跳转到常用地址页面并重新访问网络*/
			/*mUserWeb.deleteAddress(address.getId(), new DataListener() {

				@Override
				public void onSuccess() {
					PublicUtil.ShowToast("删除地址成功");
					mAddrAdapter.removeItem(address);
				}

			});*/
			//p.L("跳转启动","跳转启动");
			Intent intent=new Intent();
			intent.setClass(context, AddrModificationActivity.class);
			Bundle bundle=new Bundle();
			bundle.putSerializable("address",address);
			intent.putExtras(bundle);
			context.startActivity(intent);
		}
	}

}
