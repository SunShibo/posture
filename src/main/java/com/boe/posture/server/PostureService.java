package com.boe.posture.server;

import com.boe.posture.domain.*;
import com.boe.posture.domain.Point;
import com.boe.posture.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


@Service
public class PostureService {
    @Autowired
    private UserService userService;

    /**
     * 1.5 正面
     */
    public Map<String, Object> body(BufferedImage bufferedImage, BufferedImage side,  String name, String userkey) throws Exception {
        Map<String, Object> map = new HashMap<>();
        AssessmentResultBO userInfo = new AssessmentResultBO();
        OSSClientUtil ossClientUtil = new OSSClientUtil();

        String baiduStr = BodyKeyPointUtil.getBodyKeyPoint(bufferedImage);
        JsonRootBean rootBean = JsonUtils.getJSONtoList(baiduStr, JsonRootBean.class);
        PersonInfoBO personMsg = rootBean.getPerson_info().get(0); //获取人体信息
        BodyPartsBO personInfo = personMsg.getBody_parts();
        LocationBO location = personMsg.getLocation();//边框信息

        int headX = MathUtil.getInt(personInfo.getTop_head().getX()); //头顶
        int headY = MathUtil.getInt(personInfo.getTop_head().getY());
        int rightEyeX = MathUtil.getInt(personInfo.getRight_eye().getX()); //右眼
        int rightEyeY = MathUtil.getInt(personInfo.getRight_eye().getY());
        int leftEyeX = MathUtil.getInt(personInfo.getLeft_eye().getX()); //左眼
        int leftEyeY = MathUtil.getInt(personInfo.getLeft_eye().getY());
        int noseX = MathUtil.getInt(personInfo.getNose().getX());      //鼻子
        int noseY = MathUtil.getInt(personInfo.getNose().getY());
        int rightEarX = MathUtil.getInt(personInfo.getRight_ear().getX()); //右耳
        int rightEarY = MathUtil.getInt(personInfo.getRight_ear().getY()) + 10;
        int leftEarX = MathUtil.getInt(personInfo.getLeft_ear().getX()); //左耳
        int leftEarY = MathUtil.getInt(personInfo.getLeft_ear().getY()) + 10;
        int neckX = MathUtil.getInt(personInfo.getNeck().getX()); //脖子
        int neckY = MathUtil.getInt(personInfo.getNeck().getY());
        int rightElbowX = MathUtil.getInt(personInfo.getRight_elbow().getX()); //右手肘
        int rightElbowY = MathUtil.getInt(personInfo.getRight_elbow().getY());
        int leftElbowX = MathUtil.getInt(personInfo.getLeft_elbow().getX()); //左手肘
        int leftElbowY = MathUtil.getInt(personInfo.getLeft_elbow().getY());
        int rightWristX = MathUtil.getInt(personInfo.getRight_wrist().getX());// 右手腕
        int rightWristY = MathUtil.getInt(personInfo.getRight_wrist().getY());
        int leftWristX = MathUtil.getInt(personInfo.getLeft_wrist().getX());//左手腕
        int leftWristY = MathUtil.getInt(personInfo.getLeft_wrist().getY());
        int rightShoulderX = MathUtil.getInt(personInfo.getRight_shoulder().getX()); //右肩
        int rightShoulderY = MathUtil.getInt(personInfo.getRight_shoulder().getY());
        int leftShoulderX = MathUtil.getInt(personInfo.getLeft_shoulder().getX()); //左肩
        int leftShoulderY = MathUtil.getInt(personInfo.getLeft_shoulder().getY());
        int rigthHipX = MathUtil.getInt(personInfo.getRight_hip().getX()); //右臀部
        int rigthHipY = MathUtil.getInt(personInfo.getRight_hip().getY());
        int leftHipX = MathUtil.getInt(personInfo.getLeft_hip().getX()); //左臀部
        int leftHipY = MathUtil.getInt(personInfo.getLeft_hip().getY());
        int rightKneeX = MathUtil.getInt(personInfo.getRight_knee().getX()); //右膝盖
        int rightKneeY = MathUtil.getInt(personInfo.getRight_knee().getY());
        int leftKneeX = MathUtil.getInt(personInfo.getLeft_knee().getX());//左膝盖
        int leftKneeY = MathUtil.getInt(personInfo.getLeft_knee().getY());
        int rightAnkleX = MathUtil.getInt(personInfo.getRight_ankle().getX()); //右脚踝
        int rightAnkleY = MathUtil.getInt(personInfo.getRight_ankle().getY());
        int leftAnkleX = MathUtil.getInt(personInfo.getLeft_ankle().getX()); //左脚踝
        int leftAnkleY = MathUtil.getInt(personInfo.getLeft_ankle().getY());

        int top = MathUtil.getInt(location.getTop()); //上
        int left = MathUtil.getInt(location.getLeft()); //左
        int height = MathUtil.getInt(location.getHeight()); //高
        int width = MathUtil.getInt(location.getWidth()); //宽

        BufferedImage spineCut = ImageUtil.cut(bufferedImage, rightShoulderX, neckY, width / 2, rigthHipY - rightShoulderY);//脊柱
        BufferedImage headCut = ImageUtil.cut(bufferedImage, left, top, width, rightShoulderY - top);//头部
        BufferedImage thoraxCut = ImageUtil.cut(bufferedImage, left, neckY, width, neckY - headY);//胸部
        BufferedImage legCut = ImageUtil.cut(bufferedImage, left, rigthHipY, width, rightAnkleY - rigthHipY);//腿部
        BufferedImage pelvicumCut = ImageUtil.cut(bufferedImage, left, rigthHipY - 40, width, neckY - headY);//盆骨

        //画线
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(new Color(16, 168, 229));
        ImageUtil.line(g, rightEyeX, rightEyeY, noseX, noseY);//右眼-鼻子
        ImageUtil.line(g, rightEyeX, rightEyeY, rightEarX, rightEarY);//右眼-右耳
        ImageUtil.line(g, leftEyeX, leftEyeY, noseX, noseY);//左眼-鼻子
        ImageUtil.line(g, leftEyeX, leftEyeY, leftEarX, leftEarY);//左眼-左耳
        ImageUtil.line(g, neckX, neckY, rightShoulderX, rightShoulderY);//脖子-右肩
        ImageUtil.line(g, neckX, neckY, leftShoulderX, leftShoulderY);//脖子-左肩
        ImageUtil.line(g, rightShoulderX, rightShoulderY, rightElbowX, rightElbowY);//右肩-右手肘
        ImageUtil.line(g, rightElbowX, rightElbowY, rightWristX, rightWristY);//右手肘-右手腕
        ImageUtil.line(g, rightElbowX, rightElbowY, rightWristX, rightWristY);//右手肘-右手腕
        ImageUtil.line(g, leftShoulderX, leftShoulderY, leftElbowX, leftElbowY);//左肩-左手肘
        ImageUtil.line(g, leftElbowX, leftElbowY, leftWristX, leftWristY);//左手肘-左手腕
        ImageUtil.line(g, rigthHipX, rigthHipY, rightKneeX, rightKneeY);//右臀-右膝盖
        ImageUtil.line(g, rightKneeX, rightKneeY, rightAnkleX, rightAnkleY);//右膝盖-右脚踝
        ImageUtil.line(g, leftHipX, leftHipY, leftKneeX, leftKneeY);//左臀-左膝盖
        ImageUtil.line(g, leftKneeX, leftKneeY, leftAnkleX, leftAnkleY);//左膝盖-左脚踝

        g.setColor(Color.green);
        //肩部延长线及角度
        Point newLeftShoulder = ExtensionUtils.getExtensionPointWithLength(new Point(rightShoulderX, rightShoulderY), new Point(leftShoulderX, leftShoulderY), 70.0);
        Point newRightShoulder = ExtensionUtils.getExtensionPointWithLength(new Point(leftShoulderX, leftShoulderY), new Point(rightShoulderX, rightShoulderY), 70.0);
        ImageUtil.line(g, newRightShoulder.getXInt(), newRightShoulder.getYInt(), newLeftShoulder.getXInt(), newLeftShoulder.getYInt());
        Double shouldersC = PointsUtils.getRad(new Point(rightShoulderX, rightShoulderY), new Point(leftShoulderX, leftShoulderY));
        g.drawString(MathUtil.doubleToStr(shouldersC), leftShoulderX - rightShoulderX, rightShoulderY - 10);
        //胯骨延长线及角度
        Point hipboneLeft = ExtensionUtils.getExtensionPointWithLength(new Point(rigthHipX, rigthHipY), new Point(leftHipX, leftHipY), 70.0);
        Point hipboneRight = ExtensionUtils.getExtensionPointWithLength(new Point(leftHipX, leftHipY), new Point(rigthHipX, rigthHipY), 70.0);
        ImageUtil.line(g, hipboneLeft.getXInt(), hipboneRight.getYInt(), hipboneRight.getXInt(), hipboneRight.getYInt());
        Double waistC = PointsUtils.getRad(new Point(rigthHipX, rigthHipY), new Point(leftHipX, leftHipY));
        g.drawString(MathUtil.doubleToStr(waistC), leftShoulderX - rightShoulderX, rigthHipY - 10);
        //膝盖延长线及角度
        Point leftKnee = ExtensionUtils.getExtensionPointWithLength(new Point(rightKneeX, rightKneeY), new Point(leftKneeX, leftKneeY), 70.0);
        Point rightKnee = ExtensionUtils.getExtensionPointWithLength(new Point(leftKneeX, leftKneeY), new Point(rightKneeX, rightKneeY), 70.0);
        ImageUtil.line(g, leftKnee.getXInt(), leftKnee.getYInt(), rightKnee.getXInt(), rightKnee.getYInt());
        Double kneeC = PointsUtils.getRad(new Point(rightKneeX, rightKneeY), new Point(leftKneeX, leftKneeY));
        g.drawString(MathUtil.doubleToStr(kneeC), leftShoulderX - rightShoulderX, rightKneeY - 10);
        //三条竖线
        ImageUtil.line(g, headX, headY - 40, headX, height / 3 * 2);
        ImageUtil.line(g, leftShoulderX, leftShoulderY - 40, leftShoulderX, height / 3 * 2);
        ImageUtil.line(g, rightShoulderX, rightShoulderY - 40, rightShoulderX, height / 3 * 2);

        //画点
        g.setColor(Color.red);
        g.drawString("•", headX, headY);                        //头顶
        g.drawString("•", rightEyeX, rightEyeY);     //右眼
        g.drawString("•", leftEyeX, leftEyeY);       //左眼
        g.drawString("•", noseX, noseY);                  //鼻子
        g.drawString("•", rightEarX, rightEarY);         //右耳
        g.drawString("•", leftEarX, leftEarY);            //左耳
        g.drawString("•", neckX, neckY);                       //脖子
        g.drawString("•", rightElbowX, rightElbowY);           //右手肘
        g.drawString("•", leftElbowX, leftElbowY);             //左手肘
        g.drawString("•", rightWristX, rightWristY);           //右手腕
        g.drawString("•", leftWristX, leftWristY);             //左手腕
        g.drawString("•", rightShoulderX, rightShoulderY);     //右肩
        g.drawString("•", leftShoulderX, leftShoulderY);       //左肩
        g.drawString("•", rigthHipX, rigthHipY);               //右臀
        g.drawString("•", leftHipX, leftHipY);                 //左臀
        g.drawString("•", rightKneeX, rightKneeY);             //右膝盖
        g.drawString("•", leftKneeX, leftKneeY);               //左膝盖
        g.drawString("•", rightAnkleX, rightAnkleY);           //右脚踝
        g.drawString("•", leftAnkleX, leftAnkleY);             //左脚踝

        //评估
        int items = 0;  //风险项
        //头部
        Double headC = PointsUtils.getRad(new Point(rightEarX, rightEarY), new Point(leftEarX, leftEarY));
        int head = MathUtil.getValue(headC, 13);
        if (Math.abs(head) > 50) {
            items++;
        }
        if (head == 0) {
            map.put("headHeel_L", "0");
            map.put("headHeel_R", "0");
        } else if (head > 0) {
            map.put("headHeel_L", String.valueOf(head));
            map.put("headHeel_R", "0");
        } else if (head < 0) {
            map.put("headHeel_L", "0");
            map.put("headHeel_R", MathUtil.getAbs(head));
        }
        //高低肩
        int shoulders = MathUtil.getValue(shouldersC, 13);
        if (Math.abs(shoulders) > 50) {
            items++;
        }
        if (shoulders == 0) {
            map.put("highLowShoulder_L", "0");
            map.put("highLowShoulder_R", "0");
        } else if (shoulders > 0) {
            map.put("highLowShoulder_L", String.valueOf(shoulders));
            map.put("highLowShoulder_R", "0");
        } else if (shoulders < 0) {
            map.put("highLowShoulder_L", "0");
            map.put("highLowShoulder_R", MathUtil.getAbs(shoulders));
        }
        //盆骨侧倾
        int waist = MathUtil.getValue(waistC, 13);
        if (Math.abs(waist) > 50) {
            items++;
        }
        if (waist == 0) {
            map.put("pelvicTilt_L", "0");
            map.put("pelvicTilt_R", "0");
        } else if (waist > 0) {
            map.put("pelvicTilt_L", String.valueOf(waist));
            map.put("pelvicTilt_R", "0");
        } else if (waist < 0) {
            map.put("pelvicTilt_L", "0");
            map.put("pelvicTilt_R", MathUtil.getAbs(waist));
        }
        //脊柱侧弯
        int spine = MathUtil.getValue(shouldersC, 13);
        if (Math.abs(spine) > 50) {
            items++;
        }
        if (spine == 0) {
            map.put("scoliosis_L", "0");
            map.put("scoliosis_R", "0");
        } else if (spine > 0) {
            map.put("scoliosis_L", String.valueOf(spine));
            map.put("scoliosis_R", "0");
        } else if (spine < 0) {
            map.put("scoliosis_L", "0");
            map.put("scoliosis_R", MathUtil.getAbs(spine));
            userInfo.setScoliosisL("0");
        }

        //右腿
        Double rightLegC = PointsUtils.getYRad(new Point(rightAnkleX, rightAnkleY), new Point(rightKneeX, rightKneeY));
        int rightLeg = MathUtil.getValue(rightLegC, 13);
        if (Math.abs(rightLeg) > 50) {
            items++;
        }
        if (rightLeg == 0) {
            map.put("o_rightleg_r", "0");
            map.put("x_rightleg_r", "0");
        } else if (rightLeg > 0) {
            map.put("o_rightleg_r", "0");
            map.put("x_rightleg_r", String.valueOf(rightLeg));
        } else if (rightLeg < 0) {
            map.put("o_rightleg_r", MathUtil.getAbs(rightLeg));
            map.put("x_rightleg_r", "0");
        }
        //左腿
        Double leftLegC = PointsUtils.getYRad(new Point(leftAnkleX, leftAnkleY), new Point(leftKneeX, leftKneeY));
        int leftLeg = MathUtil.getValue(leftLegC, 13);
        if (Math.abs(leftLeg) > 50) {
            items++;
        }
        if (leftLeg == 0) {
            map.put("o_leftleg_r", "0");
            map.put("x_leftleg_r", "0");
        } else if (leftLeg > 0) {
            map.put("o_leftleg_r", String.valueOf(leftLeg));
            map.put("x_leftleg_r", "0");
        } else if (leftLeg < 0) {
            map.put("o_leftleg_r", "0");
            map.put("x_leftleg_r", MathUtil.getAbs(leftLeg));
        }

        //上传图片
        String spineUrl = ossClientUtil.uploadBufferImage(spineCut);
        String headUrl = ossClientUtil.uploadBufferImage(headCut);
        String thoraxUrl = ossClientUtil.uploadBufferImage(thoraxCut);
        String legUrl = ossClientUtil.uploadBufferImage(legCut);
        String pelvicumUrl = ossClientUtil.uploadBufferImage(pelvicumCut);
        String analysis = ossClientUtil.uploadBufferImage(bufferedImage);

        map.put("out_oss", analysis);//正面解析图
        map.put("head_image_oss", headUrl);//头部
        map.put("chest_image_oss", thoraxUrl);//胸部
        map.put("leg_image_oss", legUrl);//腿部
        map.put("pelvis_image_oss", pelvicumUrl);//盆骨
        map.put("spine_image_oss", spineUrl);//脊柱

        //侧面
        int count=0;
        if (side!=null) {
            count=side(side, map, ossClientUtil);
        }
        map.put("highRiskCount", items+count);
        map.put("ut", userkey);
        map.put("name", name);
        userService.addInfo(map);
        return map;
    }


