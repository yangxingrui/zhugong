package com.zhugong.dao;

import com.zhugong.entity.Attendence;
import com.zhugong.entity.Attstatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendenceDao {

    @Select("select worker.name,attendence.*,attstatus.name as status from (attendence left join worker " +
            "on attendence.workerid = worker.id) left join attstatus on attstatus.id  = attendence.state " +
            "where worker.projectid=#{projectid} and date_format(checkin,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')" +
            " order by attendence.id desc")
    List<Attendence> getRealtime(@Param("projectid") Integer projectid);

    @Select("<script> select worker.name,attendence.*,attstatus.name as status from (attendence left join worker " +
            "on attendence.workerid = worker.id) left join attstatus on attstatus.id = attendence.state " +
            "where 1=1 and worker.projectid=#{projectid} <if test='name != null and name != \"\"'> and worker.name LIKE CONCAT('%',#{name},'%') </if> " +
            "<if test='year != null and year != \"\"'> and date_format(checkin,'%Y')=#{year}</if> " +
            "<if test='month != null and month != \"\"'> and date_format(checkin,'%Y-%m')=#{month}</if> " +
            "<if test='days != null and days != \"\"'> and date_format(checkin,'%Y-%m-%d')=#{days}</if> " +
            "<if test='state != null and state != \"\"'> and #{state}=attstatus.name </if>" +
            "order by attendence.id desc </script>")
    List<Attendence> getStatistics(@Param("projectid") Integer projectid, @Param("name") String name, @Param("year") String year, @Param("month") String month,
                                   @Param("days") String days, @Param("state") String state);

    @Select("select worker.name,attendence.*,attstatus.name as status from (attendence left join worker on attendence.workerid = worker.id) left join attstatus on attstatus.id  = attendence.state " +
            "where worker.projectid=#{projectid} and attendence.state != #{stateid}")
    List<Attendence> getMonthView(@Param("projectid") Integer projectid,@Param("stateid") Integer stateid);

    @Insert("insert into attendence(workerid,checkin,checkinremark,state) VALUES(#{id},#{checkin},#{checkinremark},#{stateid})")
    Integer checkin(@Param("id") String id, @Param("checkin") String checkin, @Param("checkinremark") String checkinremark,@Param("stateid") Integer stateid);

    @Select("select * from attstatus where #{checkin} < time and name='正常签到' and projectid=#{projectid}")
    Boolean normaltime(@Param("projectid") Integer projectid, @Param("checkin") String checkin);

    @Select("select * from attstatus where exists(select * from attstatus where #{checkin} <time and name='迟到' " +
            "and projectid=#{projectid}) and #{checkin} > time and name='正常签到' and projectid=#{projectid}")
    Boolean latetime(@Param("projectid") Integer projectid, @Param("checkin") String checkin);

    @Select("select * from attstatus where #{checkin} > time and name='迟到' and projectid=#{projectid}")
    Boolean absencein(@Param("projectid") Integer projectid, @Param("checkin") String checkin);

    @Select("select count(*) from attendence where workerid = #{workerid}  and date(checkin)=date(#{checkin})")
    Integer existin(@Param("workerid") String id, @Param("checkin") String checkin);

    @Select("select count(*) from attendence where workerid = #{workerid}  and date(checkout)=date(#{checkout})")
    Integer existout(@Param("workerid") String id, @Param("checkout") String checkout);

    @Select("select * from attstatus where #{checkout} > time and name='正常签退' and projectid=#{projectid}")
    Boolean normalouttime(@Param("projectid") Integer projectid,@Param("checkout") String checkout);

    @Select("select * from attstatus where exists(select * from attstatus where #{checkout} <time and name='正常签退' " +
            "and projectid=#{projectid}) and #{checkout} > time and name='早退' and projectid=#{projectid}")
    Boolean earlyouttime(@Param("projectid") Integer projectid,@Param("checkout") String checkout);

    @Select("select * from attendence,worker,attstatus where attendence.workerid=worker.id and worker.projectid=attstatus.projectid and " +
            "workerid=#{workerid} and date(checkin)=date(#{checkout}) and attendence.state=attstatus.id and attstatus.name = '正常签到'")
    Boolean normaltimeByout(@Param("workerid") String id, @Param("checkout") String checkout);

    @Update("update attendence set checkout = #{checkout} , checkoutremark = #{checkoutremark}, state = #{stateid} where workerid=#{workerid} and date(checkin)=date(#{checkout})")
    Integer checkout(@Param("workerid") String id, @Param("checkout") String checkout, @Param("checkoutremark") String checkoutremark,@Param("stateid") Integer stateid);

    @Select("select * from attendence,worker,attstatus where attendence.workerid=worker.id and worker.projectid=attstatus.projectid and " +
            "workerid=#{workerid} and date(checkin)=date(#{checkout}) and attendence.state=attstatus.id and attstatus.name = '迟到'")
    Boolean latetimeByout(@Param("workerid") String id, @Param("checkout") String checkout);

    @Select("select count(*) from attendence where workerid = #{workerid} and date(checkin)=date(#{checkout})")
    Integer existinByout(@Param("workerid") String id, @Param("checkout") String checkout);

    @Select("select id from attstatus where projectid=#{projectid} and name=#{state}")
    Integer selectState(@Param("projectid") Integer projectid, @Param("state") String state);

    @Select("<script> select worker.name,attendence.*,attstatus.name as status from (attendence left join worker on attendence.workerid = worker.id) left join attstatus on attstatus.id = attendence.state " +
            "where worker.id=#{id} " +
            "<if test='year != null and year != \"\"'> and date_format(checkin,'%Y')=#{year}</if> " +
            "<if test='month != null and month != \"\"'> and date_format(checkin,'%Y-%m')=#{month}</if> " +
            "<if test='days != null and days != \"\"'> and date_format(checkin,'%Y-%m-%d')=#{days}</if> " +
            "<if test='state != null and state != \"\"'> and #{state}=attstatus.name </if>" +
            "order by attendence.id desc </script>")
    List<Attendence> getAttendence(@Param("id") String id, @Param("year") String year, @Param("month") String month, @Param("days") String days, @Param("state") String state);

    @Select("select count(*) from attendence where workerid=#{id} and date_format(checkin,'%Y-%m')=#{month}")
    int getAttDays(@Param("id") Integer id, @Param("month") String month);

    @Select("select * from attstatus where projectid=#{projectid}")
    List<Attstatus> getSetting(@Param("projectid") Integer projectid);

    @Update("update attstatus set time=#{time} where id=#{id}")
    Boolean timeset(@Param("id") String id, @Param("time") String time);

    @Update("update attstatus set fine=#{fine} where id=#{id}")
    Boolean fineset(@Param("id") String id, @Param("fine") String fine);
}
