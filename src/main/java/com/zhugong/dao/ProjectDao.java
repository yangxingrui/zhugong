package com.zhugong.dao;

import com.zhugong.entity.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao {

    @Select("select * from project")
    List<Project> getFullCompany();

    @Select("select project.* from project,worker where worker.projectid = project.id and worker.id = #{id}")
    List<Project> getOneCompany(@Param("id") Integer id);

    @Select("select * from project")
    List<Project> findAllCons();

    @Delete("delete from project where id = #{id}")
    Integer deleteProjectById(@Param("id") Integer id);

    @Select("select count(*) from project where companyname=#{companyname}")
    Boolean checkProject(@Param("companyname") String companyname);

    @Insert("insert into project(companyname,address,lng,lat,state) " +
            "values(#{companyname},#{address},#{lng},#{lat},#{state})")
    Boolean addProject(@Param("companyname") String companyname, @Param("address") String address,
                       @Param("lng") String lng, @Param("lat") String lat, @Param("state") String state);

    @Select("select id from project where companyname = #{companyname}")
    Integer selectByName(@Param("companyname") String companyname);

    @Insert("insert into attstatus(name,time,fine,projectid) values(#{name},#{time},#{fine},#{projectid})")
    Boolean addSetting(@Param("name") String name, @Param("time") String time, @Param("fine") Integer fine ,
                       @Param("projectid") Integer projectid);

    @Select("select * from project where id = #{id}")
    Project editProjectInfo(@Param("id") Integer id);

    @Update("update project set companyname=#{companyname},address=#{address},lng=#{lng},lat=#{lat},state=#{state} " +
            "where id=#{id}")
    Boolean updateProject(@Param("companyname") String companyname, @Param("address") String address,
                          @Param("lng") String lng, @Param("lat") String lat, @Param("state") String state,
                          @Param("id") String id);

    @Select("select count(*) from project where companyname=#{companyname} and companyname!=" +
            "(select companyname from project where id=#{id})")
    Boolean checkEditProject(@Param("id") String id, @Param("companyname") String companyname);
}
