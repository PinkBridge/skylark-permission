package cn.skylark.permission.authorization.dto;

import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * 用户DTO，包含角色信息
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Data
public class UserDTO {
  /**
   * 用户基本信息
   */
  private SysUser user;

  /**
   * 用户角色列表
   */
  private List<SysRole> roles;
}

