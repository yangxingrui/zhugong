package com.zhugong.dao;

import com.zhugong.entity.Wortype;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WortypeDao {

    @Select("select * from wortype where projectid=#{projectid}")
    List<Wortype> selectByPro(@Param("projectid") Integer projectid);
}
