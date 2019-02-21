package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
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
public class HomeworkScoreDTO {
    @JsonProperty(value = "scores")
    HashMap<String,Integer> scores;
    @JsonProperty(value = "open")
    Boolean isOpen;
}
