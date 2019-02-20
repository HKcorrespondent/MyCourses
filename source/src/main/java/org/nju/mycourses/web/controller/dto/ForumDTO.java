package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName ForumDTO
 * @PackageName org.nju.mycourses.web.controller.dto
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForumDTO {
    /**
     * 帖子标题
     */
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "content")
    private String content;
}
