package com.zhugong.entity;


import java.util.Date;

public class Salary {

  private Integer id;
  private Integer workerid;
  private Date month;
  private double payable;
  private double realpay;
  private double normalearly;
  private double latenormal;
  private double lateearly;
  private double absence;
  private String accstate;
  private String submit;
  private String audit;
  private String signfor;
  //视图层
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getWorkerid() {
    return workerid;
  }

  public void setWorkerid(Integer workerid) {
    this.workerid = workerid;
  }

  public Date getMonth() {
    return month;
  }

  public void setMonth(Date month) {
    this.month = month;
  }

  public double getPayable() {
    return payable;
  }

  public void setPayable(double payable) {
    this.payable = payable;
  }

  public double getRealpay() {
    return realpay;
  }

  public void setRealpay(double realpay) {
    this.realpay = realpay;
  }

  public double getNormalearly() {
    return normalearly;
  }

  public void setNormalearly(double normalearly) {
    this.normalearly = normalearly;
  }

  public double getLatenormal() {
    return latenormal;
  }

  public void setLatenormal(double latenormal) {
    this.latenormal = latenormal;
  }

  public double getLateearly() {
    return lateearly;
  }

  public void setLateearly(double lateearly) {
    this.lateearly = lateearly;
  }

  public double getAbsence() {
    return absence;
  }

  public void setAbsence(double absence) {
    this.absence = absence;
  }

  public String getAccstate() {
    return accstate;
  }

  public void setAccstate(String accstate) {
    this.accstate = accstate;
  }

  public String getSubmit() {
    return submit;
  }

  public void setSubmit(String submit) {
    this.submit = submit;
  }

  public String getAudit() {
    return audit;
  }

  public void setAudit(String audit) {
    this.audit = audit;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSignfor() {
    return signfor;
  }

  public void setSignfor(String signfor) {
    this.signfor = signfor;
  }
}
