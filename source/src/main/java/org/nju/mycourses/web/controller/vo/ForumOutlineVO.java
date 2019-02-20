package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.nju.mycourses.data.entity.Forum;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ForumVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForumOutlineVO {
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
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    public ForumOutlineVO(Forum forum) {
        BeanUtils.copyProperties(forum,this);
    }
}
