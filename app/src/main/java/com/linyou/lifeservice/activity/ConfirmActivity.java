package com.linyou.lifeservice.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Code;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ceshi.PromptManager;
import com.linyou.lifeservice.customdialog.PopSexDialog;
import com.linyou.lifeservice.customview.MyListView;
import com.linyou.lifeservice.entity.DeliveryAddress;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.model.ConfirmModel;
import com.linyou.lifeservice.utils.LogTxt;
import com.linyou.lifeservice.utils.MyDialog;
import com.linyou.lifeservice.utils.MyLog;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.ShowUtil;
import com.linyou.lifeservice.wheel.WheelPayType;
import com.linyou.lifeservice.wheel.WheelSex;
import com.linyou.lifeservice.zhiFuBao.PayResult;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

/**
 * 结算
 *
 * @author 志强
 */
//这个是付款的具体操作  就是点击提交按钮后的行为 void buttonSubmitClick(View v)
//这个是弹出提示选用哪种付款方式 showTypeChoose
//buttonSubmit  提交按钮
    /*支付的具体思路
    mHandler  处理支付是否成功的回调结果
    执行微信请求密钥付款的操作  mConfirmModel.weiXin_Pay
    mHandler  去微信官方拿到密钥在去本地字符宝支付
    */
@ContentView(R.layout.activity_confirm)
public class ConfirmActivity extends TitleBarActivity {

    private static ConfirmModel mConfirmModel;
    private DeliveryAddress mDeliveryAddress;
    // 支付方式的列表
    private ArrayList<String> typeList;

    private static final int SDK_PAY_FLAG = 1;
    private static final int WeiXin_PAY_FLAG = 2;

