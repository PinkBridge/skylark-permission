package cn.skylark.permission.authorization.dto;

import lombok.Data;

/**
 * 父菜单信息DTO
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Data
public class ParentMenuDTO {
  /**
   * 菜单ID
   */
  private Long id;

  /**
   * 菜单名称
   */
  private String name;

  /**
   * 菜单路径
   */
  private String path;

  /**
   * 菜单图标
   */
  private String icon;

  /**
   * 模块标识
   */
  private String moduleKey;
}

