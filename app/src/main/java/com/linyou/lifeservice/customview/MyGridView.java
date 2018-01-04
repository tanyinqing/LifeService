package com.linyou.lifeservice.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**自定义的GridView 解决GridView在滚动视图中只显示一行的问题
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
        //高度不在继承父类的值，而是采用自定义的值
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
