package com.boe.posture.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class MathUtil {
    /**
     * （1）四舍五入把double转化int整型，0.5进一，小于0.5不进一
     *
     * @param number
     * @return
     */
    public static int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    public static String getString(double d){
        int anInt = getInt(d);
        return String.valueOf(anInt);
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Double 保留两位小数 四舍五入
     */
    public static String doubleToStr(Double c){
        DecimalFormat format  = new DecimalFormat("#0.00");
        String format1 = format.format(c);
        return format1+"°";
    }

    /**
     *  计算值
     */
    public static int getValue(double c,int value) {
        BigDecimal R = new BigDecimal(c).divide(new BigDecimal(value), 4, BigDecimal.ROUND_HALF_UP);
        int i = R.multiply(new BigDecimal(100)).intValue();
        return i>100?100:i;
    }


    /**
     * 绝对值
     */
    public static String getAbs(int num){
        BigDecimal abs = new BigDecimal(num).abs();
        return  abs.intValue()>100?String.valueOf(100):String.valueOf(abs.intValue());
    }

}