package org.nju.mycourses.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
    @Id
    @JsonProperty(value = "username")
    private String username;

    private String name;
    private String number;

    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(name="STUDENT_COURSE", joinColumns = @JoinColumn(name = "student", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "publishedCourses", referencedColumnName = "id"))
    private Set<PublishedCourse> courses = new HashSet<>();
}
