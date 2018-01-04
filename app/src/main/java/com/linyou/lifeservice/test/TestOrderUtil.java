package com.linyou.lifeservice.test;

import android.test.AndroidTestCase;

import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.utils.MyLog;
import com.linyou.lifeservice.utils.OrderUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
public class TestOrderUtil extends AndroidTestCase {

    public void testCreateDB()throws Exception{
//构建商品是 价格和数量是必须的，否则构建BigDecimal类时会出现空指针异常
        Goods goods=new Goods();
        goods.setAmount("9");//商品库存数量
        goods.setCount(9);//购买的商品的数量
        goods.setId("9");
        goods.setPrice("3");
        goods.setDescribes("描述");

        Goods goods1=new Goods();
        goods1.setId("10");
        goods1.setAmount("10");//商品库存数量
        goods1.setCount(9);//购买的商品的数量
        goods1.setPrice("4");
        goods1.setDescribes("描述1");

        OrderUtil orderUtil=OrderUtil.getInstance();//单例模式
        orderUtil.addGoods(goods);
        orderUtil.addGoods(goods1);
        MyLog.ShowLog("商品添加完毕");
    }
    public void testCreateDB1()throws Exception{
      OrderUtil orderUtil=OrderUtil.getInstance();//单例模式
         List<Goods> tan= orderUtil.getGoodsList();
        for (Goods good:tan) {
        MyLog.ShowLog(good.toString());
        }

        MyLog.ShowLog("商品的总价格是："+orderUtil.getTotalPrice());
        MyLog.ShowLog("一个购买了多少件商品："+orderUtil.getCount());

    }
}
