package cn.skylark.permission.authorization.mapper;

import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User Mapper
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Mapper
public interface UserMapper {

  /**
   * find user by username
   *
   * @param username username
   * @return user information
   */
  SysUser findByUsername(@Param("username") String username);

  SysUser selectById(@Param("id") Long id);

  List<SysUser> selectAll();

  int insert(SysUser user);

  int update(SysUser user);

  int deleteById(@Param("id") Long id);

  List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

  void deleteUserRoles(@Param("userId") Long userId);

  void insertUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

  /**
   * 更新用户密码
   *
   * @param userId   用户ID
   * @param password 新密码
   * @return 更新行数
   */
  int updatePassword(@Param("userId") Long userId, @Param("password") String password);
}
