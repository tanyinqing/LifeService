package com.linyou.lifeservice.model;

import java.util.ArrayList;
import java.util.List;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.activity.MainActivity;
import com.linyou.lifeservice.adapter.ConfirmGoodsAdapter;
import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.MyDialog;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.Util;
import com.linyou.lifeservice.web.OrderWeb;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmModel extends BaseModel {

    private ConfirmGoodsAdapter mConfirmGoodsAdapter;
    private OrderWeb mOrderWeb;
    private OrderUtil mOrderUtil;
    private DeliveryAddress deliveryAddress;
    private Context mContext;

    private IWXAPI api;
    private byte[] buf;

    public ConfirmModel(Context mContext) {
        super(mContext);
        this.mContext=mContext;
        api =api = WXAPIFactory.createWXAPI(mContext,Constant.APP_ID);
        mConfirmGoodsAdapter = new ConfirmGoodsAdapter(mContext);
        mOrderWeb = new OrderWeb(mContext);
        mOrderUtil = OrderUtil.getInstance();
        mConfirmGoodsAdapter.appendToListAndClear(mOrderUtil.getGoodsList());
    }


    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public ConfirmGoodsAdapter getConfirmGoodsAdapter() {
        return mConfirmGoodsAdapter;
    }

    public void confirm(String status,final String action_from, String addressId, String payMethodId, String remark, String discountCode, final DataListener dataListener) {
        if (!isLogin()) {
            PublicUtil.ShowToast("请登录");
            return;
        }
        String ids = getGoodsIds();
        System.out.println("ids ------> " + ids);
        mOrderWeb.createOrder(status,ids, getUser().getId(), addressId, payMethodId, remark, discountCode, new DataListener<String>() {

            @Override
            public void onSuccess(String data) {
                serviceApplication.saveAddress(deliveryAddress);
                PublicUtil.ShowToast("购买成功");
                dataListener.onSuccess(data);
                Log.d("tanyinqing", data);
                if (null != action_from && Constant.ACTION_FORM_ORDER.equals(action_from)) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                } else {
                    // 这个是通常采用的操作
                    OrderUtil.getInstance().clear();
                    //closeActivity();
                }
            }

        });
    }

    private String getGoodsIds() {
        List<Goods> list = mOrderUtil.getGoodsList();
        String s = "";
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Goods goods = list.get(i);
            s += goods.getId() + "N" + goods.getCount();
            if (i != size - 1) {
                s += "C";
            }
        }
        return s;
    }

    public void goToPay(String orderSn, final DataListener<String> listener) {
        mOrderWeb.goToPay(orderSn, new DataListener<String>() {
            @Override
            public void onSuccess(String data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }
        });
    }

    // 执行微信请求密钥付款的操作  mConfirmModel.weiXin_Pay
    public void weiXin_Pay(final Handler mHandler,String orderSn) {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    if (isPaySupported){
        final String url = "http://211.149.169.221:8080/umijoyappsvr/weixin/pay/GoWeiXinPay.do?orderSn="+orderSn;
      //  Toast.makeText(mContext, "获取订单中...", Toast.LENGTH_SHORT).show();

            final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                buf = Util.httpGet(url);

                Message msg = new Message();
                msg.what =2;
                msg.obj = buf;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }else {
        Toast.makeText(mContext,"您的微信版本不支持微信支付 请升级之后在试", Toast.LENGTH_SHORT).show();
    }
    }
//微信执行付款的操作
    public void pay_money(byte[] buf) {
        try{
        if (buf != null && buf.length > 0) {
            String content = new String(buf);
            // 这里会打印Log的信息
            Log.e("get server pay params:",content);
            JSONObject json = null;

            json = new JSONObject(content);
            String data=json.getString("data");
            JSONObject json1 = new JSONObject(data);
            if(null != json && !json.has("retcode") ){
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
				req.appId			= json1.getString("appid");
				req.partnerId		= json1.getString("partnerid");
               /* req.appId			= "wx405f674449bed855";
                req.partnerId		= "1385752402";*/
                req.prepayId		= json1.getString("prepayid");
                req.nonceStr		= json1.getString("noncestr");
                req.timeStamp		= json1.getString("timestamp");
                req.packageValue	= json1.getString("package");
                req.sign			= json1.getString("sign");
                req.extData			= "app data"; // optional
                api.sendReq(req);

               /* {
                    "data": {
                    "appid": "wx405f674449bed855",
                            "noncestr": "qSGyaFkmsJVhSfwJ",
                            "package": "Sign=WXPay",
                            "partnerid": "1401521002",
                            "prepayid": "wx20161202091918db07078ddf0488933530",
                            "sign": "FADC35FD2004DB1672991832B2B07DFF",
                            "timestamp": 1480641558
                },
                    "msg": "调起成功！",
                        "success": true
                }*/


               // Toast.makeText(mContext, "正常调起支付", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                //api.sendReq(req);
            }else{
                Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
                Toast.makeText(mContext, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.d("PAY_GET", "服务器请求错误");
            Toast.makeText(mContext, "服务器请求错误", Toast.LENGTH_SHORT).show();
        }
    }catch(Exception e){
        Log.e("PAY_GET", "异常："+e.getMessage());
        Toast.makeText(mContext, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }

    public void shiBaiCaoZuo(final Activity mActivity,final Handler mHandler) {
        MyDialog dialog = new MyDialog(mContext)
                .setTitle("提示")
                .setMessage("支付失败!")
                .setPositiveButton(R.string.Dialog_title1,
                        new View.OnClickListener() {

                            @SuppressLint("WorldWriteableFiles")
                            @Override
                            public void onClick(View arg0) {
                                mHandler.sendEmptyMessage(3);
                              /*  if(null != mUser)
                                {
                                    mUserWeb.deleteGoodsFromShoppingCart(goods.getId(), mUser.getId(), new DataListener(){
                                        @Override
                                        public void onSuccess() {
                                            mOrderUtil.remove(goods);
                                            mShopCarAdapter.removeItem(goods);
                                            PublicUtil.ShowToast("移除成功");
                                            mHandler.sendEmptyMessage(1);
                                        }

                                    });

                                }*/
                            }
                        })
                .setNegativeButton(R.string.cancel1,
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
//MyLog.ShowLog("你选择了取消");
                                mActivity.finish();
                                Intent mIntent = new Intent(mContext, MainActivity.class);
                                mContext.startActivity(mIntent);
                            }
                        });

        dialog.create(null);
        dialog.showMyDialog();
    }
}
