package com.zhugong.dao;

import com.zhugong.entity.Account;
import com.zhugong.entity.Project;
import com.zhugong.entity.Salary;
import com.zhugong.entity.Wortype;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryDao {

    @Select("<script> select salary.*,worker.name from salary,worker " +
            "where worker.id=salary.workerid and worker.projectid=#{projectid}" +
            "<if test='month != null and month != \"\"'> and date_format(month,'%Y-%m')=#{month}</if> " +
            "<if test='accstate != null and accstate != \"\"'> and #{accstate}=salary.accstate </if>" +
            "order by salary.id desc </script>")
    List<Salary> getSalary(@Param("projectid") Integer projectid, @Param("month") String month, @Param("accstate") String state);

    @Select("select salary.*,worker.name from salary,worker where worker.id=salary.workerid " +
            "and worker.projectid=#{projectid} and date_format(month,'%Y-%m')=#{month} and salary.accstate='未核算'")
    List<Salary> startAccount(@Param("projectid") Integer projectid, @Param("month") String month);

    @Insert("insert ignore into salary(workerid,month,normalearly,latenormal,lateearly,absence,accstate,submit,audit,signfor) " +
            "select distinct(worker.id),date_format(last_day(#{month}),'%Y-%m-%d %h:%m:%s')," +
            "0,0,0,0,'未核算','未提交','未审批','未签收' from worker left join attendence on worker.id=attendence.workerid " +
            "where worker.projectid=#{projectid} and date_format(checkin,'%Y-%m')=date_format(#{month},'%Y-%m')")
    Boolean getSalaryMonth(@Param("projectid") Integer projectid, @Param("month") String month);

    @Select("select worker.id,attstatus.id as state,count(*) as time from attendence,worker,attstatus " +
            "where worker.id=attendence.workerid and attendence.state=attstatus.id and worker.projectid=#{projectid} and " +
            "date_format(checkin,'%Y-%m')=date_format(#{month},'%Y-%m') group by worker.id,attendence.state")
    List<Account> statusMonth(@Param("projectid") Integer projectid, @Param("month") String month);

    @Select("select salary.*,worker.name from salary,worker where worker.id=salary.workerid " +
            "and worker.projectid=#{projectid} and date_format(month,'%Y-%m')=#{month} and salary.accstate='已核算'")
    List<Salary> getAccount(@Param("projectid") Integer projectid, @Param("month") String month);


    @Select("select wortype.salary*#{days} from worker,wortype where worker.worktypeid=wortype.id and worker.id=#{id}")
    Double payable(@Param("id") Integer id, @Param("days") int days);

    @Select("select fine from attstatus where attstatus.id=#{state}")
    Double fine(@Param("state") Integer state);

    @Select("select name from attstatus where id=#{state}")
    String getStateById(@Param("state") Integer state);

    @Update("update salary set normalearly=#{fine} where workerid=#{id} and date_format(month,'%Y-%m')=#{month}")
    Boolean setNormalEarly(@Param("id") Integer id, @Param("month") String month, @Param("fine") Double fine);

    @Update("update salary set latenormal=#{fine} where workerid=#{id} and date_format(month,'%Y-%m')=#{month}")
    Boolean setLateNormal(@Param("id") Integer id, @Param("month") String month, @Param("fine") Double fine);

    @Update("update salary set lateearly=#{fine} where workerid=#{id} and date_format(month,'%Y-%m')=#{month}")
    Boolean setLateEarly(@Param("id") Integer id, @Param("month") String month, @Param("fine") Double fine);

    @Update("update salary set absence=#{fine} where workerid=#{id} and date_format(month,'%Y-%m')=#{month}")
    Boolean setAbsence(@Param("id") Integer id, @Param("month") String month, @Param("fine") Double fine);

    @Update("update salary set payable=#{payable} where workerid=#{id} and date_format(month,'%Y-%m')=#{month}")
    Boolean setPayable(@Param("id") Integer id, @Param("month") String month, @Param("payable") Double payable);

    @Update("update salary set realpay=payable-normalearly-latenormal-lateearly-absence where workerid=#{id} " +
            "and date_format(month,'%Y-%m')=#{month}")
    Boolean setRealpay(@Param("id") Integer id, @Param("month") String month);

    @Update("update salary set accstate='已核算' where workerid=#{id} and date_format(month,'%Y-%m')=#{month}")
    Boolean setAccounted(@Param("id") Integer id, @Param("month") String month);

    @Update("update salary set submit='已提交' where workerid=#{id} and date_format(month,'%Y-%m')=#{month}")
    Boolean submit(@Param("id") Integer id, @Param("month") String month);

    @Select("select salary.*,worker.name from salary,worker where worker.id=salary.workerid " +
            "and worker.projectid=#{projectid} and date_format(month,'%Y-%m')=#{month} and salary.submit='已提交'")
    List<Salary> getSubmit(@Param("projectid") Integer projectid, @Param("month") String month);

    @Select("select distinct(worker.id) from worker,attendence where worker.id=attendence.workerid and " +
            "worker.projectid=#{projectid} and date_format(checkin,'%Y-%m')=#{month}")
    List<Integer> getShouldSubmit(@Param("projectid") Integer projectid, @Param("month") String month);

    @Select("<script> select salary.*,worker.name from salary,worker " +
            "where worker.id=salary.workerid and worker.projectid=#{projectid}  " +
            "<if test='name != null and name != \"\"'> and worker.name LIKE CONCAT('%',#{name},'%')</if> " +
            "<if test='year != null and year != \"\"'> and date_format(month,'%Y')=#{year}</if> " +
            "<if test='month != null and month != \"\"'> and date_format(month,'%Y-%m')=#{month}</if> " +
            "<if test='state != null and state != \"\"'> and (#{state}=salary.accstate or #{state}=salary.submit or #{state}=salary.audit) </if>" +
            "order by salary.id desc </script>")
    List<Salary> Summary(@Param("projectid") Integer projectid, @Param("name") String name,
                         @Param("year") String year, @Param("month") String month, @Param("state") String state);

    @Select("<script> select salary.*,worker.name from salary,worker " +
            "where worker.id=salary.workerid and worker.id=#{id} and salary.audit='已审批' " +
            "<if test='year != null and year != \"\"'> and date_format(month,'%Y')=#{year}</if> " +
            "<if test='month != null and month != \"\"'> and date_format(month,'%Y-%m')=#{month}</if> " +
            "<if test='state != null and state != \"\"'> and #{state}=salary.audit </if>" +
            "order by salary.id desc </script>")
    List<Salary> getMySalary(@Param("id") String id, @Param("year") String year, @Param("month") String month, @Param("state") String state);

    @Update("update salary set signfor='已签收' where id=#{id}")
    Integer signfor(@Param("id") Integer id);

    @Select("<script> select salary.*,worker.name from salary,worker " +
            "where worker.id=salary.workerid and salary.submit='已提交' " +
            "<if test='year != null and year != \"\"'> and date_format(month,'%Y')=#{year}</if> " +
            "<if test='month != null and month != \"\"'> and date_format(month,'%Y-%m')=#{month}</if> " +
            "<if test='state != null and state != \"\"'> and #{state}=salary.audit </if>" +
            "<if test='projectid != null and projectid != \"\"'> and worker.projectid=#{projectid}</if> " +
            "order by salary.id desc </script>")
    List<Salary> getSuperSalary(@Param("year") String year, @Param("month") String month,
                                @Param("state") String state, @Param("projectid") Integer projectid);

    @Update("update salary set audit='已审批' where id=#{id}")
    Integer auditone(@Param("id") Integer id);

    @Update("update salary set audit='已审批' where workerid in (select id from worker where worker.projectid=#{projectid}) " +
            "and date_format(month,'%Y-%m')=#{month} and submit='已提交'")
    Integer auditall(@Param("month") String audmonth, @Param("projectid") String audprojectid);

    @Select("select * from wortype where projectid=#{projectid}")
    List<Wortype> getSetting(@Param("projectid") Integer projectid);

    @Update("update wortype set salary=#{salary} where id=#{id}")
    Boolean salaryset(@Param("id") String id, @Param("salary") String salary);

    @Insert("insert into wortype(worktype,salary,projectid) values(#{worktype},#{salary},#{projectid})")
    Boolean addWorktype(@Param("worktype") String worktype, @Param("salary") String salary,@Param("projectid") String projectid);

    @Select("select * from project where id != 1")
    List<Project> getMap();

    @Select("select count(*) from salary,worker where salary.workerid=worker.id and worker.projectid=#{projectid} " +
            "and date_format(month,'%Y-%m')=#{month}")
    Integer startAccounted(@Param("projectid") Integer projectid, @Param("month") String month);

    @Select("select count(*) from salary,worker where salary.workerid=worker.id and worker.projectid=#{projectid} " +
            "and date_format(month,'%Y-%m')=#{month} and accstate='已核算'")
    Integer Accounted(@Param("projectid") Integer projectid, @Param("month") String month);


    @Delete("delete from wortype where id=#{id}")
    Integer deleteWorkTypeById(@Param("id") Integer id);

    @Select("select count(*) from wortype where worktype=#{worktype} and projectid=#{projectid}")
    Boolean checkWorkType(@Param("worktype") String worktype, @Param("projectid") String projectid);
}
