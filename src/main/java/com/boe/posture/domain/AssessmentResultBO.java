package com.boe.posture.domain;


public class AssessmentResultBO {
    private Integer id; //
    private String name;
    private String ut; //用户标识
    private String outOss;  //正面解析图
    private String headImageOss; //正面头部切图
    private String chestImageOss; // 正面胸部切图
    private String legImageOss;  //正面腿部切图
    private String pelvisImageOss; // 正面盆骨切图
    private String spineImageOss; // 正面脊柱切图

    private String headHeelL; //头部侧倾-左
    private String headHeelR; //头部侧倾-右
    private String highLowShoulderL; //高低肩-左
    private String highLowShoulderR; //高低肩-右
    private String pelvicTiltL; //盆骨侧倾-左
    private String pelvicTiltR; //盆骨侧倾-右
    private String scoliosisL; //脊柱侧弯-左
    private String scoliosisR; //脊柱侧弯-右
    private String oLeftlegR; //O型腿-左腿
    private String oRightlegR; //O型腿-右腿
    private String xLeftlegR; //X型腿-左腿
    private String xRightlegR; //X型腿-右腿

    private String head;// 侧面头部
    private String back;//侧面背部
    private String waist;//侧面腰部
    private String knee;//侧面膝盖
    private String analyticalDiagram;//侧面解析图

    private Double headForerake;//头前倾
    private Double pelvisForerake;//盆骨前倾
    private Double hunchback;//驼背
    private Double knee1;//膝过申
    private int highRiskCount; //风险项

    public int getHighRiskCount() {
        return highRiskCount;
    }

    public void setHighRiskCount(int highRiskCount) {
        this.highRiskCount = highRiskCount;
    }

    public Double getHunchback() {
        return hunchback;
    }

    public void setHunchback(String hunchback) {
        this.hunchback = Double.parseDouble(hunchback);
    }

    public Double getKnee1() {
        return knee1;
    }

    public void setKnee1(String knee1) {
        this.knee1 = Double.parseDouble(knee1);
    }

    public Double getHeadForerake() {
        return headForerake;
    }

    public void setHeadForerake(String headForerake) {
        this.headForerake = Double.parseDouble(headForerake);
    }

    public void setPelvisForerake(String pelvisForerake) {
        this.pelvisForerake = Double.parseDouble(pelvisForerake);
    }


    public Double getPelvisForerake() {
        return pelvisForerake;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getKnee() {
        return knee;
    }

    public void setKnee(String knee) {
        this.knee = knee;
    }


    public String getAnalyticalDiagram() {
        return analyticalDiagram;
    }

    public void setAnalyticalDiagram(String analyticalDiagram) {
        this.analyticalDiagram = analyticalDiagram;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }


    public String getOutOss() {
        return outOss;
    }

    public void setOutOss(String outOss) {
        this.outOss = outOss;
    }


    public String getHeadImageOss() {
        return headImageOss;
    }

    public void setHeadImageOss(String headImageOss) {
        this.headImageOss = headImageOss;
    }

    public String getChestImageOss() {
        return chestImageOss;
    }

    public void setChestImageOss(String chestImageOss) {
        this.chestImageOss = chestImageOss;
    }

    public String getLegImageOss() {
        return legImageOss;
    }

    public void setLegImageOss(String legImageOss) {
        this.legImageOss = legImageOss;
    }

    public String getPelvisImageOss() {
        return pelvisImageOss;
    }

    public void setPelvisImageOss(String pelvisImageOss) {
        this.pelvisImageOss = pelvisImageOss;
    }

    public String getSpineImageOss() {
        return spineImageOss;
    }

    public void setSpineImageOss(String spineImageOss) {
        this.spineImageOss = spineImageOss;
    }

    public String getHeadHeelL() {
        return headHeelL;
    }

    public void setHeadHeelL(String headHeelL) {
       this.headHeelL = headHeelL;
    }

    public String getHeadHeelR() {
        return headHeelR;
    }

    public void setHeadHeelR(String headHeelR) {
        this.headHeelR = headHeelR;
    }

    public String getHighLowShoulderL() {
        return highLowShoulderL;
    }

    public void setHighLowShoulderL(String highLowShoulderL) {
        this.highLowShoulderL = highLowShoulderL;
    }

    public String getHighLowShoulderR() {
        return highLowShoulderR;
    }

    public void setHighLowShoulderR(String highLowShoulderR) {
        this.highLowShoulderR = highLowShoulderR;
    }

    public String getPelvicTiltL() {
        return pelvicTiltL;
    }

    public void setPelvicTiltL(String pelvicTiltL) {
        this.pelvicTiltL = pelvicTiltL;
    }

    public String getPelvicTiltR() {
        return pelvicTiltR;
    }

    public void setPelvicTiltR(String pelvicTiltR) {
        this.pelvicTiltR = pelvicTiltR;
    }

    public String getScoliosisL() {
        return scoliosisL;
    }

    public void setScoliosisL(String scoliosisL) {
        this.scoliosisL = scoliosisL;
    }

    public String getScoliosisR() {
        return scoliosisR;
    }

    public void setScoliosisR(String scoliosisR) {
        this.scoliosisR = scoliosisR;
    }

    public String getoLeftlegR() {
        return oLeftlegR;
    }

    public void setoLeftlegR(String oLeftlegR) {
        this.oLeftlegR = oLeftlegR;
    }

    public String getoRightlegR() {
        return oRightlegR;
    }

    public void setoRightlegR(String oRightlegR) {
        this.oRightlegR = oRightlegR;
    }

    public String getxLeftlegR() {
        return xLeftlegR;
    }

    public void setxLeftlegR(String xLeftlegR) {
        this.xLeftlegR = xLeftlegR;
    }

    public String getxRightlegR() {
        return xRightlegR;
    }

    public void setxRightlegR(String xRightlegR) {
        this.xRightlegR = xRightlegR;
    }

    public int getInt(String str){
        return Integer.valueOf(str);
    }
}
