package cn.skylark.permission.authorization.dto;

import lombok.Data;

/**
 * 更新菜单信息DTO
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Data
public class UpdateMenuDTO {
  /**
   * 父菜单ID
   */
  private Long parentId;

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
   * 排序
   */
  private Integer sort;

  /**
   * 是否隐藏：true-隐藏，false-显示
   */
  private Boolean hidden;

  /**
   * 模块标识
   */
  private String moduleKey;
}

