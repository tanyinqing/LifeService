package com.linyou.lifeservice.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.AddrEditModel;
import com.linyou.lifeservice.utils.PublicUtil;

@ContentView(R.layout.activity_addr_edit)
public class AddrEditActivity extends TitleBarActivity {
	
	private String name;
	private String addr;
	private String phone;
	
	private AddrEditModel mAddrEditModel;
	
	@ViewInject(R.id.editName)
	private EditText editName;
	@ViewInject(R.id.editPhone)
	private EditText editPhone;
	@ViewInject(R.id.editAddr)
	private EditText editAddr;
	@ViewInject(R.id.buttonSave)
	private Button buttonSave;
	
	@OnClick(R.id.buttonSave)
	void OnClick(View v)
	{
		name = editName.getText().toString().trim();
		addr = editAddr.getText().toString().trim();
		phone = editPhone.getText().toString().trim();
		if(PublicUtil.isLegal(name, addr, phone))
		{
			mAddrEditModel.save(name, addr, phone);
		}
	}
	
	
	@Override
	void setListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_edit_addr);
		mAddrEditModel = new AddrEditModel(this);
	}

	@Override
	void RightButtonClicked() {
		
	}

	@Override
	void LeftButtonClicked() {
		finish();
	}

}
