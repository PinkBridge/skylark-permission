package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.entity.SysUser;
import cn.skylark.permission.authorization.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

  @Resource
  private UserMapper userMapper;

  public SysUser get(Long id) {
    return userMapper.selectById(id);
  }

  public List<SysUser> list() {
    return userMapper.selectAll();
  }

  public int create(SysUser user) {
    return userMapper.insert(user);
  }

  public int update(SysUser user) {
    return userMapper.update(user);
  }

  public int delete(Long id) {
    userMapper.deleteUserRoles(id);
    return userMapper.deleteById(id);
  }

  public List<SysRole> roles(Long userId) {
    return userMapper.selectRolesByUserId(userId);
  }

  public void bindRoles(Long userId, List<Long> roleIds) {
    userMapper.deleteUserRoles(userId);
    if (!CollectionUtils.isEmpty(roleIds)) {
      userMapper.insertUserRoles(userId, roleIds);
    }
  }

  public SysUser findByUsername(String username) {
    return userMapper.findByUsername(username);
  }
}

