package com.zhugong.dao;


import com.zhugong.entity.Rights;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限的持久层接口
 */
@Repository
public interface RightsDao {
    /**
     * 查询权限父节点
     */
    @Results(id = "rightsMap",
            value = {
                    @Result(id = true, property = "id", column = "id"),
                    @Result(property = "pid",column = "pid"),
                    @Result(column = "id",
                            property = "children",
                            many = @Many(select = "com.zhugong.dao.RightsDao.listChild",fetchType = FetchType.LAZY)
                    )
            })
    @Select("select * from rights where pid=0")
    List<Rights> getTree();

    /**
     * 查询权限子节点
     */
    @ResultMap("rightsMap")
    @Select("select * from rights where  pid = #{pid}")
    List<Rights> listChild(@Param("pid") Integer pid);

//    /**
//     * 根据角色查询权限父节点
//     */
//    @Results(id = "rightsRoleMap",
//            value = {
//                    @Result(id = true, property = "id", column = "id"),
//                    @Result(property = "pid",column = "pid"),
//                    @Result(column = "{pid=id,roleid=roleid}",
//                            property = "children",
//                            many = @Many(select = "com.zhugong.dao.RightsDao.listChildByRole",fetchType = FetchType.LAZY)
//                    )
//            })
//    @Select("select rights.*,#{roleid} as roleid from roles,role_right,rights where pid=0 AND roles.id = #{roleid} and roles.id = role_right.roleid AND role_right.rightid = rights.id")
//    List<Rights> getTreeByRole(@Param("roleid") long roleid);
//
//
//
//    /**
//     * 根据角色查询权限子节点
//     */
//    @ResultMap("rightsRoleMap")
//    @Select("select rights.* from roles,role_right,rights where  pid = #{pid} AND roles.id=#{roleid} AND roles.id = role_right.roleid AND role_right.rightid = rights.id")
//    List<Rights> listChildByRole(@Param("pid") Integer pid,@Param("roleid") Integer roleid);

    /**
     * 得到list,不取children
     */
    @Select("SELECT id, name, level, pid, path FROM rights order by level")
    List<Rights> getList();


}
