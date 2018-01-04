package com.linyou.lifeservice.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**�Զ����GridView ���GridView�ڹ�����ͼ��ֻ��ʾһ�е�����
 * Created by Administrator on 2016/2/26 0026.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        //�߶Ȳ��ڼ̳и����ֵ�����ǲ����Զ����ֵ
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
