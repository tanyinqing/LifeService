package com.linyou.lifeservice.model;

import java.util.List;

import com.linyou.lifeservice.adapter.AdImageAdapter;
import com.linyou.lifeservice.adapter.ClassficationAdapter;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Advertisement;
import com.linyou.lifeservice.entity.Classfication;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.web.CommonWeb;

import android.content.Context;

public class MainModel extends BaseModel {
	
	private AdImageAdapter mAdImageAdapter;
	private CommonWeb mCommonWeb;
	private ClassficationAdapter mClassficationAdapter;
	//这个是图片类别在首页的显示的数据对象
	private List<Classfication> classlist;

	public MainModel(Context mContext) {
		super(mContext);
		mAdImageAdapter = new AdImageAdapter(mContext);
		mCommonWeb = new CommonWeb(mContext);
		mClassficationAdapter=new ClassficationAdapter(mContext);
	}
	
	public AdImageAdapter getAdImageAdapter()
	{
		return mAdImageAdapter;
	}

	public ClassficationAdapter getmClassficationAdapter(){

		return mClassficationAdapter;
	}

	public void requestClassfication(){
		classlist= (List<Classfication>) serviceApplication.mPrefUtil.readObject("classlist");
		mClassficationAdapter.appendToListAndClear(classlist);
	}
	
	public void requestAds(final DataListener dataListener)
	{
		mCommonWeb.findAdvertisementList(new DataListener<List<Advertisement>>(){
			@Override
			public void onSuccess(List<Advertisement> data) {
				for (Advertisement a:data){
					p.L("请求广告返回的对象",a.toString());
				}
				mAdImageAdapter.appendToListAndClear(data);
				dataListener.onSuccess();
			}
			
		});
	}

	public void requestClass(String storeid){ //如果主页上需要数据，参数就要有回调对象
		//mClassficationAdapter.appendToListAndClear(classlist);
		mCommonWeb.findClass(storeid,new DataListener<List<Classfication>>(){
			@Override
			public void onSuccess(List<Classfication> data) {

				for (Classfication c:data){
					p.L("获得的店铺类别信息",c.toString());
				}
				serviceApplication.mPrefUtil.putSetting("classlist",data);
			}
		});
	}

}
