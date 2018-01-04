package com.linyou.lifeservice.model;

import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.entity.User;

import android.content.Context;

/**
 * 用户中心
 * @author 志强
 *
 */
public class UserCenterModel extends BaseModel {

	public UserCenterModel(Context mContext) {
		super(mContext);
	}
	
	public boolean isLogin()
	{
		User user = serviceApplication.readUser();
		if(null != user)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public User getUser()
	{
		return serviceApplication.readUser();
	}
	
	public District getDistrict()
	{
		return serviceApplication.readDistrict();
	}

	public void exit()
	{
		serviceApplication.saveUser(null);
	}
	
}
