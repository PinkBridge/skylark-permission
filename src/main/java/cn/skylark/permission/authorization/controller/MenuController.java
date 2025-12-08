package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.dto.MenuResponseDTO;
import cn.skylark.permission.authorization.dto.MenuTreeNode;
import cn.skylark.permission.authorization.dto.UpdateMenuDTO;
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

  @GetMapping
  public Ret<List<MenuResponseDTO>> list() {
    return Ret.data(menuService.listDTO());
  }

  @GetMapping("/{id}")
  public Ret<MenuResponseDTO> get(@PathVariable Long id) {
    MenuResponseDTO menuDTO = menuService.getDTO(id);
    if (menuDTO == null) {
      return Ret.fail(404, "menu.not.found");
    }
    return Ret.data(menuDTO);
  }

  @PostMapping
  public Ret<Integer> create(@RequestBody SysMenu menu) {
    return Ret.data(menuService.create(menu));
  }

  @PutMapping("/{id}")
  public Ret<Integer> update(@PathVariable Long id, @RequestBody UpdateMenuDTO updateMenuDTO) {
    MenuResponseDTO menuDTO = menuService.getDTO(id);
    if (menuDTO == null) {
      return Ret.fail(404, "menu.not.found");
    }
    return Ret.data(menuService.updateMenuInfo(id, updateMenuDTO));
  }

  @DeleteMapping("/{id}")
  public Ret<Integer> delete(@PathVariable Long id) {
    return Ret.data(menuService.delete(id));
  }

  /**
   * 获取所有菜单树
   *
   * @param name      菜单名称（模糊搜索，可选）
   * @param permlabel 权限标签（模糊搜索，可选）
   * @param moduleKey 模块标识（模糊搜索，可选）
   * @param path      菜单路径（模糊搜索，可选）
   * @return 菜单树
   */
  @GetMapping("/tree")
  public Ret<List<MenuTreeNode>> menuTree(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String permlabel,
      @RequestParam(required = false) String moduleKey,
      @RequestParam(required = false) String path) {
    return Ret.data(menuService.menuTree(name, permlabel, moduleKey, path));
  }

  /**
   * 获取当前用户的菜单树
   *
   * @param authentication 认证信息
   * @param name           菜单名称（模糊搜索，可选）
   * @param permlabel      权限标签（模糊搜索，可选）
   * @param moduleKey      模块标识（模糊搜索，可选）
   * @param path           菜单路径（模糊搜索，可选）
   * @return 菜单树
   */
  @GetMapping("/me/tree")
  public Ret<List<MenuTreeNode>> myMenuTree(Authentication authentication,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String permlabel,
                                             @RequestParam(required = false) String moduleKey,
                                             @RequestParam(required = false) String path) {
    String username = authentication.getName();
    return Ret.data(menuService.userMenuTree(username, name, permlabel, moduleKey, path));
  }
}
