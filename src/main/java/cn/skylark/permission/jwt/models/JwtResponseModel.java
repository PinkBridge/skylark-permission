package cn.skylark.permission.jwt.models;

import cn.skylark.permission.jwt.exception.JwtExceptionEnum;

/**
 * @author yaomianwei
 * @since 17:35 2025/10/31
 **/
public class JwtResponseModel<T> {
  /**
   *
   */
  private int code;
  private T data;
  private String message;

  public static <T> JwtResponseModel<T> data(T data) {
    JwtResponseModel<T> jwtResponseModel = new JwtResponseModel<>();
    jwtResponseModel.setCode(200);
    jwtResponseModel.setData(data);
    jwtResponseModel.setMessage("success");
    return jwtResponseModel;
  }

  public static <T> JwtResponseModel<T> ok() {
    JwtResponseModel<T> jwtResponseModel = new JwtResponseModel<>();
    jwtResponseModel.setCode(200);
    jwtResponseModel.setData(null);
    jwtResponseModel.setMessage("success");
    return jwtResponseModel;
  }

  public static <T> JwtResponseModel<T> resp(Integer code, T data, String message) {
    JwtResponseModel<T> jwtResponseModel = new JwtResponseModel<>();
    jwtResponseModel.setCode(code);
    jwtResponseModel.setData(data);
    jwtResponseModel.setMessage(message);
    return jwtResponseModel;
  }

  public static <T> JwtResponseModel<T> fail(Integer failCode, String failMessage) {
    JwtResponseModel<T> jwtResponseModel = new JwtResponseModel<>();
    jwtResponseModel.setCode(failCode);
    jwtResponseModel.setData(null);
    jwtResponseModel.setMessage(failMessage);
    return jwtResponseModel;
  }

  public static <T> JwtResponseModel<T> fail(JwtExceptionEnum exceptionEnum) {
    JwtResponseModel<T> jwtResponseModel = new JwtResponseModel<>();
    jwtResponseModel.setCode(exceptionEnum.getCode());
    jwtResponseModel.setData(null);
    jwtResponseModel.setMessage(exceptionEnum.getMessage());
    return jwtResponseModel;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
