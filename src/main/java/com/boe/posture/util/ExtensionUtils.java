package com.boe.posture.util;

import com.boe.posture.domain.Point;
import com.sun.istack.internal.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: 所有延长线或延长点的方法类
 *
 * @Author: 12252
 * @Date: 2020/7/10 16:53
 */

public class ExtensionUtils {
    private static Point a, b;

    /**
     * 功能描述: 提供两点根据Y值获得X的值
     *
     * @Param: [pointA, pointB, py]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 16:50
     */
    public static double getX(Point pointA, Point pointB, double py) {
        return ((py - pointA.getY()) * (pointB.getX() - pointA.getX()) / (pointB.getY() - pointA.getY())) + pointA.getX();
    }

    /**
     * 功能描述: 提供两点根据X值获得Y的值
     *
     * @Param: [pointA, pointB, px]
     * @Return: java.lang.Double
     * @Author: 12252
     * @Date: 2020/7/10 16:50
     */
    public static double getY(Point pointA, Point pointB, double px) {
        return ((px - pointA.getX()) * (pointB.getY() - pointA.getY()) / (pointB.getX() - pointA.getX())) + pointA.getY();
    }

    /**
     * 功能描述: 获取过两点的直线与x,y轴的交点
     *
     * @Param: [pa, pb]
     * @Return: void
     * @Author: 12252
     * @Date: 2020/7/10 16:50
     */
    public static void setaAndb(Point pa, Point pb) {
        a = new Point();
        b = new Point();

        a.setLocation(getX(pa, pb, 0.0), 0);
        b.setLocation(0, getY(pa, pb, 0.0));
    }

    /**
     * 功能描述: 获取a点指向b点的固定长度的延长点的坐标
     *
     * @Param: [pa, pb, length]
     * @Return: com.chang.entity.Point
     * @Author: 12252
     * @Date: 2020/7/10 16:50
     */
    public static Point getExtensionPointWithLength(Point pa, Point pb, double length) {
        Point point = new Point();
        if (pa.getX().equals(pb.getX())) {
            if (pa.getY() > pb.getY()) {
                point.setLocation(pa.getX(), pb.getY() - length);
            } else {
                point.setLocation(pa.getX(), pb.getY() + length);
            }
        } else if (pa.getY().equals(pb.getY())) {
            if (pa.getX() > pb.getX()) {
                point.setLocation(pb.getX() - length,pa.getY());
            } else {
                point.setLocation(pb.getX() + length,pa.getY());
            }
        } else {
            setaAndb(pa, pb);
            String dir;
            if (pa.getX() > pb.getX()) {
                dir = "left";
            } else {
                dir = "right";
            }
            if ((pa.getX() > pb.getX() && pa.getY() > pb.getY()) || (pa.getX() < pb.getX() && pa.getY() < pb.getY())) {
                double x3, y3;
                if (dir == "right") {
                    if (pa.getX() > pb.getX()) {
                        x3 = (length + Math.sqrt((-a.getX() + pa.getX()) * (-a.getX() + pa.getX()) + pa.getY() * pa.getY())) * (-a.getX() + pa.getX()) / Math.sqrt((-a.getX() + pa.getX()) * (-a.getX() + pa.getX()) + pa.getY() * pa.getY()) + a.getX();
                        point.setLocation(x3, getY(pa, pb, x3));
                    } else {
                        x3 = (length + Math.sqrt((-a.getX() + pb.getX()) * (-a.getX() + pb.getX()) + pb.getY() * pb.getY())) * (-a.getX() + pb.getX()) / Math.sqrt((-a.getX() + pb.getX()) * (-a.getX() + pb.getX()) + pb.getY() * pb.getY()) + a.getX();
                        point.setLocation(x3, getY(pa, pb, x3));
                    }
                } else {
                    if (pa.getX() > pb.getX()) {
                        y3 = pa.getY() - (Math.sqrt((pa.getX() - pb.getX()) * (pa.getX() - pb.getX()) + (pa.getY() - pb.getY()) * (pa.getY() - pb.getY())) + length) * (pa.getY() - pb.getY()) / (Math.sqrt((pa.getX() - pb.getX()) * (pa.getX() - pb.getX()) + (pa.getY() - pb.getY()) * (pa.getY() - pb.getY())));
                        point.setLocation(getX(pa, pb, y3), y3);
                    } else {
                        y3 = pb.getY() - (Math.sqrt((pb.getX() - pa.getX()) * (pb.getX() - pa.getX()) + (pb.getY() - pa.getY()) * (pb.getY() - pa.getY())) + length) * (pb.getY() - pa.getY()) / (Math.sqrt((pb.getX() - pa.getX()) * (pb.getX() - pa.getX()) + (pb.getY() - pa.getY()) * (pb.getY() - pa.getY())));
                        point.setLocation(getX(pa, pb, y3), y3);
                    }
                }
            } else {
                double x3, y3;
                if (dir == "right") {
                    if (pa.getX() > pb.getX()) {
                        x3 = ((Math.sqrt(pa.getX() * pa.getX() + (b.getY() - pa.getY()) * (b.getY() - pa.getY())) + length) * a.getX() / Math.sqrt(a.getX() * a.getX() + b.getY() * b.getY()));
                        point.setLocation(x3, getY(pa, pb, x3));
                    } else {
                        x3 = ((Math.sqrt(pb.getX() * pb.getX() + (b.getY() - pb.getY()) * (b.getY() - pb.getY())) + length) * a.getX() / Math.sqrt(a.getX() * a.getX() + b.getY() * b.getY()));
                        point.setLocation(x3, getY(pa, pb, x3));
                    }
                } else {
                    if (pa.getY() > pb.getY()) {
                        y3 = (((Math.sqrt((a.getX() - pa.getX()) * (a.getX() - pa.getX()) + pa.getY() * pa.getY())) + length) * b.getY() / Math.sqrt(a.getX() * a.getX() + b.getY() * b.getY()));
                        point.setLocation(getX(pa, pb, y3), y3);
                    } else {
                        y3 = (((Math.sqrt((a.getX() - pb.getX()) * (a.getX() - pb.getX()) + pb.getY() * pb.getY())) + length) * b.getY() / Math.sqrt(a.getX() * a.getX() + b.getY() * b.getY()));
                        point.setLocation(getX(pa, pb, y3), y3);
                    }
                }
            }
        }


        return point;
    }

