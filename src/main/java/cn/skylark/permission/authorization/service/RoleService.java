package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {

  @Resource
  private RoleMapper roleMapper;
  @Resource
  private MenuService menuService;
  @Resource
  private ApiService apiService;

  public SysRole get(Long id) {
    return roleMapper.selectById(id);
  }

  public List<SysRole> list() {
    return roleMapper.selectAll();
  }

  public int create(SysRole role) {
    return roleMapper.insert(role);
  }

  public int update(SysRole role) {
    return roleMapper.update(role);
  }

  public int delete(Long id) {
    return roleMapper.deleteById(id);
  }

  public void bindMenus(Long roleId, List<Long> menuIds) {
    menuService.bindRoleMenus(roleId, menuIds);
  }

  public void bindApis(Long roleId, List<Long> apiIds) {
    apiService.bindRoleApis(roleId, apiIds);
  }
}
