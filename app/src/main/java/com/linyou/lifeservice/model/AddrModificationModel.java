package com.linyou.lifeservice.model;

import android.content.Context;

import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;

public class AddrModificationModel extends BaseModel {

	private UserWeb mUserWeb;

	public AddrModificationModel(Context mContext) {
		super(mContext);
		mUserWeb = new UserWeb(mContext);
	}
	
	public void save(String addrId,String name,String addr,String phone)
	{
		if(!isLogin())
		{
			PublicUtil.ShowToast("请登陆");
			return;
		}
		User user = serviceApplication.readUser();
		mUserWeb.modificationAddress(addrId, name, phone, addr, new DataListener()
		{
			@Override
			public void onSuccess() {
				PublicUtil.ShowToast("修改地址成功");
				closeActivity();
			}
			
		});
	}

}
