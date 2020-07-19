package com.boe.posture.server;

import com.boe.posture.domain.BodyPartsBO;
import com.boe.posture.domain.JsonRootBean;
import com.boe.posture.domain.PersonInfoBO;
import com.boe.posture.domain.Point;
import com.boe.posture.util.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculateService {


    public static void front(BufferedImage image) throws Exception {
        Map<String,Object> resultMap=new HashMap<String,Object>();

        String baiDuStr = BodyKeyPointUtil.getBodyKeyPoint(image);
        JsonRootBean rootBean = JsonUtils.getJSONtoList(baiDuStr, JsonRootBean.class);
        PersonInfoBO personMsg = rootBean.getPerson_info().get(0); //获取人体信息
        BodyPartsBO personInfo = personMsg.getBody_parts();
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
        BufferedImage copy = ImageUtil.deepCopy(image);
        //人体分割
        String base64 = BodyKeyPointUtil.bodySeg(image);
        JSONObject resultJson = JsonUtils.getJsonObject4JavaPOJO(base64);
        //获取二值图
        BufferedImage binaryImg = ImageUtil.convert(resultJson.getString("labelmap"), image.getWidth(), image.getHeight());
        ArrayList<String> line = ImageUtil.getLine(binaryImg);

        //头顶


        //脖子
        Point leftNeck = ExtensionUtils.getVerticalPointWithRim(new Point(noseX, noseY), new Point(neckX, neckY), line);  //左手肘
        Point rightNeck = ExtensionUtils.getScalePoint(leftNeck, new Point(neckX, neckY),200);

        //左肩
        Point leftShoulder = ExtensionUtils.getVerticalPointWithRim(new Point(leftElbowX, leftElbowY), new Point(leftShoulderX, leftShoulderY), line);  //左肩
        Point leftShoulderR = ExtensionUtils.getScalePoint(leftShoulder, new Point(leftShoulderX, leftShoulderY),200);

        //右肩

        //左腋下

        //右腋下

        //左手肘
        Point leftElbowL = ExtensionUtils.getVerticalPointWithRim(new Point(leftWristX, leftWristY), new Point(leftElbowX, leftElbowY), line);  //左手肘
        Point leftElbowR = ExtensionUtils.getScalePoint(leftElbowL, new Point(leftElbowX, leftElbowY),200);

        //右手肘

        //左手腕

        //右手腕

        //腹部

        //裆下

        //左臀

        //右臀

        //左膝盖

        //右膝盖

        //左脚踝

        //右脚踝

        //左脚尖

        //右脚尖

        Graphics2D g = copy.createGraphics();
        g.setColor(Color.red);
        //脖子
        g.drawString("•", leftNeck.getXInt(), leftNeck.getYInt());
        g.drawString("•", rightNeck.getXInt(), rightNeck.getYInt());
        g.drawString("•", neckX, neckY);

        //左手肘
        g.drawString("•", leftElbowL.getXInt(), leftElbowL.getYInt());
        g.drawString("•", leftElbowR.getXInt(), leftElbowR.getYInt());
        //左肩
        g.drawString("•", leftShoulder.getXInt(), leftShoulder.getYInt());
        g.drawString("•", leftShoulderR.getXInt(), leftShoulderR.getYInt());
        g.drawString("•", leftShoulderX, leftShoulderY);


        ImageIO.write(copy,"jpg",new File("C:\\Users\\Administrator\\Desktop\\test\\1.jpg"));

    }

    public static void main(String[] args) throws Exception {
        BufferedImage img = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\test\\00.jpg"));
        front(img);
    }

}
