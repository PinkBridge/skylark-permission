package cn.skylark.permission.utils;


import lombok.Data;

/**
 * @author yaomianwei
 * @since 17:35 2025/10/31
 **/
@Data
public class Ret<T> {
  /**
   *
   */
  private int code;
  private T data;
  private String message;

  public static <T> Ret<T> data(T data) {
    Ret<T> ret = new Ret<>();
    ret.setCode(200);
    ret.setData(data);
    ret.setMessage("success");
    return ret;
  }

  public static <T> Ret<T> ok() {
    Ret<T> ret = new Ret<>();
    ret.setCode(200);
    ret.setData(null);
    ret.setMessage("success");
    return ret;
  }

  public static <T> Ret<T> resp(Integer code, T data, String message) {
    Ret<T> ret = new Ret<>();
    ret.setCode(code);
    ret.setData(data);
    ret.setMessage(message);
    return ret;
  }

  public static <T> Ret<T> fail(Integer failCode, String failMessage) {
    Ret<T> ret = new Ret<>();
    ret.setCode(failCode);
    ret.setData(null);
    ret.setMessage(failMessage);
    return ret;
  }
}
