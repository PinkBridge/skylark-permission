package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.entity.SysApi;
import cn.skylark.permission.authorization.entity.SysMenu;
import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.service.ApiService;
import cn.skylark.permission.authorization.service.MenuService;
import cn.skylark.permission.authorization.service.RoleService;
import cn.skylark.permission.utils.Ret;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yaomianwei
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

  @Resource
  private RoleService roleService;
  @Resource
  private MenuService menuService;
  @Resource
  private ApiService apiService;

  @GetMapping("/{id}")
  public Ret<SysRole> get(@PathVariable Long id) {
    return Ret.data(roleService.get(id));
  }

  @GetMapping
  public Ret<List<SysRole>> list() {
    return Ret.data(roleService.list());
  }

  @PostMapping
  public Ret<Integer> create(@RequestBody SysRole role) {
    return Ret.data(roleService.create(role));
  }

  @PutMapping("/{id}")
  public Ret<Integer> update(@PathVariable Long id, @RequestBody SysRole role) {
    role.setId(id);
    return Ret.data(roleService.update(role));
  }

  @DeleteMapping("/{id}")
  public Ret<Integer> delete(@PathVariable Long id) {
    return Ret.data(roleService.delete(id));
  }

  @GetMapping("/{id}/menus")
  public Ret<List<SysMenu>> roleMenus(@PathVariable Long id) {
    return Ret.data(menuService.listByRole(id));
  }

  @PostMapping("/{id}/menus:bind")
  public Ret<Void> bindMenus(@PathVariable Long id, @RequestBody List<Long> menuIds) {
    roleService.bindMenus(id, menuIds);
    return Ret.ok();
  }

  @GetMapping("/{id}/apis")
  public Ret<List<SysApi>> roleApis(@PathVariable Long id) {
    return Ret.data(apiService.listByRole(id));
  }

  @PostMapping("/{id}/apis:bind")
  public Ret<Void> bindApis(@PathVariable Long id, @RequestBody List<Long> apiIds) {
    roleService.bindApis(id, apiIds);
    return Ret.ok();
  }
}
