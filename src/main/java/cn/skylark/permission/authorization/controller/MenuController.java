package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.entity.SysMenu;
import cn.skylark.permission.authorization.service.MenuService;
import cn.skylark.permission.utils.Ret;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yaomianwei
 */
@RestController
@RequestMapping("/api/permission/menus")
public class MenuController {

  @Resource
  private MenuService menuService;

  @GetMapping("/{id}")
  public Ret<SysMenu> get(@PathVariable Long id) {
    return Ret.data(menuService.get(id));
  }

  @GetMapping
  public Ret<List<SysMenu>> list() {
    return Ret.data(menuService.list());
  }

  @PostMapping
  public Ret<Integer> create(@RequestBody SysMenu menu) {
    return Ret.data(menuService.create(menu));
  }

  @PutMapping("/{id}")
  public Ret<Integer> update(@PathVariable Long id, @RequestBody SysMenu menu) {
    menu.setId(id);
    return Ret.data(menuService.update(menu));
  }

  @DeleteMapping("/{id}")
  public Ret<Integer> delete(@PathVariable Long id) {
    return Ret.data(menuService.delete(id));
  }

  @GetMapping("/me/tree")
  public Ret<List<MenuService.MenuNode>> myMenuTree(Authentication authentication) {
    String username = authentication.getName();
    return Ret.data(menuService.userMenuTree(username));
  }
}
