package com.linyou.lifeservice.wheel;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;


public class WheelBirthData
{   
    private View view;
    private Context mContext;
    private WheelView wheelYear;
    private WheelView wheelMonth;
    private WheelView wheelDay;
    
    private static final int StartYear = 1900; //开始年份
    private static final int EndYear = 2050; //结束年份
    private static String mYear[]; //年份数组
    private static final String mMonth[] = {"1","2","3","4","5","6","7","8","9","10","11","12"}; //月份数组
    private static String mDay[] = {"31","28","31","30","31","30","31","31","30","31","30","31"}; //每月天的数量
    private static String mDays[];
    
    private String currentYear;
    private String currentMonth;
    private String currentDay;
    
    public View getView()
    {
        return view;
    }
    
    public WheelBirthData(Context mContext)
    {
        super();
        this.mContext = mContext;
        setView();
        initYear();
    }
    
    /**
     * 初始化显示日期
     * @param year
     * @param month
     * @param day
     */
    public void initData(String year,String month,String day)
    {
        currentDay = ""+day;
        setYearPicker(Integer.parseInt(year));
        setMonthPicker(Integer.parseInt(month)-1);
        dayGenerate();
    }
    
    
    private void initYear()
    {
        int length = EndYear - StartYear +1;
        mYear = new String[length];
        for (int i = 0; i < length; i++)
        {
            mYear[i] = "" + (StartYear + i);
        }
    }
    
    private void setView()
    {
//        this.view = LayoutInflater.from(mContext).inflate(R.layout.birth_date_picker, null);
//        wheelYear = (WheelView) this.view.findViewById(R.id.wheelYear);
//        wheelMonth = (WheelView) this.view.findViewById(R.id.wheelMonth);
//        wheelDay = (WheelView) this.view.findViewById(R.id.wheelDay);
    }
    
    /**
     * 初始化日期
     */
    public void initData()
    {
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month;
        int date = t.monthDay;
        currentDay = ""+date;
        setYearPicker(year);
        setMonthPicker(month);
        dayGenerate();
    }
    
    /**
     * 设置年份
     * @param year
     */
    private void setYearPicker(int year)
    {
        
        int index = year - StartYear;
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(mContext,mYear);
        wheelYear.setViewAdapter(adapter);
        currentYear = mYear[index];
        wheelYear.setCurrentItem(index);
        wheelYear.addChangingListener(new OnWheelChangedListener()
        {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue)
            {
                currentYear = mYear[newValue];
                dayGenerate();
            }
        });
        wheelYear.setCyclic(true);
    }
    
    /**
     * 日期生成器
     */
    private void dayGenerate()
    {
        int year = Integer.parseInt(currentYear);
        int month = Integer.parseInt(currentMonth);
        int day =Integer.parseInt(currentDay);
        if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
        {
            mDay[1] = "29";
        }
        else
        {
            mDay[1] = "28";
        }
        
        int dayLen = Integer.parseInt(mDay[month-1]);
        mDays = null;
        mDays = new String[dayLen];
        for(int i = 0;i < dayLen; i++)
        {
            mDays[i] = ""+ (i+1);
        }
        if(day >= dayLen)
        {
            setDayPicker(dayLen-1);
        }
        else
        {
            setDayPicker(day-1);
        }
    }
    
    /**
     * 设置月份
     * @param month
     */
    private void setMonthPicker(int month)
    {
        int index = month - 0;
        
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(mContext,mMonth);
        wheelMonth.setViewAdapter(adapter);
        currentMonth = mMonth[0];
        wheelMonth.addChangingListener(new OnWheelChangedListener()
        {
            public void onChanged(WheelView wheel, int oldValue, int newValue)
            {
                currentMonth = mMonth[newValue];
                dayGenerate();
            }
        });
        wheelMonth.setCurrentItem(index);
        wheelMonth.setCyclic(true);
    }
    
    ArrayWheelAdapter<String> dayAdapter;
    
    private void setDayPicker(int day)
    {
        dayAdapter = new ArrayWheelAdapter<String>(mContext,mDays);
        wheelDay.setViewAdapter(dayAdapter);
        wheelDay.setCurrentItem(day);
        wheelDay.setCyclic(true);
    }
    
    public String getTime()
    {
        return mYear[wheelYear.getCurrentItem()]+"-"+mMonth[wheelMonth.getCurrentItem()] + "-"+mDays[wheelDay.getCurrentItem()];
    }
}
