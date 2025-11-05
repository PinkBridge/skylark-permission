package cn.skylark.permission.oauth2.handler;

import cn.skylark.permission.oauth2.service.LogoutService;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yaomianwei
 * @since 2025/11/3
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

  @Resource
  private LogoutService logoutService;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException, ServletException {
    String result;
    String accessToken = extractAccessToken(request);
    try {
      if (accessToken != null && !accessToken.isEmpty()) {
        if (logoutService.logout(accessToken)) {
          result = buildSuccessResult("Logout successful.Refresh token has been deleted.");
        } else {
          result = buildSuccessResult("Logout completed.Refresh token may not exist or already deleted.");
        }
      } else {
        result = buildSuccessResult("Logout successful.");
      }
    } catch (Exception e) {
      result = buildFailResult(e.getMessage());
    }

    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(result);
  }

  private String buildSuccessResult(String msg) {
    Map<String, Object> result = new HashMap<>(16);
    result.put("success", true);
    result.put("message", msg);
    return JSON.toJSONString(result);
  }

  private String buildFailResult(String msg) {
    Map<String, Object> result = new HashMap<>(16);
    result.put("success", false);
    result.put("message", msg);
    return JSON.toJSONString(result);
  }

  private String extractAccessToken(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    if (authorization != null && authorization.startsWith("Bearer ")) {
      return authorization.substring(7);
    }

    String accessToken = request.getParameter("access_token");
    if (accessToken != null && !accessToken.isEmpty()) {
      return accessToken;
    }
    return null;
  }
}
