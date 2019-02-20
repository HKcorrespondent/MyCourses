package org.nju.mycourses.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PublishedCourseDAO
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Entity
@Data
@EqualsAndHashCode(exclude = {"classMap", "students", "Homework"})
@Table(name = "publishedCourse")
public class PublishedCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Integer id;
    /**
     * 课程长名
     */
    private String longName;

    private String description;

    private State state;
    /**
     * 课程开始时间（仅做记录）
     */
    private LocalDateTime startAt;
    /**
     * 课程结束时间（仅做记录）
     */
    private LocalDateTime endAt;
    /**
     * 选课开始时间
     */
    private LocalDateTime selectStart;
    /**
     * 选课结束时间
     */
    private LocalDateTime selectEnd;

    private Integer classNumLimit;
    private Integer studentEachClassLimit;

    private Integer studentTotalNum;

    @ElementCollection()
    private Map<String,Integer> classMap;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "cid")
    private Course course;

    /**
     * 选课学生
     */
    @ManyToMany
    @JoinTable(name="STUDENT_COURSE", joinColumns = @JoinColumn(name = "publishedCourses", referencedColumnName = "pid"),
            inverseJoinColumns = @JoinColumn(name = "student", referencedColumnName = "username"))
    private List<Student> students=new LinkedList<>();

    @ElementCollection()
    private List<Homework> Homework;
}
