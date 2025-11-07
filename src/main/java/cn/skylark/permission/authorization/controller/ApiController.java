package cn.skylark.permission.authorization.controller;

import cn.skylark.permission.authorization.entity.SysApi;
import cn.skylark.permission.authorization.service.ApiService;
import cn.skylark.permission.utils.Ret;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yaomianwei
 */
@RestController
@RequestMapping("/api/permission/apis")
public class ApiController {

  @Resource
  private ApiService apiService;

  @GetMapping("/{id}")
  public Ret<SysApi> get(@PathVariable Long id) {
    return Ret.data(apiService.get(id));
  }

  @GetMapping
  public Ret<List<SysApi>> list() {
    return Ret.data(apiService.list());
  }

  @PostMapping
  public Ret<Integer> create(@RequestBody SysApi api) {
    return Ret.data(apiService.create(api));
  }

  @PutMapping("/{id}")
  public Ret<Integer> update(@PathVariable Long id, @RequestBody SysApi api) {
    api.setId(id);
    return Ret.data(apiService.update(api));
  }

  @DeleteMapping("/{id}")
  public Ret<Integer> delete(@PathVariable Long id) {
    return Ret.data(apiService.delete(id));
  }
}
