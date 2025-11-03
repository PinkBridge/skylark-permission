package cn.skylark.permission.oauth2.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户实体
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Data
public class SysUser {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否启用：1-启用，0-禁用
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
