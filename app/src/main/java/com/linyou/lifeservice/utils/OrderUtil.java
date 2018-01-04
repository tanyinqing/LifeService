package com.linyou.lifeservice.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.widget.TextView;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Goods;

//用Map来盛装订单里所有商品对象的数据集合
public class OrderUtil {

//	private double totalPrice;

    private Map<String,Goods> goodsMap; //商品的ID 和商品的键值对  对象集合

	private static OrderUtil instance;  //订单工具单例化
	
	private static List<Handler> mHandlerList;  //有一个静态集合专门用来存储Handler对象的

	//无法从外部实例化 只能从内部实例化
	private OrderUtil()
	{
        goodsMap = new HashMap<String, Goods>();
		mHandlerList = new ArrayList<Handler>();
	}
	
	public static OrderUtil getInstance()   //订单工具单例化
	{
		if(null == instance){
			instance = new OrderUtil();
		}
		return instance;
	}

	/*public void addGoods(Goods goods)   //向订单里增加商品  这个在订单适配器中肯定有详细的用处
	{
		if (goodsMap.containsKey(goods.getId()))
		{
			//remove(goods);
		}else{
			double s = ArithUtil.mul(""+goods.getCount(), goods.getPrice());
			goodsMap.put(goods.getId(),goods);
		}
		notifyDataChanged();
	}*/

	public void addGoods(Goods goods)   //向订单里增加商品  这个在订单适配器中肯定有详细的用处
	{
		 // 如果商品重复 就先删除在添加
		if (goodsMap.containsKey(goods.getId()))
		{
			remove(goods);
		}
		//验证商品的价格是数量不为空 主要是验证作用
		double s = ArithUtil.mul(""+goods.getCount(), goods.getPrice());//getCount购买的数量
		goodsMap.put(goods.getId(), goods);
		notifyDataChanged();
	}
	
	public void remove(Goods goods)
	{
		double s = ArithUtil.mul(""+goods.getCount(), goods.getPrice());
        goodsMap.remove(goods.getId());
        notifyDataChanged();
	}
	
	public void updateGoodsList(List<Goods> list)
	//修改订单，如果集合中包括该商品，则不添加，否则添加商品到集合中
	{
		String total = "0";
		double total_ = 0;
		for(Goods goods :list)
		{
            if(goodsMap.containsKey(goods.getId()))
            {
                continue;
            }
            else {
				if (Integer.valueOf(goods.getId()) != 0) {
					goodsMap.put(goods.getId(), goods);
					//p.L("放入购物车的商品ID清单", goods.getId());
				}

			}
		}
		notifyDataChanged();

		//p.L("放入购物车的商品清单goodsList", goodsMap.size()+"");

	}

	//将所有商品集合的值组成一个集合
	public List<Goods> getGoodsList()
	{
        List<Goods> goodsList = new ArrayList<Goods>();
        goodsList.addAll(goodsMap.values());
		//notifyDataChanged();//这个是我后来加上去的

		for (Goods g:goodsList){
			//p.L("放入购物车的商品清单goodsList",goodsList.size()+"  "+g.getName());
		}
		return goodsList;
	}



	//将一个页面的handler注册进入本实例的handler集合中在页面创建时   mOrderUtil.registerHandler(mHandler);
	public void registerHandler(Handler mHandler)
	{
		mHandlerList.add(mHandler);
	}

	//获得商品总的价格
	public String getTotalPrice()
	{
        List<Goods> goodsList = getGoodsList();
        double total = 0.0;
        for (Goods goods : goodsList)
        {
			//p.L("购物车内商品的清单列表",goods.toString());
			double s = ArithUtil.mul(""+goods.getCount(), goods.getPrice());
            total += s;
        }
		return ""+total;
	}

	//如果一个页面销毁就从handler集合中清除他的的handler  mOrderUtil.unRegisterHandler(mHandler);
	public void unRegisterHandler(Handler mHandler)
	{
		if(mHandlerList.contains(mHandler))
		{
			mHandlerList.remove(mHandler);
		}
	}

	//将商品集合清空
    public void clear()
    {
        goodsMap.clear();
        notifyDataChanged();
    }


	//一个购买了多少件商品
    public int getCount()
    {
        int count = 0;
        List<Goods> goodsList = getGoodsList();
        for (Goods goods : goodsList)
        {
            count += goods.getCount();
        }
        return count;
    }


	//这个方法就是工具类中每做一次更改  都要向主Activity页面发送一个信息
	public void notifyDataChanged()
	{
		for(Handler mHandler:mHandlerList ){
			mHandler.sendEmptyMessage(Constant.ORDER_CHANGED);
		}
	}
	
}
