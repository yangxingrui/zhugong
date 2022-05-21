package com.zhugong.service;

import com.zhugong.entity.Wortype;

import java.util.List;

public interface WortypeService {
    List<Wortype> findByPro(Integer projectid);
}
