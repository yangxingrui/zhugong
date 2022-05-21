package com.zhugong.service;

import com.zhugong.entity.Rights;

import java.util.List;

public interface RightsService {

    List<Rights> getTree();

//    List<Rights> getTreeByRole(long id);

    List<Rights> getList();


}
