package com.zhugong.service.impl;

import com.github.pagehelper.PageHelper;
import com.zhugong.dao.AttendenceDao;
import com.zhugong.dao.SalaryDao;
import com.zhugong.entity.Account;
import com.zhugong.entity.Project;
import com.zhugong.entity.Salary;
import com.zhugong.entity.Wortype;
import com.zhugong.service.SalaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Resource
    private SalaryDao salaryDao;

    @Resource
    private AttendenceDao attendenceDao;

    @Override
    public List<Salary> getSalary(Integer pagenum, Integer pagesize, Integer projectid, String month, String state) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.getSalary(projectid,month,state);
    }

    @Override
    public List<Salary> startAccount(Integer pagenum, Integer pagesize, Integer projectid, String month) {
        //获得本班组/部门/公司所有员工本月的工资信息(未核算状态)
        salaryDao.getSalaryMonth(projectid,month+"-01");
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.startAccount(projectid,month);
    }

    @Override
    public List<Salary> Account(Integer pagenum, Integer pagesize, Integer projectid, String month) {
        //核算每人在该月的考勤状态
        List<Account> statusMonth = salaryDao.statusMonth(projectid,month+"-01");
        //根据工种表和考勤状态表计算工资
        for(int i=0;i<statusMonth.size();i++){
            //非正常状态扣工资
            Double singlefine = salaryDao.fine(statusMonth.get(i).getState());
            Double fine = singlefine*statusMonth.get(i).getTime();
            String state = salaryDao.getStateById(statusMonth.get(i).getState());
            switch (state){
                case "正常签到+早退":
                    salaryDao.setNormalEarly(statusMonth.get(i).getId(),month,fine);break;
                case "迟到+正常签退":
                    salaryDao.setLateNormal(statusMonth.get(i).getId(),month,fine);break;
                case "迟到+早退":
                    salaryDao.setLateEarly(statusMonth.get(i).getId(),month,fine);break;
                case "缺勤":
                    salaryDao.setAbsence(statusMonth.get(i).getId(),month,fine);break;
                default:break;
            }
            try{
                if(!Objects.equals(statusMonth.get(i).getId(), statusMonth.get(i + 1).getId())){
                    //本月应得工资
                    int days = attendenceDao.getAttDays(statusMonth.get(i).getId(),month);
                    Double payable = salaryDao.payable(statusMonth.get(i).getId(),days);
                    salaryDao.setPayable(statusMonth.get(i).getId(),month,payable);
                    salaryDao.setRealpay(statusMonth.get(i).getId(),month);
                    salaryDao.setAccounted(statusMonth.get(i).getId(),month);
                }
            }catch(IndexOutOfBoundsException ignored){
                //本月应得工资
                int days = attendenceDao.getAttDays(statusMonth.get(i).getId(),month);
                Double payable = salaryDao.payable(statusMonth.get(i).getId(),days);
                salaryDao.setPayable(statusMonth.get(i).getId(),month,payable);
                salaryDao.setRealpay(statusMonth.get(i).getId(),month);
                salaryDao.setAccounted(statusMonth.get(i).getId(),month);
            }
        }
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.getAccount(projectid,month);
    }

    @Override
    public List<Salary> Submit(Integer pagenum, Integer pagesize, Integer projectid, String month) {
        List<Integer> list = salaryDao.getShouldSubmit(projectid,month);
        for(int i=0;i<list.size();i++){
            salaryDao.submit(list.get(i),month);
        }
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.getSubmit(projectid,month);
    }

    @Override
    public List<Salary> Summary(Integer pagenum, Integer pagesize, Integer projectid, String name, String year, String month, String state) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.Summary(projectid,name,year,month,state);
    }

    @Override
    public List<Salary> getMySalary(Integer pagenum, Integer pagesize, String id, String year, String month, String state) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.getMySalary(id,year,month,state);
    }

    @Override
    public Integer signfor(Integer id) {
        return salaryDao.signfor(id);
    }

    @Override
    public List<Salary> getSuperSalary(Integer pagenum, Integer pagesize, String year, String month, String state, Integer projectid) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.getSuperSalary(year,month,state,projectid);
    }

    @Override
    public Integer auditone(Integer id) {
        return salaryDao.auditone(id);
    }

    @Override
    public Integer auditall(String audmonth, String audprojectid) {
        return salaryDao.auditall(audmonth,audprojectid);
    }

    @Override
    public List<Wortype> getSetting(Integer projectid) {
        return salaryDao.getSetting(projectid);
    }

    @Override
    public Boolean salaryset(String id, String salary) {
        return salaryDao.salaryset(id,salary);
    }

    @Override
    public Boolean addWorktype(String worktype, String salary, String projectid) {
        return salaryDao.addWorktype(worktype,salary,projectid);
    }

    @Override
    public List<Project> getMap(Integer pagenum,Integer pagesize) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return salaryDao.getMap();
    }

    @Override
    public Integer startAccounted(Integer projectid, String month) {
        return salaryDao.startAccounted(projectid,month);
    }

    @Override
    public Integer Accounted(Integer projectid, String month) {
        return salaryDao.Accounted(projectid,month);
    }

    @Override
    public List<Project> getCompany() {
        return salaryDao.getMap();
    }

    @Override
    public Integer deleteWorkTypeById(Integer id) {
        return salaryDao.deleteWorkTypeById(id);
    }

    @Override
    public Boolean checkWorkType(String worktype, String projectid) {
        return salaryDao.checkWorkType(worktype,projectid);
    }
}
