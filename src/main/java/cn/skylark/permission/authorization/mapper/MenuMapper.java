package cn.skylark.permission.authorization.mapper;

import cn.skylark.permission.authorization.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
  SysMenu selectById(@Param("id") Long id);

  List<SysMenu> selectAll();

  int insert(SysMenu menu);

  int update(SysMenu menu);

  int deleteById(@Param("id") Long id);

  List<SysMenu> selectByRoleId(@Param("roleId") Long roleId);

  int deleteBindingsByRoleId(@Param("roleId") Long roleId);

  int bindRoleMenus(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

  List<SysMenu> selectMenusByUsername(@Param("username") String username);

  /**
   * 更新菜单信息
   *
   * @param menuId 菜单ID
   * @param menu   菜单信息
   * @return 更新行数
   */
  int updateMenuInfo(@Param("menuId") Long menuId, @Param("menu") cn.skylark.permission.authorization.dto.UpdateMenuDTO menu);
}
