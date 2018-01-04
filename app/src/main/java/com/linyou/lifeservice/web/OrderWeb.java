package com.linyou.lifeservice.web;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Order;
import com.linyou.lifeservice.impl.IOrderWeb;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.LogTxt;

import android.content.Context;
import android.provider.ContactsContract.RawContacts.Data;
import android.util.Log;

public class OrderWeb extends BaseWeb implements IOrderWeb{
	
	private static String OrderUrl = BaseUrl + "/order/";
	
	//创建订单
	private static String CreateOrderUrl = OrderUrl + "createOrder.do";
	//查询订单列表
	private static String QueryOrderListUrl = OrderUrl + "queryOrders.do";
	//查询订单详情
	private static String OrderDetailUrl = OrderUrl + "queryOrderById.do";
	//删除订单
	private static String DeleteOrderUrl = OrderUrl + "deleteOrderById.do";
	
	private Context context;
	public OrderWeb(Context context) {
		super(context);
		this.context=context;
	}
	
	/**
	 * 创建订单
	 */
	public void createOrder(String status,String goodsIds,String userId,String addressId,String payMethodId,String remark,String discountCode,final DataListener dataListener)
	{
//http://210.56.209.74:9080/umijoy/order/createOrder.do?goodsIds=5N6C4N10&user.id=10&deliveryAddress%20.id=2&payMethod.id=1&remark=gfdgdfgdfg&discountCode=fghhgfhgfh
		//http://192.168.1.61:8080/umijoyappsvr/order/createOrder.do?goodsIds=%@&user.id=%@&
		// deliveryAddress.id=%@&payMethod.id=%@&remark=%@&discountCode=%@&storeId=%@&storeName=%@
		RequestParams params = new RequestParams();
		params.addBodyParameter("goodsIds", goodsIds.trim());
		params.addBodyParameter("user.id", userId.trim());
		params.addBodyParameter("deliveryAddress.id", addressId.trim());
		params.addBodyParameter("payMethod.id", payMethodId.trim());
		params.addBodyParameter("remark", remark.trim());
		params.addBodyParameter("discountCode", discountCode.trim());
		params.addBodyParameter("storeId", mPrefUtil.getStrSetting("storeId").trim());
		params.addBodyParameter("storeName", mPrefUtil.getStrSetting("storeName").trim());
		params.addBodyParameter("status", status);
		LogTxt.writeLog(goodsIds + "  goodsIds    "+
				userId + " userId  "+
				addressId + "  deliveryAddress.id  "+
				payMethodId + "  payMethod.id  "+
				discountCode + "  remark  "+
				mPrefUtil.getStrSetting("storeId") + "  discountCode  "+
				mPrefUtil.getStrSetting("storeName") + "  storeName  "+
				status + "  status  ","结算时的参数");
		//params.addBodyParameter("storeName", mPrefUtil.getStrSetting("storeName").trim());


		/*Log.d("tanyinqing查看生成订单的操作",CreateOrderUrl + "    " + "goodsIds   " + goodsIds + "      " + "user.id   " + userId +
				"      " + "deliveryAddress.id  " + addressId + "      " + "payMethod.id  " + payMethodId + "      " + "remark  " + remark + "      "
				+ "discountCode  " + discountCode + "      "+ "storeId  " + mPrefUtil.getStrSetting("storeId") + "      "+ "storeName  " + mPrefUtil.getStrSetting("storeName") + "      ");*/

		post(CreateOrderUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				Type type = new TypeToken<String>() {
				}.getType();
				parse(json, type, dataListener);
			}

			@Override
			public void onFailed() {
				dataListener.onFailed();
			}
		});
	}
	
	/**
	 * 获取订单列表
	 * @param listener
	 */
	public void queryOrderList(String userId,String limit,String start,final DataListener<List<Order>> listener)
	{
		PromptManager.showDialogTest(context, QueryOrderListUrl + "    " + "userId   " + userId + "      " + "limit   " + limit +"   "
				+ "start  " + start + "      ");
		Log.d("查看生成订单的操作",QueryOrderListUrl + "    " + "userId   " + userId + "      " + "limit   " + limit +"   "
				+ "start  " + start + "      ");
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userId);
		params.addBodyParameter("limit", limit);
		params.addBodyParameter("start", start);
		post(QueryOrderListUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				p.L("获取订单列表的返回数据", json);
				Type type = new TypeToken<List<Order>>(){}.getType();
				parse(json, type, listener);
			}
            @Override
            public void onFailed() {
                listener.onFailed();
            }
		});
	}
	
	/**
	 * 获取订单详情
	 * @param id
	 * @param listener
	 */
	public void orderDetail(String id,final DataListener<Order> listener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		p.L("查询订单详情订单ID",id);
		post(OrderDetailUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				Type type = new TypeToken<Order>() {
				}.getType();
				parse(json, type, listener);
			}

			@Override
			public void onFailed() {
				listener.onFailed();
			}
		});
	}
	
	/**
	 * 删除订单
	 * @param id
	 * @param listener
	 */
	public void deleteOrder(String id,final DataListener listener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		post(DeleteOrderUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				parse(json, listener);
			}

            @Override
            public void onFailed() {
                listener.onFailed();
            }
		});
	}

	/**
	* 去拿自付宝的加密数据
	* @param
	* @param
	*/
	public void goToPay(String orderSn,final DataListener<String> listener){
		//http://192.168.10.192:8080/umijoyappsvr/GoPay.do?orderSn=1412462532
		//http://192.168.1.9:8080/umijoyappsvr/GoPay.do?orderSn=1628815112
		RequestParams params=new RequestParams();
		params.addBodyParameter("orderSn",orderSn);
		post("http://211.149.169.221:8080/umijoyappsvr/GoPay.do", params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				Log.d("tanyinqing web", "正式数据------------------------------------>这是密钥" +
						json);


				/*Type type = new TypeToken<String>() {
				}.getType();
				parse(json, type, listener);*/
				listener.onSuccess(json);
			}

			@Override
			public void onFailed() {
				listener.onFailed();
			}
		});
	}



}
