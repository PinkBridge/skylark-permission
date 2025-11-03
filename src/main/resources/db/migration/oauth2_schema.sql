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
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 插入示例用户（密码为：1024，使用 NoOpPasswordEncoder 所以存储明文）
INSERT INTO `sys_user` (`username`, `password`, `enabled`) VALUES 
('yunai', '1024', 1)
ON DUPLICATE KEY UPDATE 
  `password` = VALUES(`password`),
  `enabled` = VALUES(`enabled`);

