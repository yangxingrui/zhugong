package com.zhugong.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhugong.entity.Project;
import com.zhugong.entity.Salary;
import com.zhugong.service.SalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SupervisionController {

    @Resource
    private SalaryService salaryService;

    /**
     * 单位信息
     * @return
     */
    @GetMapping("/supervision/company")
    @ResponseBody
    public Map<String,Object> getCompany(){
        Map<String,Object> comMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Project> projectList = salaryService.getCompany();
        metaMap.put("status",200);
        metaMap.put("msg","公司表获取成功");
        comMap.put("meta",metaMap);
        comMap.put("data",projectList);
        return comMap;
    }

    /**
     * 上报的工资表
     * @param pagenum
     * @param pagesize
     * @param year
     * @param month
     * @param state
     * @param projectid
     * @return
     */
    @GetMapping("/supervision/list")
    @ResponseBody
    public Map<String,Object> getSalaryList(@RequestParam("pagenum") Integer pagenum,
                                            @RequestParam("pagesize") Integer pagesize,
                                            @RequestParam(value = "year",required = false) String year,
                                            @RequestParam(value = "month",required = false) String month,
                                            @RequestParam(value = "state",required = false) String state,
                                            @RequestParam(value = "projectid",required = false) Integer projectid){
        //System.out.println("*********打印查询条件"+projectid+month+state+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Salary> salaryList = salaryService.getSuperSalary(pagenum,pagesize,year,month,state,projectid);
        //分页信息
        PageInfo pageInfo = new PageInfo(salaryList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("salarys",salaryList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","审批工资表获取成功");
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 审批工资
     * @param id
     * @return
     */
    @PutMapping("supervision/audit/{id}")
    @ResponseBody
    public Map<String,Object> auditone(@PathVariable("id") Integer id){
        //System.out.println("*************"+id);
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(salaryService.auditone(id)==1){
            metaMap.put("status",200);
        }
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 一键审批
     * @param auditInfo
     * @return
     */
    @PutMapping("supervision/auditall")
    @ResponseBody
    public Map<String,Object> auditall(@RequestBody String auditInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(auditInfo);
        String audmonth = json.get("audmonth").getAsString();
        String audprojectid = json.get("audprojectid").getAsString();
        //System.out.println("*************"+auditInfo+auditInfoparam[1]+auditInfoparam[3]);
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(salaryService.auditall(audmonth,audprojectid)!=0){
            metaMap.put("status",200);
        }
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 地图展示单位
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("supervision/map")
    @ResponseBody
    public Map<String,Object> getMap(@RequestParam("pagenum") Integer pagenum,
                                     @RequestParam("pagesize") Integer pagesize){
        Map<String,Object> mapMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //System.out.println("************"+pagenum+pagesize);
        List<Project> projectList = salaryService.getMap(pagenum,pagesize);
        //分页信息
        PageInfo pageInfo = new PageInfo(projectList);
        //projectList.remove(0);
        mapMap.put("data",projectList);
        mapMap.put("total",pageInfo.getTotal());
        metaMap.put("status",200);
        mapMap.put("meta",metaMap);
        return mapMap;
    }
}
