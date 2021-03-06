package org.nju.mycourses.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName Forum
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Entity
@Data
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fid")
    private Integer id;
    /**
     * 帖子数
     */
    private Integer number;
    /**
     * 帖子标题
     */
    private String name;
    /**
     * 发布者
     */
    private String username;
    /**
     * 发布时间
     */
    private LocalDateTime startAt;

    @ElementCollection()
    private List<Remark> remarks;
}
