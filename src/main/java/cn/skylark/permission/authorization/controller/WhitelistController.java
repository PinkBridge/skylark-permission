package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.entity.SysWhitelist;
import cn.skylark.permission.authorization.service.RbacService;
import cn.skylark.permission.authorization.service.WhitelistService;
import cn.skylark.permission.utils.Ret;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 白名单控制器
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@RestController
@RequestMapping("/api/permission/whitelists")
public class WhitelistController {

  @Resource
  private WhitelistService whitelistService;

  @Resource
  private RbacService rbacService;

  @GetMapping
  public Ret<List<SysWhitelist>> list() {
    return Ret.data(whitelistService.list());
  }

  @GetMapping("/{id}")
  public Ret<SysWhitelist> get(@PathVariable Long id) {
    return Ret.data(whitelistService.get(id));
  }

  @PostMapping
  public Ret<Integer> create(@RequestBody SysWhitelist whitelist) {
    int result = whitelistService.create(whitelist);
    // 创建后刷新缓存
    rbacService.refreshWhitelistCache();
    return Ret.data(result);
  }

  @PutMapping("/{id}")
  public Ret<Integer> update(@PathVariable Long id, @RequestBody SysWhitelist whitelist) {
    whitelist.setId(id);
    int result = whitelistService.update(whitelist);
    // 更新后刷新缓存
    rbacService.refreshWhitelistCache();
    return Ret.data(result);
  }

  @DeleteMapping("/{id}")
  public Ret<Integer> delete(@PathVariable Long id) {
    int result = whitelistService.delete(id);
    // 删除后刷新缓存
    rbacService.refreshWhitelistCache();
    return Ret.data(result);
  }

  /**
   * 手动刷新白名单缓存
   */
  @PostMapping("/refresh-cache")
  public Ret<Void> refreshCache() {
    rbacService.refreshWhitelistCache();
    return Ret.ok();
  }
}

