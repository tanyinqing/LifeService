package com.linyou.lifeservice.model;

import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.RegexUtil;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;
import android.os.Handler;

public class RegisterModel extends BaseModel {

	public static final int GET_CODE_SUCCESS = 0;
	public static final int GET_CODE_FAILED = 1;
	
	
	private UserWeb mUserWeb;
	private Handler mHandler;
	
//	public String code;
	public RegisterModel(Context mContext,Handler mHandler) {
		super(mContext);
		this.mHandler = mHandler;
		mUserWeb = new UserWeb(mContext);
	}
	
	/**
	 * 注册
	 * @param acc
	 * @param pwd
	 * @param code
	 */
	public void register(String acc,String pwd,String code)
	{
		if(!isLegal(acc, pwd, code))
		{
			return;
		}
		mUserWeb.register(pwd, acc, code, new DataListener<User>(){
			@Override
			public void onSuccess(User data) {
				serviceApplication.saveUser(data);
				closeActivity();
			}
			
		});
	}
	
	/**
	 * 请求发送验证码
	 * @param phone
	 */
	public void getCode(String phone)
	{
		if(!RegexUtil.isPhone(phone))
		{
			PublicUtil.ShowToast("请输入正确的手机号！");
			return;
		}
		mUserWeb.getCode(phone, new DataListener<String>()
		{
			@Override
			public void onSuccess(String data) {
//				code = data;
				mHandler.sendEmptyMessage(GET_CODE_SUCCESS);
			}

			@Override
			public void onFailed() {
				mHandler.sendEmptyMessage(GET_CODE_FAILED);
			}
			
		});
	}
	
	/**
	 * 检测输入合法性
	 * @param acc
	 * @param pwd
	 * @param code
	 * @return
	 */
	private boolean isLegal(String acc,String pwd,String code)
	{
		if(!RegexUtil.isPhone(acc))
		{
			PublicUtil.ShowToast("请输入正确的手机号！");
			return false;
		}
		if("".equals(pwd) || pwd.length() < 6)
		{
			PublicUtil.ShowToast("请输入6-12位的秘密！");
			return false;
		}
		if("".equals(code))
		{
			PublicUtil.ShowToast("请输入验证码！");
			return false;
		}
		return true;
	}

}
