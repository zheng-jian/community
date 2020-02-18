package com.uga.zj.community.mapper;


import com.uga.zj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("Insert into user (name,account_id,token,gmt_create,gmt_modified,picture_url)" +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{pictureUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token")String token);

    @Select("select * from user where id = #{id} ")
    User findById(@Param("id") Integer id);
}