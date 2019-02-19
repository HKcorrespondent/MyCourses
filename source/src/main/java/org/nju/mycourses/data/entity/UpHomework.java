package org.nju.mycourses.data.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @ClassName UpHomework
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Entity
@Data
public class UpHomework {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id",length = 32, nullable = false)
    @ToString.Exclude
    private String id;

    @Basic
    @Column(nullable = false)
    private String path;

    @Basic
    @Column(nullable = false)
    private String name;

    private String type;

    private LocalDateTime upTime;

    private Student uper;

    private Integer score=0;
}
