package com.linyou.lifeservice.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.DistrictSelectModel;

@ContentView(R.layout.activity_district_select)
public class DistrictSelectActivity extends TitleBarActivity {

	private DistrictSelectModel mDistrictSelectModel;
	@ViewInject(R.id.listDistrict)
	private ListView listDistrict;

	@OnItemClick(R.id.listDistrict)//view是被点击的子类 可动态改变item
	public void SelectedDistrict(AdapterView<?> parent, View view,
			int position, long id) {
		mDistrictSelectModel.selectDistrict(position);
	}

	@Override
	void setListeners() {
	}

	@Override
	void initDatas() {
		setTitle(R.drawable.title_select_area);
		setButtonLeft("返回");
		mDistrictSelectModel = new DistrictSelectModel(this);
		listDistrict.setAdapter(mDistrictSelectModel.getDistrictAdapter());
		mDistrictSelectModel.requestDistrict();
	}

	@Override
	void RightButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	void LeftButtonClicked() {
		finish();
	}

}
