package cn.skylark.permission.jwt.exception;

import cn.skylark.permission.jwt.models.JwtResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yaomianwei
 * @since 23:20 2025/10/31
 **/
//@ControllerAdvice
@Slf4j
public class JwtExceptionHandler {
  @ExceptionHandler(JwtException.class)
  public JwtResponseModel<Object> defaultErrorHandler(HttpServletRequest req, JwtException e) {
    Integer code = e.getJwtExceptionEnum().getCode();
    String message = e.getJwtExceptionEnum().getMessage();
    log.error("JwtExceptionHandler 获取到错误:{}", message);
    e.printStackTrace();
    return JwtResponseModel.fail(code, message);
  }
}
