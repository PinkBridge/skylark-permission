package cn.skylark.permission.authorization.mapper;

import cn.skylark.permission.authorization.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
  SysRole selectById(@Param("id") Long id);

  List<SysRole> selectAll();

  int insert(SysRole role);

  int update(SysRole role);

  int deleteById(@Param("id") Long id);
}
