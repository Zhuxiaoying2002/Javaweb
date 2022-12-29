/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost:3306
 Source Schema         : book

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : 65001

 Date: 24/12/2022 14:07:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `imgUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publisher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (6, '测试', '64c708fa-deab-4b41-a07c-09d83706708c.jpeg', '99', '测试', '测试', 99.00);
INSERT INTO `book` VALUES (9, '测试6', '5d783c0c-973c-408b-a2bf-16e3dfed3295.jpeg', '99', '测试6', '测试6', 99.00);
INSERT INTO `book` VALUES (11, '111', '02f42e4a-9f3c-4517-b534-6fee5da2232c.jpeg', '111', '111', '1111', 111.00);
INSERT INTO `book` VALUES (12, '111', '3619f3b7-0444-4ba4-ac8b-f0a61ea5adde.jpeg', '111', '111', '1111', 111.00);
INSERT INTO `book` VALUES (13, '111', '9a752ec5-8451-472d-a888-3fcd940837d6.jpeg', '111', '111', '1111', 111.00);
INSERT INTO `book` VALUES (14, '111', 'd8f1852a-d2b1-4838-8749-c9119c5ac42a.jpeg', '111', '111', '1111', 111.00);
INSERT INTO `book` VALUES (15, '111', '21560bec-23e1-4e8c-b659-4a25d4fdc21a.jpeg', '111', '111', '1111', 111.00);
INSERT INTO `book` VALUES (16, '测试6', 'aeb83c10-7508-468a-b4c4-953c2be1177c.jpeg', '666', '测试6', '测试6', 666.00);

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookId` int(11) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `total` decimal(10, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (2, 9, 1, 1, 99);
INSERT INTO `cart` VALUES (3, 13, 1, 1, 99);
INSERT INTO `cart` VALUES (4, 12, 2, 1, 99);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookId` int(11) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `total` decimal(10, 0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userType` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user1', '123456', 1);
INSERT INTO `user` VALUES (2, 'user2', '123456', 0);
INSERT INTO `user` VALUES (3, 'user3', '123456', 0);
INSERT INTO `user` VALUES (4, 'user4', '123456', 0);
INSERT INTO `user` VALUES (7, 'user5', '123456', 0);

SET FOREIGN_KEY_CHECKS = 1;
