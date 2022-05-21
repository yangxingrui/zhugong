package com.zhugong.controller;

import com.zhugong.entity.Rights;
import com.zhugong.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RightsController {

    @Autowired
    private RightsService rightsService;

    /**
     * 权限列表
     * @return
     */
    @GetMapping("/rights/list")
    @ResponseBody
    public Map<String,Object> getRights(){

        Map<String,Object> rightsMap = new HashMap<>();
        //菜单集合
        List<Rights> rightsList = rightsService.getList();
        //放入map集合
        rightsMap.put("data", rightsList);
        //状态Map
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("msg","获取权限列表成功");
        metaMap.put("status",200);
        rightsMap.put("meta",metaMap);
        return rightsMap;
    }

    /**
     * 权限树
     * @return
     */
    @GetMapping("/rights/tree")
    @ResponseBody
    public Map<String,Object> getRightsTree(){

        Map<String,Object> rightsMap = new HashMap<>();
        //菜单集合
        List<Rights> rightsList = rightsService.getTree();
        //放入map集合
        rightsMap.put("data", rightsList);
        //状态Map
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("msg","获取权限列表成功");
        metaMap.put("status",200);
        rightsMap.put("meta",metaMap);
        return rightsMap;
    }
}
