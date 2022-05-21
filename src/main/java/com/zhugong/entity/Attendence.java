package com.zhugong.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import java.util.Date;

/**
 * 考勤表，前台展示及下载
 */

@ContentRowHeight(14) //设置文本行高度
@HeadRowHeight(15) //设置标题高度
@ColumnWidth(10) // 默认列宽
public class Attendence {

  @ExcelIgnore
  private Integer id;
  @ExcelIgnore
  private Integer workerid;
  @ExcelProperty("用户名")
  private String name;
  @ExcelProperty("签到时间")
  @ColumnWidth(22)
  private Date checkin;
  @ExcelProperty("签退时间")
  @ColumnWidth(22)
  private Date checkout;
  @ExcelProperty("考勤状态")
  @ColumnWidth(13)
  private String status;
  @ExcelIgnore
  private String state;
  @ExcelProperty("签到备注")
  @ColumnWidth(30)
  private String checkinremark;
  @ExcelProperty("签退备注")
  @ColumnWidth(30)
  private String checkoutremark;


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

  public Date getCheckin() {
    return checkin;
  }

  public void setCheckin(Date checkin) {
    this.checkin = checkin;
  }

  public Date getCheckout() {
    return checkout;
  }

  public void setCheckout(Date checkout) {
    this.checkout = checkout;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCheckinremark() {
    return checkinremark;
  }

  public void setCheckinremark(String checkinremark) {
    this.checkinremark = checkinremark;
  }

  public String getCheckoutremark() {
    return checkoutremark;
  }

  public void setCheckoutremark(String checkoutremark) {
    this.checkoutremark = checkoutremark;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
