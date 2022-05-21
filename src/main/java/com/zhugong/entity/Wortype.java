package com.zhugong.entity;


public class Wortype {

  private Integer id;
  private String worktype;
  private double salary;
  private Integer projectid;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getWorktype() {
    return worktype;
  }

  public void setWorktype(String worktype) {
    this.worktype = worktype;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public Integer getProjectid() {
    return projectid;
  }

  public void setProjectid(Integer projectid) {
    this.projectid = projectid;
  }
}
