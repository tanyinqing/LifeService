package com.linyou.lifeservice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.TouXiang;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.impl.IUserWeb;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.web.BaseWeb.IRequestListener;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 用户相关请求
 * 
 * @author 志强
 * 
 */
public class UserWeb extends BaseWeb implements IUserWeb{

	private static String UserUrl = BaseUrl + "/user/";
	/**
	 * 验证码
	 */
	private static String CodeUrl = UserUrl + "obtainPhoneCode.do";
	/**
	 * 登陆
	 */
	private static String LoginUrl = UserUrl + "login.do";
	/**
	 * 注册
	 */
	private static String RegisterUrl = UserUrl + "register.do";
	/**
	 * 收货地址列表
	 */
	private static String FindAddressUrl = UserUrl + "findAddressByUser.do";
	/**
	 * 收货地址详情
	 */
	private static String AddressDetailUrl = UserUrl + "findAddressById.do";
	/**
	 * 添加收货地址
	 */
	private static String AddAddressUrl = UserUrl + "addAddress.do";
	/**
	 * 修改收货地址
	 */
	private static String ModificationAddressUrl = UserUrl + "modifyAddress.do";
	/**
	 * 删除收货地址
	 */
	private static String DeleteAddressUrl = UserUrl + "deleteAddress.do";
	/**
	 * 意见反馈
	 */
	private static String AdviceAddressUrl = UserUrl + "addAdvise.do";
	/**
	 * 上传头像
	 */
	private static String UploadHeadUrl = UserUrl + "uploadHeadPortrait.do";
	/**
	 * 查看用户头像
	 */
	private static String ViewHeadPortraitUrl = UserUrl + "viewHeadPortraitUrl.do";
	/**
	 * 用户信息
	 */
	private static String UserInfoUrl = UserUrl + "findUserInfoById.do";
	/**
	 * 修改用户信息
	 */
	private static String ModifyUserInfoUrl = UserUrl +"modifyUserInfo.do";
	/**
	 * 修改密码
	 */
	private static String ModifyPasswordUrl = UserUrl +"modifyPassword.do";

	/**
	 *  收藏
	 */
	private static String CollectUrl = UserUrl + "collectGoods.do";

	/**
	 *  删除收藏
	 */
	private static String DeleteCollectUrl = UserUrl + "deleteGoodsFromCollect.do";
	/**
	 *  查看收藏产品
	 */
	private static String CollectListUrl = UserUrl + "findCollectGoodsByUserId.do";
	/**
	 *  加入购物车
	 */
	private static String AddShoppingCarUrl = UserUrl + "addGoodsToShoppingCart.do";
	/**
	 *  查看购物车
	 */
	private static String ShowShoppingCarUrl = UserUrl + "showShoppingCart.do";
	/**
	 * 从购物车删除
	 */
	private static String DeleteShoppingCarUrl = UserUrl + "deleteGoodsFromShoppingCart.do";
	

	/**
	 * 添加评论
	 */
	private static String AddCommentUrl = UserUrl + "addGoodsComment.do";

	private Context context;

	public UserWeb(Context context) {
		super(context);
		this.context=context;
	}
	
