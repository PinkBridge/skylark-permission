package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.entity.SysWhitelist;
import cn.skylark.permission.authorization.mapper.WhitelistMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 白名单服务
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Service
public class WhitelistService {

  @Resource
  private WhitelistMapper whitelistMapper;

  public SysWhitelist get(Long id) {
    return whitelistMapper.selectById(id);
  }

  public List<SysWhitelist> list() {
    return whitelistMapper.selectAll();
  }

  /**
   * 获取所有启用的白名单
   */
  public List<SysWhitelist> listEnabled() {
    return whitelistMapper.selectEnabled();
  }

  public int create(SysWhitelist whitelist) {
    return whitelistMapper.insert(whitelist);
  }

  public int update(SysWhitelist whitelist) {
    return whitelistMapper.update(whitelist);
  }

  public int delete(Long id) {
    return whitelistMapper.deleteById(id);
  }
}

