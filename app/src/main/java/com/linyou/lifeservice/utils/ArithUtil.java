package com.linyou.lifeservice.utils;

import java.math.BigDecimal;

//�����ݵļӼ��˳�
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
     * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
     * С�����Ժ�10λ���Ժ�������������롣
     * @param d1 ������
     * @param d2 ����
     * @return ������������
     * ����Ľ��0.3333333333333333
     * ����Ľ��0.3333333333
     */
    public static double div(double d1, double d2)
    {
        
        return div(d1, d2, DEF_DIV_SCALE);
        
    }
    /**
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
     * �����ȣ��Ժ�������������롣
     * @param d1 ������
     * @param d2 ����
     * @param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
     * @return ������������
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
