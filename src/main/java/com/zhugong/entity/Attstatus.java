package com.zhugong.entity;


import java.util.Date;

public class Attstatus {

  private Integer id;
  private String name;
  private Date time;
  private String fine;
  private Integer projectid;

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

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getFine() {
    return fine;
  }

  public void setFine(String fine) {
    this.fine = fine;
  }

  public Integer getProjectid() {
    return projectid;
  }

  public void setProjectid(Integer projectid) {
    this.projectid = projectid;
  }
}
