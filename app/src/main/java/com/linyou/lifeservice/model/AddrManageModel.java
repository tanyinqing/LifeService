package com.linyou.lifeservice.model;

import java.util.ArrayList;
import java.util.List;

import com.linyou.lifeservice.Code;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.adapter.AddrAdapter;
import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.UserWeb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AddrManageModel extends BaseModel {

	private AddrAdapter mAddrAdapter;
	private UserWeb mUserWeb;
	
	public AddrManageModel(Context mContext) {
		super(mContext);
		mAddrAdapter = new AddrAdapter(mContext);
		mUserWeb = new UserWeb(mContext);
	}
	
	public void setAction(String action)
	{
		mAddrAdapter.setAction(action);
	}
	
	public AddrAdapter getAddrAdapter()
	{
		return mAddrAdapter;
	}
	
	public void requestAddr()
	{
		if(!isLogin())
		{
			PublicUtil.ShowToast("请登陆");
			return;
		}
		User user = serviceApplication.readUser();
		mUserWeb.findAddressByUser(user.getId(), new DataListener<List<DeliveryAddress>>(){

			@Override
			public void onSuccess(List<DeliveryAddress> data) {
				if(null != data)
				{
					mAddrAdapter.appendToListAndClear(data);
//					mAddrAdapter.appendToList(testData());
				}
			}
			
		});
	}
	
	public List<DeliveryAddress> testData()
	{
		List<DeliveryAddress> list = new ArrayList<DeliveryAddress>();
		for(int i =0;i<5;i++)
		{
			list.add(new DeliveryAddress());
		}
		return list;
	}

	public void selectAddr(int position) {
		Intent data = new Intent();
		DeliveryAddress address = (DeliveryAddress)mAddrAdapter.getItem(position);
		System.out.println("selectAddr -------> "+address.getAddress());
		Bundle b = new Bundle();
		b.putSerializable(Constant.INTENT_ADDR,address);
		data.putExtra(Constant.INTENT_ADDR, b);
		((Activity) mContext).setResult(Code.addrSelResult, data);
		closeActivity();
	}
	
}
