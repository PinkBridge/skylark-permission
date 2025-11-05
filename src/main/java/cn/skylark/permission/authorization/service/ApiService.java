package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.entity.SysApi;
import cn.skylark.permission.authorization.mapper.ApiMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApiService {

  @Resource
  private ApiMapper apiMapper;

  public SysApi get(Long id) {
    return apiMapper.selectById(id);
  }

  public List<SysApi> list() {
    return apiMapper.selectAll();
  }

  public int create(SysApi api) {
    return apiMapper.insert(api);
  }

  public int update(SysApi api) {
    return apiMapper.update(api);
  }

  public int delete(Long id) {
    return apiMapper.deleteById(id);
  }

  public List<SysApi> listByRole(Long roleId) {
    return apiMapper.selectByRoleId(roleId);
  }

  public void bindRoleApis(Long roleId, List<Long> apiIds) {
    apiMapper.deleteBindingsByRoleId(roleId);
    if (apiIds != null && !apiIds.isEmpty()) {
      apiMapper.bindRoleApis(roleId, apiIds);
    }
  }
}
