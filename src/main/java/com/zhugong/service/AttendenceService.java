package com.zhugong.service;

import com.zhugong.entity.Attendence;
import com.zhugong.entity.Attstatus;

import java.util.List;

public interface AttendenceService {

    List<Attendence> getRealtime(Integer pagenum, Integer pagesize,Integer projectid);

    List<Attendence> getExcel(Integer projectid);

    List<Attendence> getStatistics(Integer pagenum, Integer pagesize, Integer projectid, String name, String year, String month, String days, String state);

    List<Attendence> getMonthView(Integer projectid);

    Integer checkinnormal(String id, String checkin, String checkinremark,Integer projectid);

    Boolean normaltime(Integer projectid, String checkin);

    Boolean latetime(Integer projectid, String checkin);

    Integer checkinlate(String id, String checkin, String checkinremark,Integer projectid);

    Boolean absencein(Integer projectid, String checkin);

    Integer checkinabsence(String id, String checkin, String checkinremark,Integer projectid);

    Integer existin(String id, String checkin);

    Integer existout(String id, String checkout);

    Boolean normalouttime(Integer projectid, String checkout);

    Boolean earlyouttime(Integer projectid, String checkout);

    Boolean normaltimeByout(String id, String checkout);

    Integer checknormal(String id, String checkout, String checkoutremark,Integer projectid);

    Integer checkinnormalearly(String id, String checkout, String checkoutremark,Integer projectid);

    Boolean latetimeByout(String id, String checkout);

    Integer checkinlatenormal(String id, String checkout, String checkoutremark,Integer projectid);

    Integer checkinlateearly(String id, String checkout, String checkoutremark,Integer projectid);

    Integer checkabsence(String id, String checkout, String checkoutremark,Integer projectid);

    Integer existinByout(String id, String checkout);

    List<Attendence> getAttendence(Integer pagenum, Integer pagesize, String id, String year, String month, String days, String state);

    List<Attstatus> getSetting(Integer projectid);

    Boolean timeset(String id, String time);

    Boolean fineset(String id, String fine);
}
