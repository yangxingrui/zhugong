package com.zhugong.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhugong.entity.Attendence;
import com.zhugong.entity.Salary;
import com.zhugong.entity.Worker;
import com.zhugong.service.AttendenceService;
import com.zhugong.service.SalaryService;
import com.zhugong.service.WorkerService;
import com.zhugong.utils.Base64;
import com.zhugong.utils.PasswordHelper;
import com.zhugong.utils.TecentSmsTool;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class MyInfoController {

    @Resource
    private AttendenceService attendenceService;

    @Resource
    private SalaryService salaryService;

    @Resource
    private WorkerService workerService;

    @Resource
    private Base64 base64;

    @Resource
    private PasswordHelper passwordHelper;

    /**
     * 个人信息考勤表
     * @param pagenum
     * @param pagesize
     * @param id
     * @param year
     * @param month
     * @param days
     * @param state
     * @return
     */
    @GetMapping("/myinfo/attendence")
    @ResponseBody
    public Map<String,Object> getAttendence(@RequestParam("pagenum") Integer pagenum,
                                            @RequestParam("pagesize") Integer pagesize,
                                            @RequestParam("id") String id,
                                            @RequestParam(value = "year",required = false) String year,
                                            @RequestParam(value = "month",required = false) String month,
                                            @RequestParam(value = "days",required = false) String days,
                                            @RequestParam(value = "state",required = false) String state){
        //System.out.println("*********打印查询条件"+name+year+month+days+state+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Attendence> attendenceList = attendenceService.getAttendence(pagenum,pagesize,id,year,month,days,state);
        //分页信息
        PageInfo pageInfo = new PageInfo(attendenceList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("attendences",attendenceList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","获取考勤表成功");
        staMap.put("meta",metaMap);
        return staMap;
    }


    /**
     * 个人信息工资表
     * @param pagenum
     * @param pagesize
     * @param id
     * @param year
     * @param month
     * @param state
     * @return
     */
    @GetMapping("/myinfo/salary")
    @ResponseBody
    public Map<String,Object> Summary(@RequestParam("pagenum") Integer pagenum,
                                      @RequestParam("pagesize") Integer pagesize,
                                      @RequestParam("id") String id,
                                      @RequestParam(value = "year",required = false) String year,
                                      @RequestParam(value = "month",required = false) String month,
                                      @RequestParam(value = "state",required = false) String state
    ){
        //System.out.println("*********打印查询条件"+projectid+month+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Salary> salaryList = salaryService.getMySalary(pagenum,pagesize,id,year,month,state);
        //分页信息
        PageInfo pageInfo = new PageInfo(salaryList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("salarys",salaryList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","工资表获取成功");
        staMap.put("meta",metaMap);
        return staMap;
    }

    /**
     * 签收
     * @param id
     * @return
     */
    @PutMapping("myinfo/signfor/{id}")
    @ResponseBody
    public Map<String,Object> signfor(@PathVariable("id") Integer id){
        //System.out.println("*************"+id);
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(salaryService.signfor(id)==1){
            metaMap.put("status",200);
        }
        staMap.put("meta",metaMap);
        return staMap;
    }

    @GetMapping("myinfo/basic")
    @ResponseBody
    public Map<String,Object> getBasic(@RequestParam("id") Integer id){
        //System.out.println("*********打印查询条件"+id+"***************");
        Map<String,Object> basicMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //已分配工种
        Worker worker = workerService.selectWorkerById(id);
        //已分配项目组
        if(worker == null){
            worker = workerService.selectWorkerByIdAssignedProject(id);
        }
        //已分配角色
        if(worker == null){
            worker = workerService.selectWorkerByIdAssignedRole(id);
        }
        //未分配
        if(worker == null){
            worker = workerService.selectUserById(id);
        }
        //获取图片
        try{
            String standardImg=base64.getBase64Byurl(worker.getFace());
            worker.setImgBase(standardImg);
        }catch(NullPointerException exception){
            //无此人照片

        }
        //前端交互
        basicMap.put("data",worker);
        metaMap.put("status",200);
        metaMap.put("msg","获取成功");
        basicMap.put("meta",metaMap);
        return basicMap;
    }

    /**
     * 务工情况
     * @param id
     * @return
     */
    @GetMapping("myinfo/work")
    @ResponseBody
    public Map<String,Object> getWork(@RequestParam("id") Integer id){
        //System.out.println("*********打印查询条件"+id+"***************");
        Map<String,Object> workMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Worker worker = workerService.selectWorkerById(id);
        //前端交互
        workMap.put("data",worker);
        metaMap.put("status",200);
        metaMap.put("msg","获取成功");
        workMap.put("meta",metaMap);
        return workMap;
    }

    /**
     * 发送验证码
     * @param phonenum
     * @return
     */
    @GetMapping("myinfo/code")
    @ResponseBody
    public Map<String,Object> getCode(@RequestParam("phonenum") String phonenum){
        //System.out.println("*********打印查询条件"+phonenum+"***************");
        Map<String,Object> basicMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //生成六位随机验证码
        String msgcode = String.format("%06d", new Random().nextInt(1000000));
        //调用腾讯云短信
        new TecentSmsTool().main(phonenum,msgcode);
        //前端交互
        basicMap.put("data",msgcode);
        metaMap.put("status",200);
        metaMap.put("msg","验证码发送成功!");
        basicMap.put("meta",metaMap);
        return basicMap;
    }

    /**
     * 修改手机号
     * @param phoneInfo
     * @return
     */
    @PutMapping("myinfo/newcode")
    @ResponseBody
    public Map<String,Object> putCode(@RequestBody String phoneInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(phoneInfo);
        String id = json.get("id").getAsString();
        String phonenum = json.get("phonenum").getAsString();
        //System.out.println("*********打印查询条件"+phonenum+"***************");
        Map<String,Object> basicMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //保存手机号
        if(workerService.changePhonenum(id,phonenum)){
            //前端交互
            metaMap.put("status",200);
            metaMap.put("msg","手机号保存成功!");
        }
        basicMap.put("meta",metaMap);
        return basicMap;
    }

    /**
     * 保存基本信息
     * @param saveInfo
     * @return
     */
    @PutMapping("myinfo/basic/save")
    @ResponseBody
    public Map<String,Object> putBasic(@RequestBody String saveInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(saveInfo);
        String id = json.get("id").getAsString();
        String age = json.get("age").getAsString();
        //System.out.println("*********打印查询条件"+age+"***************");
        Map<String,Object> basicMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //保存年龄
        if(workerService.changeBasic(id,age)){
            //前端交互
            metaMap.put("status",200);
            metaMap.put("msg","保存成功");
        }
        basicMap.put("meta",metaMap);
        return basicMap;
    }

    /**
     * 修改密码
     * @param passInfo
     * @return
     */
    @PutMapping("myinfo/basic/pass")
    @ResponseBody
    public Map<String,Object> putBasicPass(@RequestBody String passInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(passInfo);
        String id = json.get("id").getAsString();
        String pass = json.get("pass").getAsString();
        //密码加盐加密
        String password = passwordHelper.encryptPassword((String) SecurityUtils.getSubject().getPrincipal(),pass);
        //System.out.println("*********打印查询条件"+(String) SecurityUtils.getSubject().getPrincipal()+"***************");
        Map<String,Object> basicMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //保存密码
        if(workerService.changePass(id,password)){
            //前端交互
            metaMap.put("status",200);
            metaMap.put("msg","保存成功");
        }
        basicMap.put("meta",metaMap);
        return basicMap;
    }
}
