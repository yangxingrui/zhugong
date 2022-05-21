package com.zhugong.dao;

import com.zhugong.entity.Worker;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerDao {

    @Select("<script>"+
            "SELECT DISTINCT(worker.id),worker.name,age,phonenum,worker.face,worker.state,worktype,roles.name as rolename,companyname FROM ((worker LEFT JOIN wortype ON " +
            "worker.worktypeid = wortype.id) LEFT JOIN roles ON worker.roleid=roles.id) LEFT JOIN project ON worker.projectid = project.id " +
            "<if test='query != null and query != \"\"'> WHERE worker.name LIKE CONCAT('%',#{query},'%') " +
            "OR age LIKE CONCAT('%',#{query},'%') OR phonenum LIKE CONCAT('%',#{query},'%') " +
            "OR worktype LIKE CONCAT('%',#{query},'%') OR roles.name LIKE CONCAT('%',#{query},'%') OR project.companyname LIKE CONCAT('%',#{query},'%')</if>"+"ORDER BY worker.id</script>")
    List<Worker> findAll(@Param("query") String query);

    @Select("<script>"+
            "SELECT DISTINCT(worker.id),worker.name,age,phonenum,worker.face,worker.state,worktype,roles.name as rolename,companyname FROM ((worker LEFT JOIN wortype ON " +
            "worker.worktypeid = wortype.id) LEFT JOIN roles ON worker.roleid=roles.id) LEFT JOIN project ON worker.projectid = project.id where worker.projectid=#{projectid}" +
            "<if test='query != null and query != \"\"'> and (worker.name LIKE CONCAT('%',#{query},'%') " +
            "OR age LIKE CONCAT('%',#{query},'%') OR phonenum LIKE CONCAT('%',#{query},'%') " +
            "OR worktype LIKE CONCAT('%',#{query},'%') OR roles.name LIKE CONCAT('%',#{query},'%') ) </if>"+"ORDER BY worker.id</script>")
    List<Worker> findProjectAll(@Param("projectid") Integer projectid, @Param("query") String query);

    @Insert("INSERT INTO worker(name,password,age,phonenum) VALUES(#{name},#{password},#{age},#{phonenum})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(Worker worker);

    @Select("SELECT * FROM worker WHERE name=#{name} and password=#{password}")
    String findByNamePassword(@Param("name") String name,@Param("password") String password);

    @Update("UPDATE worker SET state=#{state} WHERE id=#{id}")
    Integer changeStateById(@Param("id") Integer id, @Param("state") Boolean state);

    @Select("SELECT worker.*,roles.name as rolename,worker.worktypeid,wortype.worktype,wortype.salary,project.companyname FROM worker,roles,wortype," +
            "project WHERE worker.roleid=roles.id and worker.worktypeid=wortype.id and worker.projectid = project.id and " +
            "worker.id=#{id}")
    Worker selectWorkerById(Integer id);

    @Update("UPDATE worker SET name=#{name},age=#{age},phonenum=#{phonenum},projectid=#{projectid},worktypeid=#{worktypeid} WHERE id=#{id}")
    Integer updateWorkerById(@Param("id") Integer id, @Param("name") String name, @Param("age") String age,
                             @Param("phonenum") String phonenum, @Param("projectid") String projectid,
                             @Param("worktypeid") String worktype);

    @Update("UPDATE worker SET name=#{name},age=#{age},phonenum=#{phonenum},projectid=#{projectid} WHERE id=#{id}")
    Integer updateWorkerNoTypeById(@Param("id") Integer id, @Param("name") String name, @Param("age") String age,
                             @Param("phonenum") String phonenum, @Param("projectid") String projectid);

    @Insert("INSERT INTO worker(name,password,age,phonenum,state) VALUES(#{name},#{password},#{age},#{phonenum},1)")
    Integer addWorker(@Param("name") String name, @Param("password") String password,
                      @Param("age") String age, @Param("phonenum") String phonenum);

    @Insert("INSERT INTO worker(name,password,age,phonenum,state,projectid) VALUES(#{name},#{password},#{age},#{phonenum},1,#{projectid})")
    Integer addProjectWorker(@Param("name") String name, @Param("password") String password,
                             @Param("age") String age, @Param("phonenum") String phonenum,
                             @Param("projectid") String projectid);

    @Delete("DELETE FROM worker WHERE id=#{id}")
    Integer deleteWorkerById(@Param("id") Integer id);

    @Update("UPDATE worker SET roleid=#{rid} WHERE id=#{id}")
    Integer changeRoleById(@Param("id") Integer id, @Param("rid") String rid);

    @Select("SELECT * FROM worker WHERE name=#{name}")
    Worker selectWorkerByName(@Param("name") String name);

    @Select("select face from worker where id=#{id}")
    String findFaceById(@Param("id") String id);

    @Update("update worker set phonenum=#{phonenum} where id=#{id}")
    Boolean changePhonenum(@Param("id") String id, @Param("phonenum") String phonenum);

    @Update("update worker set age=#{age} where id=#{id}")
    Boolean changeBasic(@Param("id") String id, @Param("age") String age);

    @Update("update worker set password=#{pass} where id=#{id}")
    Boolean changePass(@Param("id") String id, @Param("pass") String pass);

    @Select("select * from worker where name=#{username}")
    Worker findByName(@Param("username") String username);

    @Select("select * from worker where id=#{id}")
    Worker selectUserById(@Param("id") Integer id);

    @Select("select count(*) from worker where name=#{name}")
    Integer checkUserName(@Param("name") String userName);

    @Select("select count(*) from worker where name=#{name} and name!=(select name from worker where id=#{id})")
    Integer checkEditUserName(@Param("id") Integer id, @Param("name") String userName);

    @Update("update worker set face=#{face} where id=#{id}")
    Integer updateFaceById(@Param("id") String id, @Param("face") String imgFilePath);

    @Select("select worker.*,roles.name as rolename,worker.worktypeid,project.companyname from worker,roles," +
            "project where worker.roleid=roles.id and worker.projectid = project.id and " +
            "worker.id=#{id}")
    Worker selectWorkerByIdAssignedProject(Integer id);

    @Select("select worker.*,roles.name as rolename,worker.worktypeid from worker,roles" +
            " where worker.roleid=roles.id and " +
            "worker.id=#{id}")
    Worker selectWorkerByIdAssignedRole(Integer id);



}
