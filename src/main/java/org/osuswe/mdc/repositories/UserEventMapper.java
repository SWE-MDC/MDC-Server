package org.osuswe.mdc.repositories;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.osuswe.mdc.model.UserEvent;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserEventMapper {

    @Insert("INSERT INTO user_event(user_id, event_id, status, comments) VALUES (#{user_id}, #{event_id}, #{status}, #{comments})")
    int addUserEvent(UserEvent ue);

    @Update("UPDATE user_event SET status = #{status}, show_up = #{show_up}, comments = #{comments} WHERE id = #{id}")
    void updateUserEvent(UserEvent ue);

    @Select("SELECT * FROM user_event WHERE user_id = #{user_id} and event_id = #{event_id}")
    Optional<UserEvent> getUserEvent(int user_id, int event_id);

    @Select("SELECT * FROM user_event WHERE event_id = #{event_id}")
    List<UserEvent> getUserEvents(int event_id);

    @Select("SELECT count(id) FROM user_event WHERE user_id = #{user_id} and event_id = #{event_id}")
    int hasUserEvent(int user_id, int event_id);

    @Select("SELECT user_id FROM user_event WHERE event_id = #{event_id}")
    List<Integer> getAttendees(int event_id);


}
