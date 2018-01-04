package com.linyou.lifeservice.model;

import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;

public class AddrEditModel extends BaseModel {

	private UserWeb mUserWeb;
	
	public AddrEditModel(Context mContext) {
		super(mContext);
		mUserWeb = new UserWeb(mContext);
	}
	
	public void save(String name,String addr,String phone)
	{
		if(!isLogin())
		{
			PublicUtil.ShowToast("请登陆");
			return;
		}
		User user = serviceApplication.readUser();
		mUserWeb.addAddress(user.getId(), name, phone, addr, new DataListener()
		{
			@Override
			public void onSuccess() {
				PublicUtil.ShowToast("保存地址成功");
				closeActivity();
			}
			
		});
	}

}
