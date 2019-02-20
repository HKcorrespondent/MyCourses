package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.nju.mycourses.data.entity.Remark;
import org.nju.mycourses.data.entity.Role;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @ClassName RemarkVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemarkVO {
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

    public RemarkVO(Remark remark) {
        BeanUtils.copyProperties(remark,this);
    }
}
