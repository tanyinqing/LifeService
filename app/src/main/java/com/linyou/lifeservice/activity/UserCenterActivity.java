package com.linyou.lifeservice.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.customview.CircularImage;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.model.UserCenterModel;
import com.linyou.lifeservice.utils.PublicUtil;

/**
 * 个人中心   linearCollection 我的收藏对应的页面
 * @author 志强
 *
 */
@ContentView(R.layout.activity_user_center)
public class UserCenterActivity extends TitleBarActivity {
	/**************************注解绑定控件**************************/
	private UserCenterModel mUserCenterModel;

	@ViewInject(R.id.buttonLogin)
	private Button buttonLogin;
	@ViewInject(R.id.buttonRegister)
	private Button buttonRegister;
	@ViewInject(R.id.linearOrderList)
	private LinearLayout linearOrderList;
	@ViewInject(R.id.linearCollection)
	private LinearLayout linearCollection;
	@ViewInject(R.id.linearAddr)
	private LinearLayout linearAddr;
	@ViewInject(R.id.linearExit)
	private LinearLayout linearExit;
	@ViewInject(R.id.relativeUser)
	private RelativeLayout relativeUser;
	@ViewInject(R.id.relativeLogin)
	private RelativeLayout relativeLogin;
	@ViewInject(R.id.textName)
	private TextView textName;

	@ViewInject(R.id.textAddr)
	private TextView textAddr;
	@ViewInject(R.id.imageHead)
	private CircularImage imageHead;
	/***************************************************************/

	@OnClick(R.id.buttonLogin)  //登录页面
	public void login(View view)
	{
		startActivityByClass(LoginAcitvity.class);
	}
	
	@OnClick(R.id.linearExit)  //退出登录页面
	public void exit(View view)
	{
		mUserCenterModel.exit();
		updateUser();
	}
	
	@OnClick(R.id.buttonRegister)  //注册页面
	public void register(View view)
	{
		startActivityByClass(RegisterAcitivty.class);
	}
	@OnClick(R.id.relativeUser)    //修改资料页面
	public void userInfo(View view)
	{
		startActivityByClass(UserInfoActivity.class);
	}
	@OnClick(R.id.linearOrderList)  //我的订单页面
	public void orderList(View view)
	{
        if(!mUserCenterModel.isLogin())
        {
            PublicUtil.ShowToast("请先登录");
            return;
        }
        startActivityByClass(OrderListActivity.class);
	}
	@OnClick(R.id.linearCollection)   //我的收藏页面
	public void collectionList(View view)
	{
        if(!mUserCenterModel.isLogin())
        {
            PublicUtil.ShowToast("请先登录");
            return;
        }
        startActivityByClass(CollectionActivity.class);
	}
	@OnClick(R.id.linearAddr)   //常用地址页面
	public void addrList(View view)
	{
        if(!mUserCenterModel.isLogin())
        {
            PublicUtil.ShowToast("请先登录");
            return;
        }
		Intent i = new Intent();
		i.setAction(Constant.addr_manage);
		i.setClass(this, AddrManageActivity.class);
		startActivity(i);
	}
	
	@Override
	void setListeners() {
	}

	@Override
	void initDatas() {
		//PromptManager.showDialogTest1(this, "测试是否有用");
		//PromptManager.showToastTest1(this, "测试是否有用");
		setButtonLeft("返回");
		setTitle(R.drawable.title_user_center);
		setButtonRight("", R.drawable.setting_selector);
		mUserCenterModel = new UserCenterModel(this);
	}

	@Override
	void RightButtonClicked() {
		startActivityByClass(SettingActivity.class);
	}

	@Override
	void LeftButtonClicked() {
		finish();
		
	}

	private void updateUser()
	{
		if(mUserCenterModel.isLogin())
		{
			buttonLogin.setVisibility(View.GONE);
			buttonRegister.setVisibility(View.GONE);
			relativeLogin.setVisibility(View.VISIBLE);
            linearExit.setVisibility(View.VISIBLE);
			
			User u = mUserCenterModel.getUser();
			//String name = u.getNickName();  这是原来的代码
			//这是自己写的代码
			String name = u.getUserName();
			//textName.setText("" + ((null == name || "".equals(name))?"匿名":name));
			textName.setText("" + ((null == name || "".equals(name))?u.getNickName():name));
			PublicUtil.getImageLoader().displayImage(Constant.URLBASE + u.getHeadportrait(), imageHead, PublicUtil.getHeadOption());
			
			District d = mUserCenterModel.getDistrict();
			if(d != null)
			{
				textAddr.setText("常住地："+d.getName());
			}
		}
		else
		{
			buttonLogin.setVisibility(View.VISIBLE);
			buttonRegister.setVisibility(View.VISIBLE);
			relativeLogin.setVisibility(View.GONE);
            linearExit.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onResume() {
		updateUser();
		super.onResume();
	}
	
}
