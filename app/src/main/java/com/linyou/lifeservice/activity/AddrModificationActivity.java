package com.linyou.lifeservice.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.model.AddrEditModel;
import com.linyou.lifeservice.model.AddrModificationModel;
import com.linyou.lifeservice.utils.PublicUtil;

@ContentView(R.layout.activity_addr_modification)
public class AddrModificationActivity extends TitleBarActivity {

	private  DeliveryAddress address;

	private String name;
	private String addr;
	private String phone;
	
	private AddrModificationModel mAddrModificationModel;
	
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
			mAddrModificationModel.save(address.getId(), name, addr, phone);
		}
	}
	
	
	@Override
	void setListeners() {
		// TODO Auto-generated method stub
		Intent intent=this.getIntent();
		address= (DeliveryAddress) intent.getSerializableExtra("address");
	}

	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_modification_addr);
		mAddrModificationModel = new AddrModificationModel(this);

		editName.setText(address.getReciever());
		editPhone.setText(address.getRecieverPhone());
		editAddr.setText(address.getAddress());
	}

	@Override
	void RightButtonClicked() {
		
	}

	@Override
	void LeftButtonClicked() {
		finish();
	}

}
