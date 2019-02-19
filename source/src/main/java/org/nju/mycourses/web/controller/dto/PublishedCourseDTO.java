package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName PublishedCourseDTO
 * @PackageName org.nju.mycourses.web.controller
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedCourseDTO {


    @JsonProperty("startAt")
    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @NotNull(message = "填写课程开始时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime startAt;
    @JsonProperty("endAt")
    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @NotNull(message = "填写课程结束时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime endAt;

    @JsonProperty("selectStart")
    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @NotNull(message = "填写选课开始时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime selectStart;
    @JsonProperty("selectStart")
    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @NotNull(message = "填写选课结束时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime selectEnd;

    @JsonProperty("classNum")
    private Integer classNum;
    @JsonProperty("studentEachClass")
    private Integer studentEachClass;
    @JsonProperty("description")
    private String description;
    @JsonProperty("longName")
    private String longName;
}
