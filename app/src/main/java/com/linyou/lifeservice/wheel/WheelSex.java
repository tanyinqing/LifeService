
package com.linyou.lifeservice.wheel;



import com.linyou.lifeservice.R;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;

public class WheelSex
{
    private View view;
    private Context mContext;
    private WheelView wheelViewSex;
    
    private static final String mSex[] = {"男","女"};
    
    public View getView()
    {
        return view;
    }
    
    public WheelSex(Context mContext)
    {
        super();
        this.mContext = mContext;
        setView();
        setSexPicker();
    }
    
    
    private void setView()
    {
        this.view = LayoutInflater.from(mContext).inflate(R.layout.sex_picker, null);
    }
    
    
    ArrayWheelAdapter<String> adapter;
    
   
    
    private void setSexPicker()
    {
        if (wheelViewSex == null)
        {
        	wheelViewSex = (WheelView) view.findViewById(R.id.wv_sex);
        }
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(mContext,mSex);
        wheelViewSex.setViewAdapter(adapter);
        wheelViewSex.setCurrentItem(0);
    }
    
    public String getSex()
    {
        return mSex[wheelViewSex.getCurrentItem()];
    }
}
