package cn.skylark.permission.authorization.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色响应DTO
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Data
public class RoleResponseDTO {
  /**
   * 角色ID
   */
  private Long id;

  /**
   * 角色名称
   */
  private String name;

  /**
   * 备注说明
   */
  private String remark;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  /**
   * 关联的API ID数组
   */
  private List<Long> apiIds;

  /**
   * 关联的菜单ID数组
   */
  private List<Long> menuIds;
}

