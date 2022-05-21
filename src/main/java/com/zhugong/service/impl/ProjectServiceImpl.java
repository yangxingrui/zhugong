package com.zhugong.service.impl;

import com.github.pagehelper.PageHelper;
import com.zhugong.dao.ProjectDao;
import com.zhugong.entity.Project;
import com.zhugong.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectDao projectDao;

    @Override
    public List<Project> getFullCompany() {
        return projectDao.getFullCompany();
    }

    @Override
    public List<Project> getOneCompany(Integer id) {
        return projectDao.getOneCompany(id);
    }

    @Override
    public List<Project> findAllCons(Integer pagenum, Integer pagesize) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return projectDao.findAllCons();
    }

    @Override
    public Integer deleteProjectById(Integer id) {
        return projectDao.deleteProjectById(id);
    }

    @Override
    public Boolean checkProject(String companyname) {
        return projectDao.checkProject(companyname);
    }

    @Override
    public Boolean checkEditProject(String id, String companyname) {
        return projectDao.checkEditProject(id,companyname);
    }

    @Override
    public Boolean addProject(String companyname, String address, String lng, String lat, String state) {
        return projectDao.addProject(companyname,address,lng,lat,state);
    }

    @Override
    public Boolean addSetting(String companyname) {
        Integer projectid = projectDao.selectByName(companyname);
        return projectDao.addSetting("正常签到", "08:00:00", 20, projectid) &&
                projectDao.addSetting("迟到", "08:10:00", 20, projectid) &&
                projectDao.addSetting("正常签退", "17:00:00", 0, projectid) &&
                projectDao.addSetting("早退", "16:50:00", 20, projectid) &&
                projectDao.addSetting("缺勤", null, 100, projectid) &&
                projectDao.addSetting("正常", null, 0, projectid) &&
                projectDao.addSetting("正常签到+早退", null, 20, projectid) &&
                projectDao.addSetting("迟到+正常签退", null, 20, projectid) &&
                projectDao.addSetting("迟到+早退", null, 40, projectid);
    }

    @Override
    public Project editProjectInfo(Integer id) {
        return projectDao.editProjectInfo(id);
    }

    @Override
    public Boolean updateProject(String companyname, String address, String lng, String lat, String state, String id) {
        return projectDao.updateProject(companyname,address,lng,lat,state,id);
    }
}
