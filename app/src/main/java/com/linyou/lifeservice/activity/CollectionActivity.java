package com.linyou.lifeservice.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.CollectionModel;

@ContentView(R.layout.activity_collection)
public class CollectionActivity extends TitleBarActivity {

	private CollectionModel mCollectionModel;
	
	@ViewInject(R.id.listColloction)
	private ListView listColloction;
	
	
	@OnItemClick(R.id.listColloction)
	public void SelectedDistrict(AdapterView<?> parent, View view,
			int position, long id) {
		mCollectionModel.selectOrder(position);
	}
	
	@Override
	void setListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_collections);
		mCollectionModel= new CollectionModel(context);
		mCollectionModel.refreshCollection();
		listColloction.setAdapter(mCollectionModel.getCollectionAdapter());
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
