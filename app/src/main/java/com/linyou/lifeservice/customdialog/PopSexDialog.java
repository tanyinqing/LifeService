package com.linyou.lifeservice.customdialog;


import com.linyou.lifeservice.R;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

/**
 * @author 志强
 *
 */
public class PopSexDialog extends PopupWindow
{
    
    private Button btnNeg, btnPos;
    private LinearLayout dialog_Group;
    private View mMenuView;
    private TextView textTitle;
    private Context context;
    private OnClickListener itemClickListener;
    private float mHeight = 0f;
    
    private String TAG = "SelPicturePopWin";
    
    public PopSexDialog(Context context, OnClickListener itemClickListener)
    {
        super(context);
        this.context = context;
        this.itemClickListener = itemClickListener;
        findView();
        setValues();
        setOnclickLisener();
    }
    
    private void findView()
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.toast_view_alertdialog, null);
        textTitle = (TextView) mMenuView.findViewById(R.id.textTitle);
        btnNeg = (Button) mMenuView.findViewById(R.id.btn_neg);
        btnPos = (Button) mMenuView.findViewById(R.id.btn_pos);
        dialog_Group = (LinearLayout) mMenuView.findViewById(R.id.dialog_Group);
        // 设置SelPicturePopWin的View
        this.setContentView(mMenuView);
    }
    
    public void setView(View view)
    {
        dialog_Group.addView(view);
    }
    
    public void setTitle(String title)
    {
        textTitle.setText(title);
    }
    
    private void setOnclickLisener()
    {
        
        // 设置按钮监听
        btnPos.setOnClickListener(itemClickListener);
        
        // 取消按钮
        btnNeg.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                // 销毁弹出框
                dismiss();
            }
        });
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                int height = mMenuView.findViewById(R.id.linearContent).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (y < height)
                    {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    
    private void setValues()
    {
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x88838B8B);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
    
}
