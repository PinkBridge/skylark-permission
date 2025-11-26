package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.dto.RolePageRequest;
import cn.skylark.permission.authorization.dto.RoleResponseDTO;
import cn.skylark.permission.authorization.dto.UpdateRoleDTO;
import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.mapper.RoleMapper;
import cn.skylark.permission.utils.PageRequest;
import cn.skylark.permission.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

  @Resource
  private RoleMapper roleMapper;
  @Resource
  private MenuService menuService;
  @Resource
  private ApiService apiService;

  public SysRole get(Long id) {
    return roleMapper.selectById(id);
  }

  public List<SysRole> list() {
    return roleMapper.selectAll();
  }

  public int create(SysRole role) {
    return roleMapper.insert(role);
  }

  public int update(SysRole role) {
    return roleMapper.update(role);
  }

  public int delete(Long id) {
    return roleMapper.deleteById(id);
  }

  public void bindMenus(Long roleId, List<Long> menuIds) {
    menuService.bindRoleMenus(roleId, menuIds);
  }

  public void bindApis(Long roleId, List<Long> apiIds) {
    apiService.bindRoleApis(roleId, apiIds);
  }

  /**
   * 获取角色列表（DTO）
   *
   * @return 角色列表
   */
  public List<RoleResponseDTO> listDTO() {
    List<SysRole> roles = roleMapper.selectAll();
    return roles.stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  /**
   * 获取角色信息（DTO）
   *
   * @param id 角色ID
   * @return 角色信息
   */
  public RoleResponseDTO getDTO(Long id) {
    SysRole role = roleMapper.selectById(id);
    return role != null ? convertToDTO(role) : null;
  }

  /**
   * 分页查询角色列表（DTO）
   *
   * @param pageRequest 分页请求参数
   * @return 分页结果
   */
  public PageResult<RoleResponseDTO> pageDTO(PageRequest pageRequest) {
    List<SysRole> records = roleMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
    Long total = roleMapper.countAll();
    List<RoleResponseDTO> dtoList = records.stream().map(this::convertToDTO).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 分页查询角色列表（带条件，DTO）
   *
   * @param pageRequest 分页请求参数（包含搜索条件）
   * @return 分页结果
   */
  public PageResult<RoleResponseDTO> pageDTOWithCondition(RolePageRequest pageRequest) {
    // 判断是否有搜索条件
    boolean hasCondition = StringUtils.hasText(pageRequest.getName()) ||
                           StringUtils.hasText(pageRequest.getRemark()) ||
                           pageRequest.getCreateTime() != null;

    List<SysRole> records;
    Long total;

    if (hasCondition) {
      // 使用带条件的查询
      records = roleMapper.selectPageWithCondition(
          pageRequest.getName(),
          pageRequest.getRemark(),
          pageRequest.getCreateTime(),
          pageRequest.getOffset(),
          pageRequest.getLimit()
      );
      total = roleMapper.countWithCondition(
          pageRequest.getName(),
          pageRequest.getRemark(),
          pageRequest.getCreateTime()
      );
    } else {
      // 使用无条件的查询
      records = roleMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
      total = roleMapper.countAll();
    }

    List<RoleResponseDTO> dtoList = records.stream().map(this::convertToDTO).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 更新角色信息
   *
   * @param roleId 角色ID
   * @param dto    更新角色信息DTO
   * @return 更新行数
   */
  public int updateRoleInfo(Long roleId, UpdateRoleDTO dto) {
    return roleMapper.updateRoleInfo(roleId, dto);
  }

  /**
   * 将SysRole转换为RoleResponseDTO
   *
   * @param role 角色实体
   * @return 角色响应DTO
   */
  private RoleResponseDTO convertToDTO(SysRole role) {
    if (role == null) {
      return null;
    }
    RoleResponseDTO dto = new RoleResponseDTO();
    BeanUtils.copyProperties(role, dto);
    return dto;
  }
}
