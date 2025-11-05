package cn.skylark.permission.authentication.service;

import cn.skylark.permission.authentication.entity.SysUser;
import cn.skylark.permission.authentication.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Custom UserDetailsService, load user information from database
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

  @Resource
  private UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SysUser sysUser = userMapper.findByUsername(username);
    if (sysUser == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    // simplified version: all users have ROLE_USER permission
    // if no permission control is needed, you can pass in an empty collection
    return User.builder()
            .username(sysUser.getUsername())
            .password(sysUser.getPassword())
            .roles()
            .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(!sysUser.getEnabled())
            .build();
  }
}
