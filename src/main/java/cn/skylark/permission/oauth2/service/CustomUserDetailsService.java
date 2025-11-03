package cn.skylark.permission.oauth2.service;

import cn.skylark.permission.oauth2.entity.SysUser;
import cn.skylark.permission.oauth2.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 自定义 UserDetailsService，从数据库加载用户信息
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.findByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 简化版本：所有用户都有 ROLE_USER 权限
        // 如果不需要权限控制，可以传入空集合
        return User.builder()
                .username(sysUser.getUsername())
                .password(sysUser.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!sysUser.getEnabled())
                .build();
    }
}
