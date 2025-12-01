-- 初始化用户表的租户ID字段
-- 将所有现有用户的租户ID设置为101

-- 如果字段不存在，先添加字段
-- ALTER TABLE `sys_user` 
-- ADD COLUMN `tenant_id` BIGINT(20) DEFAULT NULL COMMENT '租户ID' AFTER `address`,
-- ADD KEY `idx_tenant_id` (`tenant_id`);

-- 初始化现有数据的租户ID为101
UPDATE `sys_user` SET `tenant_id` = 101 WHERE `tenant_id` IS NULL;