    /**
     * 功能描述: 获得两个点形成的直线交于指定点的垂线相交于边框的交点
     *
     * @Param: [p1, p2, rimList]
     * @Return: com.chang.entity.Point
     * @Author: 12252
     * @Date: 2020/7/10 16:49
     */
    public static Point getVerticalPointWithRim(Point p1, Point p2, List<String> rimList) {
        double lineRad = PointsUtils.getRad(p1, p2);
        double nowRad = 0.0;
        double minRad = 360.0;

        double minLong = 2000000.0;
        double nowLong = 0.0;

        int nowi = 0;
        int mini = 0;
        List<Integer> minList = new ArrayList<Integer>();

        for (String str :
                rimList) {
            nowRad = PointsUtils.getRad(p2, new Point(Double.parseDouble(str.split(":")[0]), Double.parseDouble(str.split(":")[1])));
            nowRad = Math.abs(nowRad) + Math.abs(lineRad);
            nowRad = Math.abs(90.0 - nowRad);
            if (Math.abs(nowRad) < 1) {
                minList.add(nowi);
            }
            nowi++;
        }

        for (int i : minList) {
            nowLong = PointsUtils.getLineLong(p2, new Point(Double.parseDouble(rimList.get(i).split(":")[0]), Double.parseDouble(rimList.get(i).split(":")[1])));
            if (nowLong < minLong) {
                minLong = nowLong;
                mini = i;
            }
        }
        return new Point(Double.parseDouble(rimList.get(mini).split(":")[0]), Double.parseDouble(rimList.get(mini).split(":")[1]));
    }

