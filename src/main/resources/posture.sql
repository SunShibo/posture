/*
Navicat MySQL Data Transfer

Source Server         : wy
Source Server Version : 50727
Source Host           : 47.94.143.11:3306
Source Database       : posture

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-07-22 12:00:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_image
-- ----------------------------
DROP TABLE IF EXISTS `user_image`;
CREATE TABLE `user_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评估记录',
  `ut` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户标识',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `out_oss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '正面全身解析图',
  `head_image_oss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头部',
  `chest_image_oss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '胸部',
  `leg_image_oss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '腿部',
  `pelvis_image_oss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '盆骨',
  `spine_image_oss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '脊柱',
  `headHeel_L` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头部侧倾-左',
  `headHeel_R` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头部侧倾-右',
  `highLowShoulder_L` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '高低肩-左',
  `highLowShoulder_R` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '高低肩-右',
  `pelvicTilt_L` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '盆骨侧倾-左',
  `pelvicTilt_R` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '盆骨侧倾-右',
  `scoliosis_L` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '脊柱侧弯-左',
  `scoliosis_R` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '脊柱侧弯-右',
  `o_leftleg_r` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'O型腿-左腿',
  `o_rightleg_r` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'O型腿-右腿',
  `x_leftleg_r` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'X型腿-左腿',
  `x_rightleg_r` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'X型腿-右腿',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `head` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头部',
  `back` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '背部',
  `waist` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '腰部',
  `knee` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '膝盖',
  `analytical_diagram` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '全身照解析图',
  `head_forerake` double DEFAULT NULL COMMENT '头前倾',
  `pelvis_forerake` double DEFAULT NULL COMMENT '盆骨前倾',
  `hunchback` double DEFAULT NULL COMMENT '驼背',
  `knee1` double DEFAULT NULL COMMENT '膝过申',
  `highRiskCount` int(11) DEFAULT '0' COMMENT '风险项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user_image
-- ----------------------------
INSERT INTO `user_image` VALUES ('1', '666666', 'ceshi', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/ee14dbf9-8c7b-41e9-b9e6-b9c7e65124bd.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/fc4fa9bc-2c3d-4a2b-94ab-6e21e80ec24c.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/d163f5a7-66a4-4c17-a8ea-34bb0b88cd0e.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/bd2789f4-2872-47e8-a35f-ccb90930a447.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/c35a1215-58e6-4ef7-954e-0ad27cc3d81f.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/bde71cde-dbe0-4532-b02a-3fae8dd99860.jpg', '0', '0', '0', '23', '0', '0', '0', '23', '0', '23', '0', '0', '2020-07-17 15:57:56', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/e63a64bd-5345-4649-ac74-827a432665e3.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/6ec169d5-628c-4c31-aa51-465dfd31d58e.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/86902f45-a7b5-4313-9b90-e4ef0cf1f11d.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/498f9a3d-a867-4292-86a7-93b87ee26bbd.jpg', 'https://oopsstatic.oss-cn-beijing.aliyuncs.com/10146525-f647-4eed-9bf9-906fe6076386.jpg', null, null, '13', '13', '0');
