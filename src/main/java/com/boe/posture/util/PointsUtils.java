package com.boe.posture.util;


import com.boe.posture.domain.Point;

/**
 * 功能描述: 用于计算两点之间的各种方法
 *
 * @Author: 12252
 * @Date: 2020/7/10 16:52
 */
public class PointsUtils {
    private static final Double PI = Math.PI;

    /**
     * 功能描述: 获取两点之间的绝对长度
     *
     * @Param: [p1, p2]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 16:51
     */
    public static Double getLineLong(Point p1, Point p2) {
        return Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
    }

    /**
     * 功能描述: 获得两点连成的直线与x轴的角度
     *
     * @Param: [p1, p2]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 16:51
     */
    public static Double getRad(Point p1, Point p2) {
        Double dy = Math.abs(p1.getY() - p2.getY());
        Double z = getLineLong(p1, p2);
        if ((p1.getX() > p2.getX() && p1.getY() < p2.getY()) || (p1.getX() < p2.getX() && p1.getY() > p2.getY())) {
            z = -z;
        }
        return Math.asin(dy / z) / PI * 180;
    }

    /**
     * 功能描述: 获得两点连成的直线与y轴的角度
     *
     * @Param: [p1, p2]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 16:51
     */
    public static Double getYRad(Point p1, Point p2) {
        double xRad = getRad(p1,p2);
        double yRad = 0.0;
        if (xRad>0){
            yRad = -(90 - Math.abs(xRad));
        }else{
            yRad = 90 - Math.abs(xRad);
        }
        return yRad;
    }

    /**
     * 功能描述: 提供两点根据Y值获得X的值
     *
     * @Param: [pointA, pointB, py]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 16:51
     */
    public static Double getX(Point pointA, Point pointB, Double py) {
        return ((py - pointA.getY()) * (pointB.getX() - pointA.getX()) / (pointB.getY() - pointA.getY())) + pointA.getX();
    }

    /**
     * 功能描述: 提供两点根据X值获得Y的值
     *
     * @Param: [pointA, pointB, px]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 16:51
     */
    public static Double getY(Point pointA, Point pointB, Double px) {
        return ((px - pointA.getX()) * (pointB.getY() - pointA.getY()) / (pointB.getX() - pointA.getX())) + pointA.getY();
    }

    /**
     * 功能描述: 取得两个点与中间点构成的两条线的角的度数(总是锐角)
     * 参数: p2作为中间点,求p2与p1、p3构成的两个射线的角的度数
     *
     * @Param: [p1, p2, p3]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 17:10
     */
    public static Double getTwoLineAngle(Point p1, Point p2, Point p3) {
        //点一和点二构成的直线的X,Y轴差值
        Double l1X = p2.getX() - p1.getX();
        Double l1Y = p2.getY() - p1.getY();

        //点三和点二构成的直线的X,Y轴差值
        Double l2X = p2.getX() - p3.getX();
        Double l2Y = p2.getY() - p3.getY();

        if (l1X == 0) {
            if (l2X == 0) {
                System.out.println("两条线段都垂直于X轴,所以没有交点");
                return 0.0;
            } else {
                System.out.println("点一与点二形成的线段垂直于X轴");
                return 90 - Math.abs(getRad(p2, p3));
            }
        } else if (l2X == 0) {
            if (l1X == 0) {
                System.out.println("两条线段都垂直于X轴,所以没有交点");
                return 0.0;
            } else {
                System.out.println("点三与点二形成的线段垂直于X轴");
                return 90 - Math.abs(getRad(p2, p1));
            }
        } else if (l1Y == 0) {
            if (l2Y == 0) {
                System.out.println("两条线段都垂直于Y轴,所以没有交点");
                return 0.0;
            } else {
                System.out.println("点一与点二形成的线段垂直于Y轴");
                return Math.abs(getRad(p2, p3));
            }
        } else if (l2Y == 0) {
            if (l1Y == 0) {
                System.out.println("两条线段都垂直于Y轴,所以没有交点");
                return 0.0;
            } else {
                System.out.println("点三与点二形成的线段垂直于Y轴");
                return Math.abs(getRad(p2, p1));
            }
        } else {
            Double k1 = l1Y / l1X;
            Double k2 = l2Y / l2X;
            System.out.println(k1);
            System.out.println(k2);
            if (k1 * k2 == -1.0) {
                return 90.0;
            } else if (k1 * k2 < -1.0 && k1 * k2 < 0) {
                System.out.println(k1 * k2);
                return Math.atan((k1 - k2) / (1 + k1 * k2)) / PI * 180;
            } else if (k1 * k2 > -1 && k1 * k2 < 0) {
                System.out.println(k1 * k2);
                return 180 - Math.atan((k1 - k2) / (1 + k1 * k2)) / PI * 180;
            } else {
                System.out.println(k1 * k2);
                return Math.atan((k1 - k2) / (1 + k1 * k2)) / PI * 180;
            }
        }
    }

    /**
     * 功能描述: 取得两点与中间点构成的两条线的角的度数(自动判断锐/钝角)  未完成勿调用
     * 参数: p2作为中间点,求p2与p1、p3构成的两个射线的角的度数
     * @Param: [p1, p2, p3]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/13 17:10
     */
    public static Double getTwoLineAngle2(Point p1, Point p2, Point p3) {
        Point newPoint1 = new Point();
        Point newPoint2 = new Point();
        newPoint1.setLocation(p1.getX() - p2.getX(),p1.getY() - p2.getY());
        newPoint2.setLocation(p3.getX() - p2.getX(),p3.getY() - p2.getY());
        System.out.println(newPoint1.getLocation());
        System.out.println(newPoint2.getLocation());
        return null;
    }
}
