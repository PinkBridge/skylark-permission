package cn.skylark.permission.jwt;

import cn.skylark.permission.jwt.exception.JwtExceptionEnum;
import cn.skylark.permission.jwt.models.JwtResponseModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yaomianwei
 */
//@Component
public class JwtFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUserDetailsService userDetailsService;
  @Autowired
  private TokenManager tokenManager;

  private static final String BEARER_PREFIX = "Bearer ";
  private static final String LOGIN_PATH = "/api/auth/login";

  private void responseException(HttpServletResponse response, JwtResponseModel<Object> jwtResponseModel) throws IOException {
    ValueFilter valueFilter = (Object object, String name, Object value) -> value == null ? "" : value;
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    String jsonObject = JSON.toJSONString(jwtResponseModel, valueFilter);
    out.println(jsonObject);
    out.flush();
    out.close();
  }

  @SneakyThrows
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response, FilterChain filterChain) throws IOException {
    String tokenHeader = request.getHeader("Authorization");
    String username = null;
    String token = null;
    if (tokenHeader != null && tokenHeader.startsWith(BEARER_PREFIX)) {
      token = tokenHeader.substring(7);
      try {
        username = tokenManager.getUsernameFromToken(token);
      } catch (IllegalArgumentException e) {
        if (!LOGIN_PATH.equals(request.getRequestURI())) {
          responseException(response, JwtResponseModel.fail(JwtExceptionEnum.JWT_TOKEN_MISS));
          return;
        }
      } catch (ExpiredJwtException e) {
        if (!LOGIN_PATH.equals(request.getRequestURI())) {
          responseException(response, JwtResponseModel.fail(JwtExceptionEnum.JWT_TOKEN_EXPIRED));
          return;
        }
      }
    } else {
      if (!LOGIN_PATH.equals(request.getRequestURI())) {
        responseException(response, JwtResponseModel.fail(JwtExceptionEnum.BEARER_STRING_NOT_FOUND));
        return;
      }
    }
    if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (Boolean.TRUE.equals(tokenManager.validateJwtToken(token, userDetails))) {
        UsernamePasswordAuthenticationToken
                authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(new
                WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}