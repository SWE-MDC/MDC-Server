package org.osuswe.mdc.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.osuswe.mdc.model.AffinityGroup;

import java.util.Optional;
@Mapper
public interface AffinityGroupMapper {
    @Select("SELECT * FROM affinity_group WHERE id = #{id}")
    Optional<AffinityGroup> getGroupById(@Param("id") int id);
}
