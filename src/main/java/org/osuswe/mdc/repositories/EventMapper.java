package org.osuswe.mdc.repositories;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.osuswe.mdc.model.Event;
import org.osuswe.mdc.model.UserEvent;

import java.util.List;

@Mapper
public interface EventMapper {
    @Insert("INSERT INTO event(title, details, date, location, organizer, event_code, group_id) VALUES (#{title}, #{details}, #{date}, #{location}, #{organizer}, #{event_code}, #{group_id})")
    int addEvent(Event event);

    @Select("SELECT id, title, details, date, location, organizer, event_code, group_id from event where id = #{id}")
    Event getEvent(int id);

    @Select("SELECT id, title, details, date, location, organizer, event_code, group_id from event order by create_time desc limit #{limit}")
    List<Event> getEvents(int limit);
}
