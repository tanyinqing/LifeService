package com.linyou.lifeservice.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;  //来自jar包的网络访问类
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.LogTxt;
import com.linyou.lifeservice.utils.MyLog;
import com.linyou.lifeservice.utils.PreferenceUtil;
import com.linyou.lifeservice.utils.PublicUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Web请求基类
 * 
 * @author zeroangus
 * 
 */
public class BaseWeb<T> {
	//protected static String BaseUrl = "http://211.149.169.221:8080" + "/umijoy";
	//无线服务器
	//protected static String BaseUrl = "http://192.168.10.122:8080" + "/umijoy";
	//有线服务器
	//protected static String BaseUrl = "http://210.56.209.74:9080" + "/umijoy";
	//真正的服务器
	protected static String BaseUrl = "http://211.149.169.221:8080" + "/umijoyappsvr";
	private static String TAG = "BaseWeb";
	private HttpUtils mHttpUtil = null;//网络请求的实例
	private Context mContext;

	public static PreferenceUtil mPrefUtil; //用于配制信息的业务类 继成了各种方法
	protected ServiceApplication serviceApplication;

	private static String JSESSIONID = ServiceApplication.getInstance()
			.readSession();//作用是换页面的情况下，不要重新登录

	public BaseWeb(Context context) {
		this.mContext = context;
		mPrefUtil = new PreferenceUtil(context, Constant.prefName, Context.MODE_PRIVATE);
		serviceApplication=ServiceApplication.getInstance();
	}

	/**
	 * 获取Http实例的方法
	 * 
	 * @return
	 */
	private HttpUtils getHttpUtil() {
		if (mHttpUtil == null) {
			mHttpUtil = new HttpUtils();
			//mHttpUtil.configCurrentHttpCacheExpiry(1000 * 10); //设置当前请求的缓存时间
			mHttpUtil.configCurrentHttpCacheExpiry(600000 * 10); //设置当前请求的缓存时间
		}
		return mHttpUtil;
	}

