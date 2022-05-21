package com.zhugong.service.impl;


import com.zhugong.dao.RightsDao;
import com.zhugong.entity.Rights;
import com.zhugong.service.RightsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RightsServiceImpl implements RightsService {

    @Resource
    private RightsDao rightsDao;

    public List<Rights> getTree(){

        return rightsDao.getTree();
    }

//    public List<Rights> getTreeByRole(long id){
//        return rightsDao.getTreeByRole(id);
//    }

    public List<Rights> getList(){
        return rightsDao.getList();
    }

}
