package com.zhugong.entity;

import org.hibernate.validator.constraints.Length;

public class Sms {
    //注册所用手机号
    @Length(min=3,max=11,message = "3~11")
    String phoneNumber = "13659808862";
    //验证码
    String veriCode;
    //验证码有效时间
    int min = 1;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVeriCode() {
        return veriCode;
    }

    public void setVeriCode(String veriCode) {
        this.veriCode = veriCode;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
