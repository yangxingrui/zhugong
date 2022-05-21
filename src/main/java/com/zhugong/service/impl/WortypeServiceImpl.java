package com.zhugong.service.impl;

import com.zhugong.dao.WortypeDao;
import com.zhugong.entity.Wortype;
import com.zhugong.service.WortypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WortypeServiceImpl implements WortypeService {

    @Resource
    private WortypeDao wortypeDao;

    @Override
    public List<Wortype> findByPro(Integer projectid) {
        return wortypeDao.selectByPro(projectid);
    }
}
