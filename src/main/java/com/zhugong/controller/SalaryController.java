package com.zhugong.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhugong.entity.Salary;
import com.zhugong.entity.Wortype;
import com.zhugong.service.SalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SalaryController {

    @Resource
    private SalaryService salaryService;

    /**
     * 核算上报表
     * @param pagenum
     * @param pagesize
     * @param projectid
     * @param month
     * @param state
     * @return
     */
    @GetMapping("/accountreport/list")
    @ResponseBody
    public Map<String,Object> getSalaryList(@RequestParam("pagenum") Integer pagenum,
                                            @RequestParam("pagesize") Integer pagesize,
                                            @RequestParam("projectid") Integer projectid,
                                            @RequestParam(value = "month",required = false) String month,
                                            @RequestParam(value = "state",required = false) String state){
        //System.out.println("*********打印查询条件"+projectid+month+state+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Salary> salaryList = salaryService.getSalary(pagenum,pagesize,projectid,month,state);
        //分页信息
        PageInfo pageInfo = new PageInfo(salaryList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("salarys",salaryList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","获取工资表成功");
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 启动核算
     * @param pagenum
     * @param pagesize
     * @param projectid
     * @param month
     * @return
     */
    @GetMapping("/accountreport/start")
    @ResponseBody
    public Map<String,Object> startAccount(@RequestParam("pagenum") Integer pagenum,
                                            @RequestParam("pagesize") Integer pagesize,
                                            @RequestParam("projectid") Integer projectid,
                                            @RequestParam(value = "accmonth",required = false) String month
                                            ){
        //System.out.println("*********打印查询条件"+projectid+month+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Salary> salaryList = salaryService.startAccount(pagenum,pagesize,projectid,month);
        //分页信息
        PageInfo pageInfo = new PageInfo(salaryList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("salarys",salaryList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","启动核算成功");
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 核算
     * @param pagenum
     * @param pagesize
     * @param projectid
     * @param month
     * @return
     */
    @GetMapping("/accountreport/account")
    @ResponseBody
    public Map<String,Object> Account(@RequestParam("pagenum") Integer pagenum,
                                      @RequestParam("pagesize") Integer pagesize,
                                      @RequestParam("projectid") Integer projectid,
                                      @RequestParam(value = "accountmonth",required = false) String month
    ){
        //System.out.println("*********打印查询条件"+projectid+month+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(salaryService.startAccounted(projectid,month)==0){
            metaMap.put("status",201);
            metaMap.put("msg","请先对该月启动核算");
            staMap.put("meta",metaMap);
            return staMap;
        }
        List<Salary> salaryList = salaryService.Account(pagenum,pagesize,projectid,month);
        //分页信息
        PageInfo pageInfo = new PageInfo(salaryList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("salarys",salaryList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","核算成功");
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 提交
     * @param pagenum
     * @param pagesize
     * @param projectid
     * @param month
     * @return
     */
    @GetMapping("/accountreport/submit")
    @ResponseBody
    public Map<String,Object> Submit(@RequestParam("pagenum") Integer pagenum,
                                     @RequestParam("pagesize") Integer pagesize,
                                     @RequestParam("projectid") Integer projectid,
                                     @RequestParam(value = "submitmonth",required = false) String month
    ){
        //System.out.println("*********打印查询条件"+projectid+month+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(salaryService.Accounted(projectid,month)==0){
            metaMap.put("status",201);
            metaMap.put("msg","请先对该月核算");
            staMap.put("meta",metaMap);
            return staMap;
        }
        List<Salary> salaryList = salaryService.Submit(pagenum,pagesize,projectid,month);
        //分页信息
        PageInfo pageInfo = new PageInfo(salaryList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("salarys",salaryList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","提交成功");
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 汇总
     * @param pagenum
     * @param pagesize
     * @param projectid
     * @param name
     * @param year
     * @param month
     * @param state
     * @return
     */
    @GetMapping("/accountreport/summary")
    @ResponseBody
    public Map<String,Object> Summary(@RequestParam("pagenum") Integer pagenum,
                                     @RequestParam("pagesize") Integer pagesize,
                                     @RequestParam("projectid") Integer projectid,
                                     @RequestParam(value = "name",required = false) String name,
                                     @RequestParam(value = "year",required = false) String year,
                                     @RequestParam(value = "month",required = false) String month,
                                      @RequestParam(value = "state",required = false) String state
    ){
        //System.out.println("*********打印查询条件"+projectid+month+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Salary> salaryList = salaryService.Summary(pagenum,pagesize,projectid,name,year,month,state);
        //分页信息
        PageInfo pageInfo = new PageInfo(salaryList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("salarys",salaryList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","汇总成功");
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 修改薪资，取当前工资
     * @param projectid
     * @return
     */
    @GetMapping("/salary/setting/{projectid}")
    @ResponseBody
    public Map<String,Object> Setting(@PathVariable(value = "projectid") Integer projectid){
        Map<String,Object> monthMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //System.out.println("****************"+projectid+"****************888");
        List<Wortype>  wortypeList= salaryService.getSetting(projectid);
        //前端交互
        monthMap.put("data",wortypeList);
        metaMap.put("status",200);
        metaMap.put("msg","获取工资标准成功");
        monthMap.put("meta",metaMap);
        return monthMap;
    }

    /**
     * 修改薪资
     * @param salaryInfo
     * @return
     */
    @PutMapping("salary/salaryset")
    @ResponseBody
    public Map<String,Object> salarySet(@RequestBody String salaryInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(salaryInfo);

        //从请求体中获得参数
        String id= json.get("id").getAsString();
        String salary = json.get("salary").getAsString();
        Map<String,Object> salaryMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(salaryService.salaryset(id,salary)){
            metaMap.put("status",200);
            metaMap.put("msg","工资设置成功");
        }
        salaryMap.put("meta",metaMap);
        return salaryMap;
    }

    /**
     *添加工种
     * @param salaryInfo
     * @return
     */
    @PutMapping("salary/worktypeset")
    @ResponseBody
    public Map<String,Object> addWorktype(@RequestBody String salaryInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(salaryInfo);

        //从请求体中获得参数
        String worktype= json.get("worktype").getAsString();
        String salary = json.get("salary").getAsString();
        String projectid = json.get("projectid").getAsString();
        //System.out.println("**********"+worktype+salary+projectid);
        Map<String,Object> salaryMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(salaryService.checkWorkType(worktype,projectid)){
            metaMap.put("status",400);
            metaMap.put("msg","该工种已存在");
            salaryMap.put("meta",metaMap);
            return salaryMap;
        }
        if(salaryService.addWorktype(worktype,salary,projectid)){
            metaMap.put("status",200);
            metaMap.put("msg","工种添加成功");
        }
        salaryMap.put("meta",metaMap);
        return salaryMap;
    }

    /**
     * 删除工种
     * @param id
     * @return
     */
    @DeleteMapping("/worktype/{id}")
    @ResponseBody
    public Map<String,Object> removeWorktype(@PathVariable("id") Integer id){
        //System.out.println("*****************"+id);
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> deleteMap = new HashMap<>();
        if(salaryService.deleteWorkTypeById(id)==1){
            metaMap.put("status",200);        //修改成功，重新put状态200
        }
        deleteMap.put("meta",metaMap);
        return deleteMap;
    }
}
