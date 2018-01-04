package com.linyou.lifeservice.activity;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.customview.ImageGallery;
import com.linyou.lifeservice.customview.MyGridView;
import com.linyou.lifeservice.entity.Advertisement;
import com.linyou.lifeservice.entity.Classfication;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.model.MainModel;
import com.linyou.lifeservice.utils.DensityUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.UpdateManager;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends TitleBarActivity {
	private UpdateManager up_banben;

	//实现图片的轮播需要的参数
	private boolean isPlay;             // 标志变量,判断当前是否正在播放轮播图片
	private Handler handler = new Handler();
	int number;

	// 图片轮播线程
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if  (!isPlay)
				return ;
			 number=(adsGallery.getSelectedItemPosition() + 1);
			if (number==pointsCount){
				number=0;
			}
			adsGallery.setSelection(number);
			handler.postDelayed(this, 4000);
			//Log.d("图片轮播", "下一张");

		}
	};



	private MainModel mMainModel;

	//请求商品时的店铺和分类信息
	private String store_id;
	private String cate_id;

	//类别对象的列表
	private List<Classfication> classlist;

	/*
	@ViewInject(R.id.buttonMarket)
	private ImageButton buttonMarket;
	@ViewInject(R.id.buttonFood)
	private ImageButton buttonFood;
	@ViewInject(R.id.buttonWash)
	private ImageButton buttonWash;
	@ViewInject(R.id.buttonPack)
	private ImageButton buttonPack;
	 */
	/*@ViewInject(R.id.layout_one)
	private LinearLayout layout_one;

	@ViewInject(R.id.layout_last)
	private LinearLayout layout_last;*/

	/*@ViewInject(R.id.buttonxiangyan)
	private ImageButton buttonxiangyan;

	@ViewInject(R.id.buttoncanYin)
	private ImageButton buttoncanYin;

	@ViewInject(R.id.buttonshuCai)
	private ImageButton buttonshuCai;

	@ViewInject(R.id.buttonlengDong)
	private ImageButton buttonlengDong;

	@ViewInject(R.id.buttonDrink)
	private ImageButton buttonDrink;

	@ViewInject(R.id.buttonlingShi)
	private ImageButton buttonlingShi;

	@ViewInject(R.id.buttonnaiZhiPing)
	private ImageButton buttonnaiZhiPing;

	@ViewInject(R.id.buttonbingGan)
	private ImageButton buttonbingGan;

	@ViewInject(R.id.buttonfangBian)
	private ImageButton buttonfangBian;

	@ViewInject(R.id.buttondanGao)
	private ImageButton buttondanGao;

	@ViewInject(R.id.buttontangGuo)
	private ImageButton buttontangGuo;

	@ViewInject(R.id.buttonGuWu)
	private ImageButton buttonGuWu;

	@ViewInject(R.id.buttonjiuLei)
	private ImageButton buttonjiuLei;

	@ViewInject(R.id.buttonhuLi)
	private ImageButton buttonhuLi;

	@ViewInject(R.id.buttonriYongPin)
	private ImageButton buttonriYongPin;

	@ViewInject(R.id.buttonliangYou)
	private ImageButton buttonliangYou;

	@ViewInject(R.id.buttonbanGong)
	private ImageButton buttonbanGong;*/


	@ViewInject(R.id.adsGallery)  //图片容器的相册
	private ImageGallery adsGallery;
	@ViewInject(R.id.adimage_point) //小点的容器
	private LinearLayout adimage_point;

    @ViewInject(R.id.textCount)  //指示灯或图片的数量
    private TextView textCount;

	@ViewInject(R.id.rela)  //指示灯或图片的数量
    private FrameLayout rela;

	@ViewInject(R.id.buttonShoppingCar)
	private ImageButton buttonShoppingCar;

	private static int pointsCount;

	@ViewInject(R.id.class_list)
	private MyGridView mGridView;

	@OnItemClick(R.id.class_list)
	void gotoProductPage(AdapterView<?> parent,View view,int positon,long id){
		//p.L("我被点击了", "你知道吗" + parent.toString() + "  " + view.toString() + "  " + positon + "  " + id + " " + classlist.get(positon).getCate_id());
		//List<Classfication> list= (List<Classfication>) application.mPrefUtil.readObject("classlist");
		Classfication list= (Classfication) parent.getAdapter().getItem(positon);
		/*for (Classfication c:list){
			p.L("看对象的集合能否保存在配置文件里",c.toString());
		}*/
		Intent i=new Intent();
		i.putExtra("store_id", store_id);
		i.putExtra("cate_id", list.getCate_id());
		i.setClass(this, GoodsListActivity.class);
		startActivity(i);
	}

	@OnClick(R.id.buttonShoppingCar)
	void gotoShoppingCar(View v) {
		startActivityByClass(ShoppingCarActivity.class);
	}

	/*@OnClick(R.id.buttoncanYin)
	void buttoncanYin(View v) {
		gotoProducts(0);
	}

	@OnClick(R.id.buttonshuCai)
	void buttonshuCai(View v) {
		gotoProducts(1);
	}

	@OnClick(R.id.buttonlengDong)
	void buttonlengDong(View v) {
		gotoProducts(2);
	}

	@OnClick(R.id.buttonDrink)
	void buttonDrink(View v) {
		gotoProducts(3);
	}

	@OnClick(R.id.buttonlingShi)
	void buttonlingShi(View v) {
		gotoProducts(4);
	}

	@OnClick(R.id.buttonnaiZhiPing)
	void buttonnaiZhiPing(View v) {
		gotoProducts(5);
	}

	@OnClick(R.id.buttonbingGan)
	void buttonbingGan(View v) {
		gotoProducts(6);
	}

	@OnClick(R.id.buttonfangBian)
	void buttonfangBian(View v) {
		gotoProducts(7);
	}

	@OnClick(R.id.buttondanGao)
	void buttondanGao(View v) {
		gotoProducts(8);
	}

	@OnClick(R.id.buttontangGuo)
	void buttontangGuo(View v) {
		gotoProducts(9);
	}

	@OnClick(R.id.buttonGuWu)
	void buttonGuWu(View v) {
		gotoProducts(10);
	}

	@OnClick(R.id.buttonjiuLei)
	void buttonjiuLei(View v) {
		gotoProducts(11);
	}

	@OnClick(R.id.buttonhuLi)
	void buttonhuLi(View v) {
		gotoProducts(12);
	}

	@OnClick(R.id.buttonriYongPin)
	void buttonriYongPin(View v) {
		gotoProducts(13);
	}

	@OnClick(R.id.buttonliangYou)
	void buttonliangYou(View v) {
		gotoProducts(14);
	}

	@OnClick(R.id.buttonbanGong)
	void buttonbanGong(View v) {
		gotoProducts(15);
	}

	@OnClick(R.id.buttonxiangyan)
	void buttonxiangyan(View v) {
		gotoProducts(16);
	}*/


	//type的值根据客户选的生活超市或快餐外卖的不同而不同  选生活超市值为1
	//goodsType.id=1  type的值
	/*private void gotoProducts(int type) {
		application.mPrefUtil.putSetting(Constant.leiXing_numbe,type);
		Intent i = new Intent();
		District district = ServiceApplication.getInstance().readDistrict();
		if(null == district)
		{
			PublicUtil.ShowToast("请先选择区域！");
			startActivityByClass(DistrictSelectActivity.class);
			return;
		}else{
			//type =1258+"";
			//store_id=ServiceApplication.getInstance().read_String("storeId");
			//cate_id=1274+"";
			store_id=ServiceApplication.getInstance().read_String("storeId");
			cate_id=read_cate_id(type);
		}
		*//*
		else if(district.getName().equals("棕榈泉公寓"))
		{
			type = Integer.parseInt(type) + 4+"";
		}
		else if(district.getName().equals("成达小区"))
		{
			type = Integer.parseInt(type) + 8+"";
		}else if (district.getName().equals("zaq")){
			type = Integer.parseInt(type) + 0+"";
		}
		 *//*

		//i.setAction(type);
		//在Intent对象当中添加一个键值对
		if (cate_id.equals("0")){
			PublicUtil.ShowToast("该区域下没有该商品分类");
			return;
		}
		i.putExtra("store_id", store_id);
		i.putExtra("cate_id", cate_id);
		i.setClass(this, GoodsListActivity.class);
		startActivity(i);
	}
*/
	@Override
	void setListeners() {
		adsGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				changePositionImage(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		adsGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Advertisement ad = (Advertisement) mMainModel.getAdImageAdapter().getItem(i);
				if ("0".equals(ad.getIsChain())) {
					Intent intent = new Intent();
					intent.setClass(context, AdDetailActivity.class);
					intent.putExtra(Constant.INTENT_ADS, ad);
					startActivity(intent);
				}
			}
		});

	}

	@Override
	void initDatas() {
		//PromptManager.showDialogTest1(this, "测试是否有用");
		//PromptManager.showToastTest1(this, "测试是否有用");
		setTitle(R.drawable.title_logo);
		setButtonLeft("选区域");
		setButtonRight("", R.drawable.user_center_selector);

		mMainModel = new MainModel(this);

		store_id=ServiceApplication.getInstance().read_String("storeId");


		//广告数据的请求
		mMainModel.requestAds(new DataListener() {
			@Override
			public void onSuccess() {
				rela.setVisibility(View.VISIBLE);
				pointsCount = mMainModel.getAdImageAdapter().getCount();
				loadPositionImage();
			}
//如果没有广告  就隐藏掉
			@Override
			public void onFailed() {
				super.onFailed();

			}
		});

		//产品类别数据的请求
		mMainModel.requestClass(store_id);//先把店铺的id读出来，保存起来

		up_banben=new UpdateManager(MainActivity.this);
		up_banben.checkUpdate(false);

		/*adsGallery.setAdapter(mMainModel.getAdImageAdapter());//图片适配器*/

		adsGallery.setAdapter(mMainModel.getAdImageAdapter());//图片适配器
		mGridView.setAdapter(mMainModel.getmClassficationAdapter());
	}

	@Override
	void RightButtonClicked() {
		startActivityByClass(UserCenterActivity.class);
	}

	@Override
	void LeftButtonClicked() {
		startActivityByClass(DistrictSelectActivity.class);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		/*
		int w = PublicUtil.getDeviceWidth() - 20;
		int h = PublicUtil.getDeviceHeight();
		LayoutParams layoutParams = new LayoutParams(w / 2, w / 2);
		layoutParams.setMargins(5, 5, 5, 5);
		buttonMarket.setLayoutParams(layoutParams);
		buttonFood.setLayoutParams(layoutParams);

		double deta = (365 * 1.0) / (365 + 226);
		double deta_h = (w * deta) / 365 * 220;
		LayoutParams layoutParams2 = new LayoutParams((int) (w * deta),
				(int) deta_h);
		layoutParams2.setMargins(5, 5, 5, 5);
		buttonWash.setLayoutParams(layoutParams2);
		layoutParams = new LayoutParams((int) (w * (1 - deta)), (int) deta_h);
		layoutParams.setMargins(5, 5, 5, 5);
		buttonPack.setLayoutParams(layoutParams);
		 */

		int w = PublicUtil.getDeviceWidth() - 20;
		double deta = (140 * 1.0) / (295);
		//int h = PublicUtil.getDeviceHeight();
		int h = (int) (w /2* deta);
		LayoutParams layoutParams = new LayoutParams(w / 2, h);
		layoutParams.setMargins(5, 5, 5, 5);
		/*buttoncanYin.setLayoutParams(layoutParams);
		buttonshuCai.setLayoutParams(layoutParams);
		buttonlengDong.setLayoutParams(layoutParams);
		buttonDrink.setLayoutParams(layoutParams);
		buttonlingShi.setLayoutParams(layoutParams);
		buttonnaiZhiPing.setLayoutParams(layoutParams);
		buttonbingGan.setLayoutParams(layoutParams);
		buttonfangBian.setLayoutParams(layoutParams);
		buttondanGao.setLayoutParams(layoutParams);
		buttontangGuo.setLayoutParams(layoutParams);
		buttonGuWu.setLayoutParams(layoutParams);
		buttonjiuLei.setLayoutParams(layoutParams);
		buttonhuLi.setLayoutParams(layoutParams);
		buttonriYongPin.setLayoutParams(layoutParams);
		buttonliangYou.setLayoutParams(layoutParams);
		buttonbanGong.setLayoutParams(layoutParams);
		buttonxiangyan.setLayoutParams(layoutParams);*/


		// int dp2px = DensityUtil.dip2px(this, 60);
		// FrameLayout.LayoutParams LayoutParams3 = new
		// FrameLayout.LayoutParams(dp2px, dp2px);
		// LayoutParams3.setMargins(w/5 * 4, h/5 * 4, w/5, h/5);
		// buttonShoppingCar.setLayoutParams(LayoutParams3);

		super.onWindowFocusChanged(hasFocus);
	}

	protected void loadPositionImage() {

		ImageView aImageView = null;
		adimage_point.removeAllViews();//清除容器内所有的元素
		for (int i = 0; i < pointsCount; i++) {
			aImageView = new ImageView(this);
			if (i == 0) {
				aImageView.setImageResource(R.drawable.point1);
			} else {
				aImageView.setImageResource(R.drawable.point2);
			}

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					30, 30);
			params.leftMargin = 10;
			params.rightMargin = 10;
			adimage_point.addView(aImageView, params);//容器里加入图片和图片的宽和高
		}
	}

	public void changePositionImage(int aPos) {
		//从父容器中得到它的子孩子
		ImageView aImageView = (ImageView) adimage_point.getChildAt(aPos);
		if (aImageView != null) {
			aImageView.setImageResource(R.drawable.point1);
		}
		for (int i = 0; i < pointsCount; i++) {
			if (i != aPos) {//给父容器中的其他子孩子赋予其他的值
				aImageView = (ImageView) adimage_point.getChildAt(i);
				aImageView.setImageResource(R.drawable.point2);
			}
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            exit();
            return false;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }
    }
    
	boolean isExit = false;
    Handler mHandler = new Handler()
    {
        
        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }
        
    };
	
    public void exit()
    {
        if (!isExit)
        {
            isExit = true;
            PublicUtil.ShowToast("再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
        else
        {
            ServiceApplication.getInstance().exit();
        }
    }
//    int count = 0;
//    private void updateCount()
//    {
//        count = application.getCount();
//        if(count > 0)
//        {
//            textCount.setVisibility(View.VISIBLE);
//            textCount.setText(""+count);
//        }
//        else
//        {
//            textCount.setVisibility(View.GONE);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        application.removeTextView(textCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        application.addTextView(textCount);
//        updateCount();
		isPlay = true;
		runnable.run();

		//取出地址的对象   并把对象中的类别信息提取出来 并打印出来
		//classlist=(List<Classfication>)application.mPrefUtil.readObject("classlist");
		/*p.L("测试能否读出店铺分类",classlist.get(0).toString());
		p.L("测试能否读出店铺分类", classlist.get(1).toString());
*/
		//把对象的集合保存在配置文件中
		//application.mPrefUtil.putSetting("tan",classlist);
		//每一次显示的时候都对店铺ID和适配器的数据重新初始化
		store_id=ServiceApplication.getInstance().read_String("storeId");
		mMainModel.requestClassfication();


		/*adsGallery.setFocusable(true);
		adsGallery.setFocusableInTouchMode(true);
		adsGallery.requestFocus();*/
		mGridView.setFocusable(false);



		/*store_id=ServiceApplication.getInstance().read_String("storeId");
		int i=Integer.valueOf(store_id);
		if (i==10){
			layout_one.setVisibility(View.VISIBLE);
		}else if (i==425||i==436){
			layout_last.setVisibility(View.VISIBLE);
		}else {
			layout_one.setVisibility(View.GONE);
			layout_last.setVisibility(View.GONE);
		}*/
    }

	@Override
	protected void onPause() {
		super.onPause();
		isPlay = false;
	}
	/**
	 * 定义一个方法  来读取分类的值
	 *
	 */
/*	public  String read_cate_id(int j){
		int c=0;
		int i=Integer.valueOf(store_id);
		int k1[]={1398,1324,1461,1258,1323,1280,1290,1274,1301,1315,1321,1439,1449,1411,1419,1440};
		int k2[]={0,0,1677,1564,1688,1695,1701,1708,1713,1718,1723,1729,1735,1742,1751,1757};
		int k3[]={0,0,1479,1483,1492,1499,1505,1512,1517,1522,1527,1533,1539,1546,1555,1561};
		int k4[]={0,0,1758,1759,1760,1761,1762,1763,1764,1765,1766,1767,1768,1769,1770,1771,2324};
		int k5[]={0,0,2227,2228,2229,2230,2231,2232,2233,2234,2235,2236,2237,2238,2239,2240,2326};
		if (i==10){
			c=k1[j];
		}else if (i==28){
			c=k2[j];
		}else if (i==352){
			c=k3[j];
		}else if (i==425){
			c=k4[j];
		}else if (i==436){
			c=k5[j];
		}else {
			c=0;
		}
		return String.valueOf(c);
	}*/
}
