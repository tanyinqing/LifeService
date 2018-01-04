package com.linyou.lifeservice.model;

import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.RegexUtil;
import com.linyou.lifeservice.web.CommonWeb;
import com.linyou.lifeservice.web.UserWeb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;

public class FindPwdModel extends BaseModel {

	public static final int GET_CODE_SUCCESS = 0;
	public static final int GET_CODE_FAILED = 1;

	private UserWeb mUserWeb;
    private CommonWeb mCommonWeb;
	private Handler mHandler;
	private Context context;
	
//	public String code;
	
	public FindPwdModel(Context mContext,Handler mHandler) {
		super(mContext);
		this.context=mContext;
		mUserWeb = new UserWeb(mContext);
        mCommonWeb = new CommonWeb(mContext);
		this.mHandler = mHandler;
	}

	
	public void findPwd(String phoneNum,String phoneCode)
	{
		
		if(TextUtils.isEmpty(phoneCode))
		{
			PublicUtil.ShowToast("请输入验证码！");
			return;
		}

        mCommonWeb.forgetPassword(phoneNum, phoneCode, new DataListener<String>(){

			@Override
			public void onSuccess(String data) {
//				PublicUtil.ShowToast("重置密码成功，请重新登陆！");
//				PublicUtil.ShowToast("随机密码："+data);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("随机密码："+data);  builder.setTitle("随机密码");  builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        closeActivity();
                    }
                });
                builder.create().show();
			}
			
		});
	}
	
	
	/**
	 * 请求发送验证码
	 * @param phone
	 */
	public void getCode(String phone)
	{
		if(!RegexUtil.isPhone(phone))
		{
			PublicUtil.ShowToast("请输入正确的手机号！");
			return;
		}
		mUserWeb.getCode(phone, new DataListener<String>()
		{
			@Override
			public void onSuccess(String data) {
//				code = data;
				mHandler.sendEmptyMessage(GET_CODE_SUCCESS);
			}

			@Override
			public void onFailed() {
				mHandler.sendEmptyMessage(GET_CODE_FAILED);
			}
			
		});
	}
	
}
