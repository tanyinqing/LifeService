package com.linyou.lifeservice.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.ModifyPwdModel;

/**
 * 修改密码
 * @author 志强
 *
 */
@ContentView(R.layout.activity_modify_pwd)
public class ModifyPassWordActivity extends TitleBarActivity {
	
	private ModifyPwdModel mPwdModel;
	
	@ViewInject(R.id.editOld)
	private EditText editOld;
	
	@ViewInject(R.id.editNew)
	private EditText editNew;
	
	@ViewInject(R.id.editConfirm)
	private EditText editConfirm;
	
	@ViewInject(R.id.buttonConfirm)
	private Button buttonConfirm;
	
	@OnClick(R.id.buttonConfirm)
	void confirm(View v)
	{
		mPwdModel.modifypwd(editOld.getText().toString(), editNew.getText().toString(), editConfirm.getText().toString());
	}
	
	@Override
	void setListeners() {
	}
	
	@Override
	void initDatas() {

		setButtonLeft("返回");
		setTitle(R.drawable.title_edit_pwd);
		mPwdModel = new ModifyPwdModel(this);
	}

	@Override
	void RightButtonClicked() {
		
	}

	@Override
	void LeftButtonClicked() {
		finish();
		
	}
	
	
}
