package com.linyou.lifeservice.model;

import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;
import android.text.TextUtils;

public class AdviceModel extends BaseModel {

	private UserWeb mUserWeb;
	public AdviceModel(Context mContext) {
		super(mContext);
		mUserWeb = new UserWeb(mContext);
	}

	public void advice(String content)
	{
		User user = serviceApplication.readUser();
		if(null == user )
		{
			PublicUtil.ShowToast("请先登录");
			return;
		}
		if(isLegal(content))
		{
			mUserWeb.advice(user.getId(), content, new DataListener(){
				@Override
				public void onSuccess() {
					PublicUtil.ShowToast("您的意见已经提交成功！");
					closeActivity();
				}
				
			});
		}
		else
		{
			PublicUtil.ShowToast("请输入您的意见");
		}
	}
	
	private boolean isLegal(String content)
	{
		content = content.trim();
		if(TextUtils.isEmpty(content))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
