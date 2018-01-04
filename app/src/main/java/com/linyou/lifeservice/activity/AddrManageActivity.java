package com.linyou.lifeservice.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.AddrManageModel;

/*工作思路
每次显示时从web端请求数据  mAddrManageModel.requestAddr();
每次选中一个地址是，都把地址的对象带到下一页
buttonAdd  新增收货人地址
*/
@ContentView(R.layout.activity_addrmanage)
public class AddrManageActivity extends TitleBarActivity {
	
	private AddrManageModel mAddrManageModel;
	private String action;
	@ViewInject(R.id.listAddr)
	private ListView listAddr;

	@ViewInject(R.id.buttonAdd)
	private Button buttonAdd;
	
	@OnClick(R.id.buttonAdd)
	void addAddr(View v)
	{
		startActivityByClass(AddrEditActivity.class);
	}
	
	@OnItemClick(R.id.listAddr)
	public void SelectedDistrict(AdapterView<?> parent, View view,
			int position, long id) {
		if(Constant.addr_sel.equals(action) )
		//if(Constant.addr_sel.equals("6") )
		{
			mAddrManageModel.selectAddr(position);
		}
	}
	
	@Override
	void setListeners() {

	}

	@Override
	void initDatas() {
		
		action = getIntent().getAction();
		
		setButtonLeft("返回");
		setTitle(R.drawable.title_addr);
		mAddrManageModel = new AddrManageModel(this);
		mAddrManageModel.setAction(action);
		listAddr.setAdapter(mAddrManageModel.getAddrAdapter());//进行数据匹配
	}

	@Override
	void RightButtonClicked() {
		
	}

	@Override
	void LeftButtonClicked() {
		finish();
		
	}

	@Override
	protected void onResume() {
		mAddrManageModel.requestAddr();
		super.onResume();
	}

}
