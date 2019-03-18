package org.nju.mycourses.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName Homework
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Entity
@Data
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hid")
    private Integer id;

    private String name;

    private String description;

    private State state;

    private String type;

    /**
     * 作业可以开始提交时间
     */
    private LocalDateTime startAt;
    /**
     * 作业截止时间
     */
    private LocalDateTime endAt;
    /**
     * 提交的作业
     */
    @ElementCollection()
    private List<UpHomework> upHomework;
    @Column(nullable = true)
    private boolean isOpen=false;
}
