package cn.skylark.permission.authentication.mapper;

import cn.skylark.permission.authentication.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User Mapper
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Mapper
public interface UserMapper {

    /**
     * find user by username
     *
     * @param username username
     * @return user information
     */
    SysUser findByUsername(@Param("username") String username);
}
