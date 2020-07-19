package com.boe.posture.api;

import com.alibaba.druid.util.StringUtils;
import com.boe.posture.domain.JsonResponse;
import com.boe.posture.server.PostureService;
import com.boe.posture.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class ExhibitionController {

    @Autowired
    private PostureService postureService;

    /**
     * 展会接口
     *
     * @return
     */
    @PostMapping("/body/body")
    public JsonResponse body(MultipartFile file,  String userKey,String name) {
        // 入参验证
        if (StringUtils.isEmpty(userKey) || file == null) {
            return JsonResponse.fail("参数异常");
        }
        Map<String, Object> front;
        try {
            front = postureService.body(ImageUtil.initImage(file), null,  name, userKey);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.fail("无法解析请保证图片包含一个人,大小不超过4M,最短边至少50px，最长边最大4096px");
        }
        return new JsonResponse(front);
    }

}
