-- 用户表测试数据（15条）
-- 注意：如果表中已存在数据，请先清空或调整ID起始值

INSERT INTO `sys_user` (`username`, `password`, `enabled`, `gender`, `avatar`, `phone`, `email`, `status`, `province`, `city`, `address`) VALUES
('admin', '123456', 1, 'M', 'https://example.com/avatars/admin.jpg', '13800000001', 'admin@example.com', 'ACTIVE', '北京市', '北京市', '朝阳区建国路88号'),
('yunai', '123456', 1, 'M', 'https://example.com/avatars/yunai.jpg', '13800000002', 'yunai@example.com', 'ACTIVE', '上海市', '上海市', '浦东新区陆家嘴环路1000号'),
('zhangsan', '123456', 1, 'M', 'https://example.com/avatars/zhangsan.jpg', '13900000003', 'zhangsan@example.com', 'ACTIVE', '广东省', '广州市', '天河区天河路123号'),
('lisi', '123456', 1, 'F', 'https://example.com/avatars/lisi.jpg', '15000000004', 'lisi@example.com', 'ACTIVE', '浙江省', '杭州市', '西湖区文三路456号'),
('wangwu', '123456', 1, 'M', 'https://example.com/avatars/wangwu.jpg', '15100000005', 'wangwu@example.com', 'ACTIVE', '江苏省', '南京市', '鼓楼区中山路789号'),
('zhaoliu', '123456', 1, 'F', 'https://example.com/avatars/zhaoliu.jpg', '15200000006', 'zhaoliu@example.com', 'INACTIVE', '四川省', '成都市', '锦江区春熙路321号'),
('sunqi', '123456', 1, 'M', 'https://example.com/avatars/sunqi.jpg', '15300000007', 'sunqi@example.com', 'ACTIVE', '湖北省', '武汉市', '江汉区解放大道654号'),
('zhouba', '123456', 1, 'F', 'https://example.com/avatars/zhouba.jpg', '15400000008', 'zhouba@example.com', 'ACTIVE', '陕西省', '西安市', '雁塔区高新路987号'),
('wujiu', '123456', 1, 'M', 'https://example.com/avatars/wujiu.jpg', '15500000009', 'wujiu@example.com', 'LOCKED', '山东省', '济南市', '历下区泉城路147号'),
('zhengshi', '123456', 1, 'F', 'https://example.com/avatars/zhengshi.jpg', '15600000010', 'zhengshi@example.com', 'ACTIVE', '河南省', '郑州市', '金水区花园路258号'),
('test001', '123456', 1, 'M', NULL, '15700000011', 'test001@example.com', 'ACTIVE', '湖南省', '长沙市', '岳麓区麓山南路369号'),
('test002', '123456', 0, 'F', NULL, '15800000012', 'test002@example.com', 'INACTIVE', '福建省', '福州市', '鼓楼区五四路741号'),
('test003', '123456', 1, 'M', 'https://example.com/avatars/test003.jpg', '15900000013', 'test003@example.com', 'ACTIVE', '安徽省', '合肥市', '庐阳区长江中路852号'),
('test004', '123456', 1, 'F', NULL, '18000000014', 'test004@example.com', 'ACTIVE', '辽宁省', '沈阳市', '和平区中山路963号'),
('test005', '123456', 1, 'M', 'https://example.com/avatars/test005.jpg', '18100000015', 'test005@example.com', 'ACTIVE', '吉林省', '长春市', '朝阳区人民大街159号');

