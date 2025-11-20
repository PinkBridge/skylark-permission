package cn.skylark.permission.authorization.mapper;

import cn.skylark.permission.authorization.entity.SysWhitelist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 白名单 Mapper
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Mapper
public interface WhitelistMapper {

  /**
   * 根据ID查询
   */
  SysWhitelist selectById(@Param("id") Long id);

  /**
   * 查询所有
   */
  List<SysWhitelist> selectAll();

  /**
   * 查询所有启用的白名单
   */
  List<SysWhitelist> selectEnabled();

  /**
   * 新增
   */
  int insert(SysWhitelist whitelist);

  /**
   * 更新
   */
  int update(SysWhitelist whitelist);

  /**
   * 删除
   */
  int deleteById(@Param("id") Long id);
}

