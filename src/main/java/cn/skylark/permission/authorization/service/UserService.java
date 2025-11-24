package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.dto.UpdateUserDTO;
import cn.skylark.permission.authorization.dto.UserPageRequest;
import cn.skylark.permission.authorization.dto.UserResponseDTO;
import cn.skylark.permission.authorization.entity.SysRole;
import cn.skylark.permission.authorization.entity.SysUser;
import cn.skylark.permission.authorization.mapper.UserMapper;
import cn.skylark.permission.utils.PageRequest;
import cn.skylark.permission.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Resource
  private UserMapper userMapper;

  public SysUser get(Long id) {
    return userMapper.selectById(id);
  }

  public List<SysUser> list() {
    return userMapper.selectAll();
  }

  /**
   * 获取用户列表（DTO，不包含密码）
   *
   * @return 用户列表
   */
  public List<UserResponseDTO> listDTO() {
    List<SysUser> users = userMapper.selectAll();
    return users.stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  public int create(SysUser user) {
    return userMapper.insert(user);
  }

  public int update(SysUser user) {
    return userMapper.update(user);
  }

  public int delete(Long id) {
    userMapper.deleteUserRoles(id);
    return userMapper.deleteById(id);
  }

  public List<SysRole> roles(Long userId) {
    return userMapper.selectRolesByUserId(userId);
  }

  public void bindRoles(Long userId, List<Long> roleIds) {
    userMapper.deleteUserRoles(userId);
    if (!CollectionUtils.isEmpty(roleIds)) {
      userMapper.insertUserRoles(userId, roleIds);
    }
  }

  public SysUser findByUsername(String username) {
    return userMapper.findByUsername(username);
  }

  /**
   * 重置密码
   *
   * @param userId      用户ID
   * @param oldPassword 旧密码
   * @param newPassword 新密码
   * @return true-成功，false-失败
   */
  public boolean changePassword(Long userId, String oldPassword, String newPassword) {
    SysUser user = userMapper.selectById(userId);
    if (user == null) {
      return false;
    }

    // 验证旧密码（当前使用NoOpPasswordEncoder，密码是明文存储）
    if (oldPassword == null || !oldPassword.equals(user.getPassword())) {
      return false;
    }

    // 更新密码
    int result = userMapper.updatePassword(userId, newPassword);
    return result > 0;
  }

  /**
   * 分页查询用户列表
   *
   * @param pageRequest 分页请求参数
   * @return 分页结果
   */
  public PageResult<SysUser> page(PageRequest pageRequest) {
    List<SysUser> records = userMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
    Long total = userMapper.countAll();
    return new PageResult<>(records, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 分页查询用户列表（DTO，不包含密码）
   *
   * @param pageRequest 分页请求参数
   * @return 分页结果
   */
  public PageResult<UserResponseDTO> pageDTO(PageRequest pageRequest) {
    List<SysUser> records = userMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
    Long total = userMapper.countAll();
    List<UserResponseDTO> dtoList = records.stream().map(this::convertToDTO).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 分页查询用户列表（带条件，DTO，不包含密码）
   *
   * @param pageRequest 分页请求参数（包含搜索条件）
   * @return 分页结果
   */
  public PageResult<UserResponseDTO> pageDTOWithCondition(UserPageRequest pageRequest) {
    // 判断是否有搜索条件
    boolean hasCondition = StringUtils.hasText(pageRequest.getUsername()) ||
                           StringUtils.hasText(pageRequest.getPhone()) ||
                           StringUtils.hasText(pageRequest.getEmail()) ||
                           pageRequest.getCreateTime() != null;

    List<SysUser> records;
    Long total;

    if (hasCondition) {
      // 使用带条件的查询
      records = userMapper.selectPageWithCondition(
          pageRequest.getUsername(),
          pageRequest.getPhone(),
          pageRequest.getEmail(),
          pageRequest.getCreateTime(),
          pageRequest.getOffset(),
          pageRequest.getLimit()
      );
      total = userMapper.countWithCondition(
          pageRequest.getUsername(),
          pageRequest.getPhone(),
          pageRequest.getEmail(),
          pageRequest.getCreateTime()
      );
    } else {
      // 使用无条件的查询
      records = userMapper.selectPage(pageRequest.getOffset(), pageRequest.getLimit());
      total = userMapper.countAll();
    }

    List<UserResponseDTO> dtoList = records.stream().map(this::convertToDTO).collect(Collectors.toList());
    return new PageResult<>(dtoList, total, pageRequest.getPage(), pageRequest.getSize());
  }

  /**
   * 获取用户信息（DTO，不包含密码）
   *
   * @param id 用户ID
   * @return 用户信息
   */
  public UserResponseDTO getDTO(Long id) {
    SysUser user = userMapper.selectById(id);
    return user != null ? convertToDTO(user) : null;
  }

  /**
   * 更新用户信息（不包含密码）
   *
   * @param userId 用户ID
   * @param dto    更新用户信息DTO
   * @return 更新行数
   */
  public int updateUserInfo(Long userId, UpdateUserDTO dto) {
    return userMapper.updateUserInfo(userId, dto);
  }

  /**
   * 将SysUser转换为UserResponseDTO（不包含密码）
   *
   * @param user 用户实体
   * @return 用户响应DTO
   */
  private UserResponseDTO convertToDTO(SysUser user) {
    if (user == null) {
      return null;
    }
    UserResponseDTO dto = new UserResponseDTO();
    BeanUtils.copyProperties(user, dto);
    return dto;
  }
}

