package com.zhugong.service;

import com.zhugong.entity.Roles;

import java.util.List;

public interface RolesService {
    List<Roles> getList();

    Integer deleteRightsById(Integer roleid, Integer rightid);

    void addRight(Integer roleid, Integer rightid);

    Integer existRoleRight(Integer roleid, Integer rightid);

    List<String> getRightsByName(String name);

    Roles getRoleByName(String name);

    void editRole(Integer roleid, String text);
}
