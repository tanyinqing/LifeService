package com.linyou.lifeservice.ceshi;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;


/**
 * 测试 总服务台
 */
public class p {
    // 当测试阶段时true      测试是使用比较多的这个Toast    当正式投入市场时这个设置为false

    public static void L(String msg1, String msg) {
        if (false) {
            Log.d(msg1, msg);
        }
    }
    public static void s(String msg) {
        if (false) {
            System.out.println(msg);
        }
    }

    public static void dd(Context context, String msg) {
        if (true) {
            new AlertDialog.Builder(context).setTitle("测试数据").setMessage(msg).show();
        }
    }

    public static void tt(Context context, String msg) {
        if (true) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }







    /*以下的内容，以后不在使用*/

    //这个是测试收藏商品时是否打印出订单的解析对象
    private static  boolean switch2 =false;
    public static void L2(String msg1, String msg) {
        if (switch2) {
            Log.d(msg1, msg);
        }
    }

    public static void d2(Context context, String msg) {
        if (switch2) {
            new AlertDialog.Builder(context).setTitle("测试数据").setMessage(msg).show();
        }
    }

    //这个是测试访问商品时是否打印出订单的解析对象
    private static  boolean switch1 =false;

    public static void t1(Context context, String msg) {
        if (false) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void d1(Context context, String msg) {
        if (false) {
            new AlertDialog.Builder(context).setTitle("测试数据").setMessage(msg).show();
        }
    }

    public static void L1(String msg1, String msg) {
        if (switch1) {
            Log.d(msg1, msg);
        }
    }

    //这个是公共测试数据  是否打印出测试数据
    private static  boolean isSwitch=false;

    public static void t(Context context, String msg) {
        if (isSwitch) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void d(Context context, String msg) {
        if (isSwitch) {
            new AlertDialog.Builder(context).setTitle("测试数据").setMessage(msg).show();
        }
    }


}
