package com.zhugong.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhugong.entity.Rights;
import com.zhugong.entity.Worker;
import com.zhugong.service.RolesService;
import com.zhugong.service.WorkerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Resource
    private WorkerService workerService;

    @Resource
    private RolesService rolesService;

    @Resource
    private RolesController rolesController;

    /**
     * 登录
     * @param login
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody String login){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(login);
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //前端交互
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("data",null);
        loginMap.put("meta",null);
        //状态Map
        Map<String,Object> metaMap = new HashMap<>();
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //调用login方法,SecurityManager收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            System.out.println("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            System.out.println("对用户[" + username + "]进行登录验证..验证通过");

        }catch(UnknownAccountException uae){
            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            metaMap.put("status",500);
            metaMap.put("msg", "未知账户");
            loginMap.put("meta",metaMap);
            return loginMap;
        }catch(IncorrectCredentialsException ice){
            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            metaMap.put("status",500);
            metaMap.put("msg", "密码不正确");
            loginMap.put("meta",metaMap);
            return loginMap;
        }catch(LockedAccountException lae){
            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            metaMap.put("status",500);
            metaMap.put("msg", "账户已锁定");
            loginMap.put("meta",metaMap);
            return loginMap;
        }catch(ExcessiveAttemptsException eae){
            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            metaMap.put("status",500);
            metaMap.put("msg", "用户名或密码错误次数过多");
            loginMap.put("meta",metaMap);
            return loginMap;
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            metaMap.put("status",500);
            metaMap.put("msg", "用户名或密码不正确");
            loginMap.put("meta",metaMap);
            return loginMap;
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            System.out.println("用户[" + username + "]登录认证通过");
            //设置session1h过期
            SecurityUtils.getSubject().getSession().setTimeout(3600000);
            //生成token空间
            Map<String, Object> tokenMap = new HashMap<>();
            Worker worker = workerService.selectWorkerByName(username);
            tokenMap.put("token",worker);
            //获取该用户具有的权限
            List<String> rightslist = rolesService.getRightsByName(username);
            String[] rights=new String[rightslist.size()];
            rightslist.toArray(rights);
            tokenMap.put("rights",rights);
            try{
                //获取该用户具有的树形权限,以渲染菜单
                List<Rights> menus= rolesController.getRightsByRole(username);
                tokenMap.put("menus",menus);
            }catch(NullPointerException exception){
                //加入token
                metaMap.put("msg","请联系有关部门分配角色");
                metaMap.put("status",500);
                loginMap.put("meta",metaMap);
                return loginMap;
            }
            //加入token
            loginMap.put("data",tokenMap);
            metaMap.put("msg","登录成功");
            metaMap.put("status",200);
            loginMap.put("meta",metaMap);

        }else{
            token.clear();
        }
        return loginMap;
    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    @ResponseBody
    public Map<String,Object> logout(){
        System.out.println("******id为{"+SecurityUtils.getSubject().getSession().getId()+"}的会话销毁");
        //在这里执行退出系统前需要清空的数据
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            subject.logout();
        }
        System.out.println("退出登录成功");
        //前端交互
        Map<String,Object> logoutMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",200);
        metaMap.put("msg","退出！");
        logoutMap.put("meta",metaMap);
        return logoutMap;

    }
}
