package org.osuswe.mdc.repositories;

import org.apache.ibatis.annotations.*;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.model.User;

import java.util.Optional;

@Mapper
public interface RoleMapper {
    @Select("SELECT * FROM role WHERE role.name = #{name}")
    Optional<Role> getRoleByName(@Param("name") String name);
}
