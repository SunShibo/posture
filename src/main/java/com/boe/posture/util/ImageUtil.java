package com.boe.posture.util;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;


public class ImageUtil {

   /**
     * 获取远程网络图片信息
     *
     * @param imageURL
     * @return
     */
    public static BufferedImage getRemoteBufferedImage(String imageURL) {
        URL url = null;
        InputStream is = null;
        BufferedImage bufferedImage = null;
        try {
            url = new URL(imageURL);
            is = url.openStream();
            bufferedImage = ImageIO.read(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return bufferedImage;
    }

    public static BufferedImage initImage(MultipartFile multipartFile) throws IOException {
        try {
            File file =FileUtil.multipartToFile(multipartFile);
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            Directory directory = metadata.getFirstDirectoryOfType(ExifDirectoryBase.class);
            int orientation = 0;
            // Exif信息中有保存方向,把信息复制到缩略图
            // 原图片的方向信息
            if (directory != null && directory.containsTag(ExifDirectoryBase.TAG_ORIENTATION)) {
                orientation = directory.getInt(ExifDirectoryBase.TAG_ORIENTATION);
            }
            int angle = 0;
            if (6 == orientation) {
                //6旋转90
                angle = 90;
            } else if (3 == orientation) {
                //3旋转180
                angle = 180;
            } else if (8 == orientation) {
                //8旋转90
                angle = 270;
            }
            BufferedImage img = ImageIO.read(file);
            return img;
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MetadataException e) {
            e.printStackTrace();
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }



    private MultipartFile createFileItem(String url, String fileName) throws Exception {
        FileItem item = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();

                FileItemFactory factory = new DiskFileItemFactory(16, null);
                String textFieldName = "uploadfile";
                item = factory.createItem(textFieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
                OutputStream os = item.getOutputStream();

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonsMultipartFile commonsMultipartFile = new CommonsMultipartFile(item);
        return commonsMultipartFile;
    }



    /**
     * 划线工具类
     */
    public static void line(Graphics2D image,int x1,int y1,int x2,int y2){
        image.drawLine(x1+4,y1-6,x2+4,y2-6);
    }

    /**
     * JAVA裁剪图片
     * @param x     裁剪时x的坐标（左上角）
     * @param y     裁剪时y的坐标（左上角）
     * @param width  裁剪后的图片宽度
     * @param height 裁剪后的图片高度
     * @return
     */
    public static BufferedImage cut(BufferedImage img, int x,int y,int width,int height){
        //调用裁剪方法
        BufferedImage image = deepCopy(img);
        BufferedImage subimage = image.getSubimage(x, y, width, height);
        //获取到文件的后缀名
        return subimage;
    }

    public static ArrayList<String> getLine(BufferedImage binaryImage){
        int width = binaryImage.getWidth();
        int height = binaryImage.getHeight();
        ArrayList<String> coordinateList = new ArrayList<String>();
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int rgb = binaryImage.getRGB(i, j);
                int upRgb = binaryImage.getRGB(i - 1, j);
                int downRgb = binaryImage.getRGB(i + 1, j);
                int leftRgb = binaryImage.getRGB(i, j - 1);
                int rightRgb = binaryImage.getRGB(i, j + 1);
                int leftUpRgb = binaryImage.getRGB(i - 1, j - 1);
                int leftDownRgb = binaryImage.getRGB(i + 1, j - 1);
                int rightUpRgb = binaryImage.getRGB(i - 1, j + 1);
                int rightDownRgb = binaryImage.getRGB(i + 1, j + 1);

                if ((i != 0 && j != 0) && ((rgb != upRgb) || (rgb != downRgb) || (rgb != leftRgb) ||
                        (rgb != rightRgb) || (rgb != leftUpRgb) || (rgb != leftDownRgb) || (rgb != rightUpRgb) || (rgb != rightDownRgb))) {
                    coordinateList.add(i-3 + ":" + j);
                }
            }
        }
        return coordinateList;
    }


    /**
     * 将BufferedImage转换为InputStream
     * @param image
     * @return
     */
    public static InputStream imgToInputStream(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
        }
        return null;
    }


    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


    /**
     * 图片转 base 64
     * @param img
     * @return
     * @throws IOException
     */
    public static String imgBase64(BufferedImage img) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
        String  imgStr = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        return imgStr;
    }


    /**
     * 灰度图转为纯黑白图
     */
    public static BufferedImage convert(String labelmapBase64, int realWidth, int realHeight) {
        try {
            byte[] bytes = Base64.getDecoder().decode(labelmapBase64);
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(is);
            BufferedImage newImage = resize(image, realWidth, realHeight);
            BufferedImage grayImage = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_BYTE_BINARY);
            for (int i = 0; i < realWidth; i++) {
                for (int j = 0; j < realHeight; j++) {
                    int rgb = newImage.getRGB(i, j);
                    grayImage.setRGB(i, j, rgb * 255);  //将像素存入缓冲区
                }
            }
            return grayImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

}
