package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.entity.SysApi;
import cn.skylark.permission.authorization.entity.SysWhitelist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yaomianwei
 * @since 16:05 2025/11/7
 **/
@Slf4j
@Component
public class RbacService {
  @Resource
  private ApiService apiService;

  @Resource
  private WhitelistService whitelistService;
  
  // 白名单缓存，key为method:path，value为RequestMatcher
  private final ConcurrentHashMap<String, RequestMatcher> whitelistCache = new ConcurrentHashMap<>();
  
  // 白名单列表缓存
  private volatile List<SysWhitelist> cachedWhitelist;

  @PostConstruct
  public void init() {
    refreshWhitelistCache();
  }

  /**
   * 刷新白名单缓存
   */
  public void refreshWhitelistCache() {
    List<SysWhitelist> whitelists = whitelistService.listEnabled();
    cachedWhitelist = whitelists;
    whitelistCache.clear();
    
    for (SysWhitelist whitelist : whitelists) {
      String method = "ALL".equalsIgnoreCase(whitelist.getMethod()) ? null : whitelist.getMethod();
      String key = whitelist.getMethod() + ":" + whitelist.getPath();
      RequestMatcher matcher = new AntPathRequestMatcher(whitelist.getPath(), method);
      whitelistCache.put(key, matcher);
    }
    
    log.info("Whitelist cache refreshed, size: {}", whitelistCache.size());
  }

  public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
    String requestUri = request.getRequestURI();
    String method = request.getMethod();
    
    // 检查白名单
    if (isInWhitelist(request)) {
      log.info("Request is in whitelist: {} {}", method, requestUri);
      return true;
    }

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    List<String> roleNames = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    List<SysApi> sysApis = apiService.listByRoleNames(roleNames);
    boolean has = hasApi(sysApis, request);
    log.info("hasPermission?: {}, {}, {}, {}", roleNames, method, requestUri, has);
    return has;
  }

  /**
   * 检查请求是否在白名单中
   */
  private boolean isInWhitelist(HttpServletRequest request) {
    if (cachedWhitelist == null || cachedWhitelist.isEmpty()) {
      return false;
    }
    
    String requestMethod = request.getMethod();
    
    for (SysWhitelist whitelist : cachedWhitelist) {
      String whitelistMethod = whitelist.getMethod();
      // 如果method是ALL，或者method匹配
      if ("ALL".equalsIgnoreCase(whitelistMethod) || whitelistMethod.equalsIgnoreCase(requestMethod)) {
        RequestMatcher matcher = new AntPathRequestMatcher(whitelist.getPath(), 
                "ALL".equalsIgnoreCase(whitelistMethod) ? null : whitelistMethod);
        if (matcher.matches(request)) {
          return true;
        }
      }
    }
    
    return false;
  }

  private boolean hasApi(List<SysApi> sysApis, HttpServletRequest request) {
    for (SysApi sysApi : sysApis) {
      RequestMatcher requestMatcher = new AntPathRequestMatcher(sysApi.getPath(), sysApi.getMethod());
      if (requestMatcher.matches(request)) {
        return true;
      }
    }
    return false;
  }
}
