package com.boe.posture.util;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class BodyKeyPointUtil {
    //FIXME 这里需要替换成正式的Key
    //百度AI 开放平台
    private static String clientId = "sbjRmx1aqUBE1FRicpKfh4M1";
    private static String clientSecret = "7T6E3sajEAqkA38klRVGaXusNYB0QbZC";

    /**
     * 百度云
     *
     * @param img
     * @return
     * @throws Exception
     */
    public static String getBodyKeyPoint(BufferedImage img) throws Exception {
        String imgStr = ImageUtil.imgBase64(img);
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_analysis";   // 请求url
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
        String param = "image=" + imgParam;
        String accessToken = getAuth();
        String result = HttpUtil.post(url, accessToken, param);
        return result;
    }

       /**
     * 百度获取Token
     *
     * @return
     */
    public static String getAuth() {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + clientId
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + clientSecret;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }


    /**
     * 人体分割
     * @param image
     * @return
     */
    public static String bodySeg(BufferedImage image) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_seg";
        try {
            String imgStr = ImageUtil.imgBase64(image);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            String accessToken = BodyKeyPointUtil.getAuth();
            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}