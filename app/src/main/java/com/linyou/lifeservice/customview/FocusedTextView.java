package com.linyou.lifeservice.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class FocusedTextView extends TextView {
    public FocusedTextView(Context context) {
        super(context);
    }

    public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return true;
    }
}
