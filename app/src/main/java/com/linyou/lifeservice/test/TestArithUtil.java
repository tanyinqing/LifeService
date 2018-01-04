package com.linyou.lifeservice.test;

import android.test.AndroidTestCase;

import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Classfication;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.ArithUtil;
import com.linyou.lifeservice.utils.DateUtil;
import com.linyou.lifeservice.utils.DensityUtil;
import com.linyou.lifeservice.utils.MyLog;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.Util;
import com.linyou.lifeservice.web.CommonWeb;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
public class TestArithUtil extends AndroidTestCase {
    public void testCreateDB()throws Exception{

        MyLog.ShowLog("将dip或dp值转换为px值，保证尺寸大小不变"+ DensityUtil.dip2px(getContext(),49));
        MyLog.ShowLog("将px值转换为dip或dp值，保证尺寸大小不变"+ DensityUtil.px2dip(getContext(),49));
       /*MyLog.ShowLog("时间戳" + System.currentTimeMillis());
       MyLog.ShowLog("Date中解析出年份" + DateUtil.parserYear(new Date()));
       MyLog.ShowLog("时间戳中解析出年份" + DateUtil.parserYear(System.currentTimeMillis()));
       MyLog.ShowLog("格式化时间戳" + DateUtil.formatDate(System.currentTimeMillis()));
       MyLog.ShowLog("自定义一个时间类" + DateUtil.createDate(2017, 11, 27));
       MyLog.ShowLog("将unix时间戳转化成标准时间" + DateUtil.TimeStamp2Date(String.valueOf(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss "));*/
       /* ArithUtil arithUtil=new ArithUtil();
        double tan=arithUtil.div(1, 3);
        MyLog.ShowLog("计算的结果"+tan);*/
    }
}
