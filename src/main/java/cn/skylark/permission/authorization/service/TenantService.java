package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.dto.TenantPageRequest;
import cn.skylark.permission.authorization.dto.TenantResponseDTO;
import cn.skylark.permission.authorization.dto.UpdateTenantDTO;
import cn.skylark.permission.authorization.entity.SysTenant;
import cn.skylark.permission.authorization.mapper.TenantMapper;
import cn.skylark.permission.utils.PageRequest;
import cn.skylark.permission.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户服务
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Service
public class TenantService {

  @Resource
  private TenantMapper tenantMapper;

  public SysTenant get(Long id) {
    return tenantMapper.selectById(id);
  }

  public List<SysTenant> list() {
    return tenantMapper.selectAll();
  }

  /**
   * 获取租户列表（DTO）
   *
   * @return 租户列表
   */
  public List<TenantResponseDTO> listDTO() {
    List<SysTenant> tenants = tenantMapper.selectAll();
    return tenants.stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  public int create(SysTenant tenant) {
    return tenantMapper.insert(tenant);
  }

  public int update(SysTenant tenant) {
    return tenantMapper.update(tenant);
  }

  public int delete(Long id) {
    return tenantMapper.deleteById(id);
  }

  /**
   * 获取租户信息（DTO）
   *
   * @param id 租户ID
   * @return 租户信息
   */
  public TenantResponseDTO getDTO(Long id) {
    SysTenant tenant = tenantMapper.selectById(id);
    return tenant != null ? convertToDTO(tenant) : null;
  }

  /**
   * 根据域名获取租户信息（DTO）
   *
   * @param domain 租户域名
   * @return 租户信息
   */
  public TenantResponseDTO getDTOByDomain(String domain) {
    SysTenant tenant = tenantMapper.selectByDomain(domain);
    return tenant != null ? convertToDTO(tenant) : null;
  }

  /**
   * 分页查询租户列表（DTO）
   *
   * @param pageRequest 分页请求参数
   * @return 分页结果
   */
  public PageResult<TenantResponseDTO> pageDTO(PageRequest pageRequest) {
    List<SysTenant> records = tenantMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
    Long total = tenantMapper.countAll();
    List<TenantResponseDTO> dtoList = records.stream().map(this::convertToDTO).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 分页查询租户列表（带条件，DTO）
   *
   * @param pageRequest 分页请求参数（包含搜索条件）
   * @return 分页结果
   */
  public PageResult<TenantResponseDTO> pageDTOWithCondition(TenantPageRequest pageRequest) {
    // 判断是否有搜索条件
    boolean hasCondition = StringUtils.hasText(pageRequest.getName()) ||
                           StringUtils.hasText(pageRequest.getCode()) ||
                           StringUtils.hasText(pageRequest.getContactPhone()) ||
                           StringUtils.hasText(pageRequest.getContactEmail()) ||
                           StringUtils.hasText(pageRequest.getDomain()) ||
                           StringUtils.hasText(pageRequest.getStatus()) ||
                           pageRequest.getCreateTime() != null;

    List<SysTenant> records;
    Long total;

    if (hasCondition) {
      // 使用带条件的查询
      records = tenantMapper.selectPageWithCondition(
          pageRequest.getName(),
          pageRequest.getCode(),
          pageRequest.getContactPhone(),
          pageRequest.getContactEmail(),
          pageRequest.getDomain(),
          pageRequest.getStatus(),
          pageRequest.getCreateTime(),
          pageRequest.getOffset(),
          pageRequest.getLimit()
      );
      total = tenantMapper.countWithCondition(
          pageRequest.getName(),
          pageRequest.getCode(),
          pageRequest.getContactPhone(),
          pageRequest.getContactEmail(),
          pageRequest.getDomain(),
          pageRequest.getStatus(),
          pageRequest.getCreateTime()
      );
    } else {
      // 使用无条件的查询
      records = tenantMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
      total = tenantMapper.countAll();
    }

    List<TenantResponseDTO> dtoList = records.stream().map(this::convertToDTO).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 更新租户信息
   *
   * @param tenantId 租户ID
   * @param dto      更新租户信息DTO
   * @return 更新行数
   */
  public int updateTenantInfo(Long tenantId, UpdateTenantDTO dto) {
    return tenantMapper.updateTenantInfo(tenantId, dto);
  }

  /**
   * 将SysTenant转换为TenantResponseDTO
   *
   * @param tenant 租户实体
   * @return 租户响应DTO
   */
  private TenantResponseDTO convertToDTO(SysTenant tenant) {
    if (tenant == null) {
      return null;
    }
    TenantResponseDTO dto = new TenantResponseDTO();
    BeanUtils.copyProperties(tenant, dto);
    return dto;
  }
}


