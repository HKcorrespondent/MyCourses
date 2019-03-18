package org.nju.mycourses.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "username")
    private String username;

    private String name;
    private String number;

    @OneToOne
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="STUDENT_COURSE", joinColumns = @JoinColumn(name = "student", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "publishedCourses", referencedColumnName = "pid"))
    private Set<PublishedCourse> courses = new HashSet<>();
    @ElementCollection()
    private Map<Integer,String> PCClass = new HashMap<>();
}