    /**
     * 功能描述: 获取两个点的中点
     *
     * @Param: [p1, p2]
     * @Return: com.chang.entity.Point
     * @Author: 12252
     * @Date: 2020/7/10 16:21
     */
    public static Point getScalePoint(Point p1, Point p2) {
        if (p1.getX() > p2.getX()) {
            Point pc = new Point(p1);
            p1 = p2;
            p2 = pc;

        }
        return new Point((Math.abs(p1.getX()) - Math.abs(p2.getX())) / 2 + p1.getX(), (Math.abs(p1.getY()) - Math.abs(p2.getY())) / 2 + p1.getY());
    }

    /**
     * 功能描述: 同上需要传入比例(百分比) a向b取
     * 重载方法: ExtensionUtils.getScalePoint
     *
     * @Param: [p1, p2, scale]
     * @Return: com.chang.entity.Point
     * @Author: 12252
     * @Date: 2020/7/10 16:48
     */
    public static Point getScalePoint(Point p1, Point p2, @NotNull double scale) {
        double cx = Math.abs(Math.abs(p2.getX()) - Math.abs(p1.getX())) * scale / 100;
        double cy = Math.abs(Math.abs(p2.getY()) - Math.abs(p1.getY())) * scale / 100;
        Point cp = new Point();

        if (p1.getX() < p2.getX()) {
            cp.setX(p1.getX() + cx);
        } else {
            cp.setX(p1.getX() - cx);
        }
        if (p1.getY() < p2.getY()) {
            cp.setY(p1.getY() + cy);
        } else {
            cp.setY(p1.getY() - cy);
        }
        return cp;
    }

    /**
     * 功能描述: 同上需要传入比例(百分比) a向b取(重载方法)
     *
     * @Param: [p1, p2, scale]
     * @Return: com.chang.entity.Point
     * @Author: 12252
     * @Date: 2020/7/10 16:49
     */
    public static Point getScalePoint(Point p1, Point p2, @NotNull int scale) {
        double cx = Math.abs(Math.abs(p2.getX()) - Math.abs(p1.getX())) * scale / 100;
        double cy = Math.abs(Math.abs(p2.getY()) - Math.abs(p1.getY())) * scale / 100;
        Point cp = new Point();

        if (p1.getX() < p2.getX()) {
            cp.setX(p1.getX() + cx);
        } else {
            cp.setX(p1.getX() - cx);
        }
        if (p1.getY() < p2.getY()) {
            cp.setY(p1.getY() + cy);
        } else {
            cp.setY(p1.getY() - cy);
        }
        return cp;
    }

    /**
     * 功能描述: 获取肩膀的点位
     * 参数推荐: 求肩膀用20°
     * @Param: [p1, p2, lineList]
     * @Return: com.boe.posture.domain.Point
     * @Author: 12252
     * @Date: 2020/7/19 15:47
     */
    public static Point getShoulderOutside(Point p1,Point p2,List<String> rimList,@DefaultValue("right") String direction) {
        double lineRad = PointsUtils.getYRad(p1, p2);
        double nowRad = 0.0;
        double minRad = 360.0;

        double minLong = 999999.0;
        double nowLong = 0.0;

        int nowi = 0;
        int mini = 0;
        List<Integer> maxList = new ArrayList<Integer>();

        for (String str :
                rimList) {
            nowRad = PointsUtils.getYRad(p2, new Point(Double.parseDouble(str.split(":")[0]), Double.parseDouble(str.split(":")[1])));
            if(lineRad>0 && nowRad>0){
                nowRad = lineRad - nowRad - 20;
            }else if(lineRad<0 && nowRad<0){
                nowRad = nowRad - nowRad -20;
            }else if(lineRad == 0) {
                if (direction == "right"){
                    nowRad = 70 + nowRad;
                }else{
                    nowRad = 70 - nowRad;
                }
            }else{
                nowRad = 180.0 - Math.abs(nowRad) - Math.abs(lineRad) - 20.0;
            }

            if (Math.abs(nowRad) < 1) {
                maxList.add(nowi);
                System.out.println(PointsUtils.getLineLong(p2, new Point(Double.parseDouble(rimList.get(nowi).split(":")[0]), Double.parseDouble(rimList.get(nowi).split(":")[1]))));
            }
            nowi++;
        }

        for (int i : maxList) {
            nowLong = PointsUtils.getLineLong(p2, new Point(Double.parseDouble(rimList.get(i).split(":")[0]), Double.parseDouble(rimList.get(i).split(":")[1])));
            if (nowLong < minLong) {
                minLong = nowLong;
                mini = i;
            }

        }
        System.out.println(minLong);
        return new Point(Double.parseDouble(rimList.get(mini).split(":")[0]), Double.parseDouble(rimList.get(mini).split(":")[1]));
    }

