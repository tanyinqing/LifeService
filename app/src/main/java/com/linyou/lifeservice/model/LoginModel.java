package com.linyou.lifeservice.model;

import com.linyou.lifeservice.activity.RegisterAcitivty;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.RegexUtil;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;

public class LoginModel extends BaseModel{

	private UserWeb mUserWeb;
	
	public LoginModel(Context mContext) {
		super(mContext);
		mUserWeb = new UserWeb(mContext);
	}
	
	
	/**
	 * 登陆
	 * @param acc
	 * @param pwd
	 */
	public void login(String acc,String pwd)
	{
		if(!isLegal(acc, pwd))
		{
			return;
		}
		mUserWeb.login(acc, pwd, new DataListener<User>(){
			@Override
			public void onSuccess(User data) {
				serviceApplication.saveUser(data);
				closeActivity();
			}

			@Override
			public void onFailed() {
			}
			
		});
	}
	
	/**
	 * 跳转到注册界面
	 */
	public void gotoRegister()
	{
		startAcitityByClass(RegisterAcitivty.class);
	}
	
	/**
	 * 检测输入合法性
	 * @param acc
	 * @param pwd
	 * @return
	 */
	private boolean isLegal(String acc,String pwd)
	{

		/*
		检测手机号输入的合法性
			if(!RegexUtil.isPhone(acc))
		{
			PublicUtil.ShowToast("请输入正确的手机号！");
			return false;
		}
		 */

		if("".equals(pwd) || pwd.length() < 6)
		{
			PublicUtil.ShowToast("请输入6-12位的秘密！");
			return false;
		}
		return true;
	}

}
