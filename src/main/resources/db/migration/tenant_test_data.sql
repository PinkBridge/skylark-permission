-- 租户表测试数据（20条）
INSERT INTO `sys_tenant` (
  `name`, `system_name`, `code`, `contact_name`, `contact_phone`, `contact_email`, 
  `domain`, `logo`, `address`, `description`, `status`, `expire_time`
) VALUES
('北京科技有限公司', 'BJTech管理系统', 'TENANT-BJ-TECH', '张总', '010-88888888', 'zhang@bjtech.com', 
 'www.bjtech.com', 'https://example.com/logo/bjtech.png', '北京市海淀区中关村大街1号', 
 '专注于企业级软件开发', 'ACTIVE', '2026-12-31 23:59:59'),

('上海贸易集团', 'ShanghaiTrade平台', 'TENANT-SH-TRADE', '李经理', '021-66666666', 'li@shanghaitrade.com', 
 'www.shanghaitrade.com', 'https://example.com/logo/shtrade.png', '上海市浦东新区世纪大道100号', 
 '大型贸易企业，业务覆盖全国', 'ACTIVE', '2027-06-30 23:59:59'),

('深圳创新科技', 'SZInnov智能平台', 'TENANT-SZ-INNOV', '王总', '0755-55555555', 'wang@szinnov.com', 
 'www.szinnov.com', 'https://example.com/logo/szinnov.png', '深圳市南山区科技园南路2号', 
 '科技创新型企业，专注于AI和物联网', 'ACTIVE', '2026-09-30 23:59:59'),

('广州教育集团', 'GZEducation在线教育', 'TENANT-GZ-EDU', '陈校长', '020-77777777', 'chen@gzedu.com', 
 'www.gzedu.com', 'https://example.com/logo/gzedu.png', '广州市天河区天河路200号', 
 '专业教育机构，提供在线教育服务', 'ACTIVE', '2027-03-31 23:59:59'),

('杭州电商平台', 'HZEcomm商城系统', 'TENANT-HZ-ECOMM', '刘总', '0571-44444444', 'liu@hzecomm.com', 
 'www.hzecomm.com', 'https://example.com/logo/hzecomm.png', '杭州市西湖区文三路300号', 
 '大型电商平台，服务千万用户', 'ACTIVE', '2026-11-30 23:59:59'),

('成都餐饮连锁', 'CDFood餐饮管理', 'TENANT-CD-FOOD', '赵经理', '028-33333333', 'zhao@cdfood.com', 
 'www.cdfood.com', 'https://example.com/logo/cdfood.png', '成都市锦江区春熙路400号', 
 '知名餐饮连锁品牌，全国500+门店', 'ACTIVE', '2027-01-31 23:59:59'),

('南京物流公司', 'NJLogist物流系统', 'TENANT-NJ-LOGIST', '孙总', '025-22222222', 'sun@njlogist.com', 
 'www.njlogist.com', 'https://example.com/logo/njlogist.png', '南京市鼓楼区中山路500号', 
 '专业物流服务，覆盖华东地区', 'ACTIVE', '2026-08-31 23:59:59'),

('武汉医疗科技', 'WHMed医疗平台', 'TENANT-WH-MED', '周总', '027-11111111', 'zhou@whmed.com', 
 'www.whmed.com', 'https://example.com/logo/whmed.png', '武汉市洪山区光谷大道600号', 
 '医疗信息化解决方案提供商', 'ACTIVE', '2027-05-31 23:59:59'),

('西安旅游服务', 'XATour旅游平台', 'TENANT-XA-TOUR', '吴经理', '029-99999999', 'wu@xatour.com', 
 'www.xatour.com', 'https://example.com/logo/xatour.png', '西安市雁塔区小寨路700号', 
 '专业旅游服务平台，提供一站式服务', 'ACTIVE', '2026-10-31 23:59:59'),

