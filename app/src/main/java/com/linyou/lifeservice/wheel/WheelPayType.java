
package com.linyou.lifeservice.wheel;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.linyou.lifeservice.R;

public class WheelPayType
{
    private View view;
    private Context mContext;
    private WheelView wheelViewSex;

    private static final String mPayTypeName[] = {"现金支付","POS机刷卡"};
    private static final String mPayType[] = {"12","15"};

    public View getView()
    {
        return view;
    }

    public WheelPayType(Context mContext)
    {
        super();
        this.mContext = mContext;
        setView();
        setPicker();
    }
    
    
    private void setView()
    {
        this.view = LayoutInflater.from(mContext).inflate(R.layout.sex_picker, null);
    }
    
    
    ArrayWheelAdapter<String> adapter;
    
   
    
    private void setPicker()
    {
        if (wheelViewSex == null)
        {
        	wheelViewSex = (WheelView) view.findViewById(R.id.wv_sex);
        }
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(mContext,mPayTypeName);
        wheelViewSex.setViewAdapter(adapter);
        wheelViewSex.setCurrentItem(0);
    }
    
    public String getPayTypeName()
    {
        return mPayTypeName[wheelViewSex.getCurrentItem()];
    }
    public String getPayType()
    {
        return mPayType[wheelViewSex.getCurrentItem()];
    }

}
