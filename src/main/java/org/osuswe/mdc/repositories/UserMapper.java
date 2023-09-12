package org.osuswe.mdc.repositories;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.osuswe.mdc.model.User;

import java.util.Optional;

@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM users WHERE id = #{id}")
//    User getUser(@Param("id") int id);

    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<User> getUser(@Param("username") String username);

    @Insert("INSERT INTO users(username, password) VALUES (#{username}, #{password})")
    public int addUser(User user);
}
