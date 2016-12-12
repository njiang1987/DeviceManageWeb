/*
 Navicat MySQL Data Transfer

 Source Server         : Monitor
 Source Server Version : 50716
 Source Host           : localhost
 Source Database       : monitor

 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 12/06/2016 10:50:07 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tel_information`
-- ----------------------------
DROP TABLE IF EXISTS `tel_information`;
CREATE TABLE `tel_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `plantform` char(50) DEFAULT NULL,
  `imei` char(50) DEFAULT NULL,
  `tel_name` char(50) DEFAULT NULL,
  `tel_version` char(50) DEFAULT NULL,
  `borrow_name` char(50) DEFAULT NULL,
  `borrow_time` datetime DEFAULT NULL,
  `operation` char(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tel_information`
-- ----------------------------
BEGIN;
INSERT INTO `tel_information` VALUES ('2', null, null, 'telnumber', 'telname', 'telversion', 'wanghua', null, null), ('3', null, 'ios', '1234', 'xiaomi2', '6.0', 'jiangnan', null, null), ('5', null, 'android', 'xiaomi4', 'xiaomi4', 'xiaomi4', 'jiangnan', null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
