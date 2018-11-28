CREATE TABLE `xxw_user_info` (
  `created_by` varchar(50) NOT NULL COMMENT '创建人',
  `created_date` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(50) NOT NULL COMMENT '修改人',
  `updated_date` datetime NOT NULL COMMENT '修改时间',
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表主键',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `user_name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `password` varchar(200) NOT NULL COMMENT '密码',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `user_account` (`user_account`)
) ENGINE=InnoDB AUTO_INCREMENT=102466 DEFAULT CHARSET=utf8 COMMENT='用户信息';

