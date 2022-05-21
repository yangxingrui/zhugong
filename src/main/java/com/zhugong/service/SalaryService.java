package com.zhugong.service;

import com.zhugong.entity.Project;
import com.zhugong.entity.Salary;
import com.zhugong.entity.Wortype;

import java.util.List;

public interface SalaryService {

    List<Salary> getSalary(Integer pagenum, Integer pagesize, Integer projectid, String month, String state);

    List<Salary> startAccount(Integer pagenum, Integer pagesize, Integer projectid, String month);

    List<Salary> Account(Integer pagenum, Integer pagesize, Integer projectid, String month);

    List<Salary> Submit(Integer pagenum, Integer pagesize, Integer projectid, String month);

    List<Salary> Summary(Integer pagenum, Integer pagesize, Integer projectid, String name, String year, String month, String state);

    List<Salary> getMySalary(Integer pagenum, Integer pagesize, String id, String year, String month, String state);

    Integer signfor(Integer id);

    List<Salary> getSuperSalary(Integer pagenum, Integer pagesize, String year, String month, String state, Integer projectid);

    Integer auditone(Integer id);

    Integer auditall(String audmonth, String audprojectid);

    List<Wortype> getSetting(Integer projectid);

    Boolean salaryset(String id, String salary);

    Boolean addWorktype(String worktype, String salary, String projectid);

    List<Project> getMap(Integer pagenum,Integer size);

    Integer startAccounted(Integer projectid, String month);

    Integer Accounted(Integer projectid, String month);

    List<Project> getCompany();

    Integer deleteWorkTypeById(Integer id);

    Boolean checkWorkType(String worktype, String projectid);
}
