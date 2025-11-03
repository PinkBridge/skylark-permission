package cn.skylark.permission.oauth2.mapper;

import cn.skylark.permission.oauth2.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser findByUsername(@Param("username") String username);
}
