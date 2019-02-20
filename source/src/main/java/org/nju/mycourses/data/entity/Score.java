package org.nju.mycourses.data.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Score
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="published_course_id")
    private PublishedCourse publishedCourse;
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    private Integer score;
}
