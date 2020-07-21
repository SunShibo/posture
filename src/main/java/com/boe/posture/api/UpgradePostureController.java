package com.boe.posture.api;

import com.boe.posture.domain.JsonResponse;
import com.boe.posture.server.CalculateService;
import com.boe.posture.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 体态2.0
 */
@RestController
public class UpgradePostureController {

    @Autowired
private CalculateService calculateService;
    /**
     * 正面
     * @param file
     * @return
     */
    @PostMapping("/body/point")
    public JsonResponse point(MultipartFile file) {
        // 入参验证
        if(file==null){
            return  JsonResponse.fail("参数异常");
        }
        Map<String, Object> map = null;
        try {
            map = calculateService.front(ImageUtil.initImage(file));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.fail("无法解析请保证图片包含一个人,大小不超过4M,最短边至少50px，最长边最大4096px");
        }
        return  new JsonResponse(map);
    }


    /**
     * 侧面
     * @param file
     * @return
     */
    @PostMapping("/body/side")
    public  JsonResponse side(MultipartFile file) {
        // 入参验证
        if(file==null){
            return  JsonResponse.fail("参数异常");
        }
        Map<String, Object> map = null;
        try {
            map=calculateService.side(ImageUtil.initImage(file));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.fail("无法解析请保证图片包含一个人,大小不超过4M,最短边至少50px，最长边最大4096px");
        }
        return  new JsonResponse(map);
    }




}
