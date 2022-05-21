package com.zhugong.test;

import com.zhugong.dao.WorkerDao;
import com.zhugong.entity.Worker;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class WorkerDaoTest extends BaseJUnit4Test{

    @Resource
    private WorkerDao workerDao;

    @Test
    public void testFindAll(){
        String query=null;
        List<Worker> userList = workerDao.findAll(query);
        System.out.println(userList.size());
    }
}
