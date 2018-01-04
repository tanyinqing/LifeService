package com.linyou.lifeservice.customdialog;

import com.linyou.lifeservice.R;

import android.widget.PopupWindow;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

/**
 * "拍照"和"从相册获取"选择对话框
 * @author 志强
 *
 */
public class PopWinDialog extends PopupWindow {

	private Button btn_take_photo, btn_pick_photo, btn_cancel;//弹出的按钮
	private View mMenuView;//弹出的视图
	private Activity context;
	private OnClickListener itemClickListener;
	
	
	private String TAG = "SelPicturePopWin";
	public PopWinDialog(Activity context,OnClickListener itemClickListener) {
		super(context);
		this.context = context;
		this.itemClickListener = itemClickListener;
		findView();//设置视图
		setValues();//如何放值
		setOnclickLisener(); //事件的监听
	}
	
	private void findView(){
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popwind_dialog, null);
		btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		//设置SelPicturePopWin的View
		this.setContentView(mMenuView);
	}
	
	private void setOnclickLisener(){
		
		//设置按钮监听
		btn_pick_photo.setOnClickListener(itemClickListener);
		btn_take_photo.setOnClickListener(itemClickListener);
		
		//取消按钮
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//销毁弹出框
				dismiss();
			}
		});
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框 event点击的位置
		//当用户触摸屏幕时将创建一个MotionEvent对象。MotionEvent包含关于发生触摸的位置和时间等细节信息。
		mMenuView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int height = mMenuView.findViewById(R.id.layout_sel).getTop();//视图的高度
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){//如果客户的动作是离开屏幕
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});
	}
	
	private void setValues(){
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		//设置SelectPicPopupWindow弹出窗体可点击 能够获得焦点
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.ActionSheetDialogDialogStyle);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x88838B8B);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}
	

}
