package cn.skylark.permission.jwt.exception;


/**
 *
 * @author yaomianwei
 * @since 18:46 2025/10/31
 **/
public class JwtException extends Exception {
  private JwtExceptionEnum jwtExceptionEnum;

  public JwtException(JwtExceptionEnum jwtExceptionEnum) {
    super(jwtExceptionEnum.getMessage());
    this.jwtExceptionEnum = jwtExceptionEnum;
  }

  public JwtExceptionEnum getJwtExceptionEnum() {
    return jwtExceptionEnum;
  }

  public void setJwtExceptionEnum(JwtExceptionEnum jwtExceptionEnum) {
    this.jwtExceptionEnum = jwtExceptionEnum;
  }
}
