package com.zhugong.controller;

import com.zhugong.entity.Rights;
import com.zhugong.entity.Roles;
import com.zhugong.service.RightsService;
import com.zhugong.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private RightsService rightsService;

    /**
     * 根据角色构建权限树
     * @return
     */
    private List<Rights> buildRightsTree(List<Rights> rightsList, Integer pid,Integer roleid){
        List<Rights> treelist = new ArrayList<>();
        Rights right;
        for(Integer i=0;i<rightsList.size();i++) {
            right = rightsList.get(i);
            if (Objects.equals(pid, right.getPid())) {
                if(rolesService.existRoleRight(roleid,right.getId())!=null) {
//                    System.out.println("*********角色"+roleid+"权限"+right.getId()+
//                            "匹配"+rolesService.existRoleRight(roleid,right.getId())+"************");
                    right.setChildren(buildRightsTree(rightsList, right.getId(), roleid));
                    treelist.add(right);
//                    System.out.println("添加"+right.getId()+"到权限树中");
                }
//                else System.out.println("*********角色"+roleid+"权限"+right.getId()+"不匹配"+
//                        rolesService.existRoleRight(roleid,right.getId())+"************");
            }
        }
        return treelist;
    }


    /**
     * 获取角色权限列表
     * @return
     */
    @GetMapping("/roles/list")
    @ResponseBody
    public Map<String,Object> getRolesRights(){

        //System.out.println("*************获取角色方法被激活************");
        Map<String,Object> rolesMap = new HashMap<>();
        //角色集合，权限集合
        List<Roles> rolesList = rolesService.getList();
        List<Rights> rightsList;
        for(Integer i=0;i<rolesList.size();i++){

            //rightsList = rightsService.getTreeByRole(rolesList.get(i).getId());
            rightsList = rightsService.getList();
            rightsList = buildRightsTree(rightsList,0,rolesList.get(i).getId());
            rolesList.get(i).setChildren(rightsList);
        }
        //放入map集合
        rolesMap.put("data", rolesList);
        //状态Map
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("msg","获取角色列表成功");
        metaMap.put("status",200);
        rolesMap.put("meta",metaMap);
        return rolesMap;
    }

    /**
     * 根据角色获得权限列表
     * @return
     */
    public List<Rights> getRightsByRole(String name){

        //角色集合，权限集合
        Roles role = rolesService.getRoleByName(name);
        List<Rights> rightsList;
        rightsList = rightsService.getList();
        rightsList = buildRightsTree(rightsList,0,role.getId());
        return rightsList;
    }

    /**
     * 递归删除权限树所有权限
     */
    private void pretravelse(List<Rights> rightsList,Integer roleid){
        for(Integer i=0;i<rightsList.size();i++){
            //System.out.println("**********要删除的权限数是"+rightsList.get(i).getId()+"****************");
            if(rightsList.get(i).getChildren().size()!=0){
                pretravelse(rightsList.get(i).getChildren(),roleid);
            }
            rolesService.deleteRightsById(roleid,rightsList.get(i).getId());
        }
    }

    /**
     * 删除角色权限
     */
    @DeleteMapping("/roles/{roleid}/rights/{rightid}")
    @ResponseBody
    public Map<String,Object> removRight(@PathVariable("roleid") Integer roleid,
                                         @PathVariable("rightid") Integer rightid){
        //System.out.println("***********"+roleid+"*************"+rightid);
        //前端交互
        Map<String,Object> deleteMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Rights> rightsList;
        rightsList = rightsService.getList();
        rightsList = buildRightsTree(rightsList,rightid,roleid);
        pretravelse(rightsList,roleid);
        rolesService.deleteRightsById(roleid,rightid);
        metaMap.put("status",200);
        metaMap.put("msg","取消权限成功");

        deleteMap.put("meta",metaMap);
        //根据角色构建新的权限树
        List<Rights> newrightsList = rightsService.getList();
        newrightsList = buildRightsTree(newrightsList,0, roleid);
        deleteMap.put("data",newrightsList);
        return deleteMap;
    }


//    /**
//     * 递归查询权限树底层权限
//     */
//    private void pretravelseselect(List<Rights> rightsList,List<Integer> defkeys){
//        for(Integer i=0;i<rightsList.size();i++){
//            //System.out.println("**********要删除的权限数是"+rightsList.get(i).getId()+"****************");
//            if(rightsList.get(i).getChildren().size()!=0){
//                pretravelseselect(rightsList.get(i).getChildren(),defkeys);
//            }
//            if(rightsList.get(i).getChildren().size()==0) defkeys.add(rightsList.get(i).getId());
//        }
//    }
//
//    /**
//     * 获取数组型角色权限表
//     */
//    @GetMapping("/role/{roleid}/rights")
//    @ResponseBody
//    public Map<String,Object> getRoleRightslist(@PathVariable("roleid") Integer roleid){
//
//        //前端交互
//        Map<String,Object> getMap = new HashMap<>();
//        Map<String,Object> metaMap = new HashMap<>();
//        metaMap.put("status",null);
//        List<Rights> rightsList;
//        rightsList = rightsService.getList();
//        rightsList = buildRightsTree(rightsList,0,roleid);
//        List<Integer> integerList =new ArrayList<>();
//        pretravelseselect(rightsList,integerList);//System.out.println(integerList);
//        getMap.put("data",integerList);
//        metaMap.put("status",200);
//        metaMap.put("msg","获取角色权限成功");
//
//        getMap.put("meta",metaMap);
//        return getMap;
//    }

    /**
     * 添加角色权限
     */
    @PostMapping("/roles/{roleid}/rights")
    @ResponseBody
    public Map<String,Object> addRights(@PathVariable("roleid") Integer roleid,
                                        @RequestBody String addInfo){
        addInfo=addInfo.replace(",",":");
        addInfo=addInfo.replace("{","");
        addInfo=addInfo.replace("}","");
        addInfo=addInfo.replace("\"","");
        String[] addInfoparam = addInfo.split(":");
        //System.out.println("************"+addInfoparam[1]+addInfoparam[2]+"**********");
        //前端交互
        Map<String,Object> addMap = new HashMap<>();
        for(Integer i=1;i<addInfoparam.length;i++){
            //System.out.println("************"+addInfoparam[i]+"**********");
            rolesService.addRight(roleid,Integer.valueOf(addInfoparam[i]));
        }
        //状态Map
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("msg","添加成功");
        metaMap.put("status",200);
        addMap.put("meta",metaMap);
        return addMap;
    }

    /**
     * 获取角色表
     * @return
     */
    @GetMapping("/roles")
    @ResponseBody
    public Map<String,Object> getRoles(){

        //System.out.println("*************获取角色方法被激活************");
        Map<String,Object> rolesMap = new HashMap<>();
        //角色集合
        List<Roles> rolesList = rolesService.getList();
        //放入map集合
        rolesMap.put("data", rolesList);
        //状态Map
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("msg","获取角色列表成功");
        metaMap.put("status",200);
        rolesMap.put("meta",metaMap);
        return rolesMap;
    }

    /**
     * 编辑角色信息
     * @param roleid
     * @param text
     * @return
     */
    @GetMapping("roles/edit")
    @ResponseBody
    public Map<String,Object> editRole(@RequestParam("roleid") Integer roleid,
                                       @RequestParam("text") String text){
        Map<String,Object> rolesMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        rolesService.editRole(roleid,text);
        metaMap.put("status",200);
        metaMap.put("msg","角色修改成功");
        rolesMap.put("meta",metaMap);
        return rolesMap;
    }
}
