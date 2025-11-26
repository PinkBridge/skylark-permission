-- OAuth2 数据库表结构

-- 1. OAuth2 客户端详情表
CREATE TABLE IF NOT EXISTS `oauth_client_details` (
  `client_id` VARCHAR(256) NOT NULL COMMENT '客户端ID',
  `resource_ids` VARCHAR(256) DEFAULT NULL COMMENT '资源ID列表',
  `client_secret` VARCHAR(256) DEFAULT NULL COMMENT '客户端密钥',
  `scope` VARCHAR(256) DEFAULT NULL COMMENT '作用域',
  `authorized_grant_types` VARCHAR(256) DEFAULT NULL COMMENT '授权模式',
  `web_server_redirect_uri` VARCHAR(256) DEFAULT NULL COMMENT '重定向URI',
  `authorities` VARCHAR(256) DEFAULT NULL COMMENT '权限',
  `access_token_validity` INT(11) DEFAULT NULL COMMENT '访问令牌有效期（秒）',
  `refresh_token_validity` INT(11) DEFAULT NULL COMMENT '刷新令牌有效期（秒）',
  `additional_information` VARCHAR(4096) DEFAULT NULL COMMENT '附加信息',
  `autoapprove` VARCHAR(256) DEFAULT NULL COMMENT '自动授权',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2客户端详情表';

-- 2. OAuth2 授权码表（用于 authorization_code 模式）
CREATE TABLE IF NOT EXISTS `oauth_code` (
  `code` VARCHAR(256) NOT NULL COMMENT '授权码',
  `authentication` BLOB COMMENT '认证信息',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2授权码表';

-- 3. OAuth2 访问令牌表（如果使用数据库存储 token，当前使用 JWT 不需要）
-- CREATE TABLE IF NOT EXISTS `oauth_access_token` (
--   `token_id` VARCHAR(256) DEFAULT NULL,
--   `token` BLOB,
--   `authentication_id` VARCHAR(256) DEFAULT NULL,
--   `user_name` VARCHAR(256) DEFAULT NULL,
--   `client_id` VARCHAR(256) DEFAULT NULL,
--   `authentication` BLOB,
--   `refresh_token` VARCHAR(256) DEFAULT NULL,
--   KEY `authentication_id` (`authentication_id`),
--   KEY `user_name` (`user_name`),
--   KEY `client_id` (`client_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2访问令牌表';

-- 4. OAuth2 刷新令牌表（refresh token 需要存储在数据库中，即使 access token 使用 JWT）
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `token_id` VARCHAR(256) DEFAULT NULL COMMENT 'Token ID',
  `token` BLOB COMMENT 'Refresh Token 序列化数据',
  `authentication` BLOB COMMENT '认证信息',
  KEY `token_id` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2刷新令牌表';

-- 5. OAuth2 用户授权表（可选，用于记录用户的授权历史）
CREATE TABLE IF NOT EXISTS `oauth_approvals` (
  `userId` VARCHAR(256) DEFAULT NULL COMMENT '用户ID',
  `clientId` VARCHAR(256) DEFAULT NULL COMMENT '客户端ID',
  `scope` VARCHAR(256) DEFAULT NULL COMMENT '作用域',
  `status` VARCHAR(10) DEFAULT NULL COMMENT '状态',
  `expiresAt` DATETIME DEFAULT NULL COMMENT '过期时间',
  `lastModifiedAt` DATETIME DEFAULT NULL COMMENT '最后修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2用户授权表';

-- 插入示例数据（对应之前的配置）
-- 注意：需要在 authorized_grant_types 中包含 refresh_token 才能支持刷新令牌
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `scope`, `authorized_grant_types`, `access_token_validity`, `refresh_token_validity`, `autoapprove`) 
VALUES 
('clientapp', '112233', 'read_userinfo,read_contacts', 'password,refresh_token', 3600, 86400, NULL),
('app2', '112233', 'all', 'authorization_code,refresh_token', 3600, 86400, 'false') 
ON DUPLICATE KEY UPDATE 
  `client_secret` = VALUES(`client_secret`),
  `scope` = VALUES(`scope`),
  `authorized_grant_types` = VALUES(`authorized_grant_types`),
  `access_token_validity` = VALUES(`access_token_validity`),
  `refresh_token_validity` = VALUES(`refresh_token_validity`),
  `autoapprove` = VALUES(`autoapprove`);

INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `scope`, `authorized_grant_types`, `access_token_validity`, `web_server_redirect_uri`) 
VALUES ('app3', '112233', 'read,write', 'client_credentials', 3600, NULL)
ON DUPLICATE KEY UPDATE 
  `client_secret` = VALUES(`client_secret`),
  `scope` = VALUES(`scope`),
  `authorized_grant_types` = VALUES(`authorized_grant_types`),
  `access_token_validity` = VALUES(`access_token_validity`);

-- 为 app2 设置重定向 URI
UPDATE `oauth_client_details` SET `web_server_redirect_uri` = 'http://www.baidu.com' WHERE `client_id` = 'app2';

-- 6. 用户表（简单版本，不需要角色权限）
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `enabled` TINYINT(1) DEFAULT 1 COMMENT '是否启用：1-启用，0-禁用',
  `gender` VARCHAR(1) DEFAULT NULL COMMENT '性别：M-男，F-女',
  `avatar` VARCHAR(500) DEFAULT NULL COMMENT '照片URL',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱',
  `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-活跃，INACTIVE-非活跃，LOCKED-锁定',
  `province` VARCHAR(50) DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(50) DEFAULT NULL COMMENT '城市',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '详细地址',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_phone` (`phone`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 为已存在的表添加新字段（如果表已存在，需要手动执行或使用存储过程检查）
-- 注意：MySQL不支持IF NOT EXISTS，如果字段已存在会报错，需要手动检查或使用存储过程
-- 以下语句需要根据实际情况执行，如果字段已存在请注释掉对应行
-- ALTER TABLE `sys_user` 
--   ADD COLUMN `gender` VARCHAR(1) DEFAULT NULL COMMENT '性别：M-男，F-女' AFTER `enabled`,
--   ADD COLUMN `avatar` VARCHAR(500) DEFAULT NULL COMMENT '照片URL' AFTER `gender`,
--   ADD COLUMN `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号码' AFTER `avatar`,
--   ADD COLUMN `email` VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱' AFTER `phone`,
--   ADD COLUMN `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-活跃，INACTIVE-非活跃，LOCKED-锁定' AFTER `email`,
--   ADD COLUMN `province` VARCHAR(50) DEFAULT NULL COMMENT '省份' AFTER `status`,
--   ADD COLUMN `city` VARCHAR(50) DEFAULT NULL COMMENT '城市' AFTER `province`,
--   ADD COLUMN `address` VARCHAR(255) DEFAULT NULL COMMENT '详细地址' AFTER `city`;

-- 插入示例用户（密码为：1024，使用 NoOpPasswordEncoder 所以存储明文）
INSERT INTO `sys_user` (`username`, `password`, `enabled`) VALUES 
('yunai', '1024', 1)
ON DUPLICATE KEY UPDATE 
  `password` = VALUES(`password`),
  `enabled` = VALUES(`enabled`);


-- RBAC tables
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `parent_id` BIGINT DEFAULT NULL,
  `name` VARCHAR(100) NOT NULL,
  `path` VARCHAR(200) DEFAULT NULL,
  `icon` VARCHAR(100) DEFAULT NULL,
  `sort` INT DEFAULT 0,
  `hidden` TINYINT(1) DEFAULT 0,
  `module_key` VARCHAR(100) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_parent` (`parent_id`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单';

CREATE TABLE IF NOT EXISTS `sys_api` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `method` VARCHAR(10) NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  `permlabel` VARCHAR(100) NOT NULL,
  `module_key` VARCHAR(100) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_api` (`method`,`path`),
  KEY `idx_permlabel` (`permlabel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='API 资源';

CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `remark` VARCHAR(255) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色';

CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联';

CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT NOT NULL,
  `menu_id` BIGINT NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-菜单关联';

CREATE TABLE IF NOT EXISTS `sys_role_api` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT NOT NULL,
  `api_id` BIGINT NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_api` (`role_id`,`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-API关联';

CREATE TABLE IF NOT EXISTS `sys_whitelist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `method` VARCHAR(10) NOT NULL COMMENT 'HTTP方法：GET, POST, PUT, DELETE等，支持ALL表示所有方法',
  `path` VARCHAR(255) NOT NULL COMMENT 'API路径，支持Ant风格通配符（如：/api/public/**）',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注说明',
  `enabled` TINYINT(1) DEFAULT 1 COMMENT '是否启用：1-启用，0-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_method_path` (`method`, `path`),
  KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统白名单表';

-- seed menus
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `icon`, `sort`, `hidden`, `module_key`) VALUES
  (1, NULL, '控制台', '/dashboard', 'dashboard', 10, 0, 'console'),
  (2, NULL, '系统管理', '/system', 'setting', 20, 0, 'system'),
  (3, 2, '用户管理', '/system/users', 'user', 10, 0, 'system.user'),
  (4, 2, '角色管理', '/system/roles', 'team', 20, 0, 'system.role'),
  (5, 2, '菜单管理', '/system/menus', 'menu', 30, 0, 'system.menu')
ON DUPLICATE KEY UPDATE name=VALUES(name), path=VALUES(path), icon=VALUES(icon), sort=VALUES(sort), hidden=VALUES(hidden), module_key=VALUES(module_key);

-- seed apis
INSERT INTO `sys_api`(`method`,`path`,`permlabel`,`module_key`) VALUES
 ('GET','/api/authorization/menus','menu.read','system.menu'),
 ('POST','/api/authorization/menus','menu.create','system.menu'),
 ('PUT','/api/authorization/menus/{id}','menu.update','system.menu'),
 ('DELETE','/api/authorization/menus/{id}','menu.delete','system.menu'),
 ('GET','/api/authorization/apis','api.read','system.api'),
 ('POST','/api/authorization/apis','api.create','system.api'),
 ('PUT','/api/authorization/apis/{id}','api.update','system.api'),
 ('DELETE','/api/authorization/apis/{id}','api.delete','system.api'),
 ('GET','/api/authorization/roles','role.read','system.role'),
 ('POST','/api/authorization/roles','role.create','system.role'),
 ('PUT','/api/authorization/roles/{id}','role.update','system.role'),
 ('DELETE','/api/authorization/roles/{id}','role.delete','system.role')
ON DUPLICATE KEY UPDATE permlabel=VALUES(permlabel), module_key=VALUES(module_key);

-- seed roles
INSERT INTO `sys_role`(`name`,`remark`) VALUES
 ('ROLE_ADMIN','管理员'),
 ('ROLE_USER','普通用户')
ON DUPLICATE KEY UPDATE remark=VALUES(remark);

-- bind admin role all menus
INSERT INTO `sys_role_menu`(`role_id`,`menu_id`)
SELECT r.id, m.id FROM sys_role r, sys_menu m WHERE r.name='ROLE_ADMIN'
ON DUPLICATE KEY UPDATE role_id=role_id;

-- bind admin role all apis
INSERT INTO `sys_role_api`(`role_id`,`api_id`)
SELECT r.id, a.id FROM sys_role r, sys_api a WHERE r.name='ROLE_ADMIN'
ON DUPLICATE KEY UPDATE role_id=role_id;

-- bind user yunai to admin if exists
INSERT INTO `sys_user_role`(`user_id`,`role_id`)
SELECT u.id, r.id FROM sys_user u, sys_role r WHERE u.username='yunai' AND r.name='ROLE_ADMIN'
ON DUPLICATE KEY UPDATE user_id=user_id;

-- seed whitelist (示例数据)
INSERT INTO `sys_whitelist`(`method`,`path`,`remark`,`enabled`) VALUES
('ALL','/oauth/**','OAuth2认证相关接口',1),
('ALL','/error','错误处理接口',1),
('GET','/api/public/**','公共API接口',1)
ON DUPLICATE KEY UPDATE remark=VALUES(remark), enabled=VALUES(enabled);

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

ALTER TABLE `oauth_client_details` 
ADD COLUMN `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
ADD COLUMN `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';



