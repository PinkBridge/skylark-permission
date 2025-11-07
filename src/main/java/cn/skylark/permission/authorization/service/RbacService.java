package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.entity.SysApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
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

  public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
    String requestUri = request.getRequestURI();
    String method = request.getMethod();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    List<String> roleNames = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    List<SysApi> sysApis = apiService.listByRoleNames(roleNames);
    boolean has = hasApi(sysApis, request);
    log.info("hasPermission?: {}, {}, {}, {}", roleNames, method, requestUri, has);
    return has;
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
