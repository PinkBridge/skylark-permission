package cn.skylark.permission.authorization.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    boolean isBound = apiService.isApiBoundToRoles(roleNames, method, requestUri);
    log.info("hasPermission?: {}, {}, {}, {}", roleNames, method, requestUri, isBound);
    return isBound;
  }
}
