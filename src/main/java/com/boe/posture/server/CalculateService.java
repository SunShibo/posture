package com.boe.posture.server;

import com.boe.posture.domain.*;
import com.boe.posture.domain.Point;
import com.boe.posture.util.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculateService {

    /**
     * 正面
     * @param image
     * @return
     * @throws Exception
     */
    public  Map<String,Object> front(BufferedImage image) throws Exception {
        Map<String,Object> resultMap=new HashMap<String,Object>();

        String baiDuStr = BodyKeyPointUtil.getBodyKeyPoint(image);
        JsonRootBean rootBean = JsonUtils.getJSONtoList(baiDuStr, JsonRootBean.class);
        PersonInfoBO personMsg = rootBean.getPerson_info().get(0); //获取人体信息
        BodyPartsBO personInfo = personMsg.getBody_parts();
        LocationBO location = personMsg.getLocation();

        int headX = MathUtil.getInt(personInfo.getTop_head().getX()); //头顶
        int headY = MathUtil.getInt(personInfo.getTop_head().getY());
        int leftEarX = MathUtil.getInt(personInfo.getLeft_ear().getX()); //左耳
        int leftEarY = MathUtil.getInt(personInfo.getLeft_ear().getY()) + 10;
        int neckX = MathUtil.getInt(personInfo.getNeck().getX()); //脖子
        int neckY = MathUtil.getInt(personInfo.getNeck().getY())-7;
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
        int rightHipX = MathUtil.getInt(personInfo.getRight_hip().getX()); //右臀部
        int rightHipY = MathUtil.getInt(personInfo.getRight_hip().getY());
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

        //人体分割
        String base64 = BodyKeyPointUtil.bodySeg(image);
        JSONObject resultJson = JsonUtils.getJsonObject4JavaPOJO(base64);
        //获取二值图
        BufferedImage binaryImg = ImageUtil.convert(resultJson.getString("labelmap"), image.getWidth(), image.getHeight());
        ArrayList<String> line = ImageUtil.getLine(binaryImg);

        //头顶
        Point topHead=ExtensionUtils.getHeadOutside(new Point(neckX,neckY),new Point(headX,headY),line);
        resultMap.put("head_top",topHead.toIntPoint());
        //脖子
        Point leftNeck = ExtensionUtils.getVerticalPointWithRim(new Point(leftEarX, leftEarY), new Point(neckX, neckY), line);
        Point rightNeck = ExtensionUtils.getScalePoint(leftNeck, new Point(neckX, neckY),200);
        resultMap.put("left_neck",leftNeck.toIntPoint());
        resultMap.put("right_neck",rightNeck.toIntPoint());

        //左手腕
        Point leftWristL = ExtensionUtils.getVerticalPointWithRim(new Point(leftElbowX, leftElbowY), new Point(leftWristX, leftWristY), line);
        Point leftWristR = ExtensionUtils.getScalePoint(leftWristL, new Point(leftWristX, leftWristY),200);
        resultMap.put("left_wrist_below",leftWristL.toIntPoint());
        resultMap.put("left_wrist_top",leftWristR.toIntPoint());

        //左手肘
        Point leftElbowL = ExtensionUtils.getVerticalPointWithRim(new Point(leftWristX, leftWristY), new Point(leftElbowX, leftElbowY), line);
        Point leftElbowR = ExtensionUtils.getScalePoint(leftElbowL, new Point(leftElbowX, leftElbowY),200);
        resultMap.put("left_elbow_top",leftElbowL.toIntPoint());
        resultMap.put("left_elbow_below",leftElbowR.toIntPoint());

        //左肩
        Point leftShoulder   = ExtensionUtils.getVerticalPointWithRim(new Point(neckX, neckY), new Point(leftShoulderX, leftShoulderY), line);
        resultMap.put("left_shoulder",leftShoulder.toIntPoint());

        //右手腕
        Point rightWristR = ExtensionUtils.getVerticalPointWithRim(new Point(rightElbowX, rightElbowY), new Point(rightWristX, rightWristY), line);
        Point rightWristL = ExtensionUtils.getScalePoint(rightWristR, new Point(rightWristX, rightWristY), 200);
        resultMap.put("righ_wrist_top",rightWristR.toIntPoint());
        resultMap.put("right_wrist_below",rightWristL.toIntPoint());

        //右手肘
        Point rightElbowR = ExtensionUtils.getVerticalPointWithRim(new Point(rightWristX, rightWristY), new Point(rightElbowX, rightElbowY), line);
        Point rightElbowL = ExtensionUtils.getScalePoint(rightElbowR, new Point(rightElbowX, rightElbowY), 200);
        resultMap.put("right_elbow_top",rightElbowR.toIntPoint());
        resultMap.put("right_elbow_below",rightElbowL.toIntPoint());

        //右肩
        Point rightShoulder   = ExtensionUtils.getVerticalPointWithRim(new Point(neckX, neckY), new Point(rightShoulderX, rightShoulderY), line);
        resultMap.put("right_shoulder",rightShoulder.toIntPoint());

        //左腋下
        Point leftOxter = ExtensionUtils.getScalePoint(leftShoulder, new Point(leftShoulderX, leftShoulderY),200);
        resultMap.put("left_oxter",leftOxter.toIntPoint());

        //右腋下
        Point rightOxter = ExtensionUtils.getScalePoint(rightShoulder, new Point(rightShoulderX, rightShoulderY),200);
        resultMap.put("right_oxter",rightOxter.toIntPoint());


        Point hipScale = ExtensionUtils.getScalePoint(new Point(leftHipX, leftHipY), new Point(rightHipX, rightHipY),50);
        Point kneeScale = ExtensionUtils.getScalePoint(new Point(leftKneeX, leftKneeY), new Point(rightKneeX, rightKneeY),50);
        Point crotch = ExtensionUtils.getScalePoint(hipScale,kneeScale,30);
        //裆部
        resultMap.put("crotch",crotch.toIntPoint());

        //腹部
        Point shouolderScale = ExtensionUtils.getScalePoint(new Point(leftShoulderX, leftShoulderY), new Point(rightShoulderX, rightShoulderY),50);
        Point scalePoint = ExtensionUtils.getScalePoint(shouolderScale,hipScale,84.21);
        Point leftAbdomen   = ExtensionUtils.getVerticalPointWithRim(shouolderScale, scalePoint, line);
        Point rightAbdomen   = ExtensionUtils.getScalePoint(leftAbdomen, scalePoint, 200);

        if(!(leftAbdomen.getX() >  scalePoint.getX() )) {  // 左
           Point pointItem =   leftAbdomen;
            leftAbdomen=rightAbdomen;
            rightAbdomen=pointItem;
        }

        Double lineLong = PointsUtils.getLineLong(leftWristL, new Point(leftWristX, leftWristY));

        if (leftAbdomen.getXInt() >= leftWristX) {
            leftAbdomen = new Point(leftAbdomen.getXInt() - Math.abs(lineLong) * 2 , leftAbdomen.getY());
        }
        if (rightAbdomen.getXInt() <= rightWristX) {
            rightAbdomen = new Point(rightAbdomen.getXInt() +  Math.abs(lineLong) * 2 , rightAbdomen.getY());
        }

        resultMap.put("right_abdomen",rightAbdomen.toIntPoint());
        resultMap.put("left_abdomen",leftAbdomen.toIntPoint());

        //左臀
        Point leftHip = ExtensionUtils.getVerticalPointWithRim(new Point(leftKneeX,leftKneeY), new Point(leftHipX, (leftKneeY -leftHipY)/4 + leftHipY ), line);
        resultMap.put("left_haunch",leftHip.toIntPoint());

        //右臀
        Point rightHip = ExtensionUtils.getVerticalPointWithRim(new Point(rightKneeX,rightKneeY), new Point(rightHipX, (rightKneeY - rightHipY)/4 + leftHipY ), line);
        resultMap.put("right_haunch",rightHip.toIntPoint());

        //左膝盖
        Point leftKneeL = ExtensionUtils.getVerticalPointWithRim(new Point(leftAnkleX, leftAnkleY), new Point(leftKneeX, leftKneeY), line);
        Point leftKneeR = ExtensionUtils.getScalePoint(leftKneeL, new Point(leftKneeX, leftKneeY), 200);
        resultMap.put("left_within_knee",leftKneeR.toIntPoint());
        resultMap.put("left_outer_knee",leftKneeL.toIntPoint());


        //右膝盖
        Point rightKneeR = ExtensionUtils.getVerticalPointWithRim(new Point(rightAnkleX, rightAnkleY), new Point(rightKneeX, rightKneeY), line);
        Point rightKneeL = ExtensionUtils.getScalePoint(rightKneeR, new Point(rightKneeX, rightKneeY), 200);
        resultMap.put("right_within_knee",rightKneeL.toIntPoint());
        resultMap.put("left_outer_knee",rightKneeR.toIntPoint());

        //左脚踝
        Point leftAnkleR = ExtensionUtils.getVerticalPointWithRim(new Point(leftKneeX, leftKneeY), new Point(leftAnkleX, leftAnkleY), line);
        Point leftAnkleL = ExtensionUtils.getScalePoint(leftAnkleR , new Point(leftAnkleX, leftAnkleY), 200);
        resultMap.put("left_outer_foot",leftAnkleL.toIntPoint());
        resultMap.put("left_outer_knee",leftAnkleR.toIntPoint());

        //右脚踝
        Point rightAnkleL = ExtensionUtils.getVerticalPointWithRim(new Point(rightKneeX, leftKneeY), new Point(rightAnkleX, rightAnkleY), line);
        Point rightAnkleR = ExtensionUtils.getScalePoint(rightAnkleL, new Point(rightAnkleX, rightAnkleY), 200);
        resultMap.put("right_outer_foot",rightAnkleR.toIntPoint());
        resultMap.put("right_within_foot",rightAnkleL.toIntPoint());

        //左脚尖
        Point leftTiptoe = ExtensionUtils.getExtensionPointWithLength(new Point(leftKneeX, leftKneeY), new Point(leftAnkleX, leftAnkleY), (location.getTop() + location.getHeight())-leftAnkleY -5);
        resultMap.put("left_tiptoe",leftTiptoe.toIntPoint());
        //右脚尖
        Point fightTiptoe = ExtensionUtils.getExtensionPointWithLength(new Point(rightKneeX, rightKneeY), new Point(rightAnkleX, rightAnkleY), (location.getTop() + location.getHeight())-rightAnkleY -5);
        resultMap.put("fight_tiptoe",fightTiptoe);

        resultMap.put("line",line);

        return  resultMap;
    }


    /**
     * 侧面
     * @param image
     * @return
     * @throws Exception
     */
    public  Map<String,Object> side(BufferedImage image) throws Exception {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        String baiDuStr = BodyKeyPointUtil.getBodyKeyPoint(image);
        JsonRootBean rootBean = JsonUtils.getJSONtoList(baiDuStr, JsonRootBean.class);
        PersonInfoBO personMsg = rootBean.getPerson_info().get(0); //获取人体信息
        BodyPartsBO personInfo = personMsg.getBody_parts();
        int headX = MathUtil.getInt(personInfo.getTop_head().getX()); //头顶
        int headY = MathUtil.getInt(personInfo.getTop_head().getY());
        int leftEarX = MathUtil.getInt(personInfo.getLeft_ear().getX()); //左耳
        int leftEarY = MathUtil.getInt(personInfo.getLeft_ear().getY());
        int neckX = MathUtil.getInt(personInfo.getNeck().getX()); //脖子
        int neckY = MathUtil.getInt(personInfo.getNeck().getY());
        int rightElbowX = MathUtil.getInt(personInfo.getRight_elbow().getX()); //右手肘
        int rightElbowY = MathUtil.getInt(personInfo.getRight_elbow().getY());
        int leftWristX = MathUtil.getInt(personInfo.getLeft_wrist().getX());//左手腕
        int leftWristY = MathUtil.getInt(personInfo.getLeft_wrist().getY());
        int rightShoulderX = MathUtil.getInt(personInfo.getRight_shoulder().getX()); //右肩
        int rightShoulderY = MathUtil.getInt(personInfo.getRight_shoulder().getY())-7;
        int leftShoulderX = MathUtil.getInt(personInfo.getLeft_shoulder().getX()); //左肩
        int leftShoulderY = MathUtil.getInt(personInfo.getLeft_shoulder().getY())-7;
        int rightHipX = MathUtil.getInt(personInfo.getRight_hip().getX()); //右臀部
        int leftHipY = MathUtil.getInt(personInfo.getLeft_hip().getY());
        int rightKneeX = MathUtil.getInt(personInfo.getRight_knee().getX()); //右膝盖
        int rightKneeY = MathUtil.getInt(personInfo.getRight_knee().getY());
        int leftKneeY = MathUtil.getInt(personInfo.getLeft_knee().getY());
        int rightAnkleX = MathUtil.getInt(personInfo.getRight_ankle().getX()); //右脚踝
        int rightAnkleY = MathUtil.getInt(personInfo.getRight_ankle().getY());
        //人体分割
        String base64 = BodyKeyPointUtil.bodySeg(image);
        JSONObject resultJson = JsonUtils.getJsonObject4JavaPOJO(base64);
        //获取二值图
        BufferedImage binaryImg = ImageUtil.convert(resultJson.getString("labelmap"), image.getWidth(), image.getHeight());
        ArrayList<String> line = ImageUtil.getLine(binaryImg);

        //头顶
        Point topHead=ExtensionUtils.getHeadOutside(new Point(leftEarX,leftEarX),new Point(headX,headY),line);
        resultMap.put("top_head",topHead.toIntPoint());
        //耳朵
        resultMap.put("left_ear",new Point(neckX,neckY));
        //脖子
        Point leftNeck = ExtensionUtils.getVerticalPointWithRim(new Point(leftEarX, leftEarY), new Point(neckX, neckY), line);
        Point rightNeck = ExtensionUtils.getScalePoint(leftNeck, new Point(neckX, neckY),200);
        resultMap.put("left_neck",leftNeck.toIntPoint());
        resultMap.put("right_neck",rightNeck.toIntPoint());

        //左手腕
        List<Point> wristList = ExtensionUtils.getTwoPointPerpendicularWithLength(new Point(rightElbowX, rightElbowY), new Point(leftWristX, leftWristY), 20.0);
        resultMap.put("left_wrist",wristList.get(1).toIntPoint());
        resultMap.put("right_wrist",wristList.get(0).toIntPoint());

        //左手肘
        List<Point> elbowList = ExtensionUtils.getTwoPointPerpendicularWithLength(new Point(rightShoulderX, rightShoulderY),new Point(rightElbowX, rightElbowY), 20.0);
        resultMap.put("left_elbow",elbowList.get(1).toIntPoint());
        resultMap.put("right_elbow",elbowList.get(0).toIntPoint());

        //左肩
        Point rightShoulder = ExtensionUtils.getVerticalPointWithRim(new Point(rightElbowX,rightElbowY), new Point(leftShoulderX, leftShoulderY), line);
        Point  leftShoulder = ExtensionUtils.getVerticalPointWithRim(new Point(rightElbowX,rightElbowY), new Point(rightShoulderX, rightShoulderY), line);
        resultMap.put("left_shoulder",leftShoulder.toIntPoint());
        resultMap.put("right_shoulder",rightShoulder.toIntPoint());

        //腹部
        int  abdomenHight=leftShoulderY + (leftHipY - leftShoulderY) / 3 * 2 ;
        Point leftAbdomen = ExtensionUtils.getVerticalPointWithRim(new Point(rightHipX, abdomenHight+40), new Point(rightHipX, abdomenHight), line);
        Point rightAbdomen = ExtensionUtils.getScalePoint(leftAbdomen, new Point(rightHipX, abdomenHight), 200);
        resultMap.put("left_abdomen",leftAbdomen.toIntPoint());
        resultMap.put("right_abdomen",rightAbdomen.toIntPoint());

        //左臀
        int height= (leftKneeY -leftHipY) / 4 + leftHipY;
        Point leftHip = ExtensionUtils.getVerticalPointWithRim(new Point(rightKneeX, rightKneeY), new Point(rightHipX, height), line);
        Point rightHip = ExtensionUtils.getScalePoint(leftHip, new Point(rightHipX, height ), 200);
        resultMap.put("left_hip",leftHip.toIntPoint());
        resultMap.put("right_hip",rightHip.toIntPoint());

        //左膝盖
        Point leftKneeL = ExtensionUtils.getVerticalPointWithRim(new Point(rightAnkleX, rightAnkleY), new Point(rightKneeX, rightKneeY), line);
        Point leftKneeR = ExtensionUtils.getScalePoint(leftKneeL, new Point(rightKneeX, rightKneeY), 200);
        resultMap.put("left_knee",leftKneeL.toIntPoint());
        resultMap.put("right_knee",leftKneeR.toIntPoint());

        //左脚踝
        Point leftAnkleR = ExtensionUtils.getVerticalPointWithRim(new Point(rightAnkleX, leftKneeY), new Point(rightAnkleX, rightAnkleY), line);
        Point leftAnkleL = ExtensionUtils.getScalePoint(leftAnkleR, new Point(rightAnkleX,rightAnkleY ), 200);
        resultMap.put("left_ankle",leftAnkleL.toIntPoint());
        resultMap.put("right_ankle",leftAnkleR.toIntPoint());
        resultMap.put("line",line);
        return  resultMap;
    }


}
