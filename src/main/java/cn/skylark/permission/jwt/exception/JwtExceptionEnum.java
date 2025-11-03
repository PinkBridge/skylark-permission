package cn.skylark.permission.jwt.exception;

/**
 * 异常枚举
 *
 * @author yaomianwei
 */
public enum JwtExceptionEnum {
  // ACCOUNT OR PASSWORD ERROR
  LOGIN_ERROR(501, "ACCOUNT OR PASSWORD ERROR"),
  // USER DISABLED
  USER_DISABLED(502, "USER DISABLED"),
  // INVALID CREDENTIALS
  INVALID_CREDENTIALS(503, "INVALID CREDENTIALS"),
  // REGISTER ERROR
  REGISTER_ERROR(510, "REGISTER ERROR"),
  // UNABLE TO GET JWT TOKEN
  JWT_TOKEN_MISS(520, "UNABLE TO GET JWT TOKEN"),
  // JWT TOKEN HAS EXPIRED
  JWT_TOKEN_EXPIRED(521, "JWT TOKEN HAS EXPIRED"),
  // BEARER STRING NOT FOUND
  BEARER_STRING_NOT_FOUND(522, "BEARER STRING NOT FOUND");


  private final Integer code;
  private final String message;

  JwtExceptionEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }


  public String getMessage() {
    return message;
  }

}
