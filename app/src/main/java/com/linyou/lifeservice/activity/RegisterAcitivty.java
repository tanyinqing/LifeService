package com.linyou.lifeservice.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.RegisterModel;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.utils.RegexUtil;

/**
 * 注册
 * 
 * @author 志强
 * 
 */
@ContentView(R.layout.acitvity_register)
public class RegisterAcitivty extends TitleBarActivity {
	@ViewInject(R.id.editPhone)
	private EditText editPhone;

	@ViewInject(R.id.editPassword)
	private EditText editPassword;

	@ViewInject(R.id.editCode)
	private EditText editCode;

	@ViewInject(R.id.checkCode)
	private CheckBox checkCode;

	@ViewInject(R.id.checkAgree)
	private CheckBox checkAgree;

	@ViewInject(R.id.buttonRegister)
	private Button buttonRegister;

	private RegisterModel mRegisterModel;

	@Override
	void setListeners() {
	}

	@OnClick(R.id.buttonRegister)
	public void register(View view) {
		if(checkAgree.isChecked())
		{
			mRegisterModel.register(editPhone.getText().toString(), editPassword
					.getText().toString(), editCode.getText().toString());
			}
		else
		{
			PublicUtil.ShowToast("请同意用户协议");
		}
	}

	@OnClick(R.id.checkCode)
	public void getCode(View view) {
		mRegisterModel.getCode(editPhone.getText().toString());
		/*if(RegexUtil.isPhone(editPhone.getText().toString())){
			startTimer();
		}*/
	}

	@Override
	void initDatas() {

		setButtonLeft("返回");
		setTitle(R.drawable.title_register);
		if (mRegisterModel == null) {
			mRegisterModel = new RegisterModel(this,mHandler);
		}
	}
	
	
	private int time;
    private TimerTask timerTask;
    
    private void startTimer()
    {
        time = 60;
        checkCode.setChecked(true);
        checkCode.setClickable(false);
        editPhone.setEnabled(false);
        
        timerTask = new TimerTask()
        {
            
            public void run()
            {
                mHandler.sendEmptyMessage(2);
            }
        };
        
        Timer timer = new Timer(true);
        timer.schedule(timerTask, 1000, 1000);
    }
    
    private static String mStr1 = "重新发送";
    private static String mStr2 = "已发送";
    
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RegisterModel.GET_CODE_FAILED:
				startTimer();
				//PublicUtil.ShowToast("获取验证码失败，请再次获取");
				break;
			case RegisterModel.GET_CODE_SUCCESS:
                PublicUtil.ShowToast("获取验证码成功");
//				editCode.setText(""+mRegisterModel.code);
				startTimer();
				break;
			 case 2:
                 time--;
                 checkCode.setText(mStr2 + "(" + time + ")");
                 if (time < 1)
                 {
                     checkCode.setText(mStr1);
                     checkCode.setChecked(false);
                     checkCode.setClickable(true);
                     timerTask.cancel();
                 }
             break;
			default:
				break;
			}
		}

	};

	@Override
	void RightButtonClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void LeftButtonClicked() {
		finish();
	}

}