('天津制造企业', 'TJManuf生产管理', 'TENANT-TJ-MANUF', '郑总', '022-88888888', 'zheng@tjmanuf.com', 
 'www.tjmanuf.com', 'https://example.com/logo/tjmanuf.png', '天津市滨海新区开发区800号', 
 '大型制造企业，生产各类工业产品', 'ACTIVE', '2027-02-28 23:59:59'),

('重庆金融服务', 'CQFin金融平台', 'TENANT-CQ-FIN', '冯总', '023-77777777', 'feng@cqfin.com', 
 'www.cqfin.com', 'https://example.com/logo/cqfin.png', '重庆市渝中区解放碑900号', 
 '金融服务公司，提供理财和投资服务', 'ACTIVE', '2026-07-31 23:59:59'),

('苏州智能制造', 'SZSmart智造系统', 'TENANT-SZ-SMART', '何总', '0512-66666666', 'he@szsmart.com', 
 'www.szsmart.com', 'https://example.com/logo/szsmart.png', '苏州市工业园区星海街1000号', 
 '智能制造解决方案提供商', 'ACTIVE', '2027-04-30 23:59:59'),

('长沙文化传媒', 'CSMedia内容平台', 'TENANT-CS-MEDIA', '罗总', '0731-55555555', 'luo@csmedia.com', 
 'www.csmedia.com', 'https://example.com/logo/csmedia.png', '长沙市岳麓区麓山南路1100号', 
 '文化传媒公司，专注于内容创作', 'ACTIVE', '2026-12-31 23:59:59'),

('郑州零售连锁', 'ZZRetail零售系统', 'TENANT-ZZ-RETAIL', '高总', '0371-44444444', 'gao@zzretail.com', 
 'www.zzretail.com', 'https://example.com/logo/zzretail.png', '郑州市金水区花园路1200号', 
 '大型零售连锁企业，覆盖河南省', 'ACTIVE', '2027-08-31 23:59:59'),

('济南房地产', 'JNReal房产管理', 'TENANT-JN-REAL', '林总', '0531-33333333', 'lin@jnreal.com', 
 'www.jnreal.com', 'https://example.com/logo/jnreal.png', '济南市历下区经十路1300号', 
 '房地产开发和物业管理公司', 'ACTIVE', '2026-09-30 23:59:59'),

('大连港口物流', 'DLPort港口系统', 'TENANT-DL-PORT', '黄总', '0411-22222222', 'huang@dlport.com', 
 'www.dlport.com', 'https://example.com/logo/dlport.png', '大连市中山区人民路1400号', 
 '港口物流服务，连接国内外贸易', 'ACTIVE', '2027-07-31 23:59:59'),

('青岛海洋科技', 'QDOcean海洋平台', 'TENANT-QD-OCEAN', '徐总', '0532-11111111', 'xu@qdocean.com', 
 'www.qdocean.com', 'https://example.com/logo/qdocean.png', '青岛市市南区香港中路1500号', 
 '海洋科技研发，专注于海洋资源开发', 'ACTIVE', '2026-11-30 23:59:59'),

('厦门互联网', 'XMInter SaaS平台', 'TENANT-XM-INTER', '马总', '0592-99999999', 'ma@xminter.com', 
 'www.xminter.com', 'https://example.com/logo/xminter.png', '厦门市思明区软件园1600号', 
 '互联网科技公司，提供SaaS服务', 'ACTIVE', '2027-03-31 23:59:59'),

('福州农业科技', 'FZAgri智慧农业', 'TENANT-FZ-AGRI', '谢总', '0591-88888888', 'xie@fzagri.com', 
 'www.fzagri.com', 'https://example.com/logo/fzagri.png', '福州市鼓楼区五四路1700号', 
 '农业科技公司，提供智慧农业解决方案', 'INACTIVE', '2025-12-31 23:59:59'),

('石家庄能源公司', 'SJZEnerg能源管理', 'TENANT-SJZ-ENER', '唐总', '0311-77777777', 'tang@sjzener.com', 
 'www.sjzener.com', 'https://example.com/logo/sjzener.png', '石家庄市长安区中山东路1800号', 
 '能源服务公司，提供清洁能源解决方案', 'ACTIVE', '2027-09-30 23:59:59');

