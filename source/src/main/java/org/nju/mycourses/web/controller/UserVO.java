package org.nju.mycourses.web.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.data.Role;
import org.nju.mycourses.data.State;
import org.nju.mycourses.data.User;
import org.springframework.beans.BeanUtils;

import java.util.Optional;
@Data
public class UserVO {
    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "role")
    private Role role;

    @JsonProperty(value = "State")
    private State state;

    private String CERTIFIED_CODE;
    public UserVO(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
