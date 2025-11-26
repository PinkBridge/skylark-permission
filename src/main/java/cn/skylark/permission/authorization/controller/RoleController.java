package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.dto.RolePageRequest;
import cn.skylark.permission.authorization.dto.RoleResponseDTO;
import cn.skylark.permission.authorization.dto.UpdateRoleDTO;
import cn.skylark.permission.authorization.entity.SysApi;
import cn.skylark.permission.authorization.entity.SysMenu;
import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.service.ApiService;
import cn.skylark.permission.authorization.service.MenuService;
import cn.skylark.permission.authorization.service.RoleService;
import cn.skylark.permission.utils.PageResult;
import cn.skylark.permission.utils.Ret;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yaomianwei
 */
@RestController
@RequestMapping("/api/permission/roles")
public class RoleController {

  @Resource
  private RoleService roleService;
  @Resource
  private MenuService menuService;
  @Resource
  private ApiService apiService;

  @GetMapping
  public Ret<List<RoleResponseDTO>> list() {
    return Ret.data(roleService.listDTO());
  }

  /**
   * 分页查询角色列表（支持搜索）
   *
   * @param page       页码，从1开始，默认1
   * @param size       每页大小，默认10
   * @param name       角色名称（模糊搜索）
   * @param remark     备注（模糊搜索）
   * @param createTime 创建时间（查询此时间之前的数据，支持格式：ISO 8601如2025-11-22T16:00:00.000Z，或yyyy-MM-dd HH:mm:ss）
   * @return 分页结果
   */
  @GetMapping("/page")
  public Ret<PageResult<RoleResponseDTO>> page(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String remark,
      @RequestParam(required = false) LocalDateTime createTime) {
    RolePageRequest pageRequest = new RolePageRequest();
    pageRequest.setPage(page);
    pageRequest.setSize(size);
    pageRequest.setName(name);
    pageRequest.setRemark(remark);
    pageRequest.setCreateTime(createTime);
    return Ret.data(roleService.pageDTOWithCondition(pageRequest));
  }

  @GetMapping("/{id}")
  public Ret<RoleResponseDTO> get(@PathVariable Long id) {
    RoleResponseDTO roleDTO = roleService.getDTO(id);
    if (roleDTO == null) {
      return Ret.fail(404, "role.not.found");
    }
    return Ret.data(roleDTO);
  }

  @PostMapping
  public Ret<Integer> create(@RequestBody SysRole role) {
    return Ret.data(roleService.create(role));
  }

  @PutMapping("/{id}")
  public Ret<Integer> update(@PathVariable Long id, @RequestBody UpdateRoleDTO updateRoleDTO) {
    RoleResponseDTO roleDTO = roleService.getDTO(id);
    if (roleDTO == null) {
      return Ret.fail(404, "role.not.found");
    }
    return Ret.data(roleService.updateRoleInfo(id, updateRoleDTO));
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
