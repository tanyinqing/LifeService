package com.linyou.lifeservice.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;

@ContentView(R.layout.activity_contact)
public class ContactUsActivity extends TitleBarActivity {

	@ViewInject(R.id.main_phone_img)
	private ImageButton editPhone;

	@OnClick(R.id.main_phone_img)
	public void onclick(View view){
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(getResources().getString(R.string.phone_number_ofzixun)));
		startActivity(intent);
	}

	@Override
	void RightButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	void LeftButtonClicked() {
		finish();
	}

	@Override
	void setListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_contact);

	}

}
