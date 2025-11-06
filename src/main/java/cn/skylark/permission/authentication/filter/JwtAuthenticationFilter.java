package cn.skylark.permission.authentication.filter;

import cn.skylark.permission.authentication.OauthConfig;
import cn.skylark.permission.authentication.service.CustomUserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * JWT 认证过滤器
 * 用于验证请求中的 JWT token 并设置认证信息
 *
 * @author yaomianwei
 * @since 2025/11/6
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";

  @Resource
  private OauthConfig oauthConfig;
  @Resource
  private CustomUserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String token = extractToken(request);

    if (token != null) {
      try {
        String signingKey = oauthConfig.getSigningKey();
        JwtParser parser = Jwts.parser()
                .setSigningKey(signingKey.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> claims = parser.parseClaimsJws(token);
        Claims body = claims.getBody();

        String username = (String) body.get("user_name");
        if (username == null) {
          username = body.getSubject();
        }

        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

        if (username != null && existingAuth == null) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          Authentication authentication = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
          log.error("Skipping authentication setup - username: {}, existingAuth: {}",
                  username, existingAuth != null ? "present" : "null");
        }
      } catch (Exception e) {
        log.error("JWT token validation failed for request {}: {}",
                request.getRequestURI(), e.getMessage());
        log.error("JWT validation exception details", e);
      }
    } else {
      // 没有提供 token，继续执行让其他认证机制处理
      log.error("No JWT token found in request: {}", request.getRequestURI());
      log.error("Authorization header: {}", request.getHeader("Authorization"));
    }

    filterChain.doFilter(request, response);
  }

  /**
   * 从请求中提取 JWT token
   */
  private String extractToken(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
      return authorization.substring(BEARER_PREFIX.length());
    }
    return null;
  }
}

