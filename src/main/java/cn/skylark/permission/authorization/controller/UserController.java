package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.dto.ChangePasswordDTO;
import cn.skylark.permission.authorization.dto.UserDTO;
import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.entity.SysUser;
import cn.skylark.permission.authorization.service.UserService;
import cn.skylark.permission.utils.Ret;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
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

  /**
   * 获取当前登录用户信息
   */
  @GetMapping("/me")
  public Ret<UserDTO> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Ret.fail(401, "user.not.login");
    }

    String username = null;
    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else if (principal instanceof String) {
      username = (String) principal;
    }

    if (username == null || username.isEmpty()) {
      return Ret.fail(401, "user.info.not.available");
    }

    SysUser user = userService.findByUsername(username);
    if (user == null) {
      return Ret.fail(404, "user.not.found");
    }

    List<SysRole> roles = userService.roles(user.getId());
    UserDTO dto = new UserDTO();
    dto.setUser(user);
    dto.setRoles(roles);
    return Ret.data(dto);
  }

  @GetMapping("/{id}")
  public Ret<UserDTO> get(@PathVariable Long id) {
    SysUser user = userService.get(id);
    if (user == null) {
      return Ret.fail(404, "user.not.found");
    }
    List<SysRole> roles = userService.roles(id);
    UserDTO dto = new UserDTO();
    dto.setUser(user);
    dto.setRoles(roles);
    return Ret.data(dto);
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

  /**
   * 当前用户重置密码
   */
  @PutMapping("/me/password")
  public Ret<Void> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
    // 获取当前登录用户
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Ret.fail(401, "user.not.login");
    }

    String username = null;
    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else if (principal instanceof String) {
      username = (String) principal;
    }

    if (username == null || username.isEmpty()) {
      return Ret.fail(401, "user.info.not.available");
    }

    // 参数验证
    if (!StringUtils.hasText(changePasswordDTO.getOldPassword())) {
      return Ret.fail(400, "password.old.required");
    }
    if (!StringUtils.hasText(changePasswordDTO.getNewPassword())) {
      return Ret.fail(400, "password.new.required");
    }
    if (changePasswordDTO.getOldPassword().equals(changePasswordDTO.getNewPassword())) {
      return Ret.fail(400, "password.same.as.old");
    }

    // 获取用户信息
    SysUser user = userService.findByUsername(username);
    if (user == null) {
      return Ret.fail(404, "user.not.found");
    }

    // 重置密码
    boolean success = userService.changePassword(user.getId(), 
                                                  changePasswordDTO.getOldPassword(), 
                                                  changePasswordDTO.getNewPassword());
    if (!success) {
      return Ret.fail(400, "password.incorrect");
    }

    return Ret.ok();
  }
}

