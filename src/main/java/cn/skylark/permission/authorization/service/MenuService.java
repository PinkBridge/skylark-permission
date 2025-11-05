package cn.skylark.permission.authorization.service;

import cn.skylark.permission.authorization.entity.SysMenu;
import cn.skylark.permission.authorization.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MenuService {

  @Resource
  private MenuMapper menuMapper;

  public SysMenu get(Long id) {
    return menuMapper.selectById(id);
  }

  public List<SysMenu> list() {
    return menuMapper.selectAll();
  }

  public int create(SysMenu menu) {
    return menuMapper.insert(menu);
  }

  public int update(SysMenu menu) {
    return menuMapper.update(menu);
  }

  public int delete(Long id) {
    return menuMapper.deleteById(id);
  }

  public List<SysMenu> listByRole(Long roleId) {
    return menuMapper.selectByRoleId(roleId);
  }

  public void bindRoleMenus(Long roleId, List<Long> menuIds) {
    menuMapper.deleteBindingsByRoleId(roleId);
    if (menuIds != null && !menuIds.isEmpty()) {
      menuMapper.bindRoleMenus(roleId, menuIds);
    }
  }

  public List<MenuNode> userMenuTree(String username) {
    List<SysMenu> menus = menuMapper.selectMenusByUsername(username);
    return buildTree(menus);
  }

  private List<MenuNode> buildTree(List<SysMenu> menus) {
    Map<Long, MenuNode> map = new LinkedHashMap<>();
    for (SysMenu m : menus) {
      MenuNode n = toNode(m);
      map.put(n.getId(), n);
    }
    List<MenuNode> roots = new ArrayList<>();
    for (MenuNode n : map.values()) {
      if (n.getParentId() == null || !map.containsKey(n.getParentId())) {
        roots.add(n);
      } else {
        map.get(n.getParentId()).getChildren().add(n);
      }
    }
    sort(roots);
    return roots;
  }

  private void sort(List<MenuNode> list) {
    list.sort(Comparator.comparing(MenuNode::getSort, Comparator.nullsLast(Integer::compareTo))
            .thenComparing(MenuNode::getId));
    for (MenuNode n : list) {
      sort(n.getChildren());
    }
  }

  private MenuNode toNode(SysMenu m) {
    MenuNode n = new MenuNode();
    n.setId(m.getId());
    n.setParentId(m.getParentId());
    n.setName(m.getName());
    n.setPath(m.getPath());
    n.setIcon(m.getIcon());
    n.setSort(m.getSort());
    n.setHidden(Boolean.TRUE.equals(m.getHidden()));
    n.setModuleKey(m.getModuleKey());
    n.setChildren(new ArrayList<>());
    return n;
  }

  public static class MenuNode {
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String icon;
    private Integer sort;
    private boolean hidden;
    private String moduleKey;
    private List<MenuNode> children;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Long getParentId() {
      return parentId;
    }

    public void setParentId(Long parentId) {
      this.parentId = parentId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public String getIcon() {
      return icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }

    public Integer getSort() {
      return sort;
    }

    public void setSort(Integer sort) {
      this.sort = sort;
    }

    public boolean getHidden() {
      return hidden;
    }

    public void setHidden(boolean hidden) {
      this.hidden = hidden;
    }

    public String getModuleKey() {
      return moduleKey;
    }

    public void setModuleKey(String moduleKey) {
      this.moduleKey = moduleKey;
    }

    public List<MenuNode> getChildren() {
      return children;
    }

    public void setChildren(List<MenuNode> children) {
      this.children = children;
    }
  }
}
