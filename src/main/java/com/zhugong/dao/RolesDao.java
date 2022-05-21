package com.zhugong.dao;

import com.zhugong.entity.Roles;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色的持久层接口
 */
@Repository
public interface RolesDao {

    /**
     *得到角色list,不取children
     */
    @Select("SELECT id, name, describ FROM roles")
    List<Roles> getList();

    /**
     * 删除角色下的权限
     * @param roleid
     * @param rightid
     * @return
     */
    @Delete("DELETE FROM role_right WHERE roleid=#{roleid} AND rightid=#{rightid}")
    Integer deleteRightsById(@Param("roleid") Integer roleid, @Param("rightid") Integer rightid);

    /**
     * 角色插入权限
     * @param roleid
     * @param rightid
     */
    @Insert("INSERT IGNORE INTO role_right(roleid,rightid) VALUES(#{roleid},#{rightid})")
    void addRight(@Param("roleid") Integer roleid, @Param("rightid") Integer rightid);

    /**
     * 角色是否存在权限
     * @param roleid
     * @param rightid
     * @return
     */
    @Select("SELECT id FROM role_right WHERE roleid=#{roleid} AND rightid=#{rightid}")
    Integer existRoleRight(@Param("roleid") Integer roleid, @Param("rightid") Integer rightid);

    @Select("select rights.path from worker,role_right,rights where worker.roleid=role_right.roleid " +
            "and role_right.rightid=rights.id and worker.name=#{name}")
    List<String> getRightsByName(@Param("name") String name);

    @Select("select roles.* from roles,worker where worker.roleid=roles.id  and worker.name=#{name}")
    Roles getRoleByName(@Param("name") String name);

    @Update("update roles set describ=#{describ} where id=#{id}")
    void editRole(@Param("id") Integer roleid, @Param("describ") String text);
}
