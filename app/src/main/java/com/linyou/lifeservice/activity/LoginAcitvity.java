package com.linyou.lifeservice.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.LoginModel;

/**
 * 登陆
 * @author 志强
 *
 */
@ContentView(R.layout.activity_login)
public class LoginAcitvity extends TitleBarActivity{
	
	@ViewInject(R.id.editPhone)
	private EditText editPhone;
	
	@ViewInject(R.id.editPassword)
	private EditText editPassword;
	

	@ViewInject(R.id.buttonLogin)
	private Button buttonLogin;

	@ViewInject(R.id.buttonRegister)
	private Button buttonRegister;
	
	@ViewInject(R.id.textForget)
	private TextView textForget;

	private LoginModel mLoginModel;
	
	@Override
	void setListeners() {
	}
	
	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_logo);
		if(mLoginModel == null)
		{
			mLoginModel = new LoginModel(this);
		}
	}
	
	@OnClick(R.id.buttonLogin)
	public void login(View view)
	{
		mLoginModel.login(editPhone.getText().toString(), editPassword.getText().toString());
	}
	@OnClick(R.id.buttonRegister)
	public void register(View view)
	{
		mLoginModel.gotoRegister();
	}
	
	@OnClick(R.id.textForget)
	public void forgetPwd(View view)
	{
		startActivityByClass(FindPwdActivity.class);
	}

	@Override
	void RightButtonClicked() {
		
	}

	@Override
	void LeftButtonClicked() {
		finish();
	}
}
