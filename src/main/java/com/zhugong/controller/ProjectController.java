package com.zhugong.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhugong.entity.Project;
import com.zhugong.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 获得单位列表
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("/project/list")
    @ResponseBody
    public Map<String,Object> findAll(@RequestParam("pagenum") Integer pagenum,
                                      @RequestParam("pagesize") Integer pagesize){
        List<Project> projectList;
        //查询
        projectList = projectService.findAllCons(pagenum,pagesize);
        //分页信息
        PageInfo pageInfo = new PageInfo(projectList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("projects",projectList);
        dataMap.put("total",pageInfo.getTotal());
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",200);
        Map<String,Object> projectMap = new HashMap<>();
        projectMap.put("data",dataMap);
        projectMap.put("meta",metaMap);
        return projectMap;
    }

    /**
     * 删除单位
     * @param id
     * @return
     */
    @DeleteMapping("/projects/{id}")
    @ResponseBody
    public Map<String,Object> removeProject(@PathVariable("id") Integer id){
        //System.out.println("*****************"+id);
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> deleteMap = new HashMap<>();
        if(projectService.deleteProjectById(id)==1){
            metaMap.put("status",200);        //修改成功，重新put状态200
        }
        deleteMap.put("meta",metaMap);
        return deleteMap;
    }

    /**
     * 添加单位
     * @param projectInfo
     * @return
     */
    @PutMapping("projects/add")
    @ResponseBody
    public Map<String,Object> addProject(@RequestBody String projectInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(projectInfo);

        //从请求体中获得参数
        String companyname= json.get("companyname").getAsString();
        String address = json.get("address").getAsString();
        String lng = json.get("lng").getAsString();
        String lat = json.get("lat").getAsString();
        String state = json.get("state").getAsString();
        //System.out.println("**********"+companyname+address+lng+lat+state);
        Map<String,Object> projectMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(projectService.checkProject(companyname)){
            metaMap.put("status",400);
            metaMap.put("msg","该单位已存在");
            projectMap.put("meta",metaMap);
            return projectMap;
        }
        if(projectService.addProject(companyname,address,lng,lat,state)&&projectService.addSetting(companyname)){
            metaMap.put("status",200);
            metaMap.put("msg","单位添加成功");
        }
        projectMap.put("meta",metaMap);
        return projectMap;
    }

    /**
     * 修改单位信息前获取当前信息
     * @param id
     * @return
     */
    @GetMapping("/projects/edit/{id}")
    @ResponseBody
    public Map<String,Object> editProjectInfo(@PathVariable("id") Integer id){
        Project project;
        //查询
        project = projectService.editProjectInfo(id);
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",200);
        Map<String,Object> projectMap = new HashMap<>();
        projectMap.put("data",project);
        projectMap.put("meta",metaMap);
        return projectMap;
    }

    /**
     * 编辑单位信息
     * @param editInfo
     * @return
     */
    @PostMapping("/projects/edit")
    @ResponseBody
    public Map<String,Object> editProjectInfo(@RequestBody String editInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(editInfo);

        //从请求体中获得参数
        String id= json.get("id").getAsString();
        String companyname= json.get("companyname").getAsString();
        String address = json.get("address").getAsString();
        String lng = json.get("lng").getAsString();
        String lat = json.get("lat").getAsString();
        String state = json.get("state").getAsString();

        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        Map<String,Object> projectMap = new HashMap<>();
        metaMap.put("status",null);
        metaMap.put("msg",null);
        //查询单位名称是否重复
        if(projectService.checkEditProject(id,companyname)){
            metaMap.put("status",400);
            metaMap.put("msg","该单位已存在");
            projectMap.put("meta",metaMap);
            return projectMap;
        }
        if(projectService.updateProject(companyname,address,lng,lat,state,id)){
            metaMap.put("status",200);
            projectMap.put("msg","编辑单位成功");
        }
        projectMap.put("meta",metaMap);
        return projectMap;
    }
}
