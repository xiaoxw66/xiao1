CREATE TABLE `xxw_money_info` (
  `created_by` varchar(50) NOT NULL COMMENT '创建人',
  `created_date` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(50) NOT NULL COMMENT '修改人',
  `updated_date` datetime NOT NULL COMMENT '修改时间',
  `xxw_money_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '金钱表主键',
  `user_id` int(11) NOT NULL COMMENT '用户表主键',
  `gold_ingot` int(8) NOT NULL COMMENT '金元宝',
  `silver_ingot` int(8) NOT NULL COMMENT '银元宝',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`xxw_money_info_id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT= 0 DEFAULT CHARSET=utf8 COMMENT='金钱信息';

