package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.dto.OrganizationPageRequest;
import cn.skylark.permission.authorization.dto.OrganizationResponseDTO;
import cn.skylark.permission.authorization.dto.ParentOrganizationDTO;
import cn.skylark.permission.authorization.dto.UpdateOrganizationDTO;
import cn.skylark.permission.authorization.entity.SysOrganization;
import cn.skylark.permission.authorization.mapper.OrganizationMapper;
import cn.skylark.permission.utils.PageRequest;
import cn.skylark.permission.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 组织服务
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Service
public class OrganizationService {

  @Resource
  private OrganizationMapper organizationMapper;

  public SysOrganization get(Long id) {
    return organizationMapper.selectById(id);
  }

  public List<SysOrganization> list() {
    return organizationMapper.selectAll();
  }

  /**
   * 获取组织列表（DTO）
   *
   * @return 组织列表
   */
  public List<OrganizationResponseDTO> listDTO() {
    List<SysOrganization> organizations = organizationMapper.selectAll();
    // 构建组织ID到组织的映射，用于快速查找父组织
    Map<Long, SysOrganization> organizationMap = new HashMap<>();
    for (SysOrganization org : organizations) {
      organizationMap.put(org.getId(), org);
    }
    return organizations.stream().map(org -> convertToDTO(org, organizationMap)).collect(Collectors.toList());
  }

  public int create(SysOrganization organization) {
    return organizationMapper.insert(organization);
  }

  public int update(SysOrganization organization) {
    return organizationMapper.update(organization);
  }

  public int delete(Long id) {
    return organizationMapper.deleteById(id);
  }

  /**
   * 获取组织信息（DTO）
   *
   * @param id 组织ID
   * @return 组织信息
   */
  public OrganizationResponseDTO getDTO(Long id) {
    SysOrganization organization = organizationMapper.selectById(id);
    if (organization == null) {
      return null;
    }
    // 如果有父组织，查询父组织信息
    Map<Long, SysOrganization> organizationMap = new HashMap<>();
    if (organization.getParentId() != null) {
      SysOrganization parentOrg = organizationMapper.selectById(organization.getParentId());
      if (parentOrg != null) {
        organizationMap.put(parentOrg.getId(), parentOrg);
      }
    }
    return convertToDTO(organization, organizationMap);
  }

  /**
   * 分页查询组织列表（DTO）
   *
   * @param pageRequest 分页请求参数
   * @return 分页结果
   */
  public PageResult<OrganizationResponseDTO> pageDTO(PageRequest pageRequest) {
    List<SysOrganization> records = organizationMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
    Long total = organizationMapper.countAll();
    // 构建组织映射
    Map<Long, SysOrganization> organizationMap = new HashMap<>();
    for (SysOrganization org : records) {
      organizationMap.put(org.getId(), org);
    }
    // 添加父组织到映射中
    for (SysOrganization org : records) {
      if (org.getParentId() != null && !organizationMap.containsKey(org.getParentId())) {
        SysOrganization parentOrg = organizationMapper.selectById(org.getParentId());
        if (parentOrg != null) {
          organizationMap.put(parentOrg.getId(), parentOrg);
        }
      }
    }
    List<OrganizationResponseDTO> dtoList = records.stream().map(org -> convertToDTO(org, organizationMap)).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 分页查询组织列表（带条件，DTO）
   *
   * @param pageRequest 分页请求参数（包含搜索条件）
   * @return 分页结果
   */
  public PageResult<OrganizationResponseDTO> pageDTOWithCondition(OrganizationPageRequest pageRequest) {
    // 判断是否有搜索条件
    boolean hasCondition = StringUtils.hasText(pageRequest.getName()) ||
                           StringUtils.hasText(pageRequest.getCode()) ||
                           StringUtils.hasText(pageRequest.getPhone()) ||
                           StringUtils.hasText(pageRequest.getEmail()) ||
                           StringUtils.hasText(pageRequest.getType()) ||
                           StringUtils.hasText(pageRequest.getStatus()) ||
                           pageRequest.getCreateTime() != null;

    List<SysOrganization> records;
    Long total;

    if (hasCondition) {
      // 使用带条件的查询
      records = organizationMapper.selectPageWithCondition(
          pageRequest.getName(),
          pageRequest.getCode(),
          pageRequest.getPhone(),
          pageRequest.getEmail(),
          pageRequest.getType(),
          pageRequest.getStatus(),
          pageRequest.getCreateTime(),
          pageRequest.getOffset(),
          pageRequest.getLimit()
      );
      total = organizationMapper.countWithCondition(
          pageRequest.getName(),
          pageRequest.getCode(),
          pageRequest.getPhone(),
          pageRequest.getEmail(),
          pageRequest.getType(),
          pageRequest.getStatus(),
          pageRequest.getCreateTime()
      );
    } else {
      // 使用无条件的查询
      records = organizationMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
      total = organizationMapper.countAll();
    }

    // 构建组织映射
    Map<Long, SysOrganization> organizationMap = new HashMap<>();
    for (SysOrganization org : records) {
      organizationMap.put(org.getId(), org);
    }
    // 添加父组织到映射中
    for (SysOrganization org : records) {
      if (org.getParentId() != null && !organizationMap.containsKey(org.getParentId())) {
        SysOrganization parentOrg = organizationMapper.selectById(org.getParentId());
        if (parentOrg != null) {
          organizationMap.put(parentOrg.getId(), parentOrg);
        }
      }
    }

    List<OrganizationResponseDTO> dtoList = records.stream().map(org -> convertToDTO(org, organizationMap)).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 更新组织信息
   *
   * @param organizationId 组织ID
   * @param dto            更新组织信息DTO
   * @return 更新行数
   */
  public int updateOrganizationInfo(Long organizationId, UpdateOrganizationDTO dto) {
    return organizationMapper.updateOrganizationInfo(organizationId, dto);
  }

  /**
   * 将SysOrganization转换为OrganizationResponseDTO
   *
   * @param organization      组织实体
   * @param organizationMap   组织映射（用于查找父组织）
   * @return 组织响应DTO
   */
  private OrganizationResponseDTO convertToDTO(SysOrganization organization, Map<Long, SysOrganization> organizationMap) {
    if (organization == null) {
      return null;
    }
    OrganizationResponseDTO dto = new OrganizationResponseDTO();
    BeanUtils.copyProperties(organization, dto);

    // 设置父组织信息
    if (organization.getParentId() != null && organizationMap.containsKey(organization.getParentId())) {
      SysOrganization parentOrg = organizationMap.get(organization.getParentId());
      dto.setParentOrganization(convertToParentOrganizationDTO(parentOrg));
    }

    return dto;
  }

  /**
   * 将SysOrganization转换为ParentOrganizationDTO
   *
   * @param organization 组织实体
   * @return 父组织DTO
   */
  private ParentOrganizationDTO convertToParentOrganizationDTO(SysOrganization organization) {
    if (organization == null) {
      return null;
    }
    ParentOrganizationDTO dto = new ParentOrganizationDTO();
    dto.setId(organization.getId());
    dto.setName(organization.getName());
    dto.setCode(organization.getCode());
    dto.setType(organization.getType());
    return dto;
  }
}

