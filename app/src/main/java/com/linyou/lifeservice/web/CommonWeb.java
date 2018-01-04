package com.linyou.lifeservice.web;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams; //携带参数的类是来自jar包
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Advertisement;
import com.linyou.lifeservice.entity.Classfication;
import com.linyou.lifeservice.entity.Comment;
import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.impl.ICommonWeb;
import com.linyou.lifeservice.listener.DataListener;

import android.content.Context;
import android.util.Log;

/**
 *广告、区域相关
 * @author 志强
 *
 */
public class CommonWeb extends BaseWeb implements ICommonWeb{

	//  http://211.149.169.221:8080/umijoyappsvr/common/findDistrict.do
	private static String CommonUrl = BaseUrl + "/common/";

	private static String FindDistrictUrl = CommonUrl + "findDistrict.do";

	//private static String FindClass = CommonUrl + "findClass.do";
	private static String FindClass = "http://211.149.169.221:8080/umijoyappsvr/goods/findServiceTypeByDistrict.do";

	/*http://211.149.169.221:8080/umijoyappsvr/goods/findServiceTypeByDistrict.do?storeId=10*/

	private static String FindAdvertisementList = CommonUrl + "findAdvertisementList.do";

	private static String FindGoodsCommentsList = CommonUrl + "findGoodsCommentsByCon.do";

    /**
     * 忘记密码
     */
    private static String ForgetPasswordUrl = BaseUrl + "/user/" +"forgetPassword.do";

	private Context context;
	public CommonWeb(Context context) {
		super(context);
	}

	/*
	* 获取店铺类别的信息
	*/
	@Override
	public void findClass(String storeid, final DataListener<List<Classfication>> dataListener) {

		RequestParams params=new RequestParams();
		params.addBodyParameter("storeId",storeid);
		p.L("获得产品类别时传递的参数", storeid);
		//把访问的url改了，目的是访问网络成功  等接口写好后再把网址该过来
		post(FindClass, params, new IRequestListener() {
			public void onSuccess(String json) {    //利用模拟数据进行测试 数据处理能力的方法
				Type type = new TypeToken<List<Classfication>>() {
				}.getType();
				parse(json, type, dataListener);
			}

			@Override
			public void onFailed() {
				//如果访问网络失败要在主页回应   则调用该方法
				dataListener.onFailed();
			}

		});
	}


	/**
	 * 获取区域列表
	 * @param dataListener
	 */
	public void findDistrict(final DataListener<List<District>> dataListener)
	{
		//RequestParams就是一个请求是携带的一个数据参数  以键值对的形式
		RequestParams params = new RequestParams();
		post(FindDistrictUrl, params, new IRequestListener() {

            @Override
            public void onFailed() {
				/*
				 //dataListener.onFailed();
				String json="{\"data\":[{\"createTime\":\"2015-02-14T14:41:46\",\"id\":1,\"name\":\"模拟数据\n" +
						"1\"},{\"createTime\":\"2015-02-14T14:41:46\",\"id\":1,\"name\":\"模拟数据2\"}],\"msg\":\"查询成功\n" +
						"！\",\"success\":true}";
				Type type = new TypeToken<List<District>>() {
				}.getType();
				parse(json, type, dataListener);
				 */

            }
			public void onSuccess(String json) {
				String json1=""; //这个是模拟数据
				Type type = new TypeToken<List<District>>() {
				}.getType();
				parse(json, type, dataListener);
			}
		});
	}



	public void findAdvertisementList(final DataListener<List<Advertisement>> dataListener)
	{
		RequestParams params = new RequestParams();
		serviceApplication.mPrefUtil.putSetting("tiShi",1);
		post(FindAdvertisementList, params, new IRequestListener() {
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
			public void onSuccess(String json) {
				Type type = new TypeToken<List<Advertisement>>() {
				}.getType();
				parse(json, type, dataListener);
			}
		});
	}
/*
	获取评论列表
 */
	@Override
	public void findGoodsCommentsByCon(String goodsId, String limit,
			String start, final DataListener<List<Comment>> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("goodsId", goodsId);
		params.addBodyParameter("limit", limit);
		params.addBodyParameter("start", start);
				post(FindGoodsCommentsList, params, new IRequestListener() {

					@Override
					public void onFailed() {
						dataListener.onFailed();
					}

					public void onSuccess(String json) {
						Type type = new TypeToken<List<Comment>>() {
						}.getType();
						parse(json, type, dataListener);
					}
				});

	}

    @Override
    public void forgetPassword(String phoneNum, String phoneCode,
                               final DataListener<String> dataListener) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phoneNum", phoneNum);
        params.addBodyParameter("phoneCode", phoneCode);
		p.L("忘记密码时传递的参数",ForgetPasswordUrl+"  "+phoneNum+"  "+phoneCode);
		post(ForgetPasswordUrl, params, new IRequestListener() {

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



}
