package org.osuswe.mdc.model;

import lombok.Data;

@Data
public class UserEvent {
    int id;
    int user_id;
    int event_id;
    int status; // 0 undecided, 1, accept, 2 reject;
    int show_up;
    String comments;
}
