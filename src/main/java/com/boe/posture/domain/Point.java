package com.boe.posture.domain;

public class Point {
    private Double x;
    private Double y;

    /**
     * 功能描述:对应Integer,Double两种参数类型的移动方法
     * @Param: [x, y]
     * @Return: void
     * @Author: 12252
     * @Date: 2020/7/10 16:54
     */
    public void move(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public void move(Integer x, Integer y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }

    public void move(Integer x, Double y) {
        this.x = x.doubleValue();
        this.y = y;
    }

    public void move(Double x, Integer y) {
        this.x = x;
        this.y = y.doubleValue();
    }

    /**
     * 功能描述: 快速获取点的位置信息(toString)
     * @Param: []
     * @Return: java.lang.String
     * @Author: 12252
     * @Date: 2020/7/10 16:55
     */
    public String getLocation() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    /**
     * 功能描述: 对应Integer,Double两种参数类型的为点设置x,y坐标的方法
     * @Param: [x, y]
     * @Return: void
     * @Author: 12252
     * @Date: 2020/7/10 16:56
     */
    public void setLocation(Double x, Double y) {
        move(x, y);
    }

    public void setLocation(Integer x, Integer y) {
        move(x, y);
    }

    public void setLocation(Integer x, Double y) {
        move(x, y);
    }

    public void setLocation(Double x, Integer y) {
        move(x, y);
    }


    /**
     * 功能描述: 各种参数类型的构造方法,当参数为空时自动创建x,y为0的原点(0,0)
     * @Param: []
     * @Return:
     * @Author: 12252
     * @Date: 2020/7/10 16:57
     */
    public Point() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Integer x, Integer y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }

    public Point(Double x, Integer y) {
        this.x = x;
        this.y = y.doubleValue();
    }

    public Point(Integer x, Double y) {
        this.x = x.doubleValue();
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public int getXInt() {
        return x.intValue();
    }

    public int getYInt() {
        return y.intValue();
    }
    public void setX(Double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }


    public IntPoint toIntPoint(){
        return new IntPoint(this.x,this.y);
    }


    public class IntPoint{
        private Integer x;
        private Integer y;


        public IntPoint(Double x, Double y) {
            this.x = x.intValue();
            this.y = y.intValue();
        }

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }
    }



}
