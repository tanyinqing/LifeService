package com.linyou.lifeservice.utils;

import java.math.BigDecimal;

//大数据的加减乘除
public class ArithUtil
{
    private static final int DEF_DIV_SCALE = 10;

    public static double add(String d1, String d2)
    {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();

    }

    public static double sub(String d1, String d2)
    {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();

    }

    public static double mul(String d1, String d2)
    {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();

    }
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param d1 被除数
     * @param d2 除数
     * @return 两个参数的商
     * 计算的结果0.3333333333333333
     * 计算的结果0.3333333333
     */
    public static double div(double d1, double d2)
    {

        return div(d1, d2, DEF_DIV_SCALE);

    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param d1 被除数
     * @param d2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double d1, double d2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

}
