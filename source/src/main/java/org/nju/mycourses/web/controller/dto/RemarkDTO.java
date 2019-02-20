package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.data.entity.Role;

import java.time.LocalDateTime;

/**
 * @ClassName RemarkDTO
 * @PackageName org.nju.mycourses.web.controller.dto
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemarkDTO {
    /**
     * 帖子内容
     */
    @JsonProperty(value = "content")
    private String content;
}
