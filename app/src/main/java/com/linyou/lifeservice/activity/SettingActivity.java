package com.linyou.lifeservice.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.utils.DataCleanUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.UpdateManager;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends TitleBarActivity {

	private UpdateManager up_banben;

	@ViewInject(R.id.linear_advice)
	private LinearLayout linear_advice;

	@ViewInject(R.id.linear_about)
	private LinearLayout linear_about;
	
	@ViewInject(R.id.linear_contactus)
	private LinearLayout linear_contactus;
	
	@ViewInject(R.id.linear_clear)
	private LinearLayout linear_clear;
	
	@ViewInject(R.id.ban_ben)  //显示版本的信息栏
	private TextView ban_ben;

	@ViewInject(R.id.linear_update)
	private LinearLayout linear_update;

	@OnClick(R.id.linear_update)//对图层点击的监听
	void update_banben(View v)
	{
		//PublicUtil.ShowToast("需要更新");
		up_banben.checkUpdate(true);
	}

	@OnClick(R.id.linear_advice)
	void advice(View v)
	{
		startActivityByClass(AdviceActivity.class);
	}
	
	@OnClick(R.id.linear_about)
	void about(View v)
	{
		startActivityByClass(AboutActivity.class);
	}
	
	@OnClick(R.id.linear_contactus)
	void contact(View v)
	{
		startActivityByClass(ContactUsActivity.class);
	}
	
	@OnClick(R.id.linear_clear)
	void clear(View v)
	{
//		AsyncTask<String, null, Result> ds = new AsyncTask<Params, Progress, Result>
		ClearCacheTask d = new ClearCacheTask();
        PublicUtil.ShowToast("清除缓存中...");
        d.execute(this);
		
	}
	
	@Override
	void setListeners() {
		up_banben=new UpdateManager(SettingActivity.this);
	}

	
	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_setting);
		ban_ben.setText(up_banben.getVersionName());
	}

	@Override
	void RightButtonClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void LeftButtonClicked() {
		finish();
		
	}


	private class ClearCacheTask extends AsyncTask<Context, Integer, Long> {

			public void onPreExecute() {
				super.onPreExecute();
			}
			protected Long doInBackground(Context... mContexts) {
				DataCleanUtil.cleanFiles(context);
				DataCleanUtil.cleanExternalCache(context);
				return (long) 0;
			}
			protected void onProgressUpdate(Integer... progress) {
			}
			protected void onPostExecute(Long result){
				
			}
	}


}