    private static String status = "11";
    private  String data1 = "";
    private  static byte[] buf1;


    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(ConfirmActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                        finish();
                        Intent mIntent=new Intent(ConfirmActivity.this,MainActivity.class);
                        startActivity(mIntent);

                    } else {
                        Toast.makeText(ConfirmActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        mConfirmModel.shiBaiCaoZuo(ConfirmActivity.this,mHandler);
                        /*finish();
                        Intent mIntent=new Intent(ConfirmActivity.this,MainActivity.class);
                        startActivity(mIntent);*/
                    }
                    break;
                case WeiXin_PAY_FLAG:
                    finish();
                    buf1=(byte[]) msg.obj;
                    mConfirmModel.pay_money((byte[]) msg.obj);//用支付密码进入微信客户端付款
                    break;
                case 3:
                   //加上这一句，表示付款付1分钱
                    payV2(data1);
                    break;
                case 4:
                    LogTxt.writeLog("再次付款","再次付款");
                    mConfirmModel.pay_money(buf1);//用支付密码进入微信客户端付款
                    break;
                default:
                    break;
            }
        }
    };
    @ViewInject(R.id.editRemark)
    private EditText editRemark;

    @ViewInject(R.id.textCount)
    private TextView textCount;

    @ViewInject(R.id.listGoods)
    private MyListView listGoods;

    @ViewInject(R.id.editName)
    private EditText editName;

    @ViewInject(R.id.textGoodsCount)
    private TextView textGoodsCount;

    @ViewInject(R.id.textCost)
    private TextView textCost;

    @ViewInject(R.id.linearPayType)
    private LinearLayout linearPayType;

    @ViewInject(R.id.editPhone)
    private EditText editPhone;

    @ViewInject(R.id.editDiscoutCode)
    private EditText editDiscoutCode;

    @ViewInject(R.id.textAddr)
    private TextView textAddr;

    @ViewInject(R.id.linearAddr)
    private LinearLayout linearAddr;

    @ViewInject(R.id.textPayType)
    private TextView textPayType;

    @ViewInject(R.id.buttonSubmit)
    private Button buttonSubmit;

    private String action_from;

    @OnClick(R.id.linearAddr)
    void linearAddrClick(View v) {
        Intent i = new Intent();
        i.setAction(Constant.addr_sel);
        i.setClass(this, AddrManageActivity.class);
        startActivityForResult(i, Code.requestCode);
    }

    private String payType;   //这个是付款方式 一共有两种付款方式 默认为1
    private PopSexDialog dialog;

    @OnClick(R.id.linearPayType)
    void payTypeClick(View v) {
        PublicUtil.closeKeyBoard(this);
        final WheelPayType wheelData = new WheelPayType(this);
        dialog = new PopSexDialog(this, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                textPayType.setText("" + wheelData.getPayTypeName());
                payType = wheelData.getPayType();
            }
        });
        dialog.setTitle("支付方式");
        dialog.setView(wheelData.getView());
        dialog.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    //这个是付款的具体操作  就是点击提交按钮后的行为
    @OnClick(R.id.buttonSubmit)
    void buttonSubmitClick(View v) {
      /*  if (null == mDeliveryAddress) {
            PublicUtil.ShowToast("请选择地址");
            return;
        }
        System.out.println("mDeliveryAddress.getId() ----> " + mDeliveryAddress.getId());
//		mConfirmModel.confirm(mDeliveryAddress.getId(), textPayType.getText().toString(), editRemark.getText().toString(), editDiscoutCode.getText().toString());
        // mConfirmModel.confirm(action_from,mDeliveryAddress.getId(),payType, editRemark.getText().toString(), editDiscoutCode.getText().toString());
        //http://210.56.209.74:9080/umijoy/order/createOrder.do?goodsIds=5N6C4N10&user.id=10&deliveryAddress%20.id=2&payMethod.id=1&remark=gfdgdfgdfg&discountCode=fghhgfhgfh
        mConfirmModel.confirm(action_from, mDeliveryAddress.getId(), payType, editRemark.getText().toString(), editDiscoutCode.getText().toString(), new DataListener<String>() {
            @Override
            public void onSuccess(String data) {
                //Toast.makeText(ConfirmActivity.this, "返回的订单号是" + data, Toast.LENGTH_SHORT).show();
                showTypeChoose(ConfirmActivity.this, typeList, "请选择支付方式", data);
            }
        });*/

        showTypeChoose(ConfirmActivity.this, typeList, "请选择支付方式");
    }

    @Override
    void setListeners() {
        editRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textCount.setText("" + (100 - editRemark.getText().length()) + "字");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    void initDatas() {
        action_from = getIntent().getAction();
        PromptManager.showDialogTest1(this, action_from);
        setButtonLeft("返回");
       // setButtonRight("测试付款");
        setTitle(R.drawable.title_calc);
        mConfirmModel = new ConfirmModel(context);
//		listGoods = (MyListView) findViewById(R.id.listGoods);
        listGoods.setAdapter(mConfirmModel.getConfirmGoodsAdapter());
        payType = "12";
//		mConfirmModel.requestGoodsList();
        textCost.setText("共计:￥" + OrderUtil.getInstance().getTotalPrice());
        textGoodsCount.setText("商品清单:（" + OrderUtil.getInstance().getCount() + "）");
        mDeliveryAddress = (DeliveryAddress) application.readAddress();
        if (null != mDeliveryAddress) {

            editName.setText("" + mDeliveryAddress.getReciever());
            editPhone.setText("" + mDeliveryAddress.getRecieverPhone());
            textAddr.setText("" + mDeliveryAddress.getAddress());
        }
        typeList = new ArrayList<String>();
        typeList.add("支付宝支付");
        typeList.add("微信支付");
        typeList.add("货到付款");
    }

    @Override
    void RightButtonClicked() {
        //showTypeChoose(ConfirmActivity.this, typeList, "请选择支付方式", "1628896520");
        // TODO Auto-generated method stub
        //showTypeChoose(ConfirmActivity.this,typeList,"请选择支付方式");
        /*mConfirmModel.goToPay(new DataListener<String >(){
			@Override
			public void onSuccess(String data) {
				Log.d("这是密钥",data);
			}
		});*/
		/*mConfirmModel.goToPay("1628896520",new DataListener<String >(){
			@Override
			public void onSuccess(String data) {
				Log.d("tanyinqing web", "正式数据------------------------------------>这是密钥" +
						data);
			   //正式版关闭
				//Log.d("这是订单号",orderSn);
				payV2(data);

			}
		});*/
        //payV2("df");
    }

    @Override
    void LeftButtonClicked() {
        finish();

    }

    //这是返回的Activity中接受到的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Code.requestCode == requestCode && Code.addrSelResult == resultCode) {
            mDeliveryAddress = (DeliveryAddress) data.getBundleExtra(Constant.INTENT_ADDR).get(Constant.INTENT_ADDR);
            mConfirmModel.setDeliveryAddress(mDeliveryAddress);
            editName.setText("" + mDeliveryAddress.getReciever());
            editPhone.setText("" + mDeliveryAddress.getRecieverPhone());
            textAddr.setText("" + mDeliveryAddress.getAddress());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //这个是弹出提示选用哪种付款方式 showTypeChoose
    public void showTypeChoose(final Context context, ArrayList<String> typeList, String title) {
        final String[] stringArray = (String[]) typeList.toArray(new String[typeList.size()]);
        final MyDialog myDialog = new MyDialog(context);
        myDialog.setTitle(title);
        myDialog.setSingleChoiceItems(stringArray, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0://这个是支付宝支付
                        myDialog.dismiss();
                        if (null == mDeliveryAddress) {
                            PublicUtil.ShowToast("请选择地址");
                            return;
                        }
                        payType="3";  //支付方式 支付宝
                        status="11";  //支付方式 支付宝
                       //去支付获取订单号
                        mConfirmModel.confirm(status,action_from, mDeliveryAddress.getId(), payType, editRemark.getText().toString(), editDiscoutCode.getText().toString(), new DataListener<String>() {
                            @Override
                            public void onSuccess(String data) {
//去支付宝拿加密数据  data是订单号  所以要想变更付款金额 就要在生成订单时变更
                                mConfirmModel.goToPay(data, new DataListener<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        data1=data;
                                        payV2(data); //去支付宝付款

                                    }
                                });

                            }
                        });
                       /* finish();
                        Intent mIntent=new Intent(ConfirmActivity.this,MainActivity.class);
                        startActivity(mIntent);*/
                        break;
                    case 1://这个是微信的支付
                       /* myDialog.dismiss();
                        mConfirmModel.weiXin_Pay(mHandler, orderSn);
                        finish();*/
                        myDialog.dismiss();
                        if (null == mDeliveryAddress) {
                            PublicUtil.ShowToast("请选择地址");
                            return;
                        }
                        payType="4";  //支付方式 支付宝
                        status="11";  //支付方式 支付宝
                        mConfirmModel.confirm(status,action_from, mDeliveryAddress.getId(), payType, editRemark.getText().toString(), editDiscoutCode.getText().toString(), new DataListener<String>() {
                            @Override
                            public void onSuccess(String data) {
                                        finish();//data  是订单号
                                        mConfirmModel.weiXin_Pay(mHandler, data);//去微信拿支付密码
                            }
                        });

                        break;
                    case 2://  这个是货到付款
                        /*myDialog.dismiss();
                        finish();
                        Intent mIntent2=new Intent(ConfirmActivity.this,MainActivity.class);
                        startActivity(mIntent2);*/
                        myDialog.dismiss();
                        if (null == mDeliveryAddress) {
                            PublicUtil.ShowToast("请选择地址");
                            return;
                        }
                        payType="1";  //支付方式 支付宝
                        status="10";  //支付方式 支付宝
                        mConfirmModel.confirm(status,action_from, mDeliveryAddress.getId(), payType, editRemark.getText().toString(), editDiscoutCode.getText().toString(), new DataListener<String>() {
                            @Override
                            public void onSuccess(String data) {
                                mConfirmModel.goToPay(data, new DataListener<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        // payV2(data);
                                        finish();
                                        Intent mIntent2 = new Intent(ConfirmActivity.this, MainActivity.class);
                                        startActivity(mIntent2);

                                    }
                                });
                            }
                        });
                        break;

                    default:
                        break;
                }

            }
        });
        myDialog.create(null).show();
    }

    //付款
    public void payV2(final String data) {
        if (TextUtils.isDigitsOnly(data)) {
            Toast.makeText(context, "请重新支付", Toast.LENGTH_SHORT).show();
            return;
        }
       final String orderInfo = data;//这是订单号 按照订单号去付款
        //final String orderInfo="timestamp=2016-07-29+16%3A55%3A53&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22%E7%BA%A2%E7%89%9B+%E7%BB%B4%E7%94%9F%E7%B4%A0%E5%8A%9F%E8%83%BD%E9%A5%AE%E6%96%99+250ml%2F%E5%90%AC%22%2C%22out_trade_no%22%3A%221628815112%22%7D&sign_type=RSA&notify_url=http%3A%2F%2F192.168.1.9%3A8080%2Fumijoyappsvr%2FPayNotify.do&charset=utf-8&method=alipay.trade.app.pay&app_id=2016082301791132&version=1.0&sign=C8VYfnKzTLHNQwkkI6mc8L4VNkfSNI5wvPJN%2FBBQBdFpnfEheCtUMVOpoRl6RA6NYaco1IesbuNoYVrEdhd8v5z8%2BlZJu3VuUXHgopo9TtmFk5pcJLLR8GpCUtIC931Vf%2FeCYn083FSjjrMkYYpLLrXkq754n2ounjHiw1y0Nq8%3D";
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask aplipay = new PayTask(ConfirmActivity.this);
                Map<String, String> result = aplipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);

            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
