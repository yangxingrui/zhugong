package com.zhugong.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhugong.entity.Attendence;
import com.zhugong.entity.Attstatus;
import com.zhugong.service.AttendenceService;
import com.zhugong.service.WorkerService;
import com.zhugong.utils.Base64;
import com.zhugong.utils.FaceDetect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendenceController {

    @Resource
    private AttendenceService attendenceService;

    @Resource
    private WorkerService workerService;

    @Resource
    private Base64 base64;

    @Resource
    private FaceDetect faceDetect;
    /**
     * 实时表
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("/attendence")
    @ResponseBody
    public Map<String,Object> getRealtime(@RequestParam("pagenum") Integer pagenum,
                                          @RequestParam("pagesize") Integer pagesize,
                                          @RequestParam("projectid") Integer projectid
                                          ){
        Map<String,Object> realMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //System.out.println("*************"+projectid+"*******************");
        List<Attendence> attendenceList = attendenceService.getRealtime(pagenum,pagesize,projectid);
        //分页信息
        PageInfo pageInfo = new PageInfo(attendenceList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("attendences",attendenceList);
        dataMap.put("total",pageInfo.getTotal());
        realMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","获取考勤表成功");
        realMap.put("meta",metaMap);
        return realMap;
    }

    /**
     * 统计
     * @param pagenum
     * @param pagesize
     * @param name
     * @param year
     * @param month
     * @param days
     * @param state
     * @return
     */
    @GetMapping("/statistics")
    @ResponseBody
    public Map<String,Object> getStatistics(@RequestParam("pagenum") Integer pagenum,
                                          @RequestParam("pagesize") Integer pagesize,
                                          @RequestParam("projectid") Integer projectid,
                                          @RequestParam(value = "name",required = false) String name,
                                          @RequestParam(value = "year",required = false) String year,
                                          @RequestParam(value = "month",required = false) String month,
                                          @RequestParam(value = "days",required = false) String days,
                                          @RequestParam(value = "state",required = false) String state){
        //System.out.println("*********打印查询条件"+name+year+month+days+state+"***************");
        Map<String,Object> staMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        List<Attendence> attendenceList = attendenceService.getStatistics(pagenum,pagesize,projectid,name,year,month,days,state);
        //分页信息
        PageInfo pageInfo = new PageInfo(attendenceList);
        //前端交互
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("attendences",attendenceList);
        dataMap.put("total",pageInfo.getTotal());
        staMap.put("data",dataMap);
        metaMap.put("status",200);
        metaMap.put("msg","获取考勤统计成功");
        staMap.put("meta",metaMap);
        return staMap;
    }


    /**
     * 下载Excel考勤表
     * @param response
     * @throws IOException
     */
    @GetMapping("/attendence/download")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response,
                              @RequestParam("projectid") Integer projectid) throws IOException {
//        Map<String,Object> excelMap = new HashMap<>();
//        Map<String,Object> metaMap = new HashMap<>();
//        metaMap.put("status",null);
//        //前端交互
//        Map<String,Object> dataMap = new HashMap<>();
//        List<Attendence> attendenceList = attendenceService.getExcel();
//        String fileName = "E:\\OneDrive - lefthandofking\\桌面\\" + "easyTest.xlsx";
//        EasyExcel.write(fileName, Attendence.class).sheet("考勤").doWrite(attendenceList);
//        excelMap.put("data",dataMap);
//        metaMap.put("status",200);
//        metaMap.put("msg","考勤表下载成功");
//        excelMap.put("meta",metaMap);
//        return excelMap;

        //从数据库中获得Excel考勤表
        List<Attendence> attendenceList=attendenceService.getExcel(projectid);
        //设置内容类型
        response.setContentType("application/vnd.ms-excel");//针对excel的contentType
        response.setCharacterEncoding("utf-8");//设置编码
        //这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("考勤表下载", "UTF-8");
        //设置响应头及excel文件名称
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //使用EasyExcel.write().sheet().doWrite()方式导出数据
        EasyExcel.write(response.getOutputStream(),Attendence.class).sheet("最新考勤数据").doWrite(attendenceList);
    }

    /**
     * 获得月视图
     * @return
     */
    @GetMapping("/attendence/monthview/{projectid}")
    @ResponseBody
    public Map<String,Object> getMonthView(@PathVariable(value = "projectid") Integer projectid){
        Map<String,Object> monthMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //System.out.println("****************"+projectid+"****************888");
        List<Attendence> attendenceList = attendenceService.getMonthView(projectid);
//        for(Integer i=0;i<attendenceList.size();i++){
//            System.out.println("***************"+attendenceList.get(i).getName());
//        }
        //前端交互
        monthMap.put("data",attendenceList);
        metaMap.put("status",200);
        metaMap.put("msg","获取考勤月视图成功");
        monthMap.put("meta",metaMap);
        return monthMap;
    }

    /**
     * 签到
     * @return
     */
    @PostMapping("/attcheckin")
    @ResponseBody
    public Map<String,Object> checkin(@RequestBody String checkinForm){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(checkinForm);

        //从请求体中获得参数
        String id= json.get("id").getAsString();
        String checkin= json.get("checkin").getAsString();
        String checkinremark= json.get("checkinremark").getAsString();
        String img= json.get("img").getAsString();
        Integer projectid = json.get("projectid").getAsInt();
        Map<String,Object> checkinMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //System.out.println("*********"+checkinForm+"********"+id+"*"+checkin+"*"+checkinremark+"*"+projectid+"**************");
        if(img==null||img.equals("")){
            //前端交互
            metaMap.put("status", 501);
            metaMap.put("msg", "请打开摄像头进行拍照!");
            checkinMap.put("meta", metaMap);
            return checkinMap;
        }
        //System.out.println("*********"+workerService.selectUserById(54).getProjectid());
        if(!projectid.equals(workerService.selectUserById(Integer.valueOf(id)).getProjectid())){
            //前端交互
            metaMap.put("status", 504);
            metaMap.put("msg", "该项目组无此工号!");
            checkinMap.put("meta", metaMap);
            return checkinMap;
        }
        try{
            //从数据库拿参照图片地址
            String standardUrl=workerService.findFaceById(id);
            //System.out.println("**********************"+standardUrl+"***********************");
            //转为base64格式
            String standardImg;
            try{
                standardImg=base64.getBase64Byurl(standardUrl);
            }catch (NullPointerException exception){
                //前端交互
                metaMap.put("status", 505);
                metaMap.put("msg", "您的照片未上传，请联系有关人员");
                checkinMap.put("meta", metaMap);
                return checkinMap;
            }
            //人脸比对不成功
            if(faceDetect.detect(standardImg,img)<80.0){
                //前端交互
                metaMap.put("status", 502);
                metaMap.put("msg", "人脸识别失败，请重新拍照！");
                checkinMap.put("meta", metaMap);
                return checkinMap;
            }
        }catch (IllegalStateException e){
            //前端交互
            metaMap.put("status", 503);
            metaMap.put("msg", "图片中没有人脸！");
            checkinMap.put("meta", metaMap);
            return checkinMap;
        }
        //比对成功，进入考勤分析阶段
        if(attendenceService.existin(id,checkin)!=0){
            //前端交互
            metaMap.put("status", 500);
            metaMap.put("msg", "今日已签到，不可重复签到");
            checkinMap.put("meta", metaMap);
            return checkinMap;
        }
        //System.out.println("*****************"+attendenceService.existin(id,checkin)+"**************");
        //正常签到
        if(attendenceService.normaltime(projectid,checkin)!=null){
            attendenceService.checkinnormal(id,checkin,checkinremark,projectid);
        }
        //迟到
        if(attendenceService.latetime(projectid,checkin)!=null){
            attendenceService.checkinlate(id,checkin,checkinremark,projectid);
        }
        //缺勤
        if(attendenceService.absencein(projectid,checkin)!=null){
            attendenceService.checkinabsence(id,checkin,checkinremark,projectid);
        }
        //前端交互
        metaMap.put("status", 200);
        metaMap.put("msg", "签到成功");
        checkinMap.put("meta", metaMap);
        return checkinMap;
    }

    /**
     * 签退
     * @return
     */
    @PostMapping("/attcheckout")
    @ResponseBody
    public Map<String,Object> checkout(@RequestBody String checkoutForm){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(checkoutForm);

        //从请求体中获得参数
        String id= json.get("id").getAsString();
        String checkout= json.get("checkout").getAsString();
        String checkoutremark= json.get("checkoutremark").getAsString();
        String img= json.get("img").getAsString();
        Integer projectid = json.get("projectid").getAsInt();
        Map<String,Object> checkoutMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //System.out.println("*********"+checkoutForm+"********"+id+"*"+checkout+"*"+checkoutremark+"*"+projectid+"**************");
        if(img==null||img.equals("")){
            //前端交互
            metaMap.put("status", 501);
            metaMap.put("msg", "请打开摄像头进行拍照!");
            checkoutMap.put("meta", metaMap);
            return checkoutMap;
        }
        //System.out.println("*********"+projectid+workerService.selectWorkerById(Integer.valueOf(id)).getProjectid());
        if(!projectid.equals(workerService.selectUserById(Integer.valueOf(id)).getProjectid())){
            //前端交互
            metaMap.put("status", 504);
            metaMap.put("msg", "该项目组无此工号!");
            checkoutMap.put("meta", metaMap);
            return checkoutMap;
        }
        try{
            //从数据库拿参照图片地址
            String standardUrl=workerService.findFaceById(id);
            //System.out.println("**********************"+standardUrl+"***********************");
            //转为base64格式
            String standardImg;
            try{
                standardImg=base64.getBase64Byurl(standardUrl);
            }catch (NullPointerException exception){
                //前端交互
                metaMap.put("status", 505);
                metaMap.put("msg", "您的照片未上传，请联系有关人员");
                checkoutMap.put("meta", metaMap);
                return checkoutMap;
            }

            //人脸比对不成功
            if(faceDetect.detect(standardImg,img)<80.0){
                //前端交互
                metaMap.put("status", 502);
                metaMap.put("msg", "人脸识别失败，请重新拍照！");
                checkoutMap.put("meta", metaMap);
                return checkoutMap;
            }
        }catch (IllegalStateException e){
            //前端交互
            metaMap.put("status", 503);
            metaMap.put("msg", "图片中没有人脸！");
            checkoutMap.put("meta", metaMap);
            return checkoutMap;
        }
        //比对成功，进入考勤分析阶段
        if(attendenceService.existout(id,checkout)!=0){
            //前端交互
            metaMap.put("status", 500);
            metaMap.put("msg", "今日已签退，请勿重复签退！");
            checkoutMap.put("meta", metaMap);
            return checkoutMap;
        }
        if(attendenceService.existinByout(id,checkout)==0){
            //前端交互
            metaMap.put("status", 501);
            metaMap.put("msg", "请先签到！");
            checkoutMap.put("meta", metaMap);
            return checkoutMap;
        }
        //System.out.println("*****************"+attendenceService.existinByout(id,checkout));
        //正常签到+正常签退
        if(attendenceService.normaltimeByout(id,checkout)!=null&&attendenceService.normalouttime(projectid,checkout)!=null){
            attendenceService.checknormal(id,checkout,checkoutremark,projectid);
        }
        //正常签到+早退
        else if(attendenceService.normaltimeByout(id,checkout)!=null&&attendenceService.earlyouttime(projectid,checkout)!=null){
            attendenceService.checkinnormalearly(id,checkout,checkoutremark,projectid);
        }
        //迟到+正常签退
        else if(attendenceService.latetimeByout(id,checkout)!=null&&attendenceService.normalouttime(projectid,checkout)!=null){
            attendenceService.checkinlatenormal(id,checkout,checkoutremark,projectid);
        }
        //迟到+早退
        else if(attendenceService.latetimeByout(id,checkout)!=null&&attendenceService.earlyouttime(projectid,checkout)!=null){
            attendenceService.checkinlateearly(id,checkout,checkoutremark,projectid);
        }
        //否则，缺勤
        else{
            attendenceService.checkabsence(id,checkout,checkoutremark,projectid);
        }
        //前端交互
        metaMap.put("status", 200);
        metaMap.put("msg", "签退成功");
        checkoutMap.put("meta", metaMap);
        return checkoutMap;
    }

    /**
     * 获得某建筑单位考勤标准信息
     * @param projectid
     * @return
     */
    @GetMapping("/attendence/setting/{projectid}")
    @ResponseBody
    public Map<String,Object> Setting(@PathVariable(value = "projectid") Integer projectid){
        Map<String,Object> monthMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        //System.out.println("****************"+projectid+"****************888");
        List<Attstatus> attstatusList = attendenceService.getSetting(projectid);
        //System.out.println("************"+attstatusList.get(1).getTime());
        //前端交互
        monthMap.put("data",attstatusList);
        metaMap.put("status",200);
        metaMap.put("msg","获取考勤状态成功");
        monthMap.put("meta",metaMap);
        return monthMap;
    }

    /**
     * 设置考勤时间
     * @param timeInfo
     * @return
     */
    @PutMapping("/attendence/timeset")
    @ResponseBody
    public Map<String,Object> timeSet(@RequestBody String timeInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(timeInfo);

        //从请求体中获得参数
        String id= json.get("id").getAsString();
        String time= json.get("time").getAsString();
        Map<String,Object> timeMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(attendenceService.timeset(id,time)){
            metaMap.put("status",200);
            metaMap.put("msg","考勤时间设置成功");
        }
        timeMap.put("meta",metaMap);
        return timeMap;
    }

    /**
     * 设置考勤扣款
     * @param fineInfo
     * @return
     */
    @PutMapping("/attendence/fineset")
    @ResponseBody
    public Map<String,Object> fineSet(@RequestBody String fineInfo){
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(fineInfo);

        //从请求体中获得参数
        String id= json.get("id").getAsString();
        String fine= json.get("fine").getAsString();
        Map<String,Object> fineMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",null);
        if(attendenceService.fineset(id,fine)){
            metaMap.put("status",200);
            metaMap.put("msg","考勤扣款设置成功");
        }
        fineMap.put("meta",metaMap);
        return fineMap;
    }
}
