package org.nju.mycourses.data.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private Integer id;
    /**
     * 课程名
     */
    private String name;
//
//    private String description;

    private State state;

    @ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private Teacher teacher;

    @ElementCollection()
    private List<Document> docs;

    @ElementCollection()
    private List<Forum> forums;
}
