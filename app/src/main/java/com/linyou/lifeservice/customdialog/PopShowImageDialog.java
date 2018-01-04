package com.linyou.lifeservice.customdialog;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.utils.PublicUtil;

/**
 * @author 志强
 *
 */
public class PopShowImageDialog extends PopupWindow
{

    private View mMenuView;
    private ImageView imageView;
    private Context context;
    private float mHeight = 0f;

    private String TAG = "SelPicturePopWin";

    private String image_url;

    public PopShowImageDialog(Context context,String url)
    {
        super(context);
        this.context = context;
        this.image_url = url;
        findView();
        setValues();
        setOnclickLisener();
        showImage();
    }

    private void showImage()
    {
        PublicUtil.getImageLoader().displayImage(Constant.URLBASE + image_url,imageView,PublicUtil.getAdsOption());
    }



    private void findView()
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//布局适配器
        mMenuView = inflater.inflate(R.layout.dialog_show_image, null);
        imageView = (ImageView) mMenuView.findViewById(R.id.imageView);
        // 设置SelPicturePopWin的View
        this.setContentView(mMenuView);
    }

    
    private void setOnclickLisener()
    {

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                int height = mMenuView.findViewById(R.id.linearContent).getTop();
                dismiss();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP)
//                {
//                    if (y < height)
//                    {
//                        dismiss();
//                    }
//                }
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
//        this.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
    
}
