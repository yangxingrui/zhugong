package com.zhugong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 权限实体类
 */
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Rights {

  /**
   * 权限id
   */
  private Integer id;

  /**
   * 权限名称
   */
  private String name;

  /**
   * 权限层级
   */
  private String level;

  /**
   * 父级权限id
   */
  private Integer pid;

  /**
   * 访问路径
   */
  private String path;

  /**
   * 子权限
   */
  private List<Rights> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Rights> getChildren() {
        return children;
    }

    public void setChildren(List<Rights> children) {
        this.children = children;
    }
}
