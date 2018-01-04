package com.linyou.lifeservice.activity;

import com.lidroid.xutils.view.annotation.ContentView;
import com.linyou.lifeservice.R;

@ContentView(R.layout.activity_about)
public class AboutActivity extends TitleBarActivity {

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
		setTitle(R.drawable.title_about);
	}

}
