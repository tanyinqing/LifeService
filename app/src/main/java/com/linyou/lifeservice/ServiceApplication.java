package com.linyou.lifeservice;

import java.util.ArrayList;
import java.util.List;

import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.utils.PreferenceUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ServiceApplication extends Application {

	private static ServiceApplication instance;
	public static PreferenceUtil mPrefUtil;  //用于配制信息的业务类  继成了各种方法
	private List<Activity> mActivityList;
	// IWXAPI 是第三方app和微信通信的openapi接口
	private static IWXAPI api;
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		mPrefUtil = new PreferenceUtil(this, Constant.prefName, Context.MODE_PRIVATE);
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
		mActivityList = new ArrayList<Activity>();
	}


	public static ServiceApplication getInstance() {
		return instance;
	}


	/**
	 * 定义一个方法  来读取配置的信息的值
	 *
	 */
	public static String read_String(String store_id){
		String m=mPrefUtil.getStrSetting(store_id);
		return m;
	}
	/**
	 * 保存用户信息
	 * @param u
	 */
	public void saveUser(User u)
	{
		mPrefUtil.putSetting(Constant.user_pref, u);
		if (null!=u){
			mPrefUtil.putSetting(Constant.user_name,u.getNickName());
		}

	}
	/**
	 * 读取用户信息
	 * @return
	 */
	public User readUser()
	{
		User user = (User) mPrefUtil.readObject(Constant.user_pref);
		return (User) mPrefUtil.readObject(Constant.user_pref);
	}

	/**
	 * 保存服务区域
	 * @param district
	 */
	public void saveDistrict(District district)
	{
		mPrefUtil.putSetting(Constant.district_pref, district);
		if (null!=district) {
			mPrefUtil.putSetting("storeId",district.getId());
			mPrefUtil.putSetting("storeName",district.getName());
			mPrefUtil.putSetting("classlist",district.getClasslist());
		}

	}

	/**
	 * 读取服务区域
	 * @return
	 */
	public District readDistrict()
	{
		return (District) mPrefUtil.readObject(Constant.district_pref);
	}

    /**
     * 最近使用地址
     * @param address
     */
    public void saveAddress(DeliveryAddress address)
    {
        mPrefUtil.putSetting(Constant.address_pref, address);
    }

    public DeliveryAddress readAddress()
    {
        return (DeliveryAddress) mPrefUtil.readObject(Constant.address_pref);
    }
	/**
	 * session信息
	 * @param
	 */
	public void saveSession(String session)
	{
		mPrefUtil.putSetting(Constant.session_pref, session);
	}

	public String readSession()
	{
		return mPrefUtil.getStrSetting(Constant.session_pref);
	}
	/**
	 * 注册和退出Activity
	 * @param
	 */

	public void register(Activity activity)
	{
		mActivityList.add(activity);
	}

	public void exit()
    {
        try {
            for (Activity activity : mActivityList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}
