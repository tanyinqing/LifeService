package com.linyou.lifeservice.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Switch;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.activity.ConfirmActivity;
import com.linyou.lifeservice.activity.MainActivity;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.LogTxt;
import com.linyou.lifeservice.utils.MyDialog;
import com.linyou.lifeservice.utils.PublicUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.pay_result);

    	api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			MyDialog dialog = new MyDialog(this);
			dialog.setTitle(R.string.Dialog_title)
					.setPositiveButton(R.string.Dialog_title1,
							new View.OnClickListener() {

								@SuppressLint("WorldWriteableFiles")
								@Override
								public void onClick(View arg0) {
									(new ConfirmActivity()).mHandler.sendEmptyMessage(4);
								}
							});
			dialog.setNegativeButton(R.string.cancel1,
					new View.OnClickListener() {

						@Override
						public void onClick(View arg0) {
							finish();
							Intent mIntent1 = new Intent(WXPayEntryActivity.this, MainActivity.class);
							startActivity(mIntent1);
						}
					});
			switch (resp.errCode){
				case 0:
					PublicUtil.ShowToast("微信支付成功");
					LogTxt.writeLog("微信支付成功", "微信支付成功");
					Intent mIntent1 = new Intent(WXPayEntryActivity.this, MainActivity.class);
					startActivity(mIntent1);
					break;
				case -1:
					PublicUtil.ShowToast("微信支付失败");
					LogTxt.writeLog("微信支付失败", "微信支付失败");
					dialog.setMessage(R.string.WeiXinZhiFu2);
					dialog.create(null);
					dialog.showMyDialog();
					break;
				case -2:
					PublicUtil.ShowToast("微信支付已经取消");
					LogTxt.writeLog("微信支付已经取消", "微信支付已经取消");
					dialog.setMessage(R.string.WeiXinZhiFu3);
					dialog.create(null);
					dialog.showMyDialog();
					break;

			}


			/*Intent mIntent1=new Intent(WXPayEntryActivity.this,MainActivity.class);
			startActivity(mIntent1);*/

			// 弹出提示微信支付的结果
			/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();*/
		}
	}
}