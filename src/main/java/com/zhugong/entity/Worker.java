package com.zhugong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Worker implements Serializable {
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @Length(min = 6,max = 16,message = "密码长度必须在3~16位之间")
    private String password;
    @Range(min = 0,max = 150,message = "年龄必须在0~150岁之间")
    private Integer age;
    //注册所用手机号
    @Length(min=3,max=11,message = "3~11")
    String phonenum = "13659808862";
    //标识该工人是否在职
    private Boolean state;
    //工种id
    private Integer worktypeid;
    //工种
    private String worktype;
    //角色
    private String rolename;
    //项目id
    private Integer projectid;
    //项目组
    private String companyname;
    //薪水
    private Double salary;
    //人脸图片地址
    private String face;
    //base64格式人脸图片
    private String imgBase;
    //验证码
    String code;

    public Worker(){}
    public Worker(String name, String password, Integer age, String phonenum) {

        this.name = name;
        this.password = password;
        this.age = age;
        this.phonenum = phonenum;
    }

    public Worker(Long id,String name, String password, Integer age, String phonenum, String worktype, String rolename){
        this.id=id;
        this.name=name;
        this.password=password;
        this.age=age;
        this.phonenum=phonenum;
        this.worktype=worktype;
        this.rolename=rolename;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getWorktypeid() {
        return worktypeid;
    }

    public void setWorktypeid(Integer worktypeid) {
        this.worktypeid = worktypeid;
    }

    public String getImgBase() {
        return imgBase;
    }

    public void setImgBase(String imgBase) {
        this.imgBase = imgBase;
    }
}
