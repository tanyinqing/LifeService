package com.linyou.lifeservice.model;

import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;
import android.text.TextUtils;

public class ModifyPwdModel extends BaseModel {

	private UserWeb mUserWeb;
	public ModifyPwdModel(Context mContext) {
		super(mContext);
		mUserWeb = new UserWeb(mContext);
	}
	
	public void modifypwd(String old_pwd,String new_pwd,String confirm_pwd)
	{	
		User user = serviceApplication.readUser();
		if(null == user)
		{
			PublicUtil.ShowToast("请先登录");
			return;
		}
		old_pwd = old_pwd.trim();
		new_pwd = new_pwd.trim();
		confirm_pwd = confirm_pwd.trim();
		if(!isLegal(old_pwd, new_pwd, confirm_pwd))
		{
			return;
		}
		
		mUserWeb.modifyPassword(user.getId(), old_pwd, new_pwd, new DataListener(){
			@Override
			public void onSuccess() {
				PublicUtil.ShowToast("修改密码成功");
				closeActivity();
			}
			
		});
	}
	
	private boolean isLegal(String old_pwd,String new_pwd,String confirm_pwd)
	{
		old_pwd = old_pwd.trim();
		new_pwd = new_pwd.trim();
		confirm_pwd = confirm_pwd.trim();
		System.out.println("new_pwd --> "+new_pwd);
		System.out.println("confirm_pwd --> "+confirm_pwd);
		if(TextUtils.isEmpty(old_pwd) || old_pwd.length() < 6)
		{
			PublicUtil.ShowToast("请输入原密码！");
			return false;
		}
		if(TextUtils.isEmpty(new_pwd) || new_pwd.length() < 6)
		{
			PublicUtil.ShowToast("请输入6-12位的新密码！");
			return false;
		}
		if(!new_pwd.equals(confirm_pwd))
		{
			PublicUtil.ShowToast("两次输入密码不一致！");
			return false;
		}
		return true;
	}

}
