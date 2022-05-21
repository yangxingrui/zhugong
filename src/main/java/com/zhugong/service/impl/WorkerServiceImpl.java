package com.zhugong.service.impl;

import com.github.pagehelper.PageHelper;
import com.zhugong.dao.WorkerDao;
import com.zhugong.entity.Worker;
import com.zhugong.service.WorkerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Resource
    private WorkerDao workerDao;

    @Override
    public List<Worker> findAll(Integer pagenum, Integer pagesize, String query){
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return workerDao.findAll(query);
    }

    @Override
    public List<Worker> findProjectAll(Integer pagenum, Integer pagesize, Integer projectid, String query) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return workerDao.findProjectAll(projectid,query);
    }

    @Override
    public int insert(Worker worker){return workerDao.insert(worker);}

    @Override
    public boolean findByNamePassword(String name,String password){
        return !Objects.equals(workerDao.findByNamePassword(name, password), "") && workerDao.findByNamePassword(name, password) != null;
    }

    @Override
    public Integer changeStateById(Integer id, Boolean state){
        return workerDao.changeStateById(id,state);
    }

    @Override
    public Worker selectWorkerById(Integer id){
        return workerDao.selectWorkerById(id);
    }

    @Override
    public Integer updateWorkerById(Integer id, String name, String age, String phonenum, String projectid, String worktype){
        return workerDao.updateWorkerById(id,name,age,phonenum,projectid,worktype);
    }

    @Override
    public Integer updateWorkerNoTypeById(Integer id, String name, String age, String phonenum, String projectid){
        return workerDao.updateWorkerNoTypeById(id,name,age,phonenum,projectid);
    }

    @Override
    public Integer addWorker(String name, String password, String age, String phonenum) {
        return workerDao.addWorker(name,password,age,phonenum);
    }

    @Override
    public Integer addProjectWorker(String name, String password, String age, String phonenum, String projectid) {
        return workerDao.addProjectWorker(name,password,age,phonenum,projectid);
    }

    @Override
    public Integer deleteWorkerById(Integer id) {
        return workerDao.deleteWorkerById(id);
    }

    @Override
    public Integer changeRoleById(Integer id, String rid) {
        return workerDao.changeRoleById(id,rid);
    }

    @Override
    public Worker selectWorkerByName(String name) {
        return workerDao.selectWorkerByName(name);
    }

    @Override
    public String findFaceById(String id) {
        return workerDao.findFaceById(id);
    }

    @Override
    public Boolean changePhonenum(String id, String phonenum) {
        return workerDao.changePhonenum(id,phonenum);
    }

    @Override
    public Boolean changeBasic(String id, String age) {
        return workerDao.changeBasic(id,age);
    }

    @Override
    public Boolean changePass(String id, String pass) {
        return workerDao.changePass(id,pass);
    }

    @Override
    public Worker findByName(String username) {
        return workerDao.findByName(username);
    }

    @Override
    public Worker selectUserById(Integer id) {
        return workerDao.selectUserById(id);
    }

    @Override
    public Integer checkUserName(String userName) {
        return workerDao.checkUserName(userName);
    }

    @Override
    public Integer checkEditUserName(Integer id, String userName) {
        return workerDao.checkEditUserName(id,userName);
    }

    @Override
    public Integer updateWorkerFaceById(String id, String imgFilePath) {
        return workerDao.updateFaceById(id,imgFilePath);
    }

    @Override
    public Worker selectWorkerByIdAssignedProject(Integer id) {
        return workerDao.selectWorkerByIdAssignedProject(id);
    }

    @Override
    public Worker selectWorkerByIdAssignedRole(Integer id) {
        return workerDao.selectWorkerByIdAssignedRole(id);
    }
}
