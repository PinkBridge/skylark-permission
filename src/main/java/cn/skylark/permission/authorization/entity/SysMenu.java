package cn.skylark.permission.authorization.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysMenu {
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String icon;
    private Integer sort;
    private Boolean hidden;
    private String type;
    private String permlabel;
    private String moduleKey;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

