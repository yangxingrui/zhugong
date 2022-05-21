package com.zhugong.service;

import com.zhugong.entity.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getFullCompany();

    List<Project> getOneCompany(Integer id);

    List<Project> findAllCons(Integer pagenum, Integer pagesize);

    Integer deleteProjectById(Integer id);

    Boolean checkProject(String companyname);

    Boolean addProject(String companyname, String address, String lng, String lat, String state);

    Boolean addSetting(String companyname);

    Project editProjectInfo(Integer id);

    Boolean updateProject(String companyname, String address, String lng, String lat, String state, String id);

    Boolean checkEditProject(String id, String companyname);
}
