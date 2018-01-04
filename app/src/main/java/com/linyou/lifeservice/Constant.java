package com.linyou.lifeservice;

public class Constant {

	public static final String URLBASE = "http://2.1.1.221/";  //下载图片是用到的网址

	//微信的真实数据 注册码
	public static final String APP_ID = "wx40";


	public static final String prefName = "umijoy_pref";//缓存数据的名字
	
	public static final String user_pref = "user";  //配置信息中用户值对应的键
	public static final String user_name = "userName";  //配置信息中用户名字值对应的键

	public static final String session_pref = "JSESSIONID";   //配置信息中session值对应的键

	public static final String COOKIE_DOMAIN = "2.1.1.2:8080";

	public static final String district_pref = "district";
    public static final String address_pref = "address";//配置信息中地址值对应的键
	
	public static final String type_market = "1";  //第一个图片对应的商品的type  用于区分四种不同的类型
	public static final String type_food = "2";
	public static final String type_wash = "3";
	public static final String type_pack = "4";
	
//	public static final String ActionType = "type";
	
	public static final String addr_manage = "5";
	public static final String addr_sel = "6";
	
	public static final String INTENT_ADDR = "addr";
	public static final String INTENT_GOODS_ID = "goods_id";
	public static final String INTENT_ORDER_ID = "order_id";
    public static final String INTENT_ADS = "ads";

	public static final String ACTION_FORM_ORDER = "from_order";

	public static final String DEFAULT_LIMIT = "15";//测试的时候10个数据  真实的时候可以用到15个数据 Log只能打印10个量的数据
	

	public static final int ORDER_CHANGED = 7;

	public static final String leiXing_numbe ="leiXing_numbe";//选择商品的类型是第几个
	public static final int dianPu_numbe = 7;//选择商品的店铺代号


	
}
