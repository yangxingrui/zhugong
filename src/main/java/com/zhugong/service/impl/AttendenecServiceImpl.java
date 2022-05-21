package com.zhugong.service.impl;

import com.github.pagehelper.PageHelper;
import com.zhugong.dao.AttendenceDao;
import com.zhugong.entity.Attendence;
import com.zhugong.entity.Attstatus;
import com.zhugong.service.AttendenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttendenecServiceImpl implements AttendenceService {

    @Resource
    private AttendenceDao attendenceDao;

    @Override
    public List<Attendence> getRealtime(Integer pagenum, Integer pagesize, Integer projectid) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return attendenceDao.getRealtime(projectid);
    }

    @Override
    public List<Attendence> getExcel(Integer projectid) {
        return attendenceDao.getRealtime(projectid);
    }

    @Override
    public List<Attendence> getStatistics(Integer pagenum, Integer pagesize, Integer projectid, String name, String year, String month, String days, String state) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return attendenceDao.getStatistics(projectid,name,year,month,days,state);
    }

    @Override
    public List<Attendence> getMonthView(Integer projectid) {
        String state = "正常";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.getMonthView(projectid,stateid);
    }

    @Override
    public Integer checkinnormal(String id, String checkin, String checkinremark,Integer projectid) {
        String state = "正常签到";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkin(id,checkin,checkinremark,stateid);
    }

    @Override
    public Boolean normaltime(Integer projectid, String checkin) {
        return attendenceDao.normaltime(projectid, checkin);
    }

    @Override
    public Boolean latetime(Integer projectid, String checkin) {
        return attendenceDao.latetime(projectid,checkin);
    }

    @Override
    public Integer checkinlate(String id, String checkin, String checkinremark, Integer projectid) {
        String state = "迟到";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkin(id,checkin,checkinremark,stateid);
    }

    @Override
    public Boolean absencein(Integer projectid, String checkin) {
        return attendenceDao.absencein(projectid,checkin);
    }

    @Override
    public Integer checkinabsence(String id, String checkin, String checkinremark,Integer projectid) {
        String state = "缺勤";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkin(id,checkin,checkinremark,stateid);
    }

    @Override
    public Integer existin(String id, String checkin) {
        return attendenceDao.existin(id,checkin);
    }

    @Override
    public Integer existout(String id, String checkout) {
        return attendenceDao.existout(id,checkout);
    }

    @Override
    public Boolean normalouttime(Integer projectid, String checkout) {
        return attendenceDao.normalouttime(projectid,checkout);
    }

    @Override
    public Boolean earlyouttime(Integer projectid,String checkout) {
        return attendenceDao.earlyouttime(projectid,checkout);
    }

    @Override
    public Boolean normaltimeByout(String id, String checkout) {
        return attendenceDao.normaltimeByout(id,checkout);
    }

    @Override
    public Integer checknormal(String id, String checkout, String checkoutremark,Integer projectid) {
        String state = "正常";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkout(id,checkout,checkoutremark,stateid);
    }

    @Override
    public Integer checkinnormalearly(String id, String checkout, String checkoutremark,Integer projectid) {
        String state = "正常签到+早退";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkout(id,checkout,checkoutremark,stateid);
    }

    @Override
    public Boolean latetimeByout(String id, String checkout) {
        return attendenceDao.latetimeByout(id,checkout);
    }

    @Override
    public Integer checkinlatenormal(String id, String checkout, String checkoutremark,Integer projectid) {
        String state = "迟到+正常签退";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkout(id,checkout,checkoutremark,stateid);
    }

    @Override
    public Integer checkinlateearly(String id, String checkout, String checkoutremark,Integer projectid) {
        String state = "迟到+早退";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkout(id,checkout,checkoutremark,stateid);
    }

    @Override
    public Integer checkabsence(String id, String checkout, String checkoutremark,Integer projectid) {
        String state = "缺勤";
        Integer stateid = attendenceDao.selectState(projectid,state);
        return attendenceDao.checkout(id,checkout,checkoutremark,stateid);
    }

    @Override
    public Integer existinByout(String id, String checkout) {
        return attendenceDao.existinByout(id,checkout);
    }

    @Override
    public List<Attendence> getAttendence(Integer pagenum, Integer pagesize, String id, String year, String month, String days, String state) {
        //开始分页
        PageHelper.startPage(pagenum,pagesize);
        return attendenceDao.getAttendence(id,year,month,days,state);
    }

    @Override
    public List<Attstatus> getSetting(Integer projectid) {
        return attendenceDao.getSetting(projectid);
    }

    @Override
    public Boolean timeset(String id, String time) {
        return attendenceDao.timeset(id,time);
    }

    @Override
    public Boolean fineset(String id, String fine) {
        return attendenceDao.fineset(id,fine);
    }
}
