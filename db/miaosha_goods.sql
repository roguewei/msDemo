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

 Date: 06/03/2019 17:17:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for miaosha_goods
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_goods`;
CREATE TABLE `miaosha_goods`  (
  `id` bigint(20) NOT NULL COMMENT '秒杀的商品表',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `miaosha_price` decimal(10, 2) DEFAULT 0.00 COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of miaosha_goods
-- ----------------------------
INSERT INTO `miaosha_goods` VALUES (1, 1, 0.01, 4, '2019-03-06 16:58:37', '2019-03-07 16:58:42');

SET FOREIGN_KEY_CHECKS = 1;
