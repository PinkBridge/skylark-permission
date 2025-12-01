package cn.skylark.permission.authorization.context;

/**
 * 租户上下文
 * 使用ThreadLocal存储当前请求的租户ID
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
public class TenantContext {

  private static final ThreadLocal<Long> TENANT_ID_HOLDER = new ThreadLocal<>();

  /**
   * 设置当前租户ID
   *
   * @param tenantId 租户ID
   */
  public static void setTenantId(Long tenantId) {
    TENANT_ID_HOLDER.set(tenantId);
  }

  /**
   * 获取当前租户ID
   *
   * @return 租户ID，如果未设置则返回null
   */
  public static Long getTenantId() {
    return TENANT_ID_HOLDER.get();
  }

  /**
   * 清除当前租户ID
   * 在请求结束后调用，防止内存泄漏
   */
  public static void clear() {
    TENANT_ID_HOLDER.remove();
  }
}

