package org.nju.mycourses.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.data.entity.Role;
import org.nju.mycourses.data.entity.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "role")
    private Role role;

    @JsonProperty(value = "State")
    private State state;

    private String CERTIFIED_CODE;
}
