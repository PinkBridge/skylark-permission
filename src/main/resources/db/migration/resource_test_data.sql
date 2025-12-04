-- 资源表测试数据（20条）
INSERT INTO `sys_resource` (
  `name`, `original_name`, `file_path`, `file_type`, `file_size`, `mime_type`, `url`, `description`, `tenant_id`
) VALUES
('产品宣传图1', 'product-banner-1.jpg', './uploads/tenant_101/2025/11/03/uuid-001.jpg', 'IMAGE', 245760, 'image/jpeg', '/api/permission/resources/download/1', '产品宣传横幅图片', 101),

('用户手册', 'user-manual.pdf', './uploads/tenant_101/2025/11/03/uuid-002.pdf', 'DOCUMENT', 1048576, 'application/pdf', '/api/permission/resources/download/2', '系统用户使用手册', 101),

('公司Logo', 'company-logo.png', './uploads/tenant_101/2025/11/03/uuid-003.png', 'IMAGE', 153600, 'image/png', '/api/permission/resources/download/3', '公司品牌标识', 101),

('产品演示视频', 'product-demo.mp4', './uploads/tenant_101/2025/11/03/uuid-004.mp4', 'VIDEO', 52428800, 'video/mp4', '/api/permission/resources/download/4', '产品功能演示视频', 101),

('背景音乐', 'background-music.mp3', './uploads/tenant_101/2025/11/03/uuid-005.mp3', 'AUDIO', 3145728, 'audio/mpeg', '/api/permission/resources/download/5', '系统背景音乐', 101),

('数据报表模板', 'report-template.xlsx', './uploads/tenant_101/2025/11/03/uuid-006.xlsx', 'DOCUMENT', 512000, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '/api/permission/resources/download/6', '月度数据报表模板', 101),

('团队合影', 'team-photo.jpg', './uploads/tenant_101/2025/11/03/uuid-007.jpg', 'IMAGE', 1024000, 'image/jpeg', '/api/permission/resources/download/7', '2025年度团队合影', 101),

('技术文档', 'technical-doc.docx', './uploads/tenant_101/2025/11/03/uuid-008.docx', 'DOCUMENT', 768000, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', '/api/permission/resources/download/8', '系统技术架构文档', 101),

('产品介绍视频', 'product-intro.mp4', './uploads/tenant_101/2025/11/03/uuid-009.mp4', 'VIDEO', 31457280, 'video/mp4', '/api/permission/resources/download/9', '产品功能介绍视频', 101),

('通知铃声', 'notification-sound.wav', './uploads/tenant_101/2025/11/03/uuid-010.wav', 'AUDIO', 512000, 'audio/wav', '/api/permission/resources/download/10', '系统通知提示音', 101),

('产品图标', 'app-icon.png', './uploads/tenant_101/2025/11/03/uuid-011.png', 'IMAGE', 81920, 'image/png', '/api/permission/resources/download/11', '应用程序图标', 101),

('培训材料', 'training-material.pptx', './uploads/tenant_101/2025/11/03/uuid-012.pptx', 'DOCUMENT', 2048000, 'application/vnd.openxmlformats-officedocument.presentationml.presentation', '/api/permission/resources/download/12', '新员工培训PPT', 101),

('宣传海报', 'promotion-poster.jpg', './uploads/tenant_101/2025/11/03/uuid-013.jpg', 'IMAGE', 1536000, 'image/jpeg', '/api/permission/resources/download/13', '双十一促销活动海报', 101),

('操作指南视频', 'operation-guide.mp4', './uploads/tenant_101/2025/11/03/uuid-014.mp4', 'VIDEO', 41943040, 'video/mp4', '/api/permission/resources/download/14', '系统操作指南视频教程', 101),

('系统提示音', 'system-beep.mp3', './uploads/tenant_101/2025/11/03/uuid-015.mp3', 'AUDIO', 256000, 'audio/mpeg', '/api/permission/resources/download/15', '系统操作提示音', 101),

('数据备份文件', 'backup-data.zip', './uploads/tenant_101/2025/11/03/uuid-016.zip', 'OTHER', 10485760, 'application/zip', '/api/permission/resources/download/16', '2025年11月数据备份', 101),

('产品截图', 'product-screenshot.png', './uploads/tenant_101/2025/11/03/uuid-017.png', 'IMAGE', 307200, 'image/png', '/api/permission/resources/download/17', '产品界面截图', 101),

('合同模板', 'contract-template.doc', './uploads/tenant_101/2025/11/03/uuid-018.doc', 'DOCUMENT', 384000, 'application/msword', '/api/permission/resources/download/18', '标准服务合同模板', 101),

('宣传短片', 'promo-video.mp4', './uploads/tenant_101/2025/11/03/uuid-019.mp4', 'VIDEO', 62914560, 'video/mp4', '/api/permission/resources/download/19', '公司宣传短片', 101),

('欢迎音乐', 'welcome-music.mp3', './uploads/tenant_101/2025/11/03/uuid-020.mp3', 'AUDIO', 2048000, 'audio/mpeg', '/api/permission/resources/download/20', '系统欢迎页面背景音乐', 101);

