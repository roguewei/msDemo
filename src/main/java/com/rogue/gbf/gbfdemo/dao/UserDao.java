package com.rogue.gbf.gbfdemo.dao;

import com.rogue.gbf.gbfdemo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserDao
 * @date 2019/3/1
 * @params
 * @return
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id")int id);

    @Insert("insert into user(id, name)values(#{id},#{name})")
    public int insert(User user);
}
