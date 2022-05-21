package com.zhugong.service.impl;

import com.zhugong.dao.RolesDao;
import com.zhugong.entity.Roles;
import com.zhugong.service.RolesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Resource
    private RolesDao rolesDao;

    public List<Roles> getList(){
        return rolesDao.getList();
    }

    public Integer deleteRightsById(Integer roleid, Integer rightid){
        return rolesDao.deleteRightsById(roleid,rightid);
    }

    @Override
    public void addRight(Integer roleid, Integer rightid) {
        rolesDao.addRight(roleid,rightid);
    }

    @Override
    public Integer existRoleRight(Integer roleid, Integer rightid) {
        return rolesDao.existRoleRight(roleid,rightid);
    }

    @Override
    public List<String> getRightsByName(String name) {
        return rolesDao.getRightsByName(name);
    }

    @Override
    public Roles getRoleByName(String name) {
        return rolesDao.getRoleByName(name);
    }

    @Override
    public void editRole(Integer roleid, String text) {
        rolesDao.editRole(roleid,text);
    }
}