    /**
     * 1.5 侧面
     *
     * @throws Exception
     */
    public int side(BufferedImage bufferedImage, Map<String, Object> map, OSSClientUtil ossClientUtil) {
        int items=0;
        try {
            String baiduStr = BodyKeyPointUtil.getBodyKeyPoint(bufferedImage);
            JsonRootBean rootBean = JsonUtils.getJSONtoList(baiduStr, JsonRootBean.class);
            PersonInfoBO personMsg = rootBean.getPerson_info().get(0); //获取人体信息
            BodyPartsBO personInfo = personMsg.getBody_parts();
            LocationBO location = personMsg.getLocation();//边框信息
            int headX = MathUtil.getInt(personInfo.getTop_head().getX()); //头顶
            int headY = MathUtil.getInt(personInfo.getTop_head().getY());
            int leftEyeX = MathUtil.getInt(personInfo.getLeft_eye().getX()); //左眼
            int leftEyeY = MathUtil.getInt(personInfo.getLeft_eye().getY());
            int noseX = MathUtil.getInt(personInfo.getNose().getX()) + 5;      //鼻子
            int noseY = MathUtil.getInt(personInfo.getNose().getY());
            int leftEarX = MathUtil.getInt(personInfo.getLeft_ear().getX()); //左耳
            int leftEarY = MathUtil.getInt(personInfo.getLeft_ear().getY());
            int neckX = MathUtil.getInt(personInfo.getNeck().getX()) + 5; //脖子
            int neckY = MathUtil.getInt(personInfo.getNeck().getY());
            int leftElbowX = MathUtil.getInt(personInfo.getLeft_elbow().getX()); //左手肘
            int leftElbowY = MathUtil.getInt(personInfo.getLeft_elbow().getY());
            int leftWristX = MathUtil.getInt(personInfo.getLeft_wrist().getX());//左手腕
            int leftWristY = MathUtil.getInt(personInfo.getLeft_wrist().getY());
            int rightShoulderY = MathUtil.getInt(personInfo.getRight_shoulder().getY());
            int leftShoulderX = MathUtil.getInt(personInfo.getLeft_shoulder().getX()) - 10; //左肩
            int leftShoulderY = MathUtil.getInt(personInfo.getLeft_shoulder().getY());
            int rigthHipY = MathUtil.getInt(personInfo.getRight_hip().getY());
            int leftHipX = MathUtil.getInt(personInfo.getLeft_hip().getX()); //左臀部
            int leftHipY = MathUtil.getInt(personInfo.getLeft_hip().getY());
            int leftKneeX = MathUtil.getInt(personInfo.getLeft_knee().getX());//左膝盖
            int leftKneeY = MathUtil.getInt(personInfo.getLeft_knee().getY());
            int rightAnkleY = MathUtil.getInt(personInfo.getRight_ankle().getY());
            int leftAnkleX = MathUtil.getInt(personInfo.getLeft_ankle().getX()); //左脚踝
            int leftAnkleY = MathUtil.getInt(personInfo.getLeft_ankle().getY());

            int top = MathUtil.getInt(location.getTop()); //上
            int left = MathUtil.getInt(location.getLeft()); //左
            int height = MathUtil.getInt(location.getHeight()); //高
            int width = MathUtil.getInt(location.getWidth()); //宽

            //图片截图
            BufferedImage headCut = ImageUtil.cut(bufferedImage, left, top, width, rightShoulderY - top);//头部
            BufferedImage spineCut = ImageUtil.cut(bufferedImage, left, neckY, width, rigthHipY - rightShoulderY);//脊柱
            BufferedImage legCut = ImageUtil.cut(bufferedImage, left, rigthHipY, width, rightAnkleY - rigthHipY);//腿部
            BufferedImage pelvicumCut = ImageUtil.cut(bufferedImage, left, rigthHipY - 40, width, neckY - headY);//盆骨

            //画线
            Graphics2D g = bufferedImage.createGraphics();
            g.setColor(Color.green);
            g.drawLine(left, top, left + width, top);
            g.drawLine(left, top, left, top + height);
            g.drawLine(left, top + height, left + width, top + height);
            g.drawLine(left + width, top, left + width, top + height);
            g.drawLine(noseX, top, noseX, top + height);
            g.drawLine(leftEarX, top, leftEarX, top + height);

            g.setColor(new Color(16, 168, 229));
            g.drawLine(leftEyeX, leftEyeY, noseX, noseY);//眼睛-鼻子
            g.drawLine(leftEyeX, leftEyeY, leftEarX, leftEarY);//眼睛-耳朵
            g.drawLine(headX, headY, leftEarX, leftEarY);//头顶-耳朵
            g.drawLine(leftEarX, leftEarY, neckX, neckY);//耳朵-脖子
            g.drawLine(neckX, neckY, leftShoulderX, leftShoulderY);//脖子-肩
            g.drawLine(leftShoulderX, leftShoulderY, leftElbowX, leftElbowY);//肩-手肘
            g.drawLine(leftElbowX, leftElbowY, leftWristX, leftWristY);//手肘-手腕
            g.drawLine(leftHipX, leftHipY, leftKneeX, leftKneeY);//盆骨-膝盖
            g.drawLine(leftKneeX, leftKneeY, leftAnkleX, leftAnkleY);//膝盖-脚踝

            g.setColor(Color.red);
            g.drawString("•", headX - 3, headY + 5);                        //头顶
            g.drawString("•", leftEyeX - 3, leftEyeY + 5);       //左眼
            g.drawString("•", noseX - 3, noseY + 5);                  //鼻子
            g.drawString("•", leftEarX - 3, leftEarY + 5);            //左耳
            g.drawString("•", neckX - 3, neckY + 5);                       //脖子
            g.drawString("•", leftElbowX - 3, leftElbowY + 5);             //左手肘
            g.drawString("•", leftWristX - 3, leftWristY + 5);             //左手腕
            g.drawString("•", leftShoulderX - 3, leftShoulderY + 5);       //左肩
            g.drawString("•", leftHipX - 3, leftHipY + 5);                 //左臀
            g.drawString("•", leftKneeX - 3, leftKneeY + 5);               //左膝盖
            g.drawString("•", leftAnkleX - 3, leftAnkleY + 5);             //左脚踝

            Double headC = PointsUtils.getYRad(new Point(leftShoulderX, leftShoulderY), new Point(leftEarX, leftEarY));
            g.drawString(MathUtil.doubleToStr(headC), neckX + 40, neckY);
            Double backC = PointsUtils.getYRad(new Point(leftHipX, leftHipY), new Point(leftShoulderX, leftShoulderY));
            g.drawString(MathUtil.doubleToStr(backC), leftShoulderX + 50, leftShoulderY);
            Double waistC = PointsUtils.getYRad(new Point(leftKneeX, leftKneeY), new Point(leftHipX, leftHipY));
            g.drawString(MathUtil.doubleToStr(waistC), leftHipX + 50, leftHipY);
            Double kneeC = PointsUtils.getYRad(new Point(leftAnkleX, leftAnkleY), new Point(leftKneeX, leftKneeY));
            g.drawString(MathUtil.doubleToStr(kneeC), leftKneeX + 50, leftKneeY);

            //评估
            //头前倾
            int head = MathUtil.getValue(headC, 15);
            if (Math.abs(head) > 50) {
                items++;
            }
            if (head == 0) {
                map.put("headForerake", "0");
            } else {
                map.put("headForerake", MathUtil.getAbs(head));
            }
            //驼背
            int back = MathUtil.getValue(backC, 13);
            if (Math.abs(back) > 50) {
                items++;
            }
            if (back == 0) {
                map.put("hunchback", "0");
            } else {
                map.put("hunchback", MathUtil.getAbs(back));
            }
            //盆骨前倾
            int waist = MathUtil.getValue(waistC, 13);
            if (Math.abs(waist) > 50) {
                items++;
            }
            if (waist == 0) {
                map.put("pelvisForerake", "0");
            } else {
                map.put("pelvisForerake", MathUtil.getAbs(waist));
            }
            //膝过伸
            int knee = MathUtil.getValue(kneeC, 20);
            if (Math.abs(knee) > 50) {
                items++;
            }
            if (knee == 0) {
                map.put("knee1", "0");
            } else {
                map.put("knee1", MathUtil.getAbs(knee));
            }

            //上传图片
            String analysis = ossClientUtil.uploadBufferImage(bufferedImage);
            String headUrl = ossClientUtil.uploadBufferImage(headCut);
            String spineUrl = ossClientUtil.uploadBufferImage(spineCut);
            String pelvicumUrl = ossClientUtil.uploadBufferImage(pelvicumCut);
            String legUrl = ossClientUtil.uploadBufferImage(legCut);

            map.put("analytical_diagram", analysis);//全身照解析图
            map.put("head", headUrl);
            map.put("back", spineUrl);
            map.put("waist", pelvicumUrl);
            map.put("knee", legUrl);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }






}

