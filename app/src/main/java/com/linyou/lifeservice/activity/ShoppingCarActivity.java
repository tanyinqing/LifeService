package com.linyou.lifeservice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.customview.FocusedTextView;
import com.linyou.lifeservice.model.ShoppingCarModel;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;

@ContentView(R.layout.activity_shoppingcar)
public class ShoppingCarActivity extends TitleBarActivity {
	
	private ShoppingCarModel mShoppingCarModel;
	
	@ViewInject(R.id.buttonConfirm)
	private Button buttonConfirm;
	@ViewInject(R.id.textCount)
	private TextView textCount;
	@ViewInject(R.id.textTotal)
	private TextView textTotal;
	@ViewInject(R.id.listProducts)
	private ListView listProducts;
	@ViewInject(R.id.point_out)
	private TextView point_out;
	@ViewInject(R.id.scroll_bar)
	private FocusedTextView scroll_bar;
	
	private OrderUtil mOrderUtil;

	private String action_from;

	//这个是商品的总价
	private String totalPrice="";

	//这个是商品的起送价格
	//private int qisongjiage=15;
	private int qisongjiage=0;

	@OnClick(R.id.buttonConfirm)
	void onClick(View v) {
//		mOrderUtil.updateGoodsList(mShoppingCarModel.getShopCarAdapter().getList());
        if(mOrderUtil.getCount() == 0)
        {
            PublicUtil.ShowToast("请先选购商品");
            return;
        }
		if(null != action_from)
		{
			Intent intent = new Intent();
			intent.setAction(action_from);
			intent.setClass(context,ConfirmActivity.class);
			startActivity(intent);

		}else
		{
			startActivityByClass(ConfirmActivity.class);
		}
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
		action_from = getIntent().getAction();
		setButtonLeft("返回");
		setTitle(R.drawable.title_shoppingcar);
		mShoppingCarModel = new ShoppingCarModel(this,mHandler);
		listProducts.setAdapter(mShoppingCarModel.getShopCarAdapter());
		mShoppingCarModel.showCar();
		mOrderUtil = OrderUtil.getInstance();
		mOrderUtil.registerHandler(mHandler);

	}

    private void updateBar()
    {
		totalPrice=mOrderUtil.getTotalPrice();
        textTotal.setText("合计：￥"+PublicUtil.priceFormat(mOrderUtil.getTotalPrice()));
        textCount.setText("共计："+mOrderUtil.getCount()+"件商品");
        if( 0 == mOrderUtil.getCount())
        {
            mShoppingCarModel.getShopCarAdapter().clear();
        }

		//根据商品的总价格对界面做的操作
		if (!(Double.valueOf(mOrderUtil.getTotalPrice())>=qisongjiage)){
			buttonConfirm.setClickable(false);
			buttonConfirm.setBackgroundColor(getResources().getColor(R.color.typeface));
			point_out.setVisibility(View.VISIBLE);
		}else{
			buttonConfirm.setClickable(true);
			buttonConfirm.setBackgroundColor(getResources().getColor(R.color.app_color));
			point_out.setVisibility(View.GONE);
		}

    }

	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.ORDER_CHANGED:
                updateBar();
				/*//这句是我后来加上去的
				mShoppingCarModel.getShopCarAdapter().notifyDataSetChanged();*/
				break;
				case 1:
					if (!(Double.valueOf(mOrderUtil.getTotalPrice())>qisongjiage)){
						buttonConfirm.setClickable(false);
						buttonConfirm.setBackgroundColor(getResources().getColor(R.color.typeface));
						point_out.setVisibility(View.VISIBLE);
					}
					break;
				case 2:
					if ((Double.valueOf(mOrderUtil.getTotalPrice())>qisongjiage)){
						if(!buttonConfirm.isClickable()){
							buttonConfirm.setClickable(true);
							buttonConfirm.setBackgroundColor(getResources().getColor(R.color.app_color));
							point_out.setVisibility(View.GONE);
						}
					}

			default:
				break;
			}
		}
		
	};

    @Override
    protected void onResume() {
        mHandler.sendEmptyMessage(Constant.ORDER_CHANGED);
        super.onResume();
		if (ShoppingCarModel.iscurrentInTimeScope(0,0,23,59)){
			scroll_bar.setVisibility(View.VISIBLE);
		}
    }

}
