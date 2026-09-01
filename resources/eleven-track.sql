/*
 Navicat Premium Dump SQL

 Source Server         : track
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : 119.45.21.146:3306
 Source Schema         : eleven-track

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 01/09/2026 21:14:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for got_department
-- ----------------------------
DROP TABLE IF EXISTS `got_department`;
CREATE TABLE `got_department` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  `dept_code` varchar(64) DEFAULT NULL COMMENT '部门编码',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门ID',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` int DEFAULT '1' COMMENT '状态：0禁用 1启用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `deleted` int DEFAULT '0' COMMENT '逻辑删除 0未删 1已删',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';

-- ----------------------------
-- Table structure for got_parking_apply
-- ----------------------------
DROP TABLE IF EXISTS `got_parking_apply`;
CREATE TABLE `got_parking_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_no` varchar(40) NOT NULL COMMENT '申请编号',
  `visitor_name` varchar(32) NOT NULL COMMENT '访客姓名',
  `visitor_phone` varchar(16) NOT NULL COMMENT '访客手机号',
  `plate_number` varchar(20) NOT NULL COMMENT '车牌号',
  `owner_name` varchar(32) NOT NULL COMMENT '被访业主',
  `house_no` varchar(32) NOT NULL COMMENT '房号',
  `apply_start` datetime NOT NULL COMMENT '申请停车开始时间',
  `apply_end` datetime NOT NULL COMMENT '申请停车结束时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0正常 1删除',
  `apply_status` tinyint DEFAULT '0' COMMENT '申请状态：0待审批 1审批通过 2审批驳回 3已取消',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审批人ID',
  `audit_user_name` varchar(64) DEFAULT NULL COMMENT '审批人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `audit_remark` varchar(512) DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_apply_no` (`apply_no`),
  KEY `idx_plate_number` (`plate_number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='外来临时停车申请';

-- ----------------------------
-- Table structure for got_point
-- ----------------------------
DROP TABLE IF EXISTS `got_point`;
CREATE TABLE `got_point` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点位ID',
  `point_name` varchar(100) NOT NULL COMMENT '点位名称',
  `area` varchar(50) DEFAULT '' COMMENT '所属区域',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '打卡经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '打卡纬度',
  `address` varchar(200) DEFAULT '' COMMENT '详细地址',
  `nfc_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '该点位专属NFC打卡链接',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '0停用 1正常',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除',
  `sort` int DEFAULT '0' COMMENT '排序权重',
  `point_id` varchar(64) DEFAULT NULL COMMENT '业务点位编号',
  `dep_ids` varchar(512) DEFAULT NULL COMMENT '负责部门ID集合，多个用逗号分隔',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_got_point_status_deleted` (`status`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='NFC巡检点位';

-- ----------------------------
-- Table structure for got_point_daily
-- ----------------------------
DROP TABLE IF EXISTS `got_point_daily`;
CREATE TABLE `got_point_daily` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `point_id` bigint NOT NULL COMMENT '点位ID',
  `point_name` varchar(255) DEFAULT NULL COMMENT '点位名称',
  `area` varchar(255) DEFAULT NULL COMMENT '所属区域',
  `longitude` decimal(18,8) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18,8) DEFAULT NULL COMMENT '纬度',
  `address` varchar(500) DEFAULT NULL COMMENT '点位地址',
  `point_status` int DEFAULT NULL COMMENT '点位状态',
  `stat_date` date NOT NULL COMMENT '统计日期',
  `check_count` int NOT NULL DEFAULT '0' COMMENT '巡检次数',
  `last_check_time` datetime DEFAULT NULL COMMENT '最后巡检时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pointid_statdate` (`point_id`,`stat_date`),
  KEY `idx_stat_date` (`stat_date`),
  KEY `idx_point_id` (`point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点位每日统计表';

-- ----------------------------
-- Table structure for got_record
-- ----------------------------
DROP TABLE IF EXISTS `got_record`;
CREATE TABLE `got_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录主键',
  `point_id` bigint NOT NULL COMMENT '关联点位ID',
  `user_id` bigint NOT NULL COMMENT '巡检人ID',
  `area` varchar(50) DEFAULT NULL COMMENT '巡检区域',
  `check_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '打卡时间',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '打卡经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '打卡纬度',
  `check_status` tinyint NOT NULL DEFAULT '1' COMMENT '1正常巡检 2现场异常',
  `remark` varchar(500) DEFAULT '' COMMENT '现场备注',
  `img_url` varchar(255) DEFAULT '' COMMENT '现场照片地址（可选）',
  `device_info` varchar(200) DEFAULT '' COMMENT '手机设备标识',
  `distance_meter` decimal(10,2) DEFAULT NULL COMMENT '打卡时计算距离(米)',
  PRIMARY KEY (`id`),
  KEY `idx_point_id` (`point_id`),
  KEY `idx_user_time` (`user_id`,`check_time`),
  KEY `idx_got_record_user_checktime` (`user_id`,`check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='NFC巡检打卡记录';

-- ----------------------------
-- Table structure for got_user
-- ----------------------------
DROP TABLE IF EXISTS `got_user`;
CREATE TABLE `got_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dep_id` bigint NOT NULL COMMENT '部门ID',
  `username` varchar(50) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT '加密密码',
  `nick_name` varchar(50) DEFAULT '' COMMENT '姓名',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号',
  `role` tinyint NOT NULL DEFAULT '2' COMMENT '1管理员 2巡检员',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '0禁用 1正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除 0未删 1已删',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

SET FOREIGN_KEY_CHECKS = 1;
