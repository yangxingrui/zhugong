package com.zhugong.service;

import com.zhugong.entity.Worker;

import java.util.List;


public interface WorkerService {

    List<Worker> findAll(Integer pagenum, Integer pagesize, String query);

    List<Worker> findProjectAll(Integer pagenum, Integer pagesize, Integer projectid, String query);

    int insert(Worker worker);

    boolean findByNamePassword(String name,String password);

    Integer changeStateById(Integer id, Boolean state);


    Worker selectWorkerById(Integer id);

    Integer updateWorkerById(Integer id, String name, String age,String phonenum, String projectid, String worktype);

    Integer updateWorkerNoTypeById(Integer id, String name, String age,String phonenum, String projectid);

    Integer addWorker(String name, String password, String age,String phonenum);

    Integer addProjectWorker(String name, String password, String age, String phonenum, String projectid);

    Integer deleteWorkerById(Integer id);

    Integer changeRoleById(Integer id, String rid);

    Worker selectWorkerByName(String name);

    String findFaceById(String id);

    Boolean changePhonenum(String id, String phonenum);

    Boolean changeBasic(String id, String age);

    Boolean changePass(String id, String pass);

    Worker findByName(String username);

    Worker selectUserById(Integer id);

    Integer checkUserName(String userName);

    Integer checkEditUserName(Integer id, String userName);

    Integer updateWorkerFaceById(String id, String imgFilePath);

    Worker selectWorkerByIdAssignedProject(Integer id);

    Worker selectWorkerByIdAssignedRole(Integer id);



}
