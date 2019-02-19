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
    @Column(name = "id")
    private Integer id;

    private PublishedCourse course;

    private Student student;

    private Integer score;
}
