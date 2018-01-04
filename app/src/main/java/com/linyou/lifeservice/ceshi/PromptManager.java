package com.linyou.lifeservice.ceshi;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.utils.PreferenceUtil;


/**
 * 提示信息的管理
 */

//全局测试的引用
//PromptManager.showToastTest(this,"");
//PromptManager.showDialogTest(this,"");
public class PromptManager {

	// 当测试阶段时true      测试是使用比较多的这个Toast    当正式投入市场时这个设置为false
	private static  boolean isShow1 =false;
	public static  boolean isShow2 =false;
	public static  boolean isShow3 =false;
	public static  boolean isShow4 =false;
	private static PreferenceUtil mPrefUtil;



	/**
	 * 测试用 在正式投入市场：删
	 *
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow1) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

	public static void showDialogTest(Context context, String msg) {
		if (isShow2) {
			new Builder(context).setTitle("测试数据").setMessage(msg).show();
		}
	}

	public static void showToastTest1(Context context, String msg) {
		mPrefUtil = new PreferenceUtil(context, Constant.prefName, Context.MODE_PRIVATE);
		if (mPrefUtil.getBoolean("isShow3")) {
			Toast.makeText(context, "测试数据    " + msg, Toast.LENGTH_LONG).show();
		}
	}

	public static void showDialogTest1(Context context, String msg) {
		mPrefUtil = new PreferenceUtil(context, Constant.prefName, Context.MODE_PRIVATE);
		if (mPrefUtil.getBoolean("isShow4")) {
			new Builder(context).setTitle("测试数据").setMessage(msg)
					.show();
		}
	}

	public static void dialog3(Context context){
		mPrefUtil = new PreferenceUtil(context, Constant.prefName, Context.MODE_PRIVATE);
		final String items[]={"Toast开","Dialog开","两个全开","两个全关"};
		Builder builder=new Builder(context);  //先得到构造器
		builder.setTitle("测试设置的开关，开发专用"); //设置标题
		builder.setIcon(R.drawable.ic_launcher);//设置图标，图片id即可
		builder.setSingleChoiceItems(items,0,new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case 0:
						dialog.dismiss();
						//Toast.makeText(MainActivity.this, "Toast开", Toast.LENGTH_SHORT).show();
						mPrefUtil.putSetting("isShow3", true);
						mPrefUtil.putSetting("isShow4", false);
						break;
					case 1:
						dialog.dismiss();
						//Toast.makeText(MainActivity.this, "Dialog开", Toast.LENGTH_SHORT).show();
						mPrefUtil.putSetting("isShow3", false);
						mPrefUtil.putSetting("isShow4", true);
						break;
					case 2:
						dialog.dismiss();
						//Toast.makeText(MainActivity.this, "两个全开", Toast.LENGTH_SHORT).show();
						mPrefUtil.putSetting("isShow3", true);
						mPrefUtil.putSetting("isShow4", true);
						break;
					case 3:
						dialog.dismiss();
						//Toast.makeText(MainActivity.this, "两个全开", Toast.LENGTH_SHORT).show();
						mPrefUtil.putSetting("isShow3", false);
						mPrefUtil.putSetting("isShow4", false);
						break;

					default:
						break;
				}

			}
		});

		builder.create().show();
	}

	/*
	 //按钮加入 3次点击的监听
	        editText1.setOnClickListener(new
			View.OnClickListener()
	               {
	                  //需要监听几次点击事件数组的长度就为几
	                   //如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
	                   long[] mHints = new long[3];//初始全部为0
	                   @Override
	                   public void onClick(View v)
	                   {
	                       //将mHints数组内的所有元素左移一个位置
	                       System.arraycopy(mHints, 1, mHints, 0,
						   mHints.length - 1);
	                      //获得当前系统已经启动的时间
	                       mHints[mHints.length - 1] =
						   SystemClock.uptimeMillis();
	                       if
						   (SystemClock.uptimeMillis()-mHints[0]<=500){
	                    	   PromptManager.dialog3(LoginPage.this);
	                       }
	                   }
	               });
	 */

	/**
	 * 开启和关闭进度条的代码
	 *
	 * @param context
	 */
	private static ProgressDialog dialog;

	public static void showProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle(R.string.app_name);

		dialog.setMessage("请等候，数据加载中……");
		dialog.show();
	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("当前无网络").setPositiveButton("设置", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳转到系统的网络设置界面
						Intent intent = new Intent();
						intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
						context.startActivity(intent);

					}
				}).setNegativeButton("知道了", null).show();
	}

	/**
	 * 退出系统
	 * 
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("是否退出应用").setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
				// 多个Activity——懒人听书：没有彻底退出应用
				// 将所有用到的Activity都存起来，获取全部，干掉
				// BaseActivity——onCreated——放到容器中
			}
		})//
				.setNegativeButton("取消", null)//
				.show();

	}

	/**
	 * 显示错误提示框
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new Builder(context)//
				.setIcon(R.drawable.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton(context.getString(R.string.is_positive), null)//
				.show();
	}
	/**
	 * 用到的各种土司
	 *
	 * @param context
	 * @param msg
	 */
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
	}



}
