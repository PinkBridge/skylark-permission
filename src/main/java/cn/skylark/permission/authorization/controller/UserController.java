package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.entity.SysUser;
import cn.skylark.permission.authorization.service.UserService;
import cn.skylark.permission.utils.Ret;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/permission/users")
public class UserController {

  @Resource
  private UserService userService;

  @GetMapping
  public Ret<List<SysUser>> list() {
    return Ret.data(userService.list());
  }

  @GetMapping("/{id}")
  public Ret<SysUser> get(@PathVariable Long id) {
    return Ret.data(userService.get(id));
  }

  @PostMapping
  public Ret<Integer> create(@RequestBody SysUser user) {
    return Ret.data(userService.create(user));
  }

  @PutMapping("/{id}")
  public Ret<Integer> update(@PathVariable Long id, @RequestBody SysUser user) {
    user.setId(id);
    return Ret.data(userService.update(user));
  }

  @DeleteMapping("/{id}")
  public Ret<Integer> delete(@PathVariable Long id) {
    return Ret.data(userService.delete(id));
  }

  @GetMapping("/{id}/roles")
  public Ret<List<SysRole>> userRoles(@PathVariable Long id) {
    return Ret.data(userService.roles(id));
  }

  @PostMapping("/{id}/roles:bind")
  public Ret<Void> bindRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
    userService.bindRoles(id, roleIds);
    return Ret.ok();
  }
}

