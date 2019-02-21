package org.nju.mycourses.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Integer id;

    private String publishedCourseName;
    private Integer publishedCourseId;
    private String teacherName;
    private String teacherUsername;
    private String studentName;
    private String studentUsername;
    private String studentNumber;
    @ManyToOne
    @JoinColumn(name="hid")
    private Homework homework;
    @ManyToOne
    @JoinColumn(name="username")
    private Student student;
    @OneToOne
    @JoinColumn(name="uhid")
    private UpHomework upHomework;
    private Integer score;

    public Score(PublishedCourse publishedCourse,Teacher teacher,Student student,UpHomework upHomework,Homework homework,Integer score) {
        this.publishedCourseName = publishedCourse.getLongName();
        this.publishedCourseId = publishedCourse.getId();
        this.teacherName = teacher.getName();
        this.teacherUsername = teacher.getUsername();
        this.studentName = student.getName();
        this.studentUsername = student.getUsername();
        this.studentNumber = student.getNumber();
        this.homework = homework;
        this.student = student;
        this.upHomework = upHomework;
        this.score = score;
    }
}
