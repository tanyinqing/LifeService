package com.linyou.lifeservice.model;

import java.util.List;

import com.linyou.lifeservice.activity.MainActivity;
import com.linyou.lifeservice.adapter.DistrictAdapter;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.web.CommonWeb;
import com.nostra13.universalimageloader.utils.L;

import android.content.Context;

/**
 * 服务选择
 * @author 志强
 *
 */
public class DistrictSelectModel extends BaseModel {
	
	private DistrictAdapter mDistrictAdapter;
	private CommonWeb mDistrictWeb;
	private boolean isFirstIn;
	private  Context context;
	public DistrictSelectModel(Context mContext) {
		super(mContext);
		this.context=mContext;
		mDistrictAdapter = new DistrictAdapter(mContext);
		mDistrictWeb = new CommonWeb(mContext);
        isFirstIn = false;
        if( null == serviceApplication.readDistrict() )
        {
            isFirstIn = true;
        }
	}
	
	public DistrictAdapter getDistrictAdapter()
	{
		return mDistrictAdapter;
	}
	
	/**
	 * 保存中区域
	 * @param pos
	 */
	public void selectDistrict(int pos)
	{
		serviceApplication.saveDistrict((District)mDistrictAdapter.getItem(pos));
        if (isFirstIn)
        {
            startAcitityByClass(MainActivity.class);
        }
		//这些都是父类中的方法
		closeActivity();
	}
	
	/**
	 * 请求数据
	 */
	public void requestDistrict()
	{
		mDistrictWeb.findDistrict(new DataListener<List<District>>(){


			@Override
			public void onSuccess(List<District> data) {
				for(District d:data){
					//p.dd(context,d.toString());
					p.L("地址信息的对象", d.toString());
				}
				if(null != data)
				{
					mDistrictAdapter.appendToListAndClear(data);
				}
			}

			@Override
			public void onFailed(List<District> data) {

			}
		});
	}
}
