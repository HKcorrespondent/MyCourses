package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.web.controller.vo.DocumentVO;

import java.util.List;

/**
 * @ClassName DocDTO
 * @PackageName org.nju.mycourses.web
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class DocDTO {
    @JsonProperty(value = "docs")
    List<DocumentVO> docs;
}
