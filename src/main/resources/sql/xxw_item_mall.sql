CREATE TABLE `xxw_item_mall` (
  `created_by` varchar(50) NOT NULL COMMENT '创建人',
  `created_date` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(50) NOT NULL COMMENT '修改人',
  `updated_date` datetime NOT NULL COMMENT '修改时间',
  `item_mall_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '道具id',
  `prop_name` varchar(50) NOT NULL COMMENT '道具名称',
  `prop_type` varchar(200) NOT NULL COMMENT '道具类型',
  `prop_image_url` varchar(200) NOT NULL COMMENT '道具图片地址',
  `prop_description` varchar(200) NOT NULL COMMENT '道具描述',
  `order` int(3) NOT NULL COMMENT '道具排序',
  PRIMARY KEY (`item_mall_id`),
  UNIQUE KEY `item_mall_id` (`item_mall_id`),
  UNIQUE KEY `prop_name` (`prop_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='道具商城表';