	/**
	 * 解析json串   T是外部类定义的泛型
	 * 
	 * @param json
	 * @param type
	 * @param listener
	 */
	protected void parse(String json, Type type, DataListener<T> listener) {

		//System.out.println("从服务端返回的原始数据字符串  ----------> " + json);    //正式版关闭
		MyLog.ShowLog(json);
		LogTxt.writeLog(json,"邻优网网络返回的数据");
		//PromptManager.showDialogTest1(mContext, "从服务端返回的原始数据字符串 ----------> " + json);

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);//把字符串转化成一个json对象
			if ("true".equals(jsonObject.getString("success"))) {
				// jsonObject = new JSONObject(jsonObject.getString("data"));
				Gson gson = new Gson();

			/*	System.out
						.println("从字符串解析的data字段对应的数据 ----------> "
								+ jsonObject.getString("data"));    正式版关闭 */
				//PromptManager.showDialogTest1(mContext, "从字符串解析的data字段对应的数据  ----------> "
				//		+ jsonObject.getString("data"));

				T obj = gson.fromJson(jsonObject.getString("data"), type);
				listener.onSuccess(obj);
				//listener.onFailed(obj);
			} else {
				switch (serviceApplication.mPrefUtil.getIntSetting("tiShi")){
//					弹出的提示是公共的
					case 0:
						PublicUtil.ShowToast("访问网络失败，请重新访问");
						serviceApplication.mPrefUtil.putSetting("tiShi",10);
						break;
					//					不弹出的提示是公共的
					case 1:
						serviceApplication.mPrefUtil.putSetting("tiShi",10);
						break;
					//					弹出的服务端的提示
					default:
						PublicUtil.ShowToast("" + jsonObject.getString("msg"));
						break;
				}
				listener.onFailed();
			}
		} catch (Exception e) {
			listener.onFailed();
			PublicUtil.ShowToast("" + e.toString());
			Log.e(TAG, "json parse failed : " + e.toString());
			PromptManager.showDialogTest1(mContext, "解析失败，弹出解析失败的原因 : " + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 解析json串
	 * 
	 * @param json
	 * @param listener
	 */
	protected void parse(String json, DataListener<String> listener) {

		System.out.println("parse ----------> " + json);    //正式版关闭
		LogTxt.writeLog(json,"邻优网网络返回的数据2");
		//PromptManager.showToastTest1(mContext, "从服务端返回的原始数据字符串 ----------> " + json);

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			if ("true".equals(jsonObject.getString("success"))) {
				listener.onSuccess();
				listener.onSuccess("" + jsonObject.getString("msg"));
			} else {
				PublicUtil.ShowToast("" + jsonObject.getString("msg"));
				listener.onFailed();
			}
		} catch (Exception e) {
			listener.onFailed();
			Log.e(TAG, "json parse failed : " + e.toString());
			PromptManager.showDialogTest1(mContext, "json parse failed : " + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 请求  IRequestListener是被类定义的一个内部回调接口
	 * 
	 * @param url
	 * @param params
	 */
	protected void post(String url, RequestParams params,
			final IRequestListener listener) {
		LogTxt.writeLog(url,"邻优网访问");
		// HttpUtils mHttpUtil = getHttpUtil();
		// mHttpUtil.configCurrentHttpCacheExpiry(1000 * 10);
		// mHttpUtil.configTimeout(5000);
		if (null != JSESSIONID) {    //如果cookie不为空，带上cookie 服务器端叫session
			System.out.println("JSESSIONID ----> " + JSESSIONID);
			//运用构造方法  创建一个实例  cookie和settion是一个键值对
			BasicClientCookie c = new BasicClientCookie(Constant.session_pref,
					JSESSIONID);
			c.setVersion(0);
			c.setPath("/");
			//c.toString() 相当于省份识别码
			c.setDomain(Constant.COOKIE_DOMAIN);
			               	System.out.println("JSESSIONID ----> " + c.toString());
			//获得访问网络的http对象
			DefaultHttpClient dh = (DefaultHttpClient) getHttpUtil()
					.getHttpClient();
			CookieStore cookieStore = dh.getCookieStore();
			cookieStore.addCookie(c);
			getHttpUtil().configCookieStore(cookieStore);
		}
		Log.e("tanyinqing post", url);
		//PromptManager.showDialogTest1(mContext,url+"   "+params.toString());
		//RequestCallBack<String>()是工具本身自带的内置回调方法
		showProgressDialog();
		getHttpUtil().send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Log.e("post", arg0.toString() + "  " +arg1);
						/*PublicUtil.ShowToast("网络请求出错，请稍后再试!");*/
						if (serviceApplication.mPrefUtil.getIntSetting("PhoneCode")==1){
							serviceApplication.mPrefUtil.putSetting("PhoneCode",2);
						}else {
							PublicUtil.ShowToast("网络不畅，请刷新下哦");
						}
                        closeProgressDialog();
                        listener.onFailed();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						closeProgressDialog();
						DefaultHttpClient dh = (DefaultHttpClient) getHttpUtil()
								.getHttpClient();
						CookieStore cs = dh.getCookieStore();
						//返回的数据是一个Cookie的集合
						List<Cookie> cookies = cs.getCookies();
						for (int i = 0; i < cookies.size(); i++) {
							//从返回的Cookie集合里找到一个名字为"JSESSIONID"的保存到本地
							if ("JSESSIONID".equals(cookies.get(i).getName())) {
								JSESSIONID = cookies.get(i).getValue();
								ServiceApplication.getInstance()
										.saveSession(JSESSIONID);
								break;
							}
						}
						Log.d("jack", "比较sessionid  " + JSESSIONID);
						//PromptManager.showDialogTest1(mContext,responseInfo.result.toString());
						listener.onSuccess(responseInfo.result);
					}
				});
	}

	private ProgressDialog pd;

	protected void closeProgressDialog() {
		if (pd != null) {
			try {
				pd.dismiss();
			} catch (Exception e) {

			}
		}
	}

	protected void showProgressDialog() {
		if (pd != null) {
			pd.dismiss();
		}
		pd = ProgressDialog.show(mContext, "", "数据加载中...", true, false);
		pd.show();

	}

	protected void showProgressDialog(String title, String content) {
		if (pd != null) {
			pd.dismiss();
		}
		pd = ProgressDialog.show(mContext, title, content, true, false);
		pd.show();
	}

	//定义网络访问的回调方法
	protected interface IRequestListener {
		public void onSuccess(String json);
        public void onFailed();
	}
	//从assets文件夹中获得模拟的返回数据
	protected String getAssetsData(String name) {
		String result = "";
		try {
			//获取输入流
			InputStream mAssets = mContext.getAssets().open(name);

			//获取文件的字节数
			int lenght = mAssets.available();
			//创建byte数组
			byte[] buffer = new byte[lenght];
			//将文件中的数据写入到字节数组中
			mAssets.read(buffer);
			mAssets.close();
			result = new String(buffer);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("fuck", "error");
			LogTxt.writeLog(e.toString(), "getAssetsData  发生异常");
			return result;
		}
	}

}
