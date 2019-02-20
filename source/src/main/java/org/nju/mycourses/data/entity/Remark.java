package org.nju.mycourses.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @ClassName Remark
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Entity
@Data
public class Remark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Integer id;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 发布者
     */
    private String username;
    private Role role;
    /**
     * 发布时间
     */
    private LocalDateTime startAt;
    /**
     * 当前楼层数量
     */
    private Integer num;
//    @ManyToOne(targetEntity = Forum.class, fetch = FetchType.LAZY)
//    @JoinColumn(referencedColumnName = "forum_id")
//    private Forum forum;


}
