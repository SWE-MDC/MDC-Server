package org.osuswe.mdc.repositories;

import org.apache.ibatis.annotations.*;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.model.User;

import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE email = #{email}")
    Optional<User> getUserByEmail(@Param("email") String email);
    @Select("SELECT * FROM user WHERE username = #{username}")
    Optional<User> getUserByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE id = #{id}")
    Optional<User> getUserById(@Param("id") int id);

    @Select("SELECT * FROM user WHERE email = #{usernameOrEmail} or username = #{usernameOrEmail}")
    Optional<User> getUserByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    @Insert("INSERT INTO user(email, username, password, role_id) VALUES (#{email}, #{username}, #{password}, #{role_id})")
    int addUser(User user);

    @Select("SELECT role.id, role.name FROM role, user WHERE user.username = #{username} and role.id = user.role_id")
    Optional<Role> getRoleByUsername(@Param("username") String username);

    @Update("UPDATE user SET first_name = #{user.first_name}, last_name = #{user.last_name}, email = #{user.email}, username = #{user.username}, password = #{user.password}, pronouns = #{user.pronouns}, year = #{user.year}, major = #{user.major}, phone = #{user.phone}, role_id = #{user.role_id}, campus_id = #{user.campus_id}, expired = #{user.expired}, locked = #{user.locked} WHERE id = #{user.id}")
    int updateUser(@Param("user") User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int updatePassword(int id, String password);

    @Update("UPDATE user SET role_id = #{role_id} WHERE id = #{id}")
    int updateRole(int id, int role_id);
}
