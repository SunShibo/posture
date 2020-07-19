package com.boe.posture.api;

import com.alibaba.druid.util.StringUtils;
import com.boe.posture.domain.JsonResponse;
import com.boe.posture.server.PostureService;
import com.boe.posture.server.UserService;
import com.boe.posture.util.ImageUtil;
import com.boe.posture.util.OSSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostureController {

    @Autowired
    private PostureService postureService;
    @Autowired
    private UserService userService;
    /**
     * 上传图片
     *
     * @return
     */
    @PostMapping("/body/upload")
    public JsonResponse upload(MultipartFile file) throws Exception {
        if (file == null) {
            return JsonResponse.fail("参数异常");
        }
        OSSClientUtil oss = new OSSClientUtil();
        String path = oss.uploadBufferImage(ImageUtil.initImage(file));
        Map<String, Object> front = new HashMap<>();
        front.put("path", path);
        return new JsonResponse(front);
    }


    /**
     * 正面关键点
     *
     * @param frontPath 正面
     * @param sidePath  侧面
     * @param userKey   用户编码
     * @param name      用户名
     * @return
     */
    @PostMapping("/body/front")
    public JsonResponse front(String frontPath, String sidePath,String name, String userKey) {
        // 入参验证
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(userKey) || StringUtils.isEmpty(sidePath)) {
            return JsonResponse.fail("参数异常");
        }
        Map<String,Object> reslutMap;
        try {
            reslutMap=postureService.body(ImageUtil.getRemoteBufferedImage(frontPath), ImageUtil.getRemoteBufferedImage(sidePath),  name,userKey);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.fail("无法解析请保证图片包含一个人,大小不超过4M,最短边至少50px，最长边最大4096px");
        }
        return new JsonResponse(reslutMap);
    }


    /**
     * 评估记录
     *
     * @param userKey 用户编码
     * @return
     */
    @GetMapping("/body/list")
    public JsonResponse list(String userKey) {
        if (StringUtils.isEmpty(userKey)) {
            return JsonResponse.fail("参数异常");
        }
        return new JsonResponse(userService.getList(userKey));
    }


    /**
     * 评估详情
     *
     * @param id 评估记录id
     * @return
     */
    @GetMapping("/body/info")
    public JsonResponse list(Integer id) {
        // 入参验证
        if (id == null) {
            return JsonResponse.fail("参数异常");
        }
        return new JsonResponse(userService.getInfo(id));
    }

}
