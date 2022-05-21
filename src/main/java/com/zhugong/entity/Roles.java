package com.zhugong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 角色实体类
 */
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Roles {

  /**
   * 角色id
   */
  private Integer id;

  /**
   * 角色名
   */
  private String name;

  /**
   * 角色描述
   */
  private String describ;

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

  public String getDescrib() {
    return describ;
  }

  public void setDescrib(String describ) {
    this.describ = describ;
  }

  public List<Rights> getChildren() {
    return children;
  }

  public void setChildren(List<Rights> children) {
    this.children = children;
  }
}