    /**
     * 功能描述: 获取头部点位
     * @Param: [p1, p2, rimList]
     * @Return: com.boe.posture.domain.Point
     * @Author: 12252
     * @Date: 2020/7/21 12:09
     */
    public static Point getHeadOutside(Point p1,Point p2,List<String> rimList) {
        double lineRad = PointsUtils.getRad(p1, p2);
        double nowRad = 0.0;
        double minRad = 360.0;

        double minLong = 999999.0;
        double nowLong = 0.0;

        int nowi = 0;
        int mini = 0;
        List<Integer> maxList = new ArrayList<Integer>();

        for (String str :
                rimList) {

            nowRad = PointsUtils.getRad(p2, new Point(Double.parseDouble(str.split(":")[0]), Double.parseDouble(str.split(":")[1])));

            nowRad = Math.abs(nowRad) - Math.abs(lineRad);
            if (Math.abs(nowRad) < 1 && Math.abs(nowRad) > -1) {
                maxList.add(nowi);
            }
            nowi++;
        }

        for (int i : maxList) {
            nowLong = PointsUtils.getLineLong(p2, new Point(Double.parseDouble(rimList.get(i).split(":")[0]), Double.parseDouble(rimList.get(i).split(":")[1])));
            if (nowLong < minLong) {
                minLong = nowLong;
                mini = i;
            }
        }
        return new Point(Double.parseDouble(rimList.get(mini).split(":")[0]), Double.parseDouble(rimList.get(mini).split(":")[1]) - 3.0);
    }
    /**
     * 功能描述: 根据外边获取脚尖的点位置(经验性)
     * @Param: [rimList]
     * @Return: com.boe.posture.domain.Point
     * @Author: 12252
     * @Date: 2020/7/19 19:06
     */
    public static Point getFeetPoint(List<String> rimList) {
        double leftMin = 0.0;
        double minx = 900000.0;
        double maxx = 0.0;
        double nowX = 0.0;

        for (String str:rimList) {
            nowX = Double.parseDouble(str.split(":")[0]);

            if(nowX<minx){
                minx = nowX;
            }
            if(nowX>maxx){
                maxx = nowX;
            }


        }



        return null;
    }

    /**
     * 功能描述: 给出两点求第二个点的固定垂直线的两点坐标
     * @Param: [p1, p2, length]
     * @Return: java.util.List<com.boe.posture.domain.Point>
     * @Author: 12252
     * @Date: 2020/7/19 19:52
     */
    public static List<Point> getTwoPointPerpendicularWithLength(Point p1,Point p2,double length) {
        List<Point> pointList = new ArrayList<Point>();
        if (p1.getX()==p2.getX()){
            pointList.add(new Point(p2.getX(),p2.getY() + length));
            pointList.add(new Point(p2.getX(),p2.getY() - length));
        }else if (p1.getY() == p2.getY()){
            pointList.add(new Point(p2.getX() - length,p2.getY()));
            pointList.add(new Point(p2.getX() + length,p2.getY()));
        }else if(p1.getX()>p2.getX()){
            Point p3 = new Point((p2.getX() - length * (p1.getY() - p2.getY()) / PointsUtils.getLineLong(p1,p2)),p2.getY() + length * (p1.getX() - p2.getX()) / PointsUtils.getLineLong(p1,p2));
            pointList.add(p3);
            pointList.add(getScalePoint(p3,p2,200));
        }else if(p1.getX()<p2.getX()){
            Point p3 = new Point((p2.getX() - length * (p1.getY() - p2.getY()) / PointsUtils.getLineLong(p1,p2)),p2.getY() - length * (p1.getX() - p2.getX()) / PointsUtils.getLineLong(p1,p2));
            pointList.add(p3);
            pointList.add(getScalePoint(p3,p2,200));
        }
        return pointList;
    }
}
