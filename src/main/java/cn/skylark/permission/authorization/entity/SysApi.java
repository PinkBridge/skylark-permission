package cn.skylark.permission.authorization.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysApi {
  private Long id;
  private String method; // GET, POST, ...
  private String path;
  private String permlabel; // permission label
  private String moduleKey;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
