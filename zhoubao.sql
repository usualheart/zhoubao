# Host: 127.0.0.1  (Version: 5.7.27-log)
# Date: 2020-06-24 16:23:01
# Generator: MySQL-Front 5.3  (Build 4.269)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "detailitem"
#

CREATE TABLE `detailitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) DEFAULT NULL,
  `head` varchar(200) DEFAULT NULL COMMENT '详情标题',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `imgSrc` varchar(200) DEFAULT NULL COMMENT '图片路径',
  `time` datetime DEFAULT NULL COMMENT '日期',
  `week` int(11) DEFAULT NULL COMMENT '周',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8;

#
# Structure for table "img"
#

CREATE TABLE `img` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `time` datetime DEFAULT NULL COMMENT '日期',
  `week` int(11) NOT NULL DEFAULT '0' COMMENT '周',
  PRIMARY KEY (`id`,`week`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Structure for table "sectionitem"
#

CREATE TABLE `sectionitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) DEFAULT NULL,
  `no` int(11) DEFAULT NULL COMMENT '序号',
  `head` varchar(500) DEFAULT NULL COMMENT '表格内容',
  `class` varchar(30) DEFAULT NULL COMMENT '类',
  `detailid` varchar(30) DEFAULT NULL COMMENT '详情ID',
  `time` datetime DEFAULT NULL COMMENT '日期',
  `week` int(11) DEFAULT NULL COMMENT '周',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1429 DEFAULT CHARSET=utf8;

#
# Structure for table "summaryitem"
#

CREATE TABLE `summaryitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `time` datetime DEFAULT NULL COMMENT '日期',
  `week` int(11) NOT NULL DEFAULT '0' COMMENT '周',
  PRIMARY KEY (`id`,`week`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

#
# Structure for table "user"
#

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) DEFAULT NULL,
  `userPwd` varchar(30) DEFAULT NULL,
  `type` int(10) DEFAULT '0' COMMENT '用户类型',
  `loginTime` datetime DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `year` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;


#
# Structure for table "zhoubao"
#

CREATE TABLE `zhoubao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) DEFAULT NULL,
  `filePath` varchar(200) DEFAULT NULL COMMENT '周报路径',
  `time` datetime DEFAULT NULL COMMENT '日期',
  `week` int(11) DEFAULT '0',
  `pingjia` varchar(500) DEFAULT NULL COMMENT '评价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=288 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
