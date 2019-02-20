package org.nju.mycourses.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Teacher {
    @Id
    @Column(name = "username")
    private String username;

    private String name;

    @OneToOne
    private User user;
}
