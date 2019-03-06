/*
 Navicat Premium Data Transfer

 Source Server         : yiyi
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : 192.168.0.118:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 06/03/2019 17:16:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(20) NOT NULL COMMENT '商品id',
  `goods_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品详情介绍',
  `goods_price` decimal(10, 2) DEFAULT 0.00 COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT 0 COMMENT '商品库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 'phoneX', 'Apple iphone X', '/img/iphonex.png', '64G 银色 全网通4G', 8600.00, 20);
INSERT INTO `goods` VALUES (2, 'iphone8', 'Apple iphone 8', '/img/iphone8.png', '128G 黑色 移动联通4G', 6600.00, 50);

SET FOREIGN_KEY_CHECKS = 1;
