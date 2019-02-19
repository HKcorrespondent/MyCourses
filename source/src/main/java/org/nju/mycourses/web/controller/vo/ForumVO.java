package org.nju.mycourses.web.controller.vo;

import lombok.Data;
import org.nju.mycourses.data.entity.Forum;

/**
 * @ClassName ForumVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
public class ForumVO {
    private Integer id;
    /**
     * 帖子标题
     */
    private String name;

    public ForumVO(Forum forum) {
        this.id = forum.getId();
        this.name = forum.getName();
    }
}
