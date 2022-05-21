package com.zhugong.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhugong.entity.Project;
import com.zhugong.entity.Worker;
import com.zhugong.entity.Wortype;
import com.zhugong.service.ProjectService;
import com.zhugong.service.WorkerService;
import com.zhugong.service.WortypeService;
import com.zhugong.utils.Base64;
import com.zhugong.utils.PasswordHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Resource
    private WorkerService workerService;

    @Resource
    private ProjectService projectService;

    @Resource
    private WortypeService wortypeService;

    @Resource
    private PasswordHelper passwordHelper;//封装加密方法

    @Resource
    private Base64 base64;

    /**
     * 添加用户检查用户名是否重复
     * @param userName
     * @return
     */
    @GetMapping("/username")
    @ResponseBody
    public Map<String,Object> checkUserName(@RequestParam("name") String userName){
        //前端交互
        Map<String,Object> nameMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(workerService.checkUserName(userName)==0){
            metaMap.put("status",200);
            nameMap.put("meta",metaMap);
            return nameMap;
        }
        nameMap.put("meta",metaMap);
        return nameMap;
    }

    /**
     * 修改用户检查用户名是否重复
     * @param id
     * @param userName
     * @return
     */
    @GetMapping("edit/username")
    @ResponseBody
    public Map<String,Object> checkEditUserName(@RequestParam("id") Integer id,
                                                @RequestParam("name") String userName){
        //前端交互
        Map<String,Object> nameMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(workerService.checkEditUserName(id,userName)==0){
            metaMap.put("status",200);
            nameMap.put("meta",metaMap);
            return nameMap;
        }
        nameMap.put("meta",metaMap);
        return nameMap;
    }

    /**
     * 查询所有工人信息
     * @return
     */
    @GetMapping("/users")
    @ResponseBody
    public Map<String,Object> findAll(@RequestParam("pagenum") Integer pagenum,
                                      @RequestParam("pagesize") Integer pagesize,
                                      @RequestParam("query") String query){
        List<Worker> workerList;
        //根据查询条件模糊查询
        workerList = workerService.findAll(pagenum,pagesize,query);
        String faceUrl;
        //添加人脸照片
        for(int i=0;i<workerList.size();i++){
            faceUrl = workerList.get(i).getFace();
          if(faceUrl == null || faceUrl.length() <= 0){
              //未添加照片
              System.out.println(workerList.get(i).getName()+"未上传图片");
          }else{
              workerList.get(i).setImgBase(base64.getBase64Byurl(faceUrl));
              System.out.println(workerList.get(i).getName()+"上传图片");
          }
        }
        //分页信息
        PageInfo pageInfo = new PageInfo(workerList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("users",workerList);
        dataMap.put("total",pageInfo.getTotal());
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",200);
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("data",dataMap);
        userMap.put("meta",metaMap);
        return userMap;
    }


    /**
     * 查询建筑单位所有工人信息
     * @param pagenum
     * @param pagesize
     * @param projectid
     * @param query
     * @return
     */
    @GetMapping("/subusers")
    @ResponseBody
    public Map<String,Object> findProjectAll(@RequestParam("pagenum") Integer pagenum,
                                             @RequestParam("pagesize") Integer pagesize,
                                             @RequestParam("projectid") Integer projectid,
                                             @RequestParam("query") String query){
        List<Worker> workerList;
        //根据查询条件模糊查询
        workerList = workerService.findProjectAll(pagenum,pagesize,projectid,query);
        //人脸照片路径
        String faceUrl;
        //添加人脸照片
        for(int i=0;i<workerList.size();i++){
            faceUrl = workerList.get(i).getFace();
            if(faceUrl == null || faceUrl.length() <= 0){
                //未添加照片
                System.out.println(workerList.get(i).getName()+"未上传图片");
            }else{
                workerList.get(i).setImgBase(base64.getBase64Byurl(faceUrl));
                System.out.println(workerList.get(i).getName()+"上传图片");
            }
        }
        //分页信息
        PageInfo pageInfo = new PageInfo(workerList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("users",workerList);
        dataMap.put("total",pageInfo.getTotal());
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",200);
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("data",dataMap);
        userMap.put("meta",metaMap);
        return userMap;
    }

    /**
     * 添加用户
     */
    @PostMapping("/users")
    @ResponseBody
    public Map<String,Object> addUser(@RequestBody String addInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(addInfo);
        String name = json.get("name").getAsString();
        String oldpassword = json.get("password").getAsString();
        String age = json.get("age").getAsString();
        String phonenum = json.get("phonenum").getAsString();
        //System.out.println("************"+addInfoparam[1]+addInfoparam[3]+addInfoparam[5]+"**********");
        //加密码MD5加盐加密
        String password = passwordHelper.encryptPassword(name,oldpassword);
        //前端交互
        Map<String,Object> addMap = new HashMap<>();
        if(workerService.addWorker(name,password,age,phonenum)==1){
            //状态Map
            Map<String,Object> metaMap = new HashMap<>();
            metaMap.put("msg","添加成功");
            metaMap.put("status",201);
            addMap.put("meta",metaMap);
        }
        return addMap;
    }

    /**
     * 建筑单位添加用户
     * @param addInfo
     * @return
     */
    @PostMapping("/subusers")
    @ResponseBody
    public Map<String,Object> addProjectUser(@RequestBody String addInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(addInfo);
        String name = json.get("name").getAsString();
        String oldpassword = json.get("password").getAsString();
        String age = json.get("age").getAsString();
        String phonenum = json.get("phonenum").getAsString();
        String projectid = json.get("projectid").getAsString();
        //System.out.println("************"+addInfoparam[1]+addInfoparam[3]+addInfoparam[5]+"**********");
        System.out.println("************"+projectid);
        //加密码MD5加盐加密
        String password = passwordHelper.encryptPassword(name,oldpassword);
        //前端交互
        Map<String,Object> addMap = new HashMap<>();
        if(workerService.addProjectWorker(name,password,age,phonenum,projectid)==1){
            //状态Map
            Map<String,Object> metaMap = new HashMap<>();
            metaMap.put("msg","添加成功");
            metaMap.put("status",201);
            addMap.put("meta",metaMap);
        }
        return addMap;
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/users/{id}")
    @ResponseBody
    public Map<String,Object> removeUser(@PathVariable("id") Integer id){
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> deleteMap = new HashMap<>();
        if(workerService.deleteWorkerById(id)==1){
            metaMap.put("status",200);        //修改成功，重新put状态200
        }
        deleteMap.put("meta",metaMap);
        return deleteMap;
    }

    /**
     * 修改工人在职
     */
    @PutMapping("/users/{userinfo.id}/state/{userinfo.state}")
    @ResponseBody
    public Map<String,Object> changeUserState(@PathVariable("userinfo.id") Integer id, @PathVariable("userinfo.state") Boolean state){

        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> stateMap = new HashMap<>();
        if(workerService.changeStateById(id,state)==1){
            metaMap.put("status",200);        //修改成功，重新put状态200
        }
        stateMap.put("meta",metaMap);
        return stateMap;
    }

    /**
     * 得到某建筑单位工种
     * @param projectid
     * @return
     */
    @GetMapping("workertype")
    @ResponseBody
    public Map<String,Object> getWorkerType(@RequestParam("projectid") Integer projectid){
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> typeMap = new HashMap<>();
        List<Wortype> wortypeList = wortypeService.findByPro(projectid);
        metaMap.put("status",200);        //修改成功，重新put状态200
        metaMap.put("msg","查询成功");
        typeMap.put("meta",metaMap);
        typeMap.put("data",wortypeList);
        return typeMap;
    }

    /**
     * 修改用户信息前置条件，根据id查询
     */
    @GetMapping(value = "/users/{id}")
    @ResponseBody
    public Map<String,Object> findUserInfo(@PathVariable("id") Integer id){

        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> InfoMap = new HashMap<>();
        Worker worker;
        if((worker = workerService.selectWorkerById(id))==null){
            //特殊类型账号，存在不属于某个项目组（公司），无工种等情况
            worker=workerService.selectUserById(id);
        }
        List<Project> projectList = projectService.getFullCompany();
        InfoMap.put("pro",projectList);

        metaMap.put("status",200);        //修改成功，重新put状态200
        metaMap.put("msg","查询成功");
        InfoMap.put("meta",metaMap);
        InfoMap.put("data",worker);
        return InfoMap;
    }

    /**
     * 修改建筑单位用户信息前置条件，根据id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/subusers/{id}")
    @ResponseBody
    public Map<String,Object> findProjectUserInfo(@PathVariable("id") Integer id){

        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> InfoMap = new HashMap<>();
        Worker worker;
        if((worker = workerService.selectWorkerById(id))==null){
            //特殊类型账号，存在不属于某个项目组（公司），无工种等情况
            worker=workerService.selectUserById(id);
        }
        List<Project> projectList = projectService.getOneCompany(id);
        InfoMap.put("pro",projectList);

        metaMap.put("status",200);        //修改成功，重新put状态200
        metaMap.put("msg","查询成功");
        InfoMap.put("meta",metaMap);
        InfoMap.put("data",worker);
        return InfoMap;
    }

    /**
     *删除文件
     * @param fileName 这是图片的路径
     * @return
     */
    public boolean deleteFile(String fileName){
        File file = new File(fileName);
        //判断文件存不存在
        if(!file.exists()){
            System.out.println("删除文件失败："+fileName+"不存在！");
            return false;
        }else{
            //判断这是不是一个文件，ps：有可能是文件夹
            if(file.isFile()){
                return file.delete();
            }
        }
        return false;
    }


    /**
     * 保存人脸图片
     * @param faceInfo
     * @return
     */
    @PostMapping("/users/face")
    @ResponseBody
    public Map<String,Object> uploadFace(@RequestBody String faceInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(faceInfo);
        String id = json.get("id").getAsString();
        String type = json.get("type").getAsString();
        String face = json.get("face").getAsString();
        //System.out.println("************文件格式"+type);
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> faceMap = new HashMap<>();
        //删除原图片
        String oldFacePath = workerService.findFaceById(id);
        try{
            deleteFile(oldFacePath);
        }catch(NullPointerException exception){
          //首次添加照片
        }
        //图片名
        String uuidFileName=UUID.randomUUID().toString().replace("-","")+ type;
        //图片地址
        String imgFilePath = "D:\\D\\IDEA\\workspace\\zhugong\\src\\Face\\"+uuidFileName;
        //将图片保存到文件夹
        base64.GenerateImage(face,imgFilePath);
        //将图片地址保存到数据库
        if(workerService.updateWorkerFaceById(id,imgFilePath)!=0) {
            metaMap.put("status", 200);
            faceMap.put("meta", metaMap);
        }
        return faceMap;
    }

    /**
     * 提交修改用户申请，正式修改
     */
    @PutMapping(value = "/users/{id}")
    @ResponseBody
    public Map<String,Object> changeUserInfo(@PathVariable("id") Integer id,
                                             @RequestBody String changeInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(changeInfo);
        String name = json.get("name").getAsString();
        String age = json.get("age").getAsString();
        String phonenum = json.get("phonenum").getAsString();
        String projectid = json.get("projectid").getAsString();

        //System.out.println("**********"+name+age+phonenum+projectid+worktype);
        //System.out.println("**************"+changeInfoparam[1]);
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> stateMap = new HashMap<>();
        String worktype;
        try{
            worktype = json.get("worktype").getAsString();
            if(workerService.updateWorkerById(id,name,age,phonenum,projectid,worktype)==1){
                metaMap.put("status",200);        //修改成功，重新put状态200
            }
        }catch(UnsupportedOperationException exception){
            if(workerService.updateWorkerNoTypeById(id,name,age,phonenum,projectid)==1){
                metaMap.put("status",200);        //修改成功，重新put状态200
            }
        }

        stateMap.put("meta",metaMap);
        return stateMap;
    }


    /**
     * 分配用户角色
     */
    @PutMapping("/users/{id}/role")
    @ResponseBody
    public Map<String,Object> changeUserRole(@PathVariable("id") Integer id, @RequestBody String srid){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(srid);
        String rid = json.get("rid").getAsString();
        //前端交互
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        Map<String,Object> workerMap = new HashMap<>();
        if(workerService.changeRoleById(id,rid)==1){
            metaMap.put("status",200);
            metaMap.put("msg","设置角色成功");
        }
        workerMap.put("meta",metaMap);
        Worker worker = workerService.selectWorkerById(id);
        workerMap.put("data",worker);
        return workerMap;
    }

}