	/**
	 * 获取验证码
	 * 
	 * @param phoneNum
	 * @param dataListener
	 */
	public void getCode(String phoneNum,final DataListener<String> dataListener)
	{
		serviceApplication.mPrefUtil.putSetting("PhoneCode",1);
		PromptManager.showDialogTest(context, CodeUrl+"    "+"phoneNum   "+phoneNum);
		RequestParams params = new RequestParams();
		params.addBodyParameter("phoneNum", phoneNum);
		post(CodeUrl, params, new IRequestListener() {
			public void onSuccess(String json) {
				Type type = new TypeToken<String>(){}.getType();
				parse(json,type, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}

	/**
	 * 用户登录
	 * 
	 * @param phoneNum
	 * @param password
	 * @param dataListener
	 */
	public void login(String phoneNum, String password,
			final DataListener<User> dataListener) {
		PromptManager.showDialogTest(context, LoginUrl + "    " + "phoneNum   " + phoneNum + "      " + "password   " + password);
		RequestParams params = new RequestParams();
		params.addBodyParameter("phoneNum", phoneNum);
		params.addBodyParameter("password", password);
		params.addBodyParameter("type", "配送端1");
		post(LoginUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
			//	json=getAssetsData("login.txt");
				Type type = new TypeToken<User>(){}.getType();
				parse(json,type, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}
	
	public void register(String password, String phoneNum,
			String phoneCode,
			final DataListener<User> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("password", password);
		params.addBodyParameter("phoneNum", phoneNum);
		params.addBodyParameter("phoneCode", phoneCode);
		params.addBodyParameter("type", "配送端1");

		p.L("注册是携带的参数", RegisterUrl + " " + password + "  " + phoneNum + "  " + phoneCode);
		post(RegisterUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				Type type = new TypeToken<User>() {
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
	 * 获取收货地址列表
	 * 
	 * @param dataListener
	 */
	public void findAddressByUser(String id,final DataListener<List<DeliveryAddress>> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		post(FindAddressUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				Type type = new TypeToken<List<DeliveryAddress>>() {
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
	 * 收货地址详情
	 * 
	 * @param id
	 * @param dataListener
	 */
	public void findAddressById(String id,
			final DataListener<DeliveryAddress> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		post(AddressDetailUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				Type type = new TypeToken<DeliveryAddress>() {
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
	 * 添加收货地址
	 * 
	 * @param reciever  收货人姓名
	 * @param recieverPhone
	 * @param address
	 * @param dataListener
	 */
	public void addAddress(String id,String reciever, String recieverPhone, String address,
			 final DataListener dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("user.id", id);
		params.addBodyParameter("reciever", reciever);
		params.addBodyParameter("recieverPhone", recieverPhone);
		params.addBodyParameter("address", address);
		params.addBodyParameter("zipcode", "610000");
		params.addBodyParameter("region_name", "中国  四川   成都");
		params.addBodyParameter("region_id", "358");
		PromptManager.showDialogTest(context, AddAddressUrl + "    " + "user.id   " + id + "      " + "reciever   " + reciever +
				"      " + "recieverPhone  " + recieverPhone + "      " + "address  " + address + "      ");
		post(AddAddressUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				parse(json, dataListener);
			}

			@Override
			public void onFailed() {
				dataListener.onFailed();
			}
		});
	}

	/**
	 * 保存修改后的收货地址
	 *
	 * @param reciever
	 * @param recieverPhone
	 * @param address
	 * @param dataListener
	 */
	public void modificationAddress(String id,String reciever, String recieverPhone, String address,
						   final DataListener dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("addrId", id);
		params.addBodyParameter("reciever", reciever);
		params.addBodyParameter("recieverPhone", recieverPhone);
		params.addBodyParameter("address", address);
		params.addBodyParameter("zipcode", "610000");
		params.addBodyParameter("region_name", "中国  四川   成都");
		params.addBodyParameter("region_id", "358");
		PromptManager.showDialogTest(context, ModificationAddressUrl + "    " + "user.id   " + id + "      " + "reciever   " + reciever +
				"      " + "recieverPhone  " + recieverPhone + "      " + "address  " + address + "      ");
		post(ModificationAddressUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				parse(json, dataListener);
			}

			@Override
			public void onFailed() {
				dataListener.onFailed();
			}
		});
	}

	/**
	 * 删除地址
	 * 
	 * @param id
	 * @param dataListener
	 */
	public void deleteAddress(String id, final DataListener dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		post(DeleteAddressUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				parse(json, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}
	
	/**
	 * 意见反馈
	 * @param advice
	 * @param dataListener
	 */
	public void advice(String userId,String advice, final DataListener dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userId);
		params.addBodyParameter("advice", advice);
		post(AdviceAddressUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				parse(json, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}

	/*这个是上传头像的操作*/
	@Override
	public void uploadHeadPortrait(String file, String fileFileName,
			String userId, final DataListener<TouXiang> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("file", file);
		params.addBodyParameter("fileFileName", fileFileName);
		params.addBodyParameter("userId", userId);
		p.L("上传头像的操作", fileFileName + "  " + userId + " 图片的编码文件  " + " 省略");
		p.s(file);
		//System.out.println("String file=   " + file);

		/*//写一个模拟的操作，把这个文件存储下来
		File file1=new File(Environment.getExternalStorageDirectory(),"tan.jpg");
		try {
			FileInputStream is=new FileInputStream(file);
			FileOutputStream fos=new FileOutputStream(file1);
			int count=0;
			byte[] buffer=new byte[1024];
			while ((count=is.read(buffer))!=-1){
			fos.write(buffer,0,count);
				p.L("正在复制头像",String.valueOf(count));
			}
			fos.close();
			is.close();
		}catch (Exception e){
			p.L("异常",e.toString());
		}*/


		post(UploadHeadUrl, params, new IRequestListener() {
			@Override
			public void onSuccess(String json) {
				p.L("上传头像返回的数据", json);
				Type type = new TypeToken<TouXiang>() {
				}.getType();
				//String json1="{\"data\":{\"wangZhi\":\"data/files/head/20160215105918.jpg\"},\"msg\":\"上传成功！\",\"success\":true}";
				parse(json, type, dataListener);

			}

			@Override
			public void onFailed() {
				dataListener.onFailed();
			}
		});
	}

	@Override
	public void viewHeadPortraitUrl(String userId,
			final DataListener<String> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userId);
		post(ViewHeadPortraitUrl, params, new IRequestListener() {
			
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

	@Override
	public void findUserInfoById(String id, final DataListener<User> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		post(UserInfoUrl, params, new IRequestListener() {
			
			public void onSuccess(String json) {
				Type type = new TypeToken<User>() {
				}.getType();
				parse(json, type, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}

	@Override
	public void modifyUserInfo(String id, String nickName,
			String email, String age, String sex, final DataListener<User> dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		params.addBodyParameter("nickName", nickName);
		params.addBodyParameter("email", email);
		params.addBodyParameter("age", age);
		params.addBodyParameter("sex", sex);

		PromptManager.showDialogTest(context, ModifyUserInfoUrl + "    " + "id   " + id + "      " + "nickName   " + nickName +
				"      " + "email  " + email + "      " + "age  " + age + "      " + "sex  " + sex + "      ");

		post(ModifyUserInfoUrl, params, new IRequestListener() {
			
			public void onSuccess(String json) {
				PromptManager.showDialogTest(context,json);
				Type type = new TypeToken<User>() {
				}.getType();
				parse(json, type, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}

	@Override
	public void modifyPassword(String id, String oldPassword,
			String newPassword, final DataListener dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		params.addBodyParameter("oldPassword", oldPassword);
		params.addBodyParameter("newPassword", newPassword);
		p.L("修改密码时打印的参数",ModifyPasswordUrl+"  "+id+"  "+oldPassword+"  "+newPassword);
		post(ModifyPasswordUrl, params, new IRequestListener() {

			public void onSuccess(String json) {
				parse(json, dataListener);
			}

			@Override
			public void onFailed() {
				dataListener.onFailed();
			}
		});
	}



	/**
	 * 收藏商品
	 * @param goodsId
	 * @param dataListener
	 */
	public void collectGoods(String goodsId,String userId,final DataListener dataListener)
	{
		PromptManager.showDialogTest(context, CollectUrl+"    "+"goodsId   "+goodsId+"      "+"userId   "+userId);
		Log.d("收藏商品",CollectUrl+"    "+"goodsId   "+goodsId+"      "+"userId   "+userId);
		RequestParams params = new RequestParams();
		params.addBodyParameter("goodsId", goodsId);
		params.addBodyParameter("userId", userId);
		post(CollectUrl, params, new IRequestListener() {
			
			public void onSuccess(String json) {
				parse(json, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}

	/**
	 * 删除收藏
	 */
	@Override
	public void deleteGoodsFromCollect(String goodsId, String userId,final DataListener dataListener) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("goodsId", goodsId);
		params.addBodyParameter("userId", userId);
		Log.d("查看收藏列表的关键数据", DeleteCollectUrl + "    " + "userId   " + userId+ "  goodsId   " + goodsId);
		post(DeleteCollectUrl, params, new IRequestListener() {
			
			public void onSuccess(String json) {
				parse(json, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
		
	}
	
	/**
	 * 查看收藏列表
	 * @param dataListener
	 */
	public void findCollectGoods(String userId,final DataListener<List<Goods>> dataListener)
	{
		//PromptManager.showDialogTest(context, CollectListUrl+"    "+"userId   "+userId);
		Log.d("查看收藏列表的关键数据",CollectListUrl+"    "+"userId   "+userId);
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userId);
		post(CollectListUrl, params, new IRequestListener() {
			
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
	
	/**
	 * 添加到购物车
	 * @param goodsId
	 * @param userId
	 * @param dataListener
	 */
	public void addShoppingCar(String goodsId,String userId,final DataListener dataListener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("goodsId", goodsId);
		params.addBodyParameter("userId", userId);
		post(AddShoppingCarUrl, params, new IRequestListener() {
			
			public void onSuccess(String json) {
				parse(json, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}
	
	/**
	 * 查看购物车
	 * @param dataListener
	 */
	public void showShoppingCar(String userId,final DataListener<List<Goods>> dataListener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userId);
		p.L("查看购物车的用户ID",userId);
		post(ShowShoppingCarUrl, params, new IRequestListener() {

			public void onSuccess(String json) {
				p.L("查看购物车返回的数据", json);
				Type type = new TypeToken<List<Goods>>() {
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
	 * 从购物车删除
	 * @param goodsId
	 * @param dataListener
	 */
	public void deleteGoodsFromShoppingCart(String goodsId,String userId,final DataListener dataListener)
	{
		RequestParams params = new RequestParams();
		params.addBodyParameter("goodsId", goodsId);
		params.addBodyParameter("userId", userId);
		post(DeleteShoppingCarUrl, params, new IRequestListener() {
			
			public void onSuccess(String json) {
				parse(json, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}

	@Override
	public void addGoodsComment(String goodsId, String content,String orderId,
			final DataListener dataListener) {
		p.L("评论的三个字段", " goodsId"+goodsId+"   content  "+content+"  "+"orderId"+orderId);
		RequestParams params = new RequestParams();
		params.addBodyParameter("goods.id", goodsId);
		params.addBodyParameter("content", content);
		params.addBodyParameter("orderId", orderId);
		post(AddCommentUrl, params, new IRequestListener() {
			
			public void onSuccess(String json) {
				parse(json, dataListener);
			}
            @Override
            public void onFailed() {
                dataListener.onFailed();
            }
		});
	}
}
