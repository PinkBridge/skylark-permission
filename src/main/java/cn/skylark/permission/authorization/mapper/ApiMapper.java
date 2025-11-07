package cn.skylark.permission.authorization.mapper;

import cn.skylark.permission.authorization.entity.SysApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiMapper {
  SysApi selectById(@Param("id") Long id);

  List<SysApi> selectAll();

  int insert(SysApi api);

  int update(SysApi api);

  int deleteById(@Param("id") Long id);

  List<SysApi> selectByRoleId(@Param("roleId") Long roleId);

  int deleteBindingsByRoleId(@Param("roleId") Long roleId);

  int bindRoleApis(@Param("roleId") Long roleId, @Param("apiIds") List<Long> apiIds);

  int countByRoleNamesAndApi(@Param("roleNames") List<String> roleNames,
                             @Param("method") String method,
                             @Param("path") String path,
                             @Param("permlabel") String permlabel);
}
