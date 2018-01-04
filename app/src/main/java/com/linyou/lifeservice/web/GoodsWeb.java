package com.linyou.lifeservice.web;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.ServiceType;
import com.linyou.lifeservice.impl.IGoodsWeb;
import com.linyou.lifeservice.listener.DataListener;

import android.content.Context;
import android.util.Log;

public class GoodsWeb extends BaseWeb implements IGoodsWeb{

	private Context context;
	private static String GoodsUrl = BaseUrl + "/goods/";

	/**
	 *  产品分类
	 */
	private static String TypeUrl = GoodsUrl + "findGoodsByType.do";
	/**
	 *  产品详情
	 */
	private static String GoodsInfoUrl = GoodsUrl + "findGoodsInfoById.do";
	/**
	 * 根据区域查询服务
	 */
	private static String FindServiceUrl = GoodsUrl + "findServiceTypeByDistrict.do";
	/**
	 * 根据区域查询产品类型
	 */
	private static String FindTypeUrl = GoodsUrl + "findGoodsTypeByServiceType.do";

	public GoodsWeb(Context context) {
		super(context);
		this.context=context;
	}

	public void findGoodsByType(String store_id,String goodTypeId,String limit,String start,String orderBy,String name,String sortord,
			final DataListener<List<Goods>> dataListener)
	{
		/*PromptManager.showDialogTest1(context, "goodsType.id是  "+goodTypeId+"    "+"limit  "+limit+"  "+"start 是"+start+"    "+
				"orderBy 是"+orderBy+"    "+
				"name 是"+name+"    "+
				"sortord 是"+sortord);*/

		String userName=mPrefUtil.getStrSetting(Constant.user_name);
		RequestParams params = new RequestParams();
		params.addBodyParameter("userName", userName);//用来对香烟进行判断 是否有权限访问香烟
		params.addBodyParameter("store_id", store_id);
		params.addBodyParameter("goodsType.id", goodTypeId);
		params.addBodyParameter("limit", limit);
		params.addBodyParameter("start", start);
		params.addBodyParameter("orderBy", orderBy);
		params.addBodyParameter("name", name);
		params.addBodyParameter("sortord", sortord);
		p.L("请求商品", "store_id是  " + store_id + "    " + "goodsType.id是  " + goodTypeId + "    " + "limit  " + limit + "  " + "start 是" + start + "    " +
				"orderBy 是" + orderBy + "    " +
				"name 是" + name + "    " +
				"sortord 是" + sortord+ "    " +
				"userName 是" + userName);
		post(TypeUrl, params, new IRequestListener() {

            @Override
            public void onFailed() {
//				String json="{\"data\":[{\"amount\":0,\"createTime\":0,\"describes\":0,\"goodsCode\":0,\"id\":0,\"name\":0,\"pic\":0,\"price\":0,\"saleTime\":0,\"salesvolumn\":0,\"isPutaway\":0,\"deleted\":0}],\"msg\":\"查询成功\n" +
//						"！\",\"start\":0,\"success\":true,\"total\":2}";
//				Type type = new TypeToken<List<Goods>>() {
//				}.getType();
//				parse(json, type, dataListener);

				dataListener.onFailed();
            }
			public void onSuccess(String json) {
				String json1="";
				Type type = new TypeToken<List<Goods>>() {
				}.getType();
				parse(json, type, dataListener);
			}
		});
	}

	/**
	 * 获取商品信息
	 * @param id
	 * @param dataListener
	 */
	public void findGoodsInfo(String id,final DataListener<Goods> dataListener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		p.L("查看商品详情是商品的id号",id);
		post(GoodsInfoUrl, params, new IRequestListener() {

			@Override
			public void onFailed() {
				dataListener.onFailed();
			}

			public void onSuccess(String json) {
				Type type = new TypeToken<Goods>() {
				}.getType();
				parse(json, type, dataListener);
			}
		});
	}



	/**
	 * 根据区域查询服务
	 * @param id
	 * @param dataListener
	 */
	public void findServiceTypeByDistrict(String id,final DataListener<List<ServiceType>> dataListener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		post(FindServiceUrl, params, new IRequestListener() {

            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
			public void onSuccess(String json) {

				Type type = new TypeToken<List<ServiceType>>() {
				}.getType();
				parse(json,type, dataListener);
			}
		});
	}

	/**
	 * 根据服务查询商品
	 * @param serviceId
	 * @param dataListener
	 */
	public void findGoodsTypeByServiceType(String serviceId,final DataListener<List<Goods>> dataListener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("serviceId", serviceId);
		post(FindServiceUrl, params, new IRequestListener() {

			public void onSuccess(String json) {

				Type type = new TypeToken<List<Goods>>() {
				}.getType();
				parse(json,type, dataListener);
			}

            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
        });
	}
}
