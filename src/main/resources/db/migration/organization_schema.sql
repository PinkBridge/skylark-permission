-- 系统组织表
CREATE TABLE IF NOT EXISTS `sys_organization` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `name` VARCHAR(100) NOT NULL COMMENT '组织名称',
  `code` VARCHAR(50) NOT NULL COMMENT '组织编码（唯一）',
  `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父组织ID',
  `type` VARCHAR(20) DEFAULT 'DEPARTMENT' COMMENT '组织类型：COMPANY-公司，DEPARTMENT-部门，TEAM-小组',
  `level` INT(11) DEFAULT 1 COMMENT '层级',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `leader` VARCHAR(50) DEFAULT NULL COMMENT '负责人',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '地址',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '描述',
  `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-启用，INACTIVE-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_name` (`name`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统组织表';

